package io.serratus.api.enus.model.biosample;

import io.serratus.api.enus.request.SiteRequestEnUS;
import io.serratus.api.enus.user.SiteUser;
import io.serratus.api.enus.request.api.ApiRequest;
import io.serratus.api.enus.vertx.MailVerticle;
import io.serratus.api.enus.config.ConfigKeys;
import io.serratus.api.enus.base.BaseApiServiceImpl;
import io.vertx.ext.web.client.WebClient;
import java.util.Objects;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.pgclient.PgPool;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import io.vertx.core.eventbus.DeliveryOptions;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
import java.util.stream.Collectors;
import io.vertx.core.json.Json;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.commons.lang3.StringUtils;
import java.security.Principal;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.io.PrintWriter;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import java.util.Collection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.math.NumberUtils;
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.reactivestreams.ReactiveReadStream;
import io.vertx.ext.reactivestreams.ReactiveWriteStream;
import io.vertx.core.MultiMap;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.sqlclient.Transaction;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.Row;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.AsyncResult;
import java.net.URLEncoder;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.CompositeFuture;
import io.vertx.core.http.HttpHeaders;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import java.util.HashMap;
import io.vertx.ext.auth.User;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import org.apache.solr.util.DateMathParser;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.FacetField;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Base64;
import java.time.ZonedDateTime;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import io.serratus.api.enus.user.SiteUserEnUSApiServiceImpl;
import io.serratus.api.enus.search.SearchList;


/**
 * Map.hackathonMission: to create a new Java API implementation class to persist information about BioSamples related to RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumn: Develop BioSample API
 * Map.hackathonLabels: Java,Vert.x
 * Translate: false
 **/
