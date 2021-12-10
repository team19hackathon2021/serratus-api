package io.serratus.api.enus.model.taxonomy;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import io.serratus.api.enus.model.taxonomy.Taxonomy;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.serratus.api.enus.config.ConfigKeys;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.serratus.api.enus.request.api.ApiRequest;
import io.serratus.api.enus.search.SearchList;
import java.lang.String;
import io.serratus.api.enus.java.ZonedDateTimeDeserializer;
import java.math.RoundingMode;
import org.slf4j.Logger;
import java.math.MathContext;
import io.vertx.core.Promise;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.serratus.api.enus.base.BaseModelPage;
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
 * Map.hackathonMission: to create the a new base Java backend class to render the page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumn: Develop Taxonomy UI
 * Map.hackathonLabels: Java,Vert.x,Handlebars
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TaxonomyGenPageGen<DEV> extends BaseModelPage {
	protected static final Logger LOG = LoggerFactory.getLogger(TaxonomyGenPage.class);

	/////////////////////////
	// searchListTaxonomy_ //
	/////////////////////////

	/**	 The entity searchListTaxonomy_
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<Taxonomy> searchListTaxonomy_;

	/**	<br/> The entity searchListTaxonomy_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchListTaxonomy_">Find the entity searchListTaxonomy_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchListTaxonomy_(Wrap<SearchList<Taxonomy>> w);

	public SearchList<Taxonomy> getSearchListTaxonomy_() {
		return searchListTaxonomy_;
	}

	public void setSearchListTaxonomy_(SearchList<Taxonomy> searchListTaxonomy_) {
		this.searchListTaxonomy_ = searchListTaxonomy_;
	}
	public static SearchList<Taxonomy> staticSetSearchListTaxonomy_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TaxonomyGenPage searchListTaxonomy_Init() {
		Wrap<SearchList<Taxonomy>> searchListTaxonomy_Wrap = new Wrap<SearchList<Taxonomy>>().var("searchListTaxonomy_");
		if(searchListTaxonomy_ == null) {
			_searchListTaxonomy_(searchListTaxonomy_Wrap);
			setSearchListTaxonomy_(searchListTaxonomy_Wrap.o);
		}
		return (TaxonomyGenPage)this;
	}

	//////////////////
	// listTaxonomy //
	//////////////////

	/**	 The entity listTaxonomy
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut JsonArray(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected JsonArray listTaxonomy = new JsonArray();

	/**	<br/> The entity listTaxonomy
	 *  It is constructed before being initialized with the constructor by default JsonArray(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listTaxonomy">Find the entity listTaxonomy in Solr</a>
	 * <br/>
	 * @param listTaxonomy is the entity already constructed. 
	 **/
	protected abstract void _listTaxonomy(JsonArray l);

	public JsonArray getListTaxonomy() {
		return listTaxonomy;
	}

	public void setListTaxonomy(JsonArray listTaxonomy) {
		this.listTaxonomy = listTaxonomy;
	}
	public static JsonArray staticSetListTaxonomy(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TaxonomyGenPage listTaxonomyInit() {
		_listTaxonomy(listTaxonomy);
		return (TaxonomyGenPage)this;
	}

	///////////////////
	// taxonomyCount //
	///////////////////

	/**	 The entity taxonomyCount
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer taxonomyCount;

	/**	<br/> The entity taxonomyCount
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyCount">Find the entity taxonomyCount in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyCount(Wrap<Integer> w);

	public Integer getTaxonomyCount() {
		return taxonomyCount;
	}

	public void setTaxonomyCount(Integer taxonomyCount) {
		this.taxonomyCount = taxonomyCount;
	}
	@JsonIgnore
	public void setTaxonomyCount(String o) {
		this.taxonomyCount = TaxonomyGenPage.staticSetTaxonomyCount(siteRequest_, o);
	}
	public static Integer staticSetTaxonomyCount(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TaxonomyGenPage taxonomyCountInit() {
		Wrap<Integer> taxonomyCountWrap = new Wrap<Integer>().var("taxonomyCount");
		if(taxonomyCount == null) {
			_taxonomyCount(taxonomyCountWrap);
			setTaxonomyCount(taxonomyCountWrap.o);
		}
		return (TaxonomyGenPage)this;
	}

	public static Integer staticSolrTaxonomyCount(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrTaxonomyCount(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyCount(SiteRequestEnUS siteRequest_, String o) {
		return TaxonomyGenPage.staticSolrStrTaxonomyCount(siteRequest_, TaxonomyGenPage.staticSolrTaxonomyCount(siteRequest_, TaxonomyGenPage.staticSetTaxonomyCount(siteRequest_, o)));
	}

	///////////////
	// taxonomy_ //
	///////////////

	/**	 The entity taxonomy_
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Taxonomy taxonomy_;

	/**	<br/> The entity taxonomy_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomy_">Find the entity taxonomy_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomy_(Wrap<Taxonomy> w);

	public Taxonomy getTaxonomy_() {
		return taxonomy_;
	}

	public void setTaxonomy_(Taxonomy taxonomy_) {
		this.taxonomy_ = taxonomy_;
	}
	public static Taxonomy staticSetTaxonomy_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TaxonomyGenPage taxonomy_Init() {
		Wrap<Taxonomy> taxonomy_Wrap = new Wrap<Taxonomy>().var("taxonomy_");
		if(taxonomy_ == null) {
			_taxonomy_(taxonomy_Wrap);
			setTaxonomy_(taxonomy_Wrap.o);
		}
		return (TaxonomyGenPage)this;
	}

	////////
	// pk //
	////////

	/**	 The entity pk
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long pk;

	/**	<br/> The entity pk
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.taxonomy.TaxonomyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pk">Find the entity pk in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pk(Wrap<Long> w);

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}
	@JsonIgnore
	public void setPk(String o) {
		this.pk = TaxonomyGenPage.staticSetPk(siteRequest_, o);
	}
	public static Long staticSetPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TaxonomyGenPage pkInit() {
		Wrap<Long> pkWrap = new Wrap<Long>().var("pk");
		if(pk == null) {
			_pk(pkWrap);
			setPk(pkWrap.o);
		}
		return (TaxonomyGenPage)this;
	}

	public static Long staticSolrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPk(SiteRequestEnUS siteRequest_, String o) {
		return TaxonomyGenPage.staticSolrStrPk(siteRequest_, TaxonomyGenPage.staticSolrPk(siteRequest_, TaxonomyGenPage.staticSetPk(siteRequest_, o)));
	}

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepTaxonomyGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepTaxonomyGenPage();
	}

	public Future<Void> promiseDeepTaxonomyGenPage() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseTaxonomyGenPage(promise2);
		promise2.future().onSuccess(a -> {
			super.promiseDeepBaseModelPage(siteRequest_).onSuccess(b -> {
				promise.complete();
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseTaxonomyGenPage(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				searchListTaxonomy_Init();
				listTaxonomyInit();
				taxonomyCountInit();
				taxonomy_Init();
				pkInit();
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
		return promiseDeepTaxonomyGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTaxonomyGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModelPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTaxonomyGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTaxonomyGenPage(v);
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
	public Object obtainTaxonomyGenPage(String var) {
		TaxonomyGenPage oTaxonomyGenPage = (TaxonomyGenPage)this;
		switch(var) {
			case "searchListTaxonomy_":
				return oTaxonomyGenPage.searchListTaxonomy_;
			case "listTaxonomy":
				return oTaxonomyGenPage.listTaxonomy;
			case "taxonomyCount":
				return oTaxonomyGenPage.taxonomyCount;
			case "taxonomy_":
				return oTaxonomyGenPage.taxonomy_;
			case "pk":
				return oTaxonomyGenPage.pk;
			default:
				return super.obtainBaseModelPage(var);
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
				o = relateTaxonomyGenPage(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateTaxonomyGenPage(String var, Object val) {
		TaxonomyGenPage oTaxonomyGenPage = (TaxonomyGenPage)this;
		switch(var) {
			default:
				return super.relateBaseModelPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTaxonomyGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTaxonomyGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "taxonomyCount":
			return TaxonomyGenPage.staticSetTaxonomyCount(siteRequest_, o);
		case "pk":
			return TaxonomyGenPage.staticSetPk(siteRequest_, o);
			default:
				return BaseModelPage.staticSetBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTaxonomyGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTaxonomyGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "taxonomyCount":
			return TaxonomyGenPage.staticSolrTaxonomyCount(siteRequest_, (Integer)o);
		case "pk":
			return TaxonomyGenPage.staticSolrPk(siteRequest_, (Long)o);
			default:
				return BaseModelPage.staticSolrBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTaxonomyGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTaxonomyGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "taxonomyCount":
			return TaxonomyGenPage.staticSolrStrTaxonomyCount(siteRequest_, (Integer)o);
		case "pk":
			return TaxonomyGenPage.staticSolrStrPk(siteRequest_, (Long)o);
			default:
				return BaseModelPage.staticSolrStrBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTaxonomyGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTaxonomyGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "taxonomyCount":
			return TaxonomyGenPage.staticSolrFqTaxonomyCount(siteRequest_, o);
		case "pk":
			return TaxonomyGenPage.staticSolrFqPk(siteRequest_, o);
			default:
				return BaseModelPage.staticSolrFqBaseModelPage(entityVar,  siteRequest_, o);
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
					o = defineTaxonomyGenPage(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTaxonomyGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineBaseModelPage(var, val);
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTaxonomyGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TaxonomyGenPage) {
			TaxonomyGenPage original = (TaxonomyGenPage)o;
			super.apiRequestBaseModelPage();
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

	public static final String VAR_searchListTaxonomy_ = "searchListTaxonomy_";
	public static final String VAR_listTaxonomy = "listTaxonomy";
	public static final String VAR_taxonomyCount = "taxonomyCount";
	public static final String VAR_taxonomy_ = "taxonomy_";
	public static final String VAR_pk = "pk";
}
