package io.serratus.api.enus.model.run;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import io.serratus.api.enus.model.run.SequenceRun;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * Map.hackathonMission: to create the a new base Java backend class to render the page for RNA sequence runs. 
 * Map.hackathonColumn: Develop SequenceRun UI
 * Map.hackathonLabels: Java,Vert.x,Handlebars
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRunGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SequenceRunGenPageGen<DEV> extends BaseModelPage {
	protected static final Logger LOG = LoggerFactory.getLogger(SequenceRunGenPage.class);

	////////////////////////////
	// searchListSequenceRun_ //
	////////////////////////////

	/**	 The entity searchListSequenceRun_
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<SequenceRun> searchListSequenceRun_;

	/**	<br/> The entity searchListSequenceRun_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRunGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchListSequenceRun_">Find the entity searchListSequenceRun_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchListSequenceRun_(Wrap<SearchList<SequenceRun>> w);

	public SearchList<SequenceRun> getSearchListSequenceRun_() {
		return searchListSequenceRun_;
	}

	public void setSearchListSequenceRun_(SearchList<SequenceRun> searchListSequenceRun_) {
		this.searchListSequenceRun_ = searchListSequenceRun_;
	}
	public static SearchList<SequenceRun> staticSetSearchListSequenceRun_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SequenceRunGenPage searchListSequenceRun_Init() {
		Wrap<SearchList<SequenceRun>> searchListSequenceRun_Wrap = new Wrap<SearchList<SequenceRun>>().var("searchListSequenceRun_");
		if(searchListSequenceRun_ == null) {
			_searchListSequenceRun_(searchListSequenceRun_Wrap);
			setSearchListSequenceRun_(searchListSequenceRun_Wrap.o);
		}
		return (SequenceRunGenPage)this;
	}

	/////////////////////
	// listSequenceRun //
	/////////////////////

	/**	 The entity listSequenceRun
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut JsonArray(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected JsonArray listSequenceRun = new JsonArray();

	/**	<br/> The entity listSequenceRun
	 *  It is constructed before being initialized with the constructor by default JsonArray(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRunGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listSequenceRun">Find the entity listSequenceRun in Solr</a>
	 * <br/>
	 * @param listSequenceRun is the entity already constructed. 
	 **/
	protected abstract void _listSequenceRun(JsonArray l);

	public JsonArray getListSequenceRun() {
		return listSequenceRun;
	}

	public void setListSequenceRun(JsonArray listSequenceRun) {
		this.listSequenceRun = listSequenceRun;
	}
	public static JsonArray staticSetListSequenceRun(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SequenceRunGenPage listSequenceRunInit() {
		_listSequenceRun(listSequenceRun);
		return (SequenceRunGenPage)this;
	}

	//////////////////////
	// sequenceRunCount //
	//////////////////////

	/**	 The entity sequenceRunCount
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer sequenceRunCount;

	/**	<br/> The entity sequenceRunCount
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRunGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sequenceRunCount">Find the entity sequenceRunCount in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sequenceRunCount(Wrap<Integer> w);

	public Integer getSequenceRunCount() {
		return sequenceRunCount;
	}

	public void setSequenceRunCount(Integer sequenceRunCount) {
		this.sequenceRunCount = sequenceRunCount;
	}
	@JsonIgnore
	public void setSequenceRunCount(String o) {
		this.sequenceRunCount = SequenceRunGenPage.staticSetSequenceRunCount(siteRequest_, o);
	}
	public static Integer staticSetSequenceRunCount(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SequenceRunGenPage sequenceRunCountInit() {
		Wrap<Integer> sequenceRunCountWrap = new Wrap<Integer>().var("sequenceRunCount");
		if(sequenceRunCount == null) {
			_sequenceRunCount(sequenceRunCountWrap);
			setSequenceRunCount(sequenceRunCountWrap.o);
		}
		return (SequenceRunGenPage)this;
	}

	public static Integer staticSolrSequenceRunCount(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrSequenceRunCount(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSequenceRunCount(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRunGenPage.staticSolrStrSequenceRunCount(siteRequest_, SequenceRunGenPage.staticSolrSequenceRunCount(siteRequest_, SequenceRunGenPage.staticSetSequenceRunCount(siteRequest_, o)));
	}

	//////////////////
	// sequenceRun_ //
	//////////////////

	/**	 The entity sequenceRun_
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected SequenceRun sequenceRun_;

	/**	<br/> The entity sequenceRun_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRunGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sequenceRun_">Find the entity sequenceRun_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sequenceRun_(Wrap<SequenceRun> w);

	public SequenceRun getSequenceRun_() {
		return sequenceRun_;
	}

	public void setSequenceRun_(SequenceRun sequenceRun_) {
		this.sequenceRun_ = sequenceRun_;
	}
	public static SequenceRun staticSetSequenceRun_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SequenceRunGenPage sequenceRun_Init() {
		Wrap<SequenceRun> sequenceRun_Wrap = new Wrap<SequenceRun>().var("sequenceRun_");
		if(sequenceRun_ == null) {
			_sequenceRun_(sequenceRun_Wrap);
			setSequenceRun_(sequenceRun_Wrap.o);
		}
		return (SequenceRunGenPage)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRunGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pk">Find the entity pk in Solr</a>
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
		this.pk = SequenceRunGenPage.staticSetPk(siteRequest_, o);
	}
	public static Long staticSetPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SequenceRunGenPage pkInit() {
		Wrap<Long> pkWrap = new Wrap<Long>().var("pk");
		if(pk == null) {
			_pk(pkWrap);
			setPk(pkWrap.o);
		}
		return (SequenceRunGenPage)this;
	}

	public static Long staticSolrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPk(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRunGenPage.staticSolrStrPk(siteRequest_, SequenceRunGenPage.staticSolrPk(siteRequest_, SequenceRunGenPage.staticSetPk(siteRequest_, o)));
	}

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepSequenceRunGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepSequenceRunGenPage();
	}

	public Future<Void> promiseDeepSequenceRunGenPage() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseSequenceRunGenPage(promise2);
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

	public Future<Void> promiseSequenceRunGenPage(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				searchListSequenceRun_Init();
				listSequenceRunInit();
				sequenceRunCountInit();
				sequenceRun_Init();
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
		return promiseDeepSequenceRunGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSequenceRunGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModelPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSequenceRunGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSequenceRunGenPage(v);
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
	public Object obtainSequenceRunGenPage(String var) {
		SequenceRunGenPage oSequenceRunGenPage = (SequenceRunGenPage)this;
		switch(var) {
			case "searchListSequenceRun_":
				return oSequenceRunGenPage.searchListSequenceRun_;
			case "listSequenceRun":
				return oSequenceRunGenPage.listSequenceRun;
			case "sequenceRunCount":
				return oSequenceRunGenPage.sequenceRunCount;
			case "sequenceRun_":
				return oSequenceRunGenPage.sequenceRun_;
			case "pk":
				return oSequenceRunGenPage.pk;
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
				o = relateSequenceRunGenPage(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateSequenceRunGenPage(String var, Object val) {
		SequenceRunGenPage oSequenceRunGenPage = (SequenceRunGenPage)this;
		switch(var) {
			default:
				return super.relateBaseModelPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSequenceRunGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSequenceRunGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "sequenceRunCount":
			return SequenceRunGenPage.staticSetSequenceRunCount(siteRequest_, o);
		case "pk":
			return SequenceRunGenPage.staticSetPk(siteRequest_, o);
			default:
				return BaseModelPage.staticSetBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSequenceRunGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSequenceRunGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "sequenceRunCount":
			return SequenceRunGenPage.staticSolrSequenceRunCount(siteRequest_, (Integer)o);
		case "pk":
			return SequenceRunGenPage.staticSolrPk(siteRequest_, (Long)o);
			default:
				return BaseModelPage.staticSolrBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSequenceRunGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSequenceRunGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "sequenceRunCount":
			return SequenceRunGenPage.staticSolrStrSequenceRunCount(siteRequest_, (Integer)o);
		case "pk":
			return SequenceRunGenPage.staticSolrStrPk(siteRequest_, (Long)o);
			default:
				return BaseModelPage.staticSolrStrBaseModelPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSequenceRunGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSequenceRunGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "sequenceRunCount":
			return SequenceRunGenPage.staticSolrFqSequenceRunCount(siteRequest_, o);
		case "pk":
			return SequenceRunGenPage.staticSolrFqPk(siteRequest_, o);
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
					o = defineSequenceRunGenPage(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSequenceRunGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineBaseModelPage(var, val);
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSequenceRunGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SequenceRunGenPage) {
			SequenceRunGenPage original = (SequenceRunGenPage)o;
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

	public static final String VAR_searchListSequenceRun_ = "searchListSequenceRun_";
	public static final String VAR_listSequenceRun = "listSequenceRun";
	public static final String VAR_sequenceRunCount = "sequenceRunCount";
	public static final String VAR_sequenceRun_ = "sequenceRun_";
	public static final String VAR_pk = "pk";
}