public class BioSampleEnUSGenApiServiceImpl extends BaseApiServiceImpl implements BioSampleEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(BioSampleEnUSGenApiServiceImpl.class);

	public BioSampleEnUSGenApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider, HandlebarsTemplateEngine templateEngine) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine);
	}

	// PUTImport //

	@Override
	public void putimportBioSample(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putimportBioSample started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);

				List<String> roles = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_REQUIRED + "_BioSample")).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
				if(
						!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
						&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
						) {
					eventHandler.handle(Future.succeededFuture(
						new ServiceResponse(401, "UNAUTHORIZED", 
							Buffer.buffer().appendString(
								new JsonObject()
									.put("errorCode", "401")
									.put("errorMessage", "roles required: " + String.join(", ", roles))
									.encodePrettily()
								), MultiMap.caseInsensitiveMultiMap()
						)
					));
				} else {
					try {
						ApiRequest apiRequest = new ApiRequest();
						JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
						apiRequest.setRows(jsonArray.size());
						apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						eventBus.publish("websocketBioSample", JsonObject.mapFrom(apiRequest).toString());
						varsBioSample(siteRequest).onSuccess(d -> {
							listPUTImportBioSample(apiRequest, siteRequest).onSuccess(e -> {
								response200PUTImportBioSample(siteRequest).onSuccess(response -> {
									LOG.debug(String.format("putimportBioSample succeeded. "));
									eventHandler.handle(Future.succeededFuture(response));
								}).onFailure(ex -> {
									LOG.error(String.format("putimportBioSample failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putimportBioSample failed. "), ex);
								error(siteRequest, eventHandler, ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putimportBioSample failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("putimportBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}
			} catch(Exception ex) {
				LOG.error(String.format("putimportBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTImportBioSample(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				futures.add(Future.future(promise1 -> {
					JsonObject params = new JsonObject();
					params.put("body", obj);
					params.put("path", new JsonObject());
					params.put("cookie", new JsonObject());
					params.put("header", new JsonObject());
					params.put("form", new JsonObject());
					JsonObject query = new JsonObject();
					Boolean softCommit = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getBoolean("softCommit")).orElse(null);
					Integer commitWithin = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getInteger("commitWithin")).orElse(null);
					if(softCommit == null && commitWithin == null)
						softCommit = true;
					if(softCommit)
						query.put("softCommit", softCommit);
					if(commitWithin != null)
						query.put("commitWithin", commitWithin);
					params.put("query", query);
					JsonObject context = new JsonObject().put("params", params).put("user", Optional.ofNullable(siteRequest.getUser()).map(user -> user.principal()).orElse(null));
					JsonObject json = new JsonObject().put("context", context);
					eventBus.request("serratus-api-enUS-BioSample", json, new DeliveryOptions().addHeader("action", "putimportBioSampleFuture")).onSuccess(a -> {
						promise1.complete();
					}).onFailure(ex -> {
						LOG.error(String.format("listPUTImportBioSample failed. "), ex);
						promise1.fail(ex);
					});
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportBioSample failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	@Override
	public void putimportBioSampleFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				ApiRequest apiRequest = new ApiRequest();
				apiRequest.setRows(1);
				apiRequest.setNumFound(1L);
				apiRequest.setNumPATCH(0L);
				apiRequest.initDeepApiRequest(siteRequest);
				siteRequest.setApiRequest_(apiRequest);
				body.put("inheritPk", body.getValue("pk"));
				if(Optional.ofNullable(serviceRequest.getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getJsonArray("var")).orElse(new JsonArray()).stream().filter(s -> "refresh:false".equals(s)).count() > 0L) {
					siteRequest.getRequestVars().put( "refresh", "false" );
				}

				SearchList<BioSample> searchList = new SearchList<BioSample>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(BioSample.class);
				searchList.addFilterQuery("deleted_docvalues_boolean:false");
				searchList.addFilterQuery("archived_docvalues_boolean:false");
				searchList.addFilterQuery("inheritPk_docvalues_string:" + ClientUtils.escapeQueryChars(body.getString("pk")));
				searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
					try {
						if(searchList.size() >= 1) {
							BioSample o = searchList.getList().stream().findFirst().orElse(null);
							BioSample o2 = new BioSample();
							JsonObject body2 = new JsonObject();
							for(String f : body.fieldNames()) {
								Object bodyVal = body.getValue(f);
								if(bodyVal instanceof JsonArray) {
									JsonArray bodyVals = (JsonArray)bodyVal;
									Collection<?> vals = (Collection<?>)o.obtainForClass(f);
									if(bodyVals.size() == vals.size()) {
										Boolean match = true;
										for(Object val : vals) {
											if(val != null) {
												if(!bodyVals.contains(val.toString())) {
													match = false;
													break;
												}
											} else {
												match = false;
												break;
											}
										}
										if(!match) {
											body2.put("set" + StringUtils.capitalize(f), bodyVal);
										}
									} else {
										body2.put("set" + StringUtils.capitalize(f), bodyVal);
									}
								} else {
									o2.defineForClass(f, bodyVal);
									o2.relateForClass(f, bodyVal);
									if(!StringUtils.containsAny(f, "pk", "created", "setCreated") && !Objects.equals(o.obtainForClass(f), o2.obtainForClass(f)))
										body2.put("set" + StringUtils.capitalize(f), bodyVal);
								}
							}
							for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
								if(!body.fieldNames().contains(f)) {
									if(!StringUtils.containsAny(f, "pk", "created", "setCreated") && !Objects.equals(o.obtainForClass(f), o2.obtainForClass(f)))
										body2.putNull("set" + StringUtils.capitalize(f));
								}
							}
							if(body2.size() > 0) {
								siteRequest.setJsonObject(body2);
								patchBioSampleFuture(o, true).onSuccess(b -> {
									LOG.info("Import BioSample {} succeeded, modified BioSample. ", body.getValue("pk"));
									eventHandler.handle(Future.succeededFuture());
								}).onFailure(ex -> {
									LOG.error(String.format("putimportBioSampleFuture failed. "), ex);
									eventHandler.handle(Future.failedFuture(ex));
								});
							} else {
								eventHandler.handle(Future.succeededFuture());
							}
						} else {
							postBioSampleFuture(siteRequest, true).onSuccess(b -> {
								LOG.info("Import BioSample {} succeeded, created new BioSample. ", body.getValue("pk"));
								eventHandler.handle(Future.succeededFuture());
							}).onFailure(ex -> {
								LOG.error(String.format("putimportBioSampleFuture failed. "), ex);
								eventHandler.handle(Future.failedFuture(ex));
							});
						}
					} catch(Exception ex) {
						LOG.error(String.format("putimportBioSampleFuture failed. "), ex);
						eventHandler.handle(Future.failedFuture(ex));
					}
				}).onFailure(ex -> {
					LOG.error(String.format("putimportBioSampleFuture failed. "), ex);
					eventHandler.handle(Future.failedFuture(ex));
				});
			} catch(Exception ex) {
				LOG.error(String.format("putimportBioSampleFuture failed. "), ex);
				eventHandler.handle(Future.failedFuture(ex));
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200PUTImportBioSample(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTImportBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// POST //

	@Override
	public void postBioSample(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("postBioSample started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);

				List<String> roles = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_REQUIRED + "_BioSample")).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
				if(
						!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
						&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
						) {
					eventHandler.handle(Future.succeededFuture(
						new ServiceResponse(401, "UNAUTHORIZED", 
							Buffer.buffer().appendString(
								new JsonObject()
									.put("errorCode", "401")
									.put("errorMessage", "roles required: " + String.join(", ", roles))
									.encodePrettily()
								), MultiMap.caseInsensitiveMultiMap()
						)
					));
				} else {
					ApiRequest apiRequest = new ApiRequest();
					apiRequest.setRows(1);
					apiRequest.setNumFound(1L);
					apiRequest.setNumPATCH(0L);
					apiRequest.initDeepApiRequest(siteRequest);
					siteRequest.setApiRequest_(apiRequest);
					eventBus.publish("websocketBioSample", JsonObject.mapFrom(apiRequest).toString());
					JsonObject params = new JsonObject();
					params.put("body", siteRequest.getJsonObject());
					params.put("path", new JsonObject());
					params.put("cookie", new JsonObject());
					params.put("header", new JsonObject());
					params.put("form", new JsonObject());
					JsonObject query = new JsonObject();
					Boolean softCommit = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getBoolean("softCommit")).orElse(null);
					Integer commitWithin = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getInteger("commitWithin")).orElse(null);
					if(softCommit == null && commitWithin == null)
						softCommit = true;
					if(softCommit)
						query.put("softCommit", softCommit);
					if(commitWithin != null)
						query.put("commitWithin", commitWithin);
					params.put("query", query);
					JsonObject context = new JsonObject().put("params", params).put("user", Optional.ofNullable(siteRequest.getUser()).map(user -> user.principal()).orElse(null));
					JsonObject json = new JsonObject().put("context", context);
					eventBus.request("serratus-api-enUS-BioSample", json, new DeliveryOptions().addHeader("action", "postBioSampleFuture")).onSuccess(a -> {
						JsonObject responseMessage = (JsonObject)a.body();
						JsonObject responseBody = new JsonObject(new String(Base64.getDecoder().decode(responseMessage.getString("payload")), Charset.forName("UTF-8")));
						apiRequest.setPk(Long.parseLong(responseBody.getString("pk")));
						eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(responseBody.encodePrettily()))));
						LOG.debug(String.format("postBioSample succeeded. "));
					}).onFailure(ex -> {
						LOG.error(String.format("postBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("postBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	@Override
	public void postBioSampleFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			ApiRequest apiRequest = new ApiRequest();
			apiRequest.setRows(1);
			apiRequest.setNumFound(1L);
			apiRequest.setNumPATCH(0L);
			apiRequest.initDeepApiRequest(siteRequest);
			siteRequest.setApiRequest_(apiRequest);
			if(Optional.ofNullable(serviceRequest.getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getJsonArray("var")).orElse(new JsonArray()).stream().filter(s -> "refresh:false".equals(s)).count() > 0L) {
				siteRequest.getRequestVars().put( "refresh", "false" );
			}
			postBioSampleFuture(siteRequest, false).onSuccess(o -> {
				eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(JsonObject.mapFrom(o).encodePrettily()))));
			}).onFailure(ex -> {
				eventHandler.handle(Future.failedFuture(ex));
			});
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}

	public Future<BioSample> postBioSampleFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<BioSample> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<BioSample> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createBioSample(siteRequest).onSuccess(bioSample -> {
					sqlPOSTBioSample(bioSample, inheritPk).onSuccess(b -> {
						defineBioSample(bioSample).onSuccess(c -> {
							relateBioSample(bioSample).onSuccess(d -> {
								indexBioSample(bioSample).onSuccess(e -> {
									promise1.complete(bioSample);
								}).onFailure(ex -> {
									promise1.fail(ex);
								});
							}).onFailure(ex -> {
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				siteRequest.setSqlConnection(null);
				promise.fail(ex);
			}).compose(bioSample -> {
				Promise<BioSample> promise2 = Promise.promise();
				refreshBioSample(bioSample).onSuccess(a -> {
					try {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							bioSample.apiRequestBioSample();
							eventBus.publish("websocketBioSample", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(bioSample);
					} catch(Exception ex) {
						LOG.error(String.format("postBioSampleFuture failed. "), ex);
						promise.fail(ex);
					}
				}).onFailure(ex -> {
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(bioSample -> {
				promise.complete(bioSample);
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("postBioSampleFuture failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPOSTBioSample(BioSample o, Boolean inheritPk) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE BioSample SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			BioSample o2 = new BioSample();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				if(bParams.size() > 0) {
					bSql.append(", ");
				}
				bSql.append("sessionId=$" + num);
				num++;
				bParams.add(siteRequest.getSessionId());
			}
			if(siteRequest.getUserKey() != null) {
				if(bParams.size() > 0) {
					bSql.append(", ");
				}
				bSql.append("userKey=$" + num);
				num++;
				bParams.add(siteRequest.getUserKey());
			}

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case BioSample.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case BioSample.VAR_created:
						o2.setCreated(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_created + "=$" + num);
						num++;
						bParams.add(o2.sqlCreated());
						break;
					case BioSample.VAR_archived:
						o2.setArchived(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_archived + "=$" + num);
						num++;
						bParams.add(o2.sqlArchived());
						break;
					case BioSample.VAR_deleted:
						o2.setDeleted(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_deleted + "=$" + num);
						num++;
						bParams.add(o2.sqlDeleted());
						break;
					case BioSample.VAR_sessionId:
						o2.setSessionId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_sessionId + "=$" + num);
						num++;
						bParams.add(o2.sqlSessionId());
						break;
					case BioSample.VAR_userKey:
						o2.setUserKey(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_userKey + "=$" + num);
						num++;
						bParams.add(o2.sqlUserKey());
						break;
					case BioSample.VAR_bioSampleId:
						o2.setBioSampleId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleId + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleId());
						break;
					case BioSample.VAR_bioSampleIsolate:
						o2.setBioSampleIsolate(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleIsolate + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleIsolate());
						break;
					case BioSample.VAR_bioSampleHost:
						o2.setBioSampleHost(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleHost + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleHost());
						break;
					case BioSample.VAR_bioSampleLabHost:
						o2.setBioSampleLabHost(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleLabHost + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleLabHost());
						break;
					case BioSample.VAR_bioSampleIsolationSource:
						o2.setBioSampleIsolationSource(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleIsolationSource + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleIsolationSource());
						break;
					case BioSample.VAR_bioSampleGeographicLocation:
						o2.setBioSampleGeographicLocation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleGeographicLocation + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleGeographicLocation());
						break;
					case BioSample.VAR_bioSampleLatitudeLongitude:
						o2.setBioSampleLatitudeLongitude(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleLatitudeLongitude + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleLatitudeLongitude());
						break;
					case BioSample.VAR_bioSampleReferenceForBioMaterial:
						o2.setBioSampleReferenceForBioMaterial(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleReferenceForBioMaterial + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleReferenceForBioMaterial());
						break;
					case BioSample.VAR_bioSampleMaterialProcessing:
						o2.setBioSampleMaterialProcessing(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleMaterialProcessing + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleMaterialProcessing());
						break;
					case BioSample.VAR_bioSampleMaterialIdentifiers:
						o2.setBioSampleMaterialIdentifiers(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleMaterialIdentifiers + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleMaterialIdentifiers());
						break;
					case BioSample.VAR_bioSampleDisease:
						o2.setBioSampleDisease(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleDisease + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleDisease());
						break;
					case BioSample.VAR_bioSampleHostTissueSampled:
						o2.setBioSampleHostTissueSampled(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleHostTissueSampled + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleHostTissueSampled());
						break;
					case BioSample.VAR_bioSampleCollectedBy:
						o2.setBioSampleCollectedBy(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_bioSampleCollectedBy + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleCollectedBy());
						break;
					case BioSample.VAR_taxonomyId:
						o2.setTaxonomyId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_taxonomyId + "=$" + num);
						num++;
						bParams.add(o2.sqlTaxonomyId());
						break;
					case BioSample.VAR_taxonomyName:
						o2.setTaxonomyName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_taxonomyName + "=$" + num);
						num++;
						bParams.add(o2.sqlTaxonomyName());
						break;
					case BioSample.VAR_taxonomyUniqueName:
						o2.setTaxonomyUniqueName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_taxonomyUniqueName + "=$" + num);
						num++;
						bParams.add(o2.sqlTaxonomyUniqueName());
						break;
					case BioSample.VAR_taxonomyNameClass:
						o2.setTaxonomyNameClass(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(BioSample.VAR_taxonomyNameClass + "=$" + num);
						num++;
						bParams.add(o2.sqlTaxonomyNameClass());
						break;
					}
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
			bParams.add(pk);
			num++;
				futures2.add(0, Future.future(a -> {
					sqlConnection.preparedQuery(bSql.toString())
							.execute(Tuple.tuple(bParams)
							).onSuccess(b -> {
						a.handle(Future.succeededFuture());
					}).onFailure(ex -> {
						RuntimeException ex2 = new RuntimeException("value BioSample failed", ex);
						LOG.error(String.format("relateBioSample failed. "), ex2);
						a.handle(Future.failedFuture(ex2));
					});
				}));
			}
			CompositeFuture.all(futures1).onSuccess(a -> {
				CompositeFuture.all(futures2).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPOSTBioSample failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPOSTBioSample failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPOSTBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}


	public Future<ServiceResponse> response200POSTBioSample(BioSample o) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200POSTBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PATCH //

	@Override
	public void patchBioSample(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("patchBioSample started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);

				List<String> roles = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_REQUIRED + "_BioSample")).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
				if(
						!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
						&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
						) {
					eventHandler.handle(Future.succeededFuture(
						new ServiceResponse(401, "UNAUTHORIZED", 
							Buffer.buffer().appendString(
								new JsonObject()
									.put("errorCode", "401")
									.put("errorMessage", "roles required: " + String.join(", ", roles))
									.encodePrettily()
								), MultiMap.caseInsensitiveMultiMap()
						)
					));
				} else {
					searchBioSampleList(siteRequest, false, true, true).onSuccess(listBioSample -> {
						try {
							List<String> roles2 = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_ADMIN)).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
							if(listBioSample.getQueryResponse().getResults().getNumFound() > 1
									&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
									&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
									) {
								String message = String.format("roles required: " + String.join(", ", roles2));
								LOG.error(message);
								error(siteRequest, eventHandler, new RuntimeException(message));
							} else {

								ApiRequest apiRequest = new ApiRequest();
								apiRequest.setRows(listBioSample.getRows());
								apiRequest.setNumFound(listBioSample.getQueryResponse().getResults().getNumFound());
								apiRequest.setNumPATCH(0L);
								apiRequest.initDeepApiRequest(siteRequest);
								siteRequest.setApiRequest_(apiRequest);
								if(apiRequest.getNumFound() == 1L)
									apiRequest.setOriginal(listBioSample.first());
								apiRequest.setPk(Optional.ofNullable(listBioSample.first()).map(o2 -> o2.getPk()).orElse(null));
								eventBus.publish("websocketBioSample", JsonObject.mapFrom(apiRequest).toString());

								listPATCHBioSample(apiRequest, listBioSample).onSuccess(e -> {
									response200PATCHBioSample(siteRequest).onSuccess(response -> {
										LOG.debug(String.format("patchBioSample succeeded. "));
										eventHandler.handle(Future.succeededFuture(response));
									}).onFailure(ex -> {
										LOG.error(String.format("patchBioSample failed. "), ex);
										error(siteRequest, eventHandler, ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("patchBioSample failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}
						} catch(Exception ex) {
							LOG.error(String.format("patchBioSample failed. "), ex);
							error(siteRequest, eventHandler, ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("patchBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("patchBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("patchBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("patchBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPATCHBioSample(ApiRequest apiRequest, SearchList<BioSample> listBioSample) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listBioSample.getSiteRequest_();
		listBioSample.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			o.setSiteRequest_(siteRequest2);
			futures.add(Future.future(promise1 -> {
				patchBioSampleFuture(o, false).onSuccess(a -> {
					promise1.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("listPATCHBioSample failed. "), ex);
					promise1.fail(ex);
				});
			}));
		});
		CompositeFuture.all(futures).onSuccess( a -> {
			if(apiRequest != null) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listBioSample.getQueryResponse().getResults().size());
				if(apiRequest.getNumFound() == 1L)
					listBioSample.first().apiRequestBioSample();
				eventBus.publish("websocketBioSample", JsonObject.mapFrom(apiRequest).toString());
			}
			listBioSample.next().onSuccess(next -> {
				if(next) {
					listPATCHBioSample(apiRequest, listBioSample);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPATCHBioSample failed. "), ex);
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPATCHBioSample failed. "), ex);
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override
	public void patchBioSampleFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				serviceRequest.getParams().getJsonObject("query").put("rows", 1);
				searchBioSampleList(siteRequest, false, true, true).onSuccess(listBioSample -> {
					try {
						BioSample o = listBioSample.first();
						if(o != null && listBioSample.getQueryResponse().getResults().getNumFound() == 1) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.setNumPATCH(0L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							if(Optional.ofNullable(serviceRequest.getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getJsonArray("var")).orElse(new JsonArray()).stream().filter(s -> "refresh:false".equals(s)).count() > 0L) {
								siteRequest.getRequestVars().put( "refresh", "false" );
							}
							if(apiRequest.getNumFound() == 1L)
								apiRequest.setOriginal(o);
							apiRequest.setPk(Optional.ofNullable(listBioSample.first()).map(o2 -> o2.getPk()).orElse(null));
							eventBus.publish("websocketBioSample", JsonObject.mapFrom(apiRequest).toString());
							patchBioSampleFuture(o, false).onSuccess(a -> {
								eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(new JsonObject().encodePrettily()))));
							}).onFailure(ex -> {
								eventHandler.handle(Future.failedFuture(ex));
							});
						} else {
							eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(new JsonObject().encodePrettily()))));
						}
					} catch(Exception ex) {
						LOG.error(String.format("patchBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}).onFailure(ex -> {
					LOG.error(String.format("patchBioSample failed. "), ex);
					error(siteRequest, eventHandler, ex);
				});
			} catch(Exception ex) {
				LOG.error(String.format("patchBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			LOG.error(String.format("patchBioSample failed. "), ex);
			error(null, eventHandler, ex);
		});
	}

	public Future<BioSample> patchBioSampleFuture(BioSample o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<BioSample> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			pgPool.withTransaction(sqlConnection -> {
				Promise<BioSample> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHBioSample(o, inheritPk).onSuccess(bioSample -> {
					defineBioSample(bioSample).onSuccess(c -> {
						relateBioSample(bioSample).onSuccess(d -> {
							indexBioSample(bioSample).onSuccess(e -> {
								promise1.complete(bioSample);
							}).onFailure(ex -> {
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				siteRequest.setSqlConnection(null);
				promise.fail(ex);
			}).compose(bioSample -> {
				Promise<BioSample> promise2 = Promise.promise();
				refreshBioSample(bioSample).onSuccess(a -> {
					promise2.complete(bioSample);
				}).onFailure(ex -> {
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(bioSample -> {
				promise.complete(bioSample);
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchBioSampleFuture failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<BioSample> sqlPATCHBioSample(BioSample o, Boolean inheritPk) {
		Promise<BioSample> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE BioSample SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			BioSample o2 = new BioSample();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			for(String entityVar : methodNames) {
				switch(entityVar) {
					case "setInheritPk":
							o2.setInheritPk(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_inheritPk + "=$" + num);
							num++;
							bParams.add(o2.sqlInheritPk());
						break;
					case "setArchived":
							o2.setArchived(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_archived + "=$" + num);
							num++;
							bParams.add(o2.sqlArchived());
						break;
					case "setDeleted":
							o2.setDeleted(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_deleted + "=$" + num);
							num++;
							bParams.add(o2.sqlDeleted());
						break;
					case "setBioSampleId":
							o2.setBioSampleId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleId + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleId());
						break;
					case "setBioSampleIsolate":
							o2.setBioSampleIsolate(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleIsolate + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleIsolate());
						break;
					case "setBioSampleHost":
							o2.setBioSampleHost(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleHost + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleHost());
						break;
					case "setBioSampleLabHost":
							o2.setBioSampleLabHost(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleLabHost + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleLabHost());
						break;
					case "setBioSampleIsolationSource":
							o2.setBioSampleIsolationSource(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleIsolationSource + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleIsolationSource());
						break;
					case "setBioSampleGeographicLocation":
							o2.setBioSampleGeographicLocation(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleGeographicLocation + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleGeographicLocation());
						break;
					case "setBioSampleLatitudeLongitude":
							o2.setBioSampleLatitudeLongitude(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleLatitudeLongitude + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleLatitudeLongitude());
						break;
					case "setBioSampleReferenceForBioMaterial":
							o2.setBioSampleReferenceForBioMaterial(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleReferenceForBioMaterial + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleReferenceForBioMaterial());
						break;
					case "setBioSampleMaterialProcessing":
							o2.setBioSampleMaterialProcessing(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleMaterialProcessing + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleMaterialProcessing());
						break;
					case "setBioSampleMaterialIdentifiers":
							o2.setBioSampleMaterialIdentifiers(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleMaterialIdentifiers + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleMaterialIdentifiers());
						break;
					case "setBioSampleDisease":
							o2.setBioSampleDisease(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleDisease + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleDisease());
						break;
					case "setBioSampleHostTissueSampled":
							o2.setBioSampleHostTissueSampled(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleHostTissueSampled + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleHostTissueSampled());
						break;
					case "setBioSampleCollectedBy":
							o2.setBioSampleCollectedBy(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_bioSampleCollectedBy + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleCollectedBy());
						break;
					case "setTaxonomyId":
							o2.setTaxonomyId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_taxonomyId + "=$" + num);
							num++;
							bParams.add(o2.sqlTaxonomyId());
						break;
					case "setTaxonomyName":
							o2.setTaxonomyName(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_taxonomyName + "=$" + num);
							num++;
							bParams.add(o2.sqlTaxonomyName());
						break;
					case "setTaxonomyUniqueName":
							o2.setTaxonomyUniqueName(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_taxonomyUniqueName + "=$" + num);
							num++;
							bParams.add(o2.sqlTaxonomyUniqueName());
						break;
					case "setTaxonomyNameClass":
							o2.setTaxonomyNameClass(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(BioSample.VAR_taxonomyNameClass + "=$" + num);
							num++;
							bParams.add(o2.sqlTaxonomyNameClass());
						break;
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
				bParams.add(pk);
				num++;
				futures2.add(0, Future.future(a -> {
					sqlConnection.preparedQuery(bSql.toString())
							.execute(Tuple.tuple(bParams)
							).onSuccess(b -> {
						a.handle(Future.succeededFuture());
					}).onFailure(ex -> {
						RuntimeException ex2 = new RuntimeException("value BioSample failed", ex);
						LOG.error(String.format("relateBioSample failed. "), ex2);
						a.handle(Future.failedFuture(ex2));
					});
				}));
			}
			CompositeFuture.all(futures1).onSuccess(a -> {
				CompositeFuture.all(futures2).onSuccess(b -> {
					BioSample o3 = new BioSample();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					promise.complete(o3);
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPATCHBioSample failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPATCHBioSample failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPATCHBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}


	public Future<ServiceResponse> response200PATCHBioSample(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PATCHBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// GET //

	@Override
	public void getBioSample(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				{
					searchBioSampleList(siteRequest, false, true, false).onSuccess(listBioSample -> {
						response200GETBioSample(listBioSample).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("getBioSample succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("getBioSample failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("getBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("getBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("getBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("getBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}



	public Future<ServiceResponse> response200GETBioSample(SearchList<BioSample> listBioSample) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listBioSample.getSiteRequest_();
			SolrDocumentList solrDocuments = listBioSample.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listBioSample.getList().stream().findFirst().orElse(null));
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200GETBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// Search //

	@Override
	public void searchBioSample(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				{
					searchBioSampleList(siteRequest, false, true, false).onSuccess(listBioSample -> {
						response200SearchBioSample(listBioSample).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchBioSample succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchBioSample failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}



	public Future<ServiceResponse> response200SearchBioSample(SearchList<BioSample> listBioSample) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listBioSample.getSiteRequest_();
			QueryResponse responseSearch = listBioSample.getQueryResponse();
			SolrDocumentList solrDocuments = listBioSample.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			String nextCursorMark = responseSearch.getNextCursorMark();
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listBioSample.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			if(nextCursorMark != null) {
				json.put("nextCursorMark", nextCursorMark);
			}
			JsonArray l = new JsonArray();
			listBioSample.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
						fieldNames.remove("created");
					}
					else if(fls.size() >= 1) {
						fieldNames.removeAll(fls);
					}
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("list", l);

			List<FacetField> facetFields = responseSearch.getFacetFields();
			if(facetFields != null) {
				JsonObject facetFieldsJson = new JsonObject();
				json.put("facet_fields", facetFieldsJson);
				for(FacetField facetField : facetFields) {
					String facetFieldVar = StringUtils.substringBefore(facetField.getName(), "_docvalues_");
					JsonObject facetFieldCounts = new JsonObject();
					facetFieldsJson.put(facetFieldVar, facetFieldCounts);
					List<FacetField.Count> facetFieldValues = facetField.getValues();
					for(Integer i = 0; i < facetFieldValues.size(); i+= 1) {
						FacetField.Count count = (FacetField.Count)facetFieldValues.get(i);
						facetFieldCounts.put(count.getName(), count.getCount());
					}
				}
			}

			List<RangeFacet> facetRanges = responseSearch.getFacetRanges();
			if(facetRanges != null) {
				JsonObject rangeJson = new JsonObject();
				json.put("facet_ranges", rangeJson);
				for(RangeFacet rangeFacet : facetRanges) {
					JsonObject rangeFacetJson = new JsonObject();
					String rangeFacetVar = StringUtils.substringBefore(rangeFacet.getName(), "_docvalues_");
					rangeJson.put(rangeFacetVar, rangeFacetJson);
					JsonObject rangeFacetCountsMap = new JsonObject();
					rangeFacetJson.put("counts", rangeFacetCountsMap);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						rangeFacetCountsMap.put(count.getValue(), count.getCount());
					}
				}
			}

			NamedList<List<PivotField>> facetPivot = responseSearch.getFacetPivot();
			if(facetPivot != null) {
				JsonObject facetPivotJson = new JsonObject();
				json.put("facet_pivot", facetPivotJson);
				Iterator<Entry<String, List<PivotField>>> facetPivotIterator = responseSearch.getFacetPivot().iterator();
				while(facetPivotIterator.hasNext()) {
					Entry<String, List<PivotField>> pivotEntry = facetPivotIterator.next();
					List<PivotField> pivotFields = pivotEntry.getValue();
					String[] varsIndexed = pivotEntry.getKey().trim().split(",");
					String[] entityVars = new String[varsIndexed.length];
					for(Integer i = 0; i < entityVars.length; i++) {
						String entityIndexed = varsIndexed[i];
						entityVars[i] = StringUtils.substringBefore(entityIndexed, "_docvalues_");
					}
					JsonArray pivotArray = new JsonArray();
					facetPivotJson.put(StringUtils.join(entityVars, ","), pivotArray);
					responsePivotSearchBioSample(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void responsePivotSearchBioSample(List<PivotField> pivotFields, JsonArray pivotArray) {
		for(PivotField pivotField : pivotFields) {
			String entityIndexed = pivotField.getField();
			String entityVar = StringUtils.substringBefore(entityIndexed, "_docvalues_");
			JsonObject pivotJson = new JsonObject();
			pivotArray.add(pivotJson);
			pivotJson.put("field", entityVar);
			pivotJson.put("value", pivotField.getValue());
			pivotJson.put("count", pivotField.getCount());
			List<RangeFacet> pivotRanges = pivotField.getFacetRanges();
			List<PivotField> pivotFields2 = pivotField.getPivot();
			if(pivotRanges != null) {
				JsonObject rangeJson = new JsonObject();
				pivotJson.put("ranges", rangeJson);
				for(RangeFacet rangeFacet : pivotRanges) {
					JsonObject rangeFacetJson = new JsonObject();
					String rangeFacetVar = StringUtils.substringBefore(rangeFacet.getName(), "_docvalues_");
					rangeJson.put(rangeFacetVar, rangeFacetJson);
					JsonObject rangeFacetCountsObject = new JsonObject();
					rangeFacetJson.put("counts", rangeFacetCountsObject);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						rangeFacetCountsObject.put(count.getValue(), count.getCount());
					}
				}
			}
			if(pivotFields2 != null) {
				JsonArray pivotArray2 = new JsonArray();
				pivotJson.put("pivot", pivotArray2);
				responsePivotSearchBioSample(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageBioSampleId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageBioSample(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageBioSample(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				{
					searchBioSampleList(siteRequest, false, true, false).onSuccess(listBioSample -> {
						response200SearchPageBioSample(listBioSample).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchpageBioSample succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchpageBioSample failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchpageBioSample failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchpageBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchpageBioSample failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchpageBioSample failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public void searchpageBioSamplePageInit(BioSamplePage page, SearchList<BioSample> listBioSample) {
	}
	public String templateSearchPageBioSample() {
		return Optional.ofNullable(config.getString(ConfigKeys.TEMPLATE_PATH)).orElse("templates") + "/enUS/BioSamplePage";
	}
	public Future<ServiceResponse> response200SearchPageBioSample(SearchList<BioSample> listBioSample) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listBioSample.getSiteRequest_();
			BioSamplePage page = new BioSamplePage();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			if(listBioSample.size() == 1)
				siteRequest.setRequestPk(listBioSample.get(0).getPk());
			page.setSearchListBioSample_(listBioSample);
			page.setSiteRequest_(siteRequest);
			page.promiseDeepBioSamplePage(siteRequest).onSuccess(a -> {
				JsonObject json = JsonObject.mapFrom(page);
				templateEngine.render(json, templateSearchPageBioSample()).onSuccess(buffer -> {
					promise.complete(new ServiceResponse(200, "OK", buffer, requestHeaders));
				}).onFailure(ex -> {
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchPageBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// General //

	public Future<BioSample> createBioSample(SiteRequestEnUS siteRequest) {
		Promise<BioSample> promise = Promise.promise();
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString(ConfigKeys.SITE_ZONE))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString(ConfigKeys.SITE_ZONE))));

			sqlConnection.preparedQuery("INSERT INTO BioSample(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)).onSuccess(result -> {
				Row createLine = result.value().stream().findFirst().orElseGet(() -> null);
				Long pk = createLine.getLong(0);
				BioSample o = new BioSample();
				o.setPk(pk);
				o.setSiteRequest_(siteRequest);
				promise.complete(o);
			}).onFailure(ex -> {
				RuntimeException ex2 = new RuntimeException(ex);
				LOG.error("createBioSample failed. ", ex2);
				promise.fail(ex2);
			});
		} catch(Exception ex) {
			LOG.error(String.format("createBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public void searchBioSampleQ(SearchList<BioSample> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String searchBioSampleFq(SearchList<BioSample> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : BioSample.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : BioSample.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + ClientUtils.escapeQueryChars(BioSample.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed)).replace("\\", "\\\\");
		}
	}

	public void searchBioSampleSort(SearchList<BioSample> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void searchBioSampleRows(SearchList<BioSample> searchList, Integer valueRows) {
			searchList.setRows(valueRows != null ? valueRows : 10);
	}

	public void searchBioSampleStart(SearchList<BioSample> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void searchBioSampleVar(SearchList<BioSample> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void searchBioSampleUri(SearchList<BioSample> searchList) {
	}

	public Future<ServiceResponse> varsBioSample(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			ServiceRequest serviceRequest = siteRequest.getServiceRequest();

			serviceRequest.getParams().getJsonObject("query").stream().filter(paramRequest -> "var".equals(paramRequest.getKey()) && paramRequest.getValue() != null).findFirst().ifPresent(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					for(Object paramObject : paramObjects) {
						entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
						valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
						siteRequest.getRequestVars().put(entityVar, valueIndexed);
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchBioSample failed. "), ex);
					promise.fail(ex);
				}
			});
			promise.complete();
		} catch(Exception ex) {
			LOG.error(String.format("searchBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<BioSample>> searchBioSampleList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify) {
		Promise<SearchList<BioSample>> promise = Promise.promise();
		try {
			ServiceRequest serviceRequest = siteRequest.getServiceRequest();
			String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<BioSample> searchList = new SearchList<BioSample>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(BioSample.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);

			String id = serviceRequest.getParams().getJsonObject("path").getString("id");
			if(id != null && NumberUtils.isCreatable(id)) {
				searchList.addFilterQuery("(pk_docvalues_long:" + ClientUtils.escapeQueryChars(id) + " OR objectId_docvalues_string:" + ClientUtils.escapeQueryChars(id) + ")");
			} else if(id != null) {
				searchList.addFilterQuery("objectId_docvalues_string:" + ClientUtils.escapeQueryChars(id));
			}

			serviceRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				String varIndexed = null;
				String valueSort = null;
				Integer valueStart = null;
				Integer valueRows = null;
				String valueCursorMark = null;
				String paramName = paramRequest.getKey();
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					if(paramValuesObject != null && "facet.pivot".equals(paramName)) {
						Matcher mFacetPivot = Pattern.compile("(?:(\\{![^\\}]+\\}))?(.*)").matcher(StringUtils.join(paramObjects.getList().toArray(), ","));
						boolean foundFacetPivot = mFacetPivot.find();
						if(foundFacetPivot) {
							String solrLocalParams = mFacetPivot.group(1);
							String[] entityVars = mFacetPivot.group(2).trim().split(",");
							String[] varsIndexed = new String[entityVars.length];
							for(Integer i = 0; i < entityVars.length; i++) {
								entityVar = entityVars[i];
								varsIndexed[i] = BioSample.varIndexedBioSample(entityVar);
							}
							searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
						}
					} else if(paramValuesObject != null) {
						for(Object paramObject : paramObjects) {
							switch(paramName) {
								case "q":
									Matcher mQ = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|\\^|$)))").matcher((String)paramObject);
									boolean foundQ = mQ.find();
									if(foundQ) {
										StringBuffer sb = new StringBuffer();
										while(foundQ) {
											entityVar = mQ.group(1).trim();
											valueIndexed = mQ.group(2).trim();
											varIndexed = BioSample.varIndexedBioSample(entityVar);
											String entityQ = searchBioSampleFq(searchList, entityVar, valueIndexed, varIndexed);
											mQ.appendReplacement(sb, entityQ);
											foundQ = mQ.find();
										}
										mQ.appendTail(sb);
										searchList.setQuery(sb.toString());
									}
									break;
								case "fq":
									Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
									boolean foundFq = mFq.find();
									if(foundFq) {
										StringBuffer sb = new StringBuffer();
										while(foundFq) {
											entityVar = mFq.group(1).trim();
											valueIndexed = mFq.group(2).trim();
											varIndexed = BioSample.varIndexedBioSample(entityVar);
											String entityFq = searchBioSampleFq(searchList, entityVar, valueIndexed, varIndexed);
											mFq.appendReplacement(sb, entityFq);
											foundFq = mFq.find();
										}
										mFq.appendTail(sb);
										searchList.addFilterQuery(sb.toString());
									}
									break;
								case "sort":
									entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
									valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
									varIndexed = BioSample.varIndexedBioSample(entityVar);
									searchBioSampleSort(searchList, entityVar, valueIndexed, varIndexed);
									break;
								case "start":
									valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchBioSampleStart(searchList, valueStart);
									break;
								case "rows":
									valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchBioSampleRows(searchList, valueRows);
									break;
								case "facet":
									searchList.add("facet", ((Boolean)paramObject).toString());
									break;
								case "facet.range.start":
									String startMathStr = (String)paramObject;
									Date start = DateMathParser.parseMath(null, startMathStr);
									searchList.add("facet.range.start", start.toInstant().toString());
									break;
								case "facet.range.end":
									String endMathStr = (String)paramObject;
									Date end = DateMathParser.parseMath(null, endMathStr);
									searchList.add("facet.range.end", end.toInstant().toString());
									break;
								case "facet.range.gap":
									String gap = (String)paramObject;
									searchList.add("facet.range.gap", gap);
									break;
								case "facet.range":
									Matcher mFacetRange = Pattern.compile("(?:(\\{![^\\}]+\\}))?(.*)").matcher((String)paramObject);
									boolean foundFacetRange = mFacetRange.find();
									if(foundFacetRange) {
										String solrLocalParams = mFacetRange.group(1);
										entityVar = mFacetRange.group(2).trim();
										varIndexed = BioSample.varIndexedBioSample(entityVar);
										searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
									}
									break;
								case "facet.field":
									entityVar = (String)paramObject;
									varIndexed = BioSample.varIndexedBioSample(entityVar);
									if(varIndexed != null)
										searchList.addFacetField(varIndexed);
									break;
								case "var":
									entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
									valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
									searchBioSampleVar(searchList, entityVar, valueIndexed);
									break;
								case "cursorMark":
									valueCursorMark = (String)paramObject;
									searchList.add("cursorMark", (String)paramObject);
									break;
							}
						}
						searchBioSampleUri(searchList);
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("created_docvalues_date", ORDER.desc);
			}
			searchBioSample2(siteRequest, populate, store, modify, searchList);
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				promise.complete(searchList);
			}).onFailure(ex -> {
				LOG.error(String.format("searchBioSample failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void searchBioSample2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, SearchList<BioSample> searchList) {
	}

	public Future<Void> defineBioSample(BioSample o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM BioSample WHERE pk=$1")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk)
					).onSuccess(result -> {
				try {
					for(Row definition : result.value()) {
						for(Integer i = 0; i < definition.size(); i++) {
							String columnName = definition.getColumnName(i);
							Object columnValue = definition.getValue(i);
							if(!"pk".equals(columnName)) {
								try {
									o.defineForClass(columnName, columnValue);
								} catch(Exception e) {
									LOG.error(String.format("defineBioSample failed. "), e);
								}
							}
						}
					}
					promise.complete();
				} catch(Exception ex) {
					LOG.error(String.format("defineBioSample failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				RuntimeException ex2 = new RuntimeException(ex);
				LOG.error(String.format("defineBioSample failed. "), ex2);
				promise.fail(ex2);
			});
		} catch(Exception ex) {
			LOG.error(String.format("defineBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> relateBioSample(BioSample o) {
		Promise<Void> promise = Promise.promise();
			promise.complete();
		return promise.future();
	}

	public Future<Void> indexBioSample(BioSample o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			o.promiseDeepForClass(siteRequest).onSuccess(a -> {
				SolrInputDocument document = new SolrInputDocument();
				o.indexBioSample(document);
				String solrHostName = siteRequest.getConfig().getString(ConfigKeys.SOLR_HOST_NAME);
				Integer solrPort = siteRequest.getConfig().getInteger(ConfigKeys.SOLR_PORT);
				String solrCollection = siteRequest.getConfig().getString(ConfigKeys.SOLR_COLLECTION);
				Boolean softCommit = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getBoolean("softCommit")).orElse(null);
				Integer commitWithin = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getInteger("commitWithin")).orElse(null);
					if(softCommit == null && commitWithin == null)
						softCommit = true;
					else if(softCommit == null)
						softCommit = false;
				String solrRequestUri = String.format("/solr/%s/update%s%s%s", solrCollection, "?overwrite=true&wt=json", softCommit ? "&softCommit=true" : "", commitWithin != null ? ("&commitWithin=" + commitWithin) : "");
				JsonArray json = new JsonArray().add(new JsonObject(document.toMap(new HashMap<String, Object>())));
				webClient.post(solrPort, solrHostName, solrRequestUri).putHeader("Content-Type", "application/json").expect(ResponsePredicate.SC_OK).sendBuffer(json.toBuffer()).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("indexBioSample failed. "), new RuntimeException(ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("indexBioSample failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("indexBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> refreshBioSample(BioSample o) {
		Promise<Void> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && !Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);
				}

				CompositeFuture.all(futures).onSuccess(b -> {
					JsonObject params = new JsonObject();
					params.put("body", new JsonObject());
					params.put("cookie", new JsonObject());
					params.put("header", new JsonObject());
					params.put("form", new JsonObject());
					params.put("path", new JsonObject());
					JsonObject query = new JsonObject();
					Boolean softCommit = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getBoolean("softCommit")).orElse(null);
					Integer commitWithin = Optional.ofNullable(siteRequest.getServiceRequest().getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getInteger("commitWithin")).orElse(null);
					if(softCommit == null && commitWithin == null)
						softCommit = true;
					if(softCommit)
						query.put("softCommit", softCommit);
					if(commitWithin != null)
						query.put("commitWithin", commitWithin);
					query.put("q", "*:*").put("fq", new JsonArray().add("pk:" + o.getPk()));
					params.put("query", query);
					JsonObject context = new JsonObject().put("params", params).put("user", Optional.ofNullable(siteRequest.getUser()).map(user -> user.principal()).orElse(null));
					JsonObject json = new JsonObject().put("context", context);
					eventBus.request("serratus-api-enUS-BioSample", json, new DeliveryOptions().addHeader("action", "patchBioSampleFuture")).onSuccess(c -> {
						JsonObject responseMessage = (JsonObject)c.body();
						Integer statusCode = responseMessage.getInteger("statusCode");
						if(statusCode.equals(200))
							promise.complete();
						else
							promise.fail(new RuntimeException(responseMessage.getString("statusMessage")));
					}).onFailure(ex -> {
						LOG.error("Refresh relations failed. ", ex);
						promise.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error("Refresh relations failed. ", ex);
					promise.fail(ex);
				});
			} else {
				promise.complete();
			}
		} catch(Exception ex) {
			LOG.error(String.format("refreshBioSample failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}
