package io.serratus.api.enus.model.run;

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
 * Map.hackathonMission: to create a new Java API implementation class to persist information about RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumn: Develop SequenceRun API
 * Map.hackathonLabels: Java,Vert.x
 * Translate: false
 **/
public class SequenceRunEnUSGenApiServiceImpl extends BaseApiServiceImpl implements SequenceRunEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SequenceRunEnUSGenApiServiceImpl.class);

	public SequenceRunEnUSGenApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider, HandlebarsTemplateEngine templateEngine) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine);
	}

	// PUTImport //

	@Override
	public void putimportSequenceRun(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putimportSequenceRun started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);

				List<String> roles = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_REQUIRED + "_SequenceRun")).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
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
						eventBus.publish("websocketSequenceRun", JsonObject.mapFrom(apiRequest).toString());
						varsSequenceRun(siteRequest).onSuccess(d -> {
							listPUTImportSequenceRun(apiRequest, siteRequest).onSuccess(e -> {
								response200PUTImportSequenceRun(siteRequest).onSuccess(response -> {
									LOG.debug(String.format("putimportSequenceRun succeeded. "));
									eventHandler.handle(Future.succeededFuture(response));
								}).onFailure(ex -> {
									LOG.error(String.format("putimportSequenceRun failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putimportSequenceRun failed. "), ex);
								error(siteRequest, eventHandler, ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putimportSequenceRun failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("putimportSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}
			} catch(Exception ex) {
				LOG.error(String.format("putimportSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTImportSequenceRun(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
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
					eventBus.request("serratus-api-enUS-SequenceRun", json, new DeliveryOptions().addHeader("action", "putimportSequenceRunFuture")).onSuccess(a -> {
						promise1.complete();
					}).onFailure(ex -> {
						LOG.error(String.format("listPUTImportSequenceRun failed. "), ex);
						promise1.fail(ex);
					});
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportSequenceRun failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	@Override
	public void putimportSequenceRunFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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

				SearchList<SequenceRun> searchList = new SearchList<SequenceRun>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SequenceRun.class);
				searchList.addFilterQuery("deleted_docvalues_boolean:false");
				searchList.addFilterQuery("archived_docvalues_boolean:false");
				searchList.addFilterQuery("inheritPk_docvalues_string:" + ClientUtils.escapeQueryChars(body.getString("pk")));
				searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
					try {
						if(searchList.size() >= 1) {
							SequenceRun o = searchList.getList().stream().findFirst().orElse(null);
							SequenceRun o2 = new SequenceRun();
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
								patchSequenceRunFuture(o, true).onSuccess(b -> {
									LOG.info("Import SequenceRun {} succeeded, modified SequenceRun. ", body.getValue("pk"));
									eventHandler.handle(Future.succeededFuture());
								}).onFailure(ex -> {
									LOG.error(String.format("putimportSequenceRunFuture failed. "), ex);
									eventHandler.handle(Future.failedFuture(ex));
								});
							} else {
								eventHandler.handle(Future.succeededFuture());
							}
						} else {
							postSequenceRunFuture(siteRequest, true).onSuccess(b -> {
								LOG.info("Import SequenceRun {} succeeded, created new SequenceRun. ", body.getValue("pk"));
								eventHandler.handle(Future.succeededFuture());
							}).onFailure(ex -> {
								LOG.error(String.format("putimportSequenceRunFuture failed. "), ex);
								eventHandler.handle(Future.failedFuture(ex));
							});
						}
					} catch(Exception ex) {
						LOG.error(String.format("putimportSequenceRunFuture failed. "), ex);
						eventHandler.handle(Future.failedFuture(ex));
					}
				}).onFailure(ex -> {
					LOG.error(String.format("putimportSequenceRunFuture failed. "), ex);
					eventHandler.handle(Future.failedFuture(ex));
				});
			} catch(Exception ex) {
				LOG.error(String.format("putimportSequenceRunFuture failed. "), ex);
				eventHandler.handle(Future.failedFuture(ex));
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200PUTImportSequenceRun(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTImportSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// POST //

	@Override
	public void postSequenceRun(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("postSequenceRun started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);

				List<String> roles = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_REQUIRED + "_SequenceRun")).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
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
					eventBus.publish("websocketSequenceRun", JsonObject.mapFrom(apiRequest).toString());
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
					eventBus.request("serratus-api-enUS-SequenceRun", json, new DeliveryOptions().addHeader("action", "postSequenceRunFuture")).onSuccess(a -> {
						JsonObject responseMessage = (JsonObject)a.body();
						JsonObject responseBody = new JsonObject(new String(Base64.getDecoder().decode(responseMessage.getString("payload")), Charset.forName("UTF-8")));
						apiRequest.setPk(Long.parseLong(responseBody.getString("pk")));
						eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(responseBody.encodePrettily()))));
						LOG.debug(String.format("postSequenceRun succeeded. "));
					}).onFailure(ex -> {
						LOG.error(String.format("postSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("postSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	@Override
	public void postSequenceRunFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			postSequenceRunFuture(siteRequest, false).onSuccess(o -> {
				eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(JsonObject.mapFrom(o).encodePrettily()))));
			}).onFailure(ex -> {
				eventHandler.handle(Future.failedFuture(ex));
			});
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}

	public Future<SequenceRun> postSequenceRunFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<SequenceRun> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<SequenceRun> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createSequenceRun(siteRequest).onSuccess(sequenceRun -> {
					sqlPOSTSequenceRun(sequenceRun, inheritPk).onSuccess(b -> {
						defineSequenceRun(sequenceRun).onSuccess(c -> {
							relateSequenceRun(sequenceRun).onSuccess(d -> {
								indexSequenceRun(sequenceRun).onSuccess(e -> {
									promise1.complete(sequenceRun);
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
			}).compose(sequenceRun -> {
				Promise<SequenceRun> promise2 = Promise.promise();
				refreshSequenceRun(sequenceRun).onSuccess(a -> {
					try {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							sequenceRun.apiRequestSequenceRun();
							eventBus.publish("websocketSequenceRun", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(sequenceRun);
					} catch(Exception ex) {
						LOG.error(String.format("postSequenceRunFuture failed. "), ex);
						promise.fail(ex);
					}
				}).onFailure(ex -> {
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(sequenceRun -> {
				promise.complete(sequenceRun);
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSequenceRunFuture failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPOSTSequenceRun(SequenceRun o, Boolean inheritPk) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SequenceRun SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			SequenceRun o2 = new SequenceRun();
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
					case SequenceRun.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case SequenceRun.VAR_created:
						o2.setCreated(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_created + "=$" + num);
						num++;
						bParams.add(o2.sqlCreated());
						break;
					case SequenceRun.VAR_archived:
						o2.setArchived(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_archived + "=$" + num);
						num++;
						bParams.add(o2.sqlArchived());
						break;
					case SequenceRun.VAR_deleted:
						o2.setDeleted(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_deleted + "=$" + num);
						num++;
						bParams.add(o2.sqlDeleted());
						break;
					case SequenceRun.VAR_sessionId:
						o2.setSessionId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_sessionId + "=$" + num);
						num++;
						bParams.add(o2.sqlSessionId());
						break;
					case SequenceRun.VAR_userKey:
						o2.setUserKey(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_userKey + "=$" + num);
						num++;
						bParams.add(o2.sqlUserKey());
						break;
					case SequenceRun.VAR_runId:
						o2.setRunId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_runId + "=$" + num);
						num++;
						bParams.add(o2.sqlRunId());
						break;
					case SequenceRun.VAR_runReleaseDate:
						o2.setRunReleaseDate(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_runReleaseDate + "=$" + num);
						num++;
						bParams.add(o2.sqlRunReleaseDate());
						break;
					case SequenceRun.VAR_runLoadDate:
						o2.setRunLoadDate(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_runLoadDate + "=$" + num);
						num++;
						bParams.add(o2.sqlRunLoadDate());
						break;
					case SequenceRun.VAR_projectId:
						o2.setProjectId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_projectId + "=$" + num);
						num++;
						bParams.add(o2.sqlProjectId());
						break;
					case SequenceRun.VAR_bioSampleId:
						o2.setBioSampleId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SequenceRun.VAR_bioSampleId + "=$" + num);
						num++;
						bParams.add(o2.sqlBioSampleId());
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
						RuntimeException ex2 = new RuntimeException("value SequenceRun failed", ex);
						LOG.error(String.format("relateSequenceRun failed. "), ex2);
						a.handle(Future.failedFuture(ex2));
					});
				}));
			}
			CompositeFuture.all(futures1).onSuccess(a -> {
				CompositeFuture.all(futures2).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPOSTSequenceRun failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPOSTSequenceRun failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPOSTSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}


	public Future<ServiceResponse> response200POSTSequenceRun(SequenceRun o) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200POSTSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PATCH //

	@Override
	public void patchSequenceRun(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("patchSequenceRun started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);

				List<String> roles = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_REQUIRED + "_SequenceRun")).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
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
					searchSequenceRunList(siteRequest, false, true, true).onSuccess(listSequenceRun -> {
						try {
							List<String> roles2 = Optional.ofNullable(config.getValue(ConfigKeys.AUTH_ROLES_ADMIN)).map(v -> v instanceof JsonArray ? (JsonArray)v : new JsonArray(v.toString())).orElse(new JsonArray()).getList();
							if(listSequenceRun.getQueryResponse().getResults().getNumFound() > 1
									&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
									&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
									) {
								String message = String.format("roles required: " + String.join(", ", roles2));
								LOG.error(message);
								error(siteRequest, eventHandler, new RuntimeException(message));
							} else {

								ApiRequest apiRequest = new ApiRequest();
								apiRequest.setRows(listSequenceRun.getRows());
								apiRequest.setNumFound(listSequenceRun.getQueryResponse().getResults().getNumFound());
								apiRequest.setNumPATCH(0L);
								apiRequest.initDeepApiRequest(siteRequest);
								siteRequest.setApiRequest_(apiRequest);
								if(apiRequest.getNumFound() == 1L)
									apiRequest.setOriginal(listSequenceRun.first());
								apiRequest.setPk(Optional.ofNullable(listSequenceRun.first()).map(o2 -> o2.getPk()).orElse(null));
								eventBus.publish("websocketSequenceRun", JsonObject.mapFrom(apiRequest).toString());

								listPATCHSequenceRun(apiRequest, listSequenceRun).onSuccess(e -> {
									response200PATCHSequenceRun(siteRequest).onSuccess(response -> {
										LOG.debug(String.format("patchSequenceRun succeeded. "));
										eventHandler.handle(Future.succeededFuture(response));
									}).onFailure(ex -> {
										LOG.error(String.format("patchSequenceRun failed. "), ex);
										error(siteRequest, eventHandler, ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("patchSequenceRun failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}
						} catch(Exception ex) {
							LOG.error(String.format("patchSequenceRun failed. "), ex);
							error(siteRequest, eventHandler, ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("patchSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("patchSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("patchSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("patchSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPATCHSequenceRun(ApiRequest apiRequest, SearchList<SequenceRun> listSequenceRun) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSequenceRun.getSiteRequest_();
		listSequenceRun.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			o.setSiteRequest_(siteRequest2);
			futures.add(Future.future(promise1 -> {
				patchSequenceRunFuture(o, false).onSuccess(a -> {
					promise1.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("listPATCHSequenceRun failed. "), ex);
					promise1.fail(ex);
				});
			}));
		});
		CompositeFuture.all(futures).onSuccess( a -> {
			if(apiRequest != null) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSequenceRun.getQueryResponse().getResults().size());
				if(apiRequest.getNumFound() == 1L)
					listSequenceRun.first().apiRequestSequenceRun();
				eventBus.publish("websocketSequenceRun", JsonObject.mapFrom(apiRequest).toString());
			}
			listSequenceRun.next().onSuccess(next -> {
				if(next) {
					listPATCHSequenceRun(apiRequest, listSequenceRun);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPATCHSequenceRun failed. "), ex);
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPATCHSequenceRun failed. "), ex);
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override
	public void patchSequenceRunFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				serviceRequest.getParams().getJsonObject("query").put("rows", 1);
				searchSequenceRunList(siteRequest, false, true, true).onSuccess(listSequenceRun -> {
					try {
						SequenceRun o = listSequenceRun.first();
						if(o != null && listSequenceRun.getQueryResponse().getResults().getNumFound() == 1) {
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
							apiRequest.setPk(Optional.ofNullable(listSequenceRun.first()).map(o2 -> o2.getPk()).orElse(null));
							eventBus.publish("websocketSequenceRun", JsonObject.mapFrom(apiRequest).toString());
							patchSequenceRunFuture(o, false).onSuccess(a -> {
								eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(new JsonObject().encodePrettily()))));
							}).onFailure(ex -> {
								eventHandler.handle(Future.failedFuture(ex));
							});
						} else {
							eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(new JsonObject().encodePrettily()))));
						}
					} catch(Exception ex) {
						LOG.error(String.format("patchSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}).onFailure(ex -> {
					LOG.error(String.format("patchSequenceRun failed. "), ex);
					error(siteRequest, eventHandler, ex);
				});
			} catch(Exception ex) {
				LOG.error(String.format("patchSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			LOG.error(String.format("patchSequenceRun failed. "), ex);
			error(null, eventHandler, ex);
		});
	}

	public Future<SequenceRun> patchSequenceRunFuture(SequenceRun o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<SequenceRun> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			pgPool.withTransaction(sqlConnection -> {
				Promise<SequenceRun> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHSequenceRun(o, inheritPk).onSuccess(sequenceRun -> {
					defineSequenceRun(sequenceRun).onSuccess(c -> {
						relateSequenceRun(sequenceRun).onSuccess(d -> {
							indexSequenceRun(sequenceRun).onSuccess(e -> {
								promise1.complete(sequenceRun);
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
			}).compose(sequenceRun -> {
				Promise<SequenceRun> promise2 = Promise.promise();
				refreshSequenceRun(sequenceRun).onSuccess(a -> {
					promise2.complete(sequenceRun);
				}).onFailure(ex -> {
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(sequenceRun -> {
				promise.complete(sequenceRun);
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSequenceRunFuture failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SequenceRun> sqlPATCHSequenceRun(SequenceRun o, Boolean inheritPk) {
		Promise<SequenceRun> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SequenceRun SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SequenceRun o2 = new SequenceRun();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			for(String entityVar : methodNames) {
				switch(entityVar) {
					case "setInheritPk":
							o2.setInheritPk(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_inheritPk + "=$" + num);
							num++;
							bParams.add(o2.sqlInheritPk());
						break;
					case "setArchived":
							o2.setArchived(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_archived + "=$" + num);
							num++;
							bParams.add(o2.sqlArchived());
						break;
					case "setDeleted":
							o2.setDeleted(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_deleted + "=$" + num);
							num++;
							bParams.add(o2.sqlDeleted());
						break;
					case "setRunId":
							o2.setRunId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_runId + "=$" + num);
							num++;
							bParams.add(o2.sqlRunId());
						break;
					case "setRunReleaseDate":
							o2.setRunReleaseDate(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_runReleaseDate + "=$" + num);
							num++;
							bParams.add(o2.sqlRunReleaseDate());
						break;
					case "setRunLoadDate":
							o2.setRunLoadDate(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_runLoadDate + "=$" + num);
							num++;
							bParams.add(o2.sqlRunLoadDate());
						break;
					case "setProjectId":
							o2.setProjectId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_projectId + "=$" + num);
							num++;
							bParams.add(o2.sqlProjectId());
						break;
					case "setBioSampleId":
							o2.setBioSampleId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SequenceRun.VAR_bioSampleId + "=$" + num);
							num++;
							bParams.add(o2.sqlBioSampleId());
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
						RuntimeException ex2 = new RuntimeException("value SequenceRun failed", ex);
						LOG.error(String.format("relateSequenceRun failed. "), ex2);
						a.handle(Future.failedFuture(ex2));
					});
				}));
			}
			CompositeFuture.all(futures1).onSuccess(a -> {
				CompositeFuture.all(futures2).onSuccess(b -> {
					SequenceRun o3 = new SequenceRun();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					promise.complete(o3);
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPATCHSequenceRun failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPATCHSequenceRun failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPATCHSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}


	public Future<ServiceResponse> response200PATCHSequenceRun(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PATCHSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// GET //

	@Override
	public void getSequenceRun(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				{
					searchSequenceRunList(siteRequest, false, true, false).onSuccess(listSequenceRun -> {
						response200GETSequenceRun(listSequenceRun).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("getSequenceRun succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("getSequenceRun failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("getSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("getSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("getSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("getSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}



	public Future<ServiceResponse> response200GETSequenceRun(SearchList<SequenceRun> listSequenceRun) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSequenceRun.getSiteRequest_();
			SolrDocumentList solrDocuments = listSequenceRun.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSequenceRun.getList().stream().findFirst().orElse(null));
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200GETSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// Search //

	@Override
	public void searchSequenceRun(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				{
					searchSequenceRunList(siteRequest, false, true, false).onSuccess(listSequenceRun -> {
						response200SearchSequenceRun(listSequenceRun).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchSequenceRun succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchSequenceRun failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}



	public Future<ServiceResponse> response200SearchSequenceRun(SearchList<SequenceRun> listSequenceRun) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSequenceRun.getSiteRequest_();
			QueryResponse responseSearch = listSequenceRun.getQueryResponse();
			SolrDocumentList solrDocuments = listSequenceRun.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			String nextCursorMark = responseSearch.getNextCursorMark();
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSequenceRun.getFields();

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
			listSequenceRun.getList().stream().forEach(o -> {
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
					responsePivotSearchSequenceRun(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void responsePivotSearchSequenceRun(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchSequenceRun(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageSequenceRunId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageSequenceRun(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageSequenceRun(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				{
					searchSequenceRunList(siteRequest, false, true, false).onSuccess(listSequenceRun -> {
						response200SearchPageSequenceRun(listSequenceRun).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchpageSequenceRun succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchpageSequenceRun failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchpageSequenceRun failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchpageSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage()) || "invalid_grant: Refresh token expired".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchpageSequenceRun failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchpageSequenceRun failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public void searchpageSequenceRunPageInit(SequenceRunPage page, SearchList<SequenceRun> listSequenceRun) {
	}
	public String templateSearchPageSequenceRun() {
		return Optional.ofNullable(config.getString(ConfigKeys.TEMPLATE_PATH)).orElse("templates") + "/enUS/SequenceRunPage";
	}
	public Future<ServiceResponse> response200SearchPageSequenceRun(SearchList<SequenceRun> listSequenceRun) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSequenceRun.getSiteRequest_();
			SequenceRunPage page = new SequenceRunPage();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			if(listSequenceRun.size() == 1)
				siteRequest.setRequestPk(listSequenceRun.get(0).getPk());
			page.setSearchListSequenceRun_(listSequenceRun);
			page.setSiteRequest_(siteRequest);
			page.promiseDeepSequenceRunPage(siteRequest).onSuccess(a -> {
				JsonObject json = JsonObject.mapFrom(page);
				templateEngine.render(json, templateSearchPageSequenceRun()).onSuccess(buffer -> {
					promise.complete(new ServiceResponse(200, "OK", buffer, requestHeaders));
				}).onFailure(ex -> {
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchPageSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// General //

	public Future<SequenceRun> createSequenceRun(SiteRequestEnUS siteRequest) {
		Promise<SequenceRun> promise = Promise.promise();
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString(ConfigKeys.SITE_ZONE))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString(ConfigKeys.SITE_ZONE))));

			sqlConnection.preparedQuery("INSERT INTO SequenceRun(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)).onSuccess(result -> {
				Row createLine = result.value().stream().findFirst().orElseGet(() -> null);
				Long pk = createLine.getLong(0);
				SequenceRun o = new SequenceRun();
				o.setPk(pk);
				o.setSiteRequest_(siteRequest);
				promise.complete(o);
			}).onFailure(ex -> {
				RuntimeException ex2 = new RuntimeException(ex);
				LOG.error("createSequenceRun failed. ", ex2);
				promise.fail(ex2);
			});
		} catch(Exception ex) {
			LOG.error(String.format("createSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public void searchSequenceRunQ(SearchList<SequenceRun> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String searchSequenceRunFq(SearchList<SequenceRun> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SequenceRun.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SequenceRun.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + ClientUtils.escapeQueryChars(SequenceRun.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed)).replace("\\", "\\\\");
		}
	}

	public void searchSequenceRunSort(SearchList<SequenceRun> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void searchSequenceRunRows(SearchList<SequenceRun> searchList, Integer valueRows) {
			searchList.setRows(valueRows != null ? valueRows : 10);
	}

	public void searchSequenceRunStart(SearchList<SequenceRun> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void searchSequenceRunVar(SearchList<SequenceRun> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void searchSequenceRunUri(SearchList<SequenceRun> searchList) {
	}

	public Future<ServiceResponse> varsSequenceRun(SiteRequestEnUS siteRequest) {
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
					LOG.error(String.format("searchSequenceRun failed. "), ex);
					promise.fail(ex);
				}
			});
			promise.complete();
		} catch(Exception ex) {
			LOG.error(String.format("searchSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<SequenceRun>> searchSequenceRunList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify) {
		Promise<SearchList<SequenceRun>> promise = Promise.promise();
		try {
			ServiceRequest serviceRequest = siteRequest.getServiceRequest();
			String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<SequenceRun> searchList = new SearchList<SequenceRun>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(SequenceRun.class);
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
								varsIndexed[i] = SequenceRun.varIndexedSequenceRun(entityVar);
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
											varIndexed = SequenceRun.varIndexedSequenceRun(entityVar);
											String entityQ = searchSequenceRunFq(searchList, entityVar, valueIndexed, varIndexed);
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
											varIndexed = SequenceRun.varIndexedSequenceRun(entityVar);
											String entityFq = searchSequenceRunFq(searchList, entityVar, valueIndexed, varIndexed);
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
									varIndexed = SequenceRun.varIndexedSequenceRun(entityVar);
									searchSequenceRunSort(searchList, entityVar, valueIndexed, varIndexed);
									break;
								case "start":
									valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchSequenceRunStart(searchList, valueStart);
									break;
								case "rows":
									valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchSequenceRunRows(searchList, valueRows);
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
										varIndexed = SequenceRun.varIndexedSequenceRun(entityVar);
										searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
									}
									break;
								case "facet.field":
									entityVar = (String)paramObject;
									varIndexed = SequenceRun.varIndexedSequenceRun(entityVar);
									if(varIndexed != null)
										searchList.addFacetField(varIndexed);
									break;
								case "var":
									entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
									valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
									searchSequenceRunVar(searchList, entityVar, valueIndexed);
									break;
								case "cursorMark":
									valueCursorMark = (String)paramObject;
									searchList.add("cursorMark", (String)paramObject);
									break;
							}
						}
						searchSequenceRunUri(searchList);
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("created_docvalues_date", ORDER.desc);
			}
			searchSequenceRun2(siteRequest, populate, store, modify, searchList);
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				promise.complete(searchList);
			}).onFailure(ex -> {
				LOG.error(String.format("searchSequenceRun failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void searchSequenceRun2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, SearchList<SequenceRun> searchList) {
	}

	public Future<Void> defineSequenceRun(SequenceRun o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SequenceRun WHERE pk=$1")
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
									LOG.error(String.format("defineSequenceRun failed. "), e);
								}
							}
						}
					}
					promise.complete();
				} catch(Exception ex) {
					LOG.error(String.format("defineSequenceRun failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				RuntimeException ex2 = new RuntimeException(ex);
				LOG.error(String.format("defineSequenceRun failed. "), ex2);
				promise.fail(ex2);
			});
		} catch(Exception ex) {
			LOG.error(String.format("defineSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> relateSequenceRun(SequenceRun o) {
		Promise<Void> promise = Promise.promise();
			promise.complete();
		return promise.future();
	}

	public Future<Void> indexSequenceRun(SequenceRun o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			o.promiseDeepForClass(siteRequest).onSuccess(a -> {
				SolrInputDocument document = new SolrInputDocument();
				o.indexSequenceRun(document);
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
					LOG.error(String.format("indexSequenceRun failed. "), new RuntimeException(ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("indexSequenceRun failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("indexSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> refreshSequenceRun(SequenceRun o) {
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
					eventBus.request("serratus-api-enUS-SequenceRun", json, new DeliveryOptions().addHeader("action", "patchSequenceRunFuture")).onSuccess(c -> {
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
			LOG.error(String.format("refreshSequenceRun failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}
