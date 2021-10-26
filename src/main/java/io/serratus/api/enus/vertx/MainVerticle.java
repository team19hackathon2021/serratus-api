package io.serratus.api.enus.vertx;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import io.serratus.api.enus.config.ConfigKeys;
import io.serratus.api.enus.model.biosample.BioSampleEnUSGenApiService;
import io.serratus.api.enus.model.run.SequenceRunEnUSGenApiService;
import io.serratus.api.enus.model.taxonomy.TaxonomyEnUSGenApiService;
import io.serratus.api.enus.user.SiteUserEnUSGenApiService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.OAuth2Options;
import io.vertx.ext.auth.oauth2.authorization.KeycloakAuthorization;
import io.vertx.ext.auth.oauth2.providers.OpenIDConnectAuth;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

/**
 **/

public class MainVerticle extends MainVerticleGen<AbstractVerticle> {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	private Integer siteInstances;
	private Integer workerPoolSize;
	private PgPool pgPool;
	
	public PgPool getPgPool() {
		return pgPool;
	}
	public void setPgPool(PgPool pgPool) {
		this.pgPool = pgPool;
	}
	
	private WebClient webClient;
	private Router router;
	WorkerExecutor workerExecutor;
	OAuth2Auth oauth2AuthenticationProvider;
	AuthorizationProvider authorizationProvider;
	HandlebarsTemplateEngine templateEngine;
	
	public void start(Promise<Void> startPromise) {
		try {
			Future<Void> promiseSteps = configureWebClient().compose(a ->
				configureOpenApi().compose(d -> 
					configureHealthChecks().compose(e -> 
						configureSharedWorkerExecutor().compose(f -> 
							configureWebsockets().compose(g -> 
								configureEmail().compose(h -> 
									configureApi().compose(i -> 
										configureUi().compose(j -> 
											startServer()
										)
									)
								)
							)
						)
					)
				)
			);
			promiseSteps.onComplete(startPromise);
		} catch (Exception ex) {
			LOG.error("Couldn't start verticle. ", ex);
		}
	}
	
	private Future<Void> configureWebClient() {
		Promise<Void> promise = Promise.promise();

		try {
			webClient = WebClient.create(vertx);
			promise.complete();
		} catch(Exception ex) {
			LOG.error("Unable to configure site context. ", ex);
			promise.fail(ex);
		}

		return promise.future();
	}
	
	private static Future<PgPool> configureData(Vertx vertx, JsonObject config) {
		Promise<PgPool> promise = Promise.promise();
		try {
			PgConnectOptions pgOptions = new PgConnectOptions();
			pgOptions.setPort(config.getInteger(ConfigKeys.JDBC_PORT));
			pgOptions.setHost(config.getString(ConfigKeys.JDBC_HOST));
			pgOptions.setDatabase(config.getString(ConfigKeys.JDBC_DATABASE));
			pgOptions.setUser(config.getString(ConfigKeys.JDBC_USERNAME));
			pgOptions.setPassword(config.getString(ConfigKeys.JDBC_PASSWORD));
			Optional.ofNullable(config.getInteger(ConfigKeys.JDBC_MAX_IDLE_TIME)).ifPresent(idleTimeout -> pgOptions.setIdleTimeout(idleTimeout));
			pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
			Optional.ofNullable(config.getInteger(ConfigKeys.JDBC_CONNECT_TIMEOUT)).ifPresent(connectTimeout -> pgOptions.setConnectTimeout(connectTimeout));

			PoolOptions poolOptions = new PoolOptions();
			Integer jdbcMaxPoolSize = config.getInteger(ConfigKeys.JDBC_MAX_POOL_SIZE);
			Integer jdbcMaxWaitQueueSize = config.getInteger(ConfigKeys.JDBC_MAX_WAIT_QUEUE_SIZE);
			poolOptions.setMaxSize(jdbcMaxPoolSize);
			poolOptions.setMaxWaitQueueSize(jdbcMaxWaitQueueSize);

			PgPool pgPool = PgPool.pool(vertx, pgOptions, poolOptions);

			LOG.info(configureDataInitSuccess);
			promise.complete(pgPool);
		} catch (Exception ex) {
			LOG.error(configureDataInitError, ex);
			promise.fail(ex);
		}

		return promise.future();
	}
	
