package io.serratus.api.enus.model.biosample;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.serratus.api.enus.model.biosample.BioSample;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
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
 * Map.hackathonMission: to create the a new base Java backend class to render the page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumn: Develop BioSample UI
 * Map.hackathonLabels: Java,Vert.x,Handlebars
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSampleGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class BioSampleGenPageGen<DEV> extends BaseModelPage {
	protected static final Logger LOG = LoggerFactory.getLogger(BioSampleGenPage.class);

	//////////////////////////
	// searchListBioSample_ //
	//////////////////////////

	/**	 The entity searchListBioSample_
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<BioSample> searchListBioSample_;

	/**	<br/> The entity searchListBioSample_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSampleGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchListBioSample_">Find the entity searchListBioSample_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchListBioSample_(Wrap<SearchList<BioSample>> w);

	public SearchList<BioSample> getSearchListBioSample_() {
		return searchListBioSample_;
	}

	public void setSearchListBioSample_(SearchList<BioSample> searchListBioSample_) {
		this.searchListBioSample_ = searchListBioSample_;
	}
	public static SearchList<BioSample> staticSetSearchListBioSample_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected BioSampleGenPage searchListBioSample_Init() {
		Wrap<SearchList<BioSample>> searchListBioSample_Wrap = new Wrap<SearchList<BioSample>>().var("searchListBioSample_");
		if(searchListBioSample_ == null) {
			_searchListBioSample_(searchListBioSample_Wrap);
			setSearchListBioSample_(searchListBioSample_Wrap.o);
		}
		return (BioSampleGenPage)this;
	}

	///////////////////
	// listBioSample //
	///////////////////

	/**	 The entity listBioSample
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut JsonArray(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected JsonArray listBioSample = new JsonArray();

	/**	<br/> The entity listBioSample
	 *  It is constructed before being initialized with the constructor by default JsonArray(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSampleGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listBioSample">Find the entity listBioSample in Solr</a>
	 * <br/>
	 * @param listBioSample is the entity already constructed. 
	 **/
	protected abstract void _listBioSample(JsonArray l);

	public JsonArray getListBioSample() {
		return listBioSample;
	}

	public void setListBioSample(JsonArray listBioSample) {
		this.listBioSample = listBioSample;
	}
	public static JsonArray staticSetListBioSample(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected BioSampleGenPage listBioSampleInit() {
		_listBioSample(listBioSample);
		return (BioSampleGenPage)this;
	}

	////////////////////
	// bioSampleCount //
	////////////////////

	/**	 The entity bioSampleCount
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer bioSampleCount;

	/**	<br/> The entity bioSampleCount
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSampleGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleCount">Find the entity bioSampleCount in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleCount(Wrap<Integer> w);

	public Integer getBioSampleCount() {
		return bioSampleCount;
	}

	public void setBioSampleCount(Integer bioSampleCount) {
		this.bioSampleCount = bioSampleCount;
	}
	@JsonIgnore
	public void setBioSampleCount(String o) {
		this.bioSampleCount = BioSampleGenPage.staticSetBioSampleCount(siteRequest_, o);
	}
	public static Integer staticSetBioSampleCount(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected BioSampleGenPage bioSampleCountInit() {
		Wrap<Integer> bioSampleCountWrap = new Wrap<Integer>().var("bioSampleCount");
		if(bioSampleCount == null) {
			_bioSampleCount(bioSampleCountWrap);
			setBioSampleCount(bioSampleCountWrap.o);
		}
		return (BioSampleGenPage)this;
	}

	public static Integer staticSolrBioSampleCount(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrBioSampleCount(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleCount(SiteRequestEnUS siteRequest_, String o) {
		return BioSampleGenPage.staticSolrStrBioSampleCount(siteRequest_, BioSampleGenPage.staticSolrBioSampleCount(siteRequest_, BioSampleGenPage.staticSetBioSampleCount(siteRequest_, o)));
	}

	////////////////
	// bioSample_ //
	////////////////

	/**	 The entity bioSample_
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected BioSample bioSample_;

	/**	<br/> The entity bioSample_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSampleGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSample_">Find the entity bioSample_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSample_(Wrap<BioSample> w);

	public BioSample getBioSample_() {
		return bioSample_;
	}

	public void setBioSample_(BioSample bioSample_) {
		this.bioSample_ = bioSample_;
	}
	public static BioSample staticSetBioSample_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected BioSampleGenPage bioSample_Init() {
		Wrap<BioSample> bioSample_Wrap = new Wrap<BioSample>().var("bioSample_");
		if(bioSample_ == null) {
			_bioSample_(bioSample_Wrap);
			setBioSample_(bioSample_Wrap.o);
		}
		return (BioSampleGenPage)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSampleGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pk">Find the entity pk in Solr</a>
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
		this.pk = BioSampleGenPage.staticSetPk(siteRequest_, o);
	}
	public static Long staticSetPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected BioSampleGenPage pkInit() {
		Wrap<Long> pkWrap = new Wrap<Long>().var("pk");
		if(pk == null) {
			_pk(pkWrap);
			setPk(pkWrap.o);
		}
		return (BioSampleGenPage)this;
	}

	public static Long staticSolrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPk(SiteRequestEnUS siteRequest_, String o) {
		return BioSampleGenPage.staticSolrStrPk(siteRequest_, BioSampleGenPage.staticSolrPk(siteRequest_, BioSampleGenPage.staticSetPk(siteRequest_, o)));
	}

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepBioSampleGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepBioSampleGenPage();
	}

	public Future<Void> promiseDeepBioSampleGenPage() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseBioSampleGenPage(promise2);
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

	public Future<Void> promiseBioSampleGenPage(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				searchListBioSample_Init();
				listBioSampleInit();
				bioSampleCountInit();
				bioSample_Init();
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
		return promiseDeepBioSampleGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestBioSampleGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModelPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestBioSampleGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainBioSampleGenPage(v);
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
	public Object obtainBioSampleGenPage(String var) {
		BioSampleGenPage oBioSampleGenPage = (BioSampleGenPage)this;
		switch(var) {
			case "searchListBioSample_":
				return oBioSampleGenPage.searchListBioSample_;
			case "listBioSample":
				return oBioSampleGenPage.listBioSample;
			case "bioSampleCount":
				return oBioSampleGenPage.bioSampleCount;
			case "bioSample_":
				return oBioSampleGenPage.bioSample_;
			case "pk":
				return oBioSampleGenPage.pk;
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
				o = relateBioSampleGenPage(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateBioSampleGenPage(String var, Object val) {
		BioSampleGenPage oBioSampleGenPage = (BioSampleGenPage)this;
		switch(var) {
			default:
				return super.relateBaseModelPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetBioSampleGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetBioSampleGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "bioSampleCount":
			return BioSampleGenPage.staticSetBioSampleCount(siteRequest_, o);
		case "pk":
			return BioSampleGenPage.staticSetPk(siteRequest_, o);
			default:
				return BaseModelPage.staticSetBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrBioSampleGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrBioSampleGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "bioSampleCount":
			return BioSampleGenPage.staticSolrBioSampleCount(siteRequest_, (Integer)o);
		case "pk":
			return BioSampleGenPage.staticSolrPk(siteRequest_, (Long)o);
			default:
				return BaseModelPage.staticSolrBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrBioSampleGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrBioSampleGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "bioSampleCount":
			return BioSampleGenPage.staticSolrStrBioSampleCount(siteRequest_, (Integer)o);
		case "pk":
			return BioSampleGenPage.staticSolrStrPk(siteRequest_, (Long)o);
			default:
				return BaseModelPage.staticSolrStrBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqBioSampleGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqBioSampleGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "bioSampleCount":
			return BioSampleGenPage.staticSolrFqBioSampleCount(siteRequest_, o);
		case "pk":
			return BioSampleGenPage.staticSolrFqPk(siteRequest_, o);
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
					o = defineBioSampleGenPage(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineBioSampleGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineBaseModelPage(var, val);
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestBioSampleGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof BioSampleGenPage) {
			BioSampleGenPage original = (BioSampleGenPage)o;
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

	public static final String VAR_searchListBioSample_ = "searchListBioSample_";
	public static final String VAR_listBioSample = "listBioSample";
	public static final String VAR_bioSampleCount = "bioSampleCount";
	public static final String VAR_bioSample_ = "bioSample_";
	public static final String VAR_pk = "pk";
}
