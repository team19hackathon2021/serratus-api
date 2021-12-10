package io.serratus.api.enus.model.taxonomy;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
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
import io.vertx.core.json.JsonObject;
import java.lang.String;
import io.serratus.api.enus.java.ZonedDateTimeDeserializer;
import java.math.RoundingMode;
import org.slf4j.Logger;
import java.math.MathContext;
import io.vertx.core.Promise;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.util.Set;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.core.Future;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import io.serratus.api.enus.base.BaseModel;
import java.util.List;
import io.serratus.api.enus.java.ZonedDateTimeSerializer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.commons.lang3.math.NumberUtils;
import io.serratus.api.enus.wrap.Wrap;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**	
 * Map.hackathonMission: to create a generated Java class that can be extended and override these methods to store information about taxonomies related to RNA seqence runs. 
 * Map.hackathonColumn: Develop Taxonomy API
 * Map.hackathonLabels: Java
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.Taxonomy&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TaxonomyGen<DEV> extends BaseModel {
	protected static final Logger LOG = LoggerFactory.getLogger(Taxonomy.class);

	public static final String Taxonomy_AName = "a taxonomy";
	public static final String Taxonomy_This = "this ";
	public static final String Taxonomy_ThisName = "this taxonomy";
	public static final String Taxonomy_A = "a ";
	public static final String Taxonomy_TheName = "the taxonomy";
	public static final String Taxonomy_NameSingular = "taxonomy";
	public static final String Taxonomy_NamePlural = "taxonomies";
	public static final String Taxonomy_NameActual = "current taxonomy";
	public static final String Taxonomy_AllName = "all the taxonomies";
	public static final String Taxonomy_SearchAllNameBy = "search taxonomies by ";
	public static final String Taxonomy_Title = "taxonomies";
	public static final String Taxonomy_ThePluralName = "the taxonomies";
	public static final String Taxonomy_NoNameFound = "no taxonomy found";
	public static final String Taxonomy_NameVar = "taxonomy";
	public static final String Taxonomy_OfName = "of taxonomy";
	public static final String Taxonomy_ANameAdjective = "a taxonomy";
	public static final String Taxonomy_NameAdjectiveSingular = "taxonomy";
	public static final String Taxonomy_NameAdjectivePlural = "taxonomies";
	public static final String Taxonomy_Color = "pale-green";
	public static final String Taxonomy_IconGroup = "regular";
	public static final String Taxonomy_IconName = "project-diagram";

	////////////////
	// taxonomyId //
	////////////////

	/**	 The entity taxonomyId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String taxonomyId;

	/**	<br/> The entity taxonomyId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.Taxonomy&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyId">Find the entity taxonomyId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyId(Wrap<String> w);

	public String getTaxonomyId() {
		return taxonomyId;
	}
	public void setTaxonomyId(String o) {
		this.taxonomyId = Taxonomy.staticSetTaxonomyId(siteRequest_, o);
	}
	public static String staticSetTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Taxonomy taxonomyIdInit() {
		Wrap<String> taxonomyIdWrap = new Wrap<String>().var("taxonomyId");
		if(taxonomyId == null) {
			_taxonomyId(taxonomyIdWrap);
			setTaxonomyId(taxonomyIdWrap.o);
		}
		return (Taxonomy)this;
	}

	public static String staticSolrTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return Taxonomy.staticSolrStrTaxonomyId(siteRequest_, Taxonomy.staticSolrTaxonomyId(siteRequest_, Taxonomy.staticSetTaxonomyId(siteRequest_, o)));
	}

	public String sqlTaxonomyId() {
		return taxonomyId;
	}

	//////////////////
	// taxonomyName //
	//////////////////

	/**	 The entity taxonomyName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String taxonomyName;

	/**	<br/> The entity taxonomyName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.Taxonomy&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyName">Find the entity taxonomyName in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyName(Wrap<String> w);

	public String getTaxonomyName() {
		return taxonomyName;
	}
	public void setTaxonomyName(String o) {
		this.taxonomyName = Taxonomy.staticSetTaxonomyName(siteRequest_, o);
	}
	public static String staticSetTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Taxonomy taxonomyNameInit() {
		Wrap<String> taxonomyNameWrap = new Wrap<String>().var("taxonomyName");
		if(taxonomyName == null) {
			_taxonomyName(taxonomyNameWrap);
			setTaxonomyName(taxonomyNameWrap.o);
		}
		return (Taxonomy)this;
	}

	public static String staticSolrTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return Taxonomy.staticSolrStrTaxonomyName(siteRequest_, Taxonomy.staticSolrTaxonomyName(siteRequest_, Taxonomy.staticSetTaxonomyName(siteRequest_, o)));
	}

	public String sqlTaxonomyName() {
		return taxonomyName;
	}

	////////////////////////
	// taxonomyUniqueName //
	////////////////////////

	/**	 The entity taxonomyUniqueName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String taxonomyUniqueName;

	/**	<br/> The entity taxonomyUniqueName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.Taxonomy&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyUniqueName">Find the entity taxonomyUniqueName in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyUniqueName(Wrap<String> w);

	public String getTaxonomyUniqueName() {
		return taxonomyUniqueName;
	}
	public void setTaxonomyUniqueName(String o) {
		this.taxonomyUniqueName = Taxonomy.staticSetTaxonomyUniqueName(siteRequest_, o);
	}
	public static String staticSetTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Taxonomy taxonomyUniqueNameInit() {
		Wrap<String> taxonomyUniqueNameWrap = new Wrap<String>().var("taxonomyUniqueName");
		if(taxonomyUniqueName == null) {
			_taxonomyUniqueName(taxonomyUniqueNameWrap);
			setTaxonomyUniqueName(taxonomyUniqueNameWrap.o);
		}
		return (Taxonomy)this;
	}

	public static String staticSolrTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return Taxonomy.staticSolrStrTaxonomyUniqueName(siteRequest_, Taxonomy.staticSolrTaxonomyUniqueName(siteRequest_, Taxonomy.staticSetTaxonomyUniqueName(siteRequest_, o)));
	}

	public String sqlTaxonomyUniqueName() {
		return taxonomyUniqueName;
	}

	///////////////////////
	// taxonomyNameClass //
	///////////////////////

	/**	 The entity taxonomyNameClass
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String taxonomyNameClass;

	/**	<br/> The entity taxonomyNameClass
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.Taxonomy&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyNameClass">Find the entity taxonomyNameClass in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyNameClass(Wrap<String> w);

	public String getTaxonomyNameClass() {
		return taxonomyNameClass;
	}
	public void setTaxonomyNameClass(String o) {
		this.taxonomyNameClass = Taxonomy.staticSetTaxonomyNameClass(siteRequest_, o);
	}
	public static String staticSetTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Taxonomy taxonomyNameClassInit() {
		Wrap<String> taxonomyNameClassWrap = new Wrap<String>().var("taxonomyNameClass");
		if(taxonomyNameClass == null) {
			_taxonomyNameClass(taxonomyNameClassWrap);
			setTaxonomyNameClass(taxonomyNameClassWrap.o);
		}
		return (Taxonomy)this;
	}

	public static String staticSolrTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return Taxonomy.staticSolrStrTaxonomyNameClass(siteRequest_, Taxonomy.staticSolrTaxonomyNameClass(siteRequest_, Taxonomy.staticSetTaxonomyNameClass(siteRequest_, o)));
	}

	public String sqlTaxonomyNameClass() {
		return taxonomyNameClass;
	}

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepTaxonomy(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepTaxonomy();
	}

	public Future<Void> promiseDeepTaxonomy() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseTaxonomy(promise2);
		promise2.future().onSuccess(a -> {
			super.promiseDeepBaseModel(siteRequest_).onSuccess(b -> {
				promise.complete();
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseTaxonomy(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				taxonomyIdInit();
				taxonomyNameInit();
				taxonomyUniqueNameInit();
				taxonomyNameClassInit();
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
		return promiseDeepTaxonomy(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTaxonomy(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModel(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTaxonomy(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTaxonomy(v);
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
	public Object obtainTaxonomy(String var) {
		Taxonomy oTaxonomy = (Taxonomy)this;
		switch(var) {
			case "taxonomyId":
				return oTaxonomy.taxonomyId;
			case "taxonomyName":
				return oTaxonomy.taxonomyName;
			case "taxonomyUniqueName":
				return oTaxonomy.taxonomyUniqueName;
			case "taxonomyNameClass":
				return oTaxonomy.taxonomyNameClass;
			default:
				return super.obtainBaseModel(var);
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
				o = relateTaxonomy(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateTaxonomy(String var, Object val) {
		Taxonomy oTaxonomy = (Taxonomy)this;
		switch(var) {
			default:
				return super.relateBaseModel(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTaxonomy(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTaxonomy(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "taxonomyId":
			return Taxonomy.staticSetTaxonomyId(siteRequest_, o);
		case "taxonomyName":
			return Taxonomy.staticSetTaxonomyName(siteRequest_, o);
		case "taxonomyUniqueName":
			return Taxonomy.staticSetTaxonomyUniqueName(siteRequest_, o);
		case "taxonomyNameClass":
			return Taxonomy.staticSetTaxonomyNameClass(siteRequest_, o);
			default:
				return BaseModel.staticSetBaseModel(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTaxonomy(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTaxonomy(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "taxonomyId":
			return Taxonomy.staticSolrTaxonomyId(siteRequest_, (String)o);
		case "taxonomyName":
			return Taxonomy.staticSolrTaxonomyName(siteRequest_, (String)o);
		case "taxonomyUniqueName":
			return Taxonomy.staticSolrTaxonomyUniqueName(siteRequest_, (String)o);
		case "taxonomyNameClass":
			return Taxonomy.staticSolrTaxonomyNameClass(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTaxonomy(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTaxonomy(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "taxonomyId":
			return Taxonomy.staticSolrStrTaxonomyId(siteRequest_, (String)o);
		case "taxonomyName":
			return Taxonomy.staticSolrStrTaxonomyName(siteRequest_, (String)o);
		case "taxonomyUniqueName":
			return Taxonomy.staticSolrStrTaxonomyUniqueName(siteRequest_, (String)o);
		case "taxonomyNameClass":
			return Taxonomy.staticSolrStrTaxonomyNameClass(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrStrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTaxonomy(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTaxonomy(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "taxonomyId":
			return Taxonomy.staticSolrFqTaxonomyId(siteRequest_, o);
		case "taxonomyName":
			return Taxonomy.staticSolrFqTaxonomyName(siteRequest_, o);
		case "taxonomyUniqueName":
			return Taxonomy.staticSolrFqTaxonomyUniqueName(siteRequest_, o);
		case "taxonomyNameClass":
			return Taxonomy.staticSolrFqTaxonomyNameClass(siteRequest_, o);
			default:
				return BaseModel.staticSolrFqBaseModel(entityVar,  siteRequest_, o);
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
					o = defineTaxonomy(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTaxonomy(String var, Object val) {
		switch(var.toLowerCase()) {
			case "taxonomyid":
				if(val instanceof String)
					setTaxonomyId((String)val);
				saves.add("taxonomyId");
				return val;
			case "taxonomyname":
				if(val instanceof String)
					setTaxonomyName((String)val);
				saves.add("taxonomyName");
				return val;
			case "taxonomyuniquename":
				if(val instanceof String)
					setTaxonomyUniqueName((String)val);
				saves.add("taxonomyUniqueName");
				return val;
			case "taxonomynameclass":
				if(val instanceof String)
					setTaxonomyNameClass((String)val);
				saves.add("taxonomyNameClass");
				return val;
			default:
				return super.defineBaseModel(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateTaxonomy(solrDocument);
	}
	public void populateTaxonomy(SolrDocument solrDocument) {
		Taxonomy oTaxonomy = (Taxonomy)this;
		saves = (List<String>)solrDocument.get("saves_docvalues_strings");
		if(saves != null) {
		}

		super.populateBaseModel(solrDocument);
	}

	public void indexTaxonomy(SolrInputDocument document) {
		if(taxonomyId != null) {
			document.addField("taxonomyId_docvalues_string", taxonomyId);
		}
		if(taxonomyName != null) {
			document.addField("taxonomyName_docvalues_string", taxonomyName);
		}
		if(taxonomyUniqueName != null) {
			document.addField("taxonomyUniqueName_docvalues_string", taxonomyUniqueName);
		}
		if(taxonomyNameClass != null) {
			document.addField("taxonomyNameClass_docvalues_string", taxonomyNameClass);
		}
		super.indexBaseModel(document);

	}

	public static String varIndexedTaxonomy(String entityVar) {
		switch(entityVar) {
			case "taxonomyId":
				return "taxonomyId_docvalues_string";
			case "taxonomyName":
				return "taxonomyName_docvalues_string";
			case "taxonomyUniqueName":
				return "taxonomyUniqueName_docvalues_string";
			case "taxonomyNameClass":
				return "taxonomyNameClass_docvalues_string";
			default:
				return BaseModel.varIndexedBaseModel(entityVar);
		}
	}

	public static String varSearchTaxonomy(String entityVar) {
		switch(entityVar) {
			default:
				return BaseModel.varSearchBaseModel(entityVar);
		}
	}

	public static String varSuggestedTaxonomy(String entityVar) {
		switch(entityVar) {
			default:
				return BaseModel.varSuggestedBaseModel(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeTaxonomy(solrDocument);
	}
	public void storeTaxonomy(SolrDocument solrDocument) {
		Taxonomy oTaxonomy = (Taxonomy)this;


		super.storeBaseModel(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTaxonomy() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof Taxonomy) {
			Taxonomy original = (Taxonomy)o;
			if(!Objects.equals(taxonomyId, original.getTaxonomyId()))
				apiRequest.addVars("taxonomyId");
			if(!Objects.equals(taxonomyName, original.getTaxonomyName()))
				apiRequest.addVars("taxonomyName");
			if(!Objects.equals(taxonomyUniqueName, original.getTaxonomyUniqueName()))
				apiRequest.addVars("taxonomyUniqueName");
			if(!Objects.equals(taxonomyNameClass, original.getTaxonomyNameClass()))
				apiRequest.addVars("taxonomyNameClass");
			super.apiRequestBaseModel();
		}
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(Optional.ofNullable(taxonomyId).map(v -> "taxonomyId: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyName).map(v -> "taxonomyName: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyUniqueName).map(v -> "taxonomyUniqueName: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyNameClass).map(v -> "taxonomyNameClass: \"" + v + "\"\n" ).orElse(""));
		return sb.toString();
	}

	public static final String VAR_taxonomyId = "taxonomyId";
	public static final String VAR_taxonomyName = "taxonomyName";
	public static final String VAR_taxonomyUniqueName = "taxonomyUniqueName";
	public static final String VAR_taxonomyNameClass = "taxonomyNameClass";
}
