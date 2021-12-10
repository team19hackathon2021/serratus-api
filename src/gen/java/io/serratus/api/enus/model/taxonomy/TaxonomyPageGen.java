package io.serratus.api.enus.model.taxonomy;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.serratus.api.enus.config.ConfigKeys;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.serratus.api.enus.request.api.ApiRequest;
import io.serratus.api.enus.java.ZonedDateTimeDeserializer;
import java.math.RoundingMode;
import org.slf4j.Logger;
import java.math.MathContext;
import io.vertx.core.Promise;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.serratus.api.enus.model.taxonomy.TaxonomyGenPage;
import io.vertx.core.Future;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.java.ZonedDateTimeSerializer;
import org.apache.commons.lang3.math.NumberUtils;
import io.serratus.api.enus.wrap.Wrap;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**	
 * Map.hackathonMission: to create the a new Java backend class to render the page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumn: Develop Taxonomy UI
 * Map.hackathonLabels: Java,Vert.x,Handlebars
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TaxonomyPageGen<DEV> extends TaxonomyGenPage {
	protected static final Logger LOG = LoggerFactory.getLogger(TaxonomyPage.class);

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepTaxonomyPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepTaxonomyPage();
	}

	public Future<Void> promiseDeepTaxonomyPage() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseTaxonomyPage(promise2);
		promise2.future().onSuccess(a -> {
			super.promiseDeepTaxonomyGenPage(siteRequest_).onSuccess(b -> {
				promise.complete();
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseTaxonomyPage(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).onSuccess(a -> {
			promise.complete();
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override public Future<Void> promiseDeepForClass(SiteRequestEnUS siteRequest_) {
		return promiseDeepTaxonomyPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTaxonomyPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestTaxonomyGenPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTaxonomyPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTaxonomyPage(v);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.obtainForClass(v);
			}
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
			}
		}
		return o;
	}
	public Object obtainTaxonomyPage(String var) {
		TaxonomyPage oTaxonomyPage = (TaxonomyPage)this;
		switch(var) {
			default:
				return super.obtainTaxonomyGenPage(var);
		}
	}

	///////////////
	// relate //
	///////////////

	@Override public boolean relateForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = relateTaxonomyPage(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateTaxonomyPage(String var, Object val) {
		TaxonomyPage oTaxonomyPage = (TaxonomyPage)this;
		switch(var) {
			default:
				return super.relateTaxonomyGenPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTaxonomyPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTaxonomyPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return TaxonomyGenPage.staticSetTaxonomyGenPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTaxonomyPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTaxonomyPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return TaxonomyGenPage.staticSolrTaxonomyGenPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTaxonomyPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTaxonomyPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return TaxonomyGenPage.staticSolrStrTaxonomyGenPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTaxonomyPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTaxonomyPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return TaxonomyGenPage.staticSolrFqTaxonomyGenPage(entityVar,  siteRequest_, o);
		}
	}

	/////////////
	// define //
	/////////////

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineTaxonomyPage(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTaxonomyPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineTaxonomyGenPage(var, val);
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTaxonomyPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TaxonomyPage) {
			TaxonomyPage original = (TaxonomyPage)o;
			super.apiRequestTaxonomyGenPage();
		}
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		return sb.toString();
	}

}