	private Future<Void> configureOpenApi() {
		Promise<Void> promise = Promise.promise();
		try {
			String siteBaseUrl = config().getString(ConfigKeys.SITE_BASE_URL);

			OAuth2Options oauth2ClientOptions = new OAuth2Options();
			Boolean authSsl = config().getBoolean(ConfigKeys.AUTH_SSL);
			String authHostName = config().getString(ConfigKeys.AUTH_HOST_NAME);
			Integer authPort = config().getInteger(ConfigKeys.AUTH_PORT);
			String authUrl = String.format("%s://%s%s/auth", (authSsl ? "https" : "http"), authHostName, (authPort == 443 || authPort == 80 ? "" : ":" + authPort));
			oauth2ClientOptions.setSite(authUrl + "/realms/" + config().getString(ConfigKeys.AUTH_REALM));
			oauth2ClientOptions.setTenant(config().getString(ConfigKeys.AUTH_REALM));
			oauth2ClientOptions.setClientID(config().getString(ConfigKeys.AUTH_RESOURCE));
			oauth2ClientOptions.setClientSecret(config().getString(ConfigKeys.AUTH_SECRET));
			oauth2ClientOptions.setFlow(OAuth2FlowType.AUTH_CODE);
			JsonObject extraParams = new JsonObject();
			extraParams.put("scope", "DefaultAuthScope");
			oauth2ClientOptions.setExtraParameters(extraParams);

			OpenIDConnectAuth.discover(vertx, oauth2ClientOptions, a -> {
				if(a.succeeded()) {
					oauth2AuthenticationProvider = a.result();

					authorizationProvider = KeycloakAuthorization.create();

					OAuth2AuthHandler oauth2AuthHandler = OAuth2AuthHandler.create(vertx, oauth2AuthenticationProvider, siteBaseUrl + "/callback");
					{
						Router tempRouter = Router.router(vertx);
						oauth2AuthHandler.setupCallback(tempRouter.get("/callback"));
					}
			
					//ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx);
					LocalSessionStore sessionStore = LocalSessionStore.create(vertx, "serratus-api-sessions");
					SessionHandler sessionHandler = SessionHandler.create(sessionStore);
					if(StringUtils.startsWith(siteBaseUrl, "https://"))
						sessionHandler.setCookieSecureFlag(true);
			
					RouterBuilder.create(vertx, "webroot/openapi3-enUS.yaml", b -> {
						if (b.succeeded()) {
							RouterBuilder routerBuilder = b.result();
							routerBuilder.mountServicesFromExtensions();
			
							routerBuilder.serviceExtraPayloadMapper(routingContext -> new JsonObject()
									.put("uri", routingContext.request().uri())
									.put("method", routingContext.request().method().name())
									);
							routerBuilder.rootHandler(sessionHandler);
							routerBuilder.securityHandler("openIdConnect", oauth2AuthHandler);
							routerBuilder.operation("callback").handler(ctx -> {
			
								// Handle the callback of the flow
								final String code = ctx.request().getParam("code");
			
								// code is a require value
								if (code == null) {
									ctx.fail(400);
									return;
								}
			
								final String state = ctx.request().getParam("state");
			
								final JsonObject config = new JsonObject().put("code", code);
			
								config.put("redirect_uri", siteBaseUrl + "/callback");
			
								oauth2AuthenticationProvider.authenticate(config, res -> {
									if (res.failed()) {
										LOG.error("Failed to authenticate user. ", res.cause());
										ctx.fail(res.cause());
									} else {
										ctx.setUser(res.result());
										Session session = ctx.session();
										if (session != null) {
											// the user has upgraded from unauthenticated to authenticated
											// session should be upgraded as recommended by owasp
											Cookie cookie = Cookie.cookie("sessionIdBefore", session.id());
											if(StringUtils.startsWith(siteBaseUrl, "https://"))
												cookie.setSecure(true);
											ctx.addCookie(cookie);
											session.regenerateId();
											String redirectUri = session.get("redirect_uri");
											// we should redirect the UA so this link becomes invalid
											ctx.response()
													// disable all caching
													.putHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
													.putHeader("Pragma", "no-cache")
													.putHeader(HttpHeaders.EXPIRES, "0")
													// redirect (when there is no state, redirect to home
													.putHeader(HttpHeaders.LOCATION, redirectUri != null ? redirectUri : "/")
													.setStatusCode(302)
													.end("Redirecting to " + (redirectUri != null ? redirectUri : "/") + ".");
										} else {
											// there is no session object so we cannot keep state
											ctx.reroute(state != null ? state : "/");
										}
									}
								});
							});
							routerBuilder.operation("callback").failureHandler(c -> {});
			
							routerBuilder.operation("logout").handler(rc -> {
								String redirectUri = rc.request().params().get("redirect_uri");
								if(redirectUri == null)
									redirectUri = "/";
								rc.clearUser();
								rc.response()
										.putHeader(HttpHeaders.LOCATION, redirectUri)
										.setStatusCode(302)
										.end("Redirecting to " + redirectUri + ".");
							});
							routerBuilder.operation("logout").handler(c -> {});

							routerBuilder.operation("csvSequenceRunImport").handler(rc -> {
								rc.fileUploads();
								JsonObject response = new JsonObject();
								rc.response().setStatusCode(200).setStatusMessage("OK").end(response.toBuffer());
							});
			
							router = routerBuilder.createRouter();
			
							LOG.info(configureOpenApiSuccess);
							promise.complete();
						} else {
							LOG.error(configureOpenApiError, b.cause());
							promise.fail(b.cause());
						}
					});
				} else {
					LOG.error(configureOpenApiError, a.cause());
					promise.fail(a.cause());
				}
			});
		} catch (Exception ex) {
			LOG.error(configureOpenApiError, ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	public Future<Void> csvSequenceRunImport(Set<FileUpload> fileUploads) {
		Promise<Void> promise = Promise.promise();
		
		for(FileUpload fileUpload : fileUploads) {
			String uploadedFileName = fileUpload.uploadedFileName();
			vertx.fileSystem().readFile(uploadedFileName).onSuccess(buffer -> {
				ByteArrayInputStream is = new ByteArrayInputStream(buffer.getBytes());
				promise.complete();
			}).onFailure(ex -> {
				LOG.error("Failed to import SequenceRun CSV. ", ex);
				promise.fail(ex);
			});
		}
		
		return promise.future();
	}
	
	private Future<Void> configureSharedWorkerExecutor() {
		Promise<Void> promise = Promise.promise();
		try {
			String name = "MainVerticle-WorkerExecutor";
			Integer workerPoolSize = System.getenv(ConfigKeys.WORKER_POOL_SIZE) == null ? 5 : Integer.parseInt(System.getenv(ConfigKeys.WORKER_POOL_SIZE));
			workerExecutor = vertx.createSharedWorkerExecutor(name, workerPoolSize);
			LOG.info(configureSharedWorkerExecutorComplete, name);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureSharedWorkerExecutorFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	private Future<Void> configureHealthChecks() {
		Promise<Void> promise = Promise.promise();
		try {
			HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);
			siteInstances = Optional.ofNullable(System.getenv(ConfigKeys.SITE_INSTANCES)).map(s -> Integer.parseInt(s)).orElse(1);
			workerPoolSize = System.getenv(ConfigKeys.WORKER_POOL_SIZE) == null ? null : Integer.parseInt(System.getenv(ConfigKeys.WORKER_POOL_SIZE));

			healthCheckHandler.register("database", 2000, a -> {
				pgPool.preparedQuery("select current_timestamp").execute(selectCAsync -> {
					if(selectCAsync.succeeded()) {
						a.complete(Status.OK(new JsonObject()));
					} else {
						LOG.error(configureHealthChecksErrorDatabase, a.future().cause());
						promise.fail(a.future().cause());
					}
				});
			});
			healthCheckHandler.register("solr", 2000, a -> {
				SolrQuery query = new SolrQuery();
				query.setQuery("*:*");
				try {
					String solrHostName = config().getString(ConfigKeys.SOLR_HOST_NAME);
					Integer solrPort = config().getInteger(ConfigKeys.SOLR_PORT);
					String solrCollection = config().getString(ConfigKeys.SOLR_COLLECTION);
					String solrRequestUri = String.format("/solr/%s/select%s", solrCollection, query.toQueryString());
					webClient.get(solrPort, solrHostName, solrRequestUri).send().onSuccess(b -> {
						try {
							a.complete(Status.OK());
						} catch(Exception ex) {
							LOG.error("Could not read response from Solr. ", ex);
							a.fail(ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("Solr request failed. "), new RuntimeException(ex));
						a.fail(ex);
					});
				} catch (Exception e) {
					LOG.error(configureHealthChecksErrorSolr, a.future().cause());
					a.fail(a.future().cause());
				}
			});
			healthCheckHandler.register("vertx", 2000, a -> {
				a.complete(Status.OK(new JsonObject().put(ConfigKeys.SITE_INSTANCES, siteInstances).put("workerPoolSize", workerPoolSize)));
			});
			router.get("/health").handler(healthCheckHandler);
			LOG.info(configureHealthChecksComplete);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureHealthChecksFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	private Future<Void> configureWebsockets() {
		Promise<Void> promise = Promise.promise();
		try {
			SockJSBridgeOptions options = new SockJSBridgeOptions()
					.addOutboundPermitted(new PermittedOptions().setAddressRegex("websocket.*"));
			SockJSHandler sockJsHandler = SockJSHandler.create(vertx);
			sockJsHandler.bridge(options);
			router.route("/eventbus/*").handler(sockJsHandler);
			LOG.info(configureWebsocketsComplete);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureWebsocketsFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	private Future<Void> configureEmail() {
		Promise<Void> promise = Promise.promise();
		try {
			String emailHost = config().getString(ConfigKeys.EMAIL_HOST);
			if(StringUtils.isNotBlank(emailHost)) {
				MailConfig mailConfig = new MailConfig();
				mailConfig.setHostname(emailHost);
				mailConfig.setPort(config().getInteger(ConfigKeys.EMAIL_PORT));
				mailConfig.setSsl(config().getBoolean(ConfigKeys.EMAIL_SSL));
				mailConfig.setUsername(config().getString(ConfigKeys.EMAIL_USERNAME));
				mailConfig.setPassword(config().getString(ConfigKeys.EMAIL_PASSWORD));
				MailClient.createShared(vertx, mailConfig);
				LOG.info(configureEmailComplete);
				promise.complete();
			} else {
				LOG.info(configureEmailComplete);
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(configureEmailFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	private Future<Void> configureApi() {
		Promise<Void> promise = Promise.promise();
		try {
			templateEngine = HandlebarsTemplateEngine.create(vertx);

			SiteUserEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine, vertx);
			SequenceRunEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine, vertx);
			TaxonomyEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine, vertx);
			BioSampleEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine, vertx);

			LOG.info(configureApiComplete);
			promise.complete();
		} catch(Exception ex) {
			LOG.error(configureApiFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	private Future<Void> configureUi() {
		Promise<Void> promise = Promise.promise();
		try {
			String staticPath = config().getString(ConfigKeys.STATIC_PATH);
			String staticBaseUrl = config().getString(ConfigKeys.STATIC_BASE_URL);
			String siteBaseUrl = config().getString(ConfigKeys.SITE_BASE_URL);
			Handlebars handlebars = (Handlebars)templateEngine.unwrap();
			TemplateHandler templateHandler = TemplateHandler.create(templateEngine, staticPath + "/template/enUS", "text/html");

			handlebars.registerHelpers(ConditionalHelpers.class);
			handlebars.registerHelpers(StringHelpers.class);
			handlebars.registerHelpers(AuthHelpers.class);
			handlebars.registerHelpers(DateHelpers.class);

			router.get("/").handler(a -> {
				a.reroute("/template/home-page");
			});

			router.get("/api").handler(ctx -> {
				ctx.reroute("/template/openapi");
			});

			router.get("/template/*").handler(ctx -> {
				ctx.put("staticBaseUrl", staticBaseUrl);
				ctx.put("siteBaseUrl", siteBaseUrl);
				ctx.next();
			});

			router.get("/template/*").handler(templateHandler);
			router.errorHandler(500,  ctx -> {
				Throwable ex = ctx.failure();
				LOG.error("Error occured. ", ex);
				ctx.json(new JsonObject().put("error", new JsonObject().put("message", ex.getMessage())));
			});

			StaticHandler staticHandler = StaticHandler.create().setCachingEnabled(false).setFilesReadOnly(false);
			if(staticPath != null) {
				staticHandler.setAllowRootFileSystemAccess(true);
				staticHandler.setWebRoot(staticPath);
			}
			router.route("/static/*").handler(staticHandler);

			LOG.info(configureUiComplete);
			promise.complete();
		} catch(Exception ex) {
			LOG.error(configureUiFail);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	public Future<Void> putVarsInRoutingContext(RoutingContext ctx) {
		Promise<Void> promise = Promise.promise();
		try {
			for(Entry<String, String> entry : ctx.queryParams()) {
				String paramName = entry.getKey();
				String paramObject = entry.getValue();
				String entityVar = null;
				String valueIndexed = null;

				switch(paramName) {
					case "var":
						entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
						valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
						ctx.put(entityVar, valueIndexed);
						break;
				}
				promise.complete();
			}
		} catch(Exception ex) {
			LOG.error(String.format("putVarsInRoutingContext failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	public Future<Void> putVarsInRoutingContext(RoutingContext ctx) {
		Promise<Void> promise = Promise.promise();
		try {
			for(Entry<String, String> entry : ctx.queryParams()) {
				String paramName = entry.getKey();
				String paramObject = entry.getValue();
				String entityVar = null;
				String valueIndexed = null;

				switch(paramName) {
					case "var":
						entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
						valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
						ctx.put(entityVar, valueIndexed);
						break;
				}
				promise.complete();
			}
		} catch(Exception ex) {
			LOG.error(String.format("putVarsInRoutingContext failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	
	private Future<Void> startServer() {
		Promise<Void> promise = Promise.promise();

		try {
			Boolean sslPassthrough = config().getBoolean(ConfigKeys.SSL_PASSTHROUGH, false);
			String siteBaseUrl = config().getString(ConfigKeys.SITE_BASE_URL);
			Integer sitePort = config().getInteger(ConfigKeys.SITE_PORT);
			String sslJksPath = config().getString(ConfigKeys.SSL_JKS_PATH);
			HttpServerOptions options = new HttpServerOptions();
			if(sslPassthrough) {
				options.setKeyStoreOptions(new JksOptions().setPath(sslJksPath).setPassword(config().getString(ConfigKeys.SSL_JKS_PASSWORD)));
				options.setSsl(true);
				LOG.info(String.format(startServerSsl, sslJksPath));
			}
			options.setPort(sitePort);
	
			LOG.info(String.format(startServerBeforeServer, siteBaseUrl));
			vertx.createHttpServer(options).requestHandler(router).listen(ar -> {
				if (ar.succeeded()) {
					LOG.info(String.format(startServerSuccessServer, siteBaseUrl));
					promise.complete();
				} else {
					LOG.error(startServerErrorServer, ar.cause());
					promise.fail(ar.cause());
				}
			});
		} catch (Exception ex) {
			LOG.error(startServerErrorServer, ex);
			promise.fail(ex);
		}

		return promise.future();
	}
	
	public void stop(Promise<Void> promise) {
		if(pgPool != null) {
			pgPool.close().onSuccess(a -> {
				LOG.info(stopComplete);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(stopFail, ex);
				promise.fail(ex);
			});
		}
	}

	public String toId(String s) {
		if(s != null) {
			s = Normalizer.normalize(s, Normalizer.Form.NFD);
			s = StringUtils.lowerCase(s);
			s = StringUtils.trim(s);
			s = StringUtils.replacePattern(s, "\\s{1,}", "-");
			s = StringUtils.replacePattern(s, "[^\\w-]", "");
			s = StringUtils.replacePattern(s, "-{2,}", "-");
		}

		return s;
	}

}
