package io.serratus.api.enus.model.biosample;

import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import io.vertx.pgclient.PgPool;

/**
 * Map.hackathonMission: to create a new Java API implementation class to extend and override any generated API functionality about BioSamples related to RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumn: Develop BioSample API
 * Map.hackathonLabels: Java,Vert.x
 * Translate: false
 **/
public class BioSampleEnUSApiServiceImpl extends BioSampleEnUSGenApiServiceImpl {

	public BioSampleEnUSApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider, HandlebarsTemplateEngine templateEngine) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, templateEngine);
	}
}
