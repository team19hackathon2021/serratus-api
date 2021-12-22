package io.serratus.api.enus.model.run;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.util.Locale;
import java.util.Map;
import io.serratus.api.enus.request.api.ApiRequest;
import io.vertx.core.json.JsonObject;
import java.time.ZoneOffset;
import io.serratus.api.enus.java.ZonedDateTimeDeserializer;
import java.math.RoundingMode;
import java.math.MathContext;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import io.vertx.core.Future;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import java.time.OffsetDateTime;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.serratus.api.enus.config.ConfigKeys;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.String;
import org.slf4j.Logger;
import io.vertx.core.Promise;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.java.ZonedDateTimeSerializer;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import io.serratus.api.enus.wrap.Wrap;
import com.fasterxml.jackson.annotation.JsonInclude;

/**	
 * Map.hackathonMission: to create a generated Java class that can be extended and override these methods to store information about RNA seqence runs. 
 * Map.hackathonColumn: Develop SequenceRun API
 * Map.hackathonLabels: Java
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRun&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SequenceRunGen<DEV> extends BaseModel {
	protected static final Logger LOG = LoggerFactory.getLogger(SequenceRun.class);

	public static final String SequenceRun_AName = "a sequence run";
	public static final String SequenceRun_This = "this ";
	public static final String SequenceRun_ThisName = "this sequence run";
	public static final String SequenceRun_A = "a ";
	public static final String SequenceRun_TheName = "the sequence run";
	public static final String SequenceRun_NameSingular = "sequence run";
	public static final String SequenceRun_NamePlural = "sequence runs";
	public static final String SequenceRun_NameActual = "current sequence run";
	public static final String SequenceRun_AllName = "all the sequence runs";
	public static final String SequenceRun_SearchAllNameBy = "search sequence runs by ";
	public static final String SequenceRun_Title = "sequence runs";
	public static final String SequenceRun_ThePluralName = "the sequence runs";
	public static final String SequenceRun_NoNameFound = "no sequence run found";
	public static final String SequenceRun_NameVar = "run";
	public static final String SequenceRun_OfName = "of sequence run";
	public static final String SequenceRun_ANameAdjective = "a sequence run";
	public static final String SequenceRun_NameAdjectiveSingular = "sequence run";
	public static final String SequenceRun_NameAdjectivePlural = "sequence runs";
	public static final String SequenceRun_Color = "pale-green";
	public static final String SequenceRun_IconGroup = "regular";
	public static final String SequenceRun_IconName = "dna";

	///////////
	// runId //
	///////////

	/**	 The entity runId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String runId;

	/**	<br/> The entity runId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRun&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:runId">Find the entity runId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _runId(Wrap<String> w);

	public String getRunId() {
		return runId;
	}
	public void setRunId(String o) {
		this.runId = SequenceRun.staticSetRunId(siteRequest_, o);
	}
	public static String staticSetRunId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SequenceRun runIdInit() {
		Wrap<String> runIdWrap = new Wrap<String>().var("runId");
		if(runId == null) {
			_runId(runIdWrap);
			setRunId(runIdWrap.o);
		}
		return (SequenceRun)this;
	}

	public static String staticSolrRunId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrRunId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqRunId(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRun.staticSolrStrRunId(siteRequest_, SequenceRun.staticSolrRunId(siteRequest_, SequenceRun.staticSetRunId(siteRequest_, o)));
	}

	public String sqlRunId() {
		return runId;
	}

	////////////////////
	// runReleaseDate //
	////////////////////

	/**	 The entity runReleaseDate
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSV'['VV']'")
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime runReleaseDate;

	/**	<br/> The entity runReleaseDate
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRun&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:runReleaseDate">Find the entity runReleaseDate in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _runReleaseDate(Wrap<ZonedDateTime> w);

	public ZonedDateTime getRunReleaseDate() {
		return runReleaseDate;
	}

	public void setRunReleaseDate(ZonedDateTime runReleaseDate) {
		this.runReleaseDate = runReleaseDate;
	}
	@JsonIgnore
	public void setRunReleaseDate(Instant o) {
		this.runReleaseDate = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	@JsonIgnore
	public void setRunReleaseDate(String o) {
		this.runReleaseDate = SequenceRun.staticSetRunReleaseDate(siteRequest_, o);
	}
	public static ZonedDateTime staticSetRunReleaseDate(SiteRequestEnUS siteRequest_, String o) {
		if(StringUtils.endsWith(o, "Z"))
			return o == null ? null : Instant.parse(o).atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		else
			return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
	}
	@JsonIgnore
	public void setRunReleaseDate(Date o) {
		this.runReleaseDate = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
	}
	protected SequenceRun runReleaseDateInit() {
		Wrap<ZonedDateTime> runReleaseDateWrap = new Wrap<ZonedDateTime>().var("runReleaseDate");
		if(runReleaseDate == null) {
			_runReleaseDate(runReleaseDateWrap);
			setRunReleaseDate(runReleaseDateWrap.o);
		}
		return (SequenceRun)this;
	}

	public static Date staticSolrRunReleaseDate(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrRunReleaseDate(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqRunReleaseDate(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRun.staticSolrStrRunReleaseDate(siteRequest_, SequenceRun.staticSolrRunReleaseDate(siteRequest_, SequenceRun.staticSetRunReleaseDate(siteRequest_, o)));
	}

	public OffsetDateTime sqlRunReleaseDate() {
		return runReleaseDate == null ? null : runReleaseDate.toOffsetDateTime();
	}

	/////////////////
	// runLoadDate //
	/////////////////

	/**	 The entity runLoadDate
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSV'['VV']'")
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime runLoadDate;

	/**	<br/> The entity runLoadDate
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRun&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:runLoadDate">Find the entity runLoadDate in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _runLoadDate(Wrap<ZonedDateTime> w);

	public ZonedDateTime getRunLoadDate() {
		return runLoadDate;
	}

	public void setRunLoadDate(ZonedDateTime runLoadDate) {
		this.runLoadDate = runLoadDate;
	}
	@JsonIgnore
	public void setRunLoadDate(Instant o) {
		this.runLoadDate = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	@JsonIgnore
	public void setRunLoadDate(String o) {
		this.runLoadDate = SequenceRun.staticSetRunLoadDate(siteRequest_, o);
	}
	public static ZonedDateTime staticSetRunLoadDate(SiteRequestEnUS siteRequest_, String o) {
		if(StringUtils.endsWith(o, "Z"))
			return o == null ? null : Instant.parse(o).atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		else
			return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
	}
	@JsonIgnore
	public void setRunLoadDate(Date o) {
		this.runLoadDate = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
	}
	protected SequenceRun runLoadDateInit() {
		Wrap<ZonedDateTime> runLoadDateWrap = new Wrap<ZonedDateTime>().var("runLoadDate");
		if(runLoadDate == null) {
			_runLoadDate(runLoadDateWrap);
			setRunLoadDate(runLoadDateWrap.o);
		}
		return (SequenceRun)this;
	}

	public static Date staticSolrRunLoadDate(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrRunLoadDate(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqRunLoadDate(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRun.staticSolrStrRunLoadDate(siteRequest_, SequenceRun.staticSolrRunLoadDate(siteRequest_, SequenceRun.staticSetRunLoadDate(siteRequest_, o)));
	}

	public OffsetDateTime sqlRunLoadDate() {
		return runLoadDate == null ? null : runLoadDate.toOffsetDateTime();
	}

	///////////////
	// projectId //
	///////////////

	/**	 The entity projectId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String projectId;

	/**	<br/> The entity projectId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRun&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:projectId">Find the entity projectId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _projectId(Wrap<String> w);

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String o) {
		this.projectId = SequenceRun.staticSetProjectId(siteRequest_, o);
	}
	public static String staticSetProjectId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SequenceRun projectIdInit() {
		Wrap<String> projectIdWrap = new Wrap<String>().var("projectId");
		if(projectId == null) {
			_projectId(projectIdWrap);
			setProjectId(projectIdWrap.o);
		}
		return (SequenceRun)this;
	}

	public static String staticSolrProjectId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrProjectId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqProjectId(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRun.staticSolrStrProjectId(siteRequest_, SequenceRun.staticSolrProjectId(siteRequest_, SequenceRun.staticSetProjectId(siteRequest_, o)));
	}

	public String sqlProjectId() {
		return projectId;
	}

	/////////////////
	// bioSampleId //
	/////////////////

	/**	 The entity bioSampleId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleId;

	/**	<br/> The entity bioSampleId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.run.SequenceRun&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleId">Find the entity bioSampleId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleId(Wrap<String> w);

	public String getBioSampleId() {
		return bioSampleId;
	}
	public void setBioSampleId(String o) {
		this.bioSampleId = SequenceRun.staticSetBioSampleId(siteRequest_, o);
	}
	public static String staticSetBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SequenceRun bioSampleIdInit() {
		Wrap<String> bioSampleIdWrap = new Wrap<String>().var("bioSampleId");
		if(bioSampleId == null) {
			_bioSampleId(bioSampleIdWrap);
			setBioSampleId(bioSampleIdWrap.o);
		}
		return (SequenceRun)this;
	}

	public static String staticSolrBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return SequenceRun.staticSolrStrBioSampleId(siteRequest_, SequenceRun.staticSolrBioSampleId(siteRequest_, SequenceRun.staticSetBioSampleId(siteRequest_, o)));
	}

	public String sqlBioSampleId() {
		return bioSampleId;
	}

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepSequenceRun(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepSequenceRun();
	}

	public Future<Void> promiseDeepSequenceRun() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseSequenceRun(promise2);
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

	public Future<Void> promiseSequenceRun(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				runIdInit();
				runReleaseDateInit();
				runLoadDateInit();
				projectIdInit();
				bioSampleIdInit();
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
		return promiseDeepSequenceRun(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSequenceRun(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModel(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSequenceRun(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSequenceRun(v);
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
	public Object obtainSequenceRun(String var) {
		SequenceRun oSequenceRun = (SequenceRun)this;
		switch(var) {
			case "runId":
				return oSequenceRun.runId;
			case "runReleaseDate":
				return oSequenceRun.runReleaseDate;
			case "runLoadDate":
				return oSequenceRun.runLoadDate;
			case "projectId":
				return oSequenceRun.projectId;
			case "bioSampleId":
				return oSequenceRun.bioSampleId;
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
				o = relateSequenceRun(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateSequenceRun(String var, Object val) {
		SequenceRun oSequenceRun = (SequenceRun)this;
		switch(var) {
			default:
				return super.relateBaseModel(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSequenceRun(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSequenceRun(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "runId":
			return SequenceRun.staticSetRunId(siteRequest_, o);
		case "runReleaseDate":
			return SequenceRun.staticSetRunReleaseDate(siteRequest_, o);
		case "runLoadDate":
			return SequenceRun.staticSetRunLoadDate(siteRequest_, o);
		case "projectId":
			return SequenceRun.staticSetProjectId(siteRequest_, o);
		case "bioSampleId":
			return SequenceRun.staticSetBioSampleId(siteRequest_, o);
			default:
				return BaseModel.staticSetBaseModel(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSequenceRun(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSequenceRun(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "runId":
			return SequenceRun.staticSolrRunId(siteRequest_, (String)o);
		case "runReleaseDate":
			return SequenceRun.staticSolrRunReleaseDate(siteRequest_, (ZonedDateTime)o);
		case "runLoadDate":
			return SequenceRun.staticSolrRunLoadDate(siteRequest_, (ZonedDateTime)o);
		case "projectId":
			return SequenceRun.staticSolrProjectId(siteRequest_, (String)o);
		case "bioSampleId":
			return SequenceRun.staticSolrBioSampleId(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSequenceRun(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSequenceRun(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "runId":
			return SequenceRun.staticSolrStrRunId(siteRequest_, (String)o);
		case "runReleaseDate":
			return SequenceRun.staticSolrStrRunReleaseDate(siteRequest_, (Date)o);
		case "runLoadDate":
			return SequenceRun.staticSolrStrRunLoadDate(siteRequest_, (Date)o);
		case "projectId":
			return SequenceRun.staticSolrStrProjectId(siteRequest_, (String)o);
		case "bioSampleId":
			return SequenceRun.staticSolrStrBioSampleId(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrStrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSequenceRun(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSequenceRun(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "runId":
			return SequenceRun.staticSolrFqRunId(siteRequest_, o);
		case "runReleaseDate":
			return SequenceRun.staticSolrFqRunReleaseDate(siteRequest_, o);
		case "runLoadDate":
			return SequenceRun.staticSolrFqRunLoadDate(siteRequest_, o);
		case "projectId":
			return SequenceRun.staticSolrFqProjectId(siteRequest_, o);
		case "bioSampleId":
			return SequenceRun.staticSolrFqBioSampleId(siteRequest_, o);
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
					o = defineSequenceRun(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSequenceRun(String var, Object val) {
		switch(var.toLowerCase()) {
			case "runid":
				if(val instanceof String)
					setRunId((String)val);
				saves.add("runId");
				return val;
			case "runreleasedate":
				if(val instanceof ZonedDateTime)
					setRunReleaseDate((ZonedDateTime)val);
				else if(val instanceof OffsetDateTime)
					setRunReleaseDate(((OffsetDateTime)val).atZoneSameInstant(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))));
				saves.add("runReleaseDate");
				return val;
			case "runloaddate":
				if(val instanceof ZonedDateTime)
					setRunLoadDate((ZonedDateTime)val);
				else if(val instanceof OffsetDateTime)
					setRunLoadDate(((OffsetDateTime)val).atZoneSameInstant(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))));
				saves.add("runLoadDate");
				return val;
			case "projectid":
				if(val instanceof String)
					setProjectId((String)val);
				saves.add("projectId");
				return val;
			case "biosampleid":
				if(val instanceof String)
					setBioSampleId((String)val);
				saves.add("bioSampleId");
				return val;
			default:
				return super.defineBaseModel(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSequenceRun(solrDocument);
	}
	public void populateSequenceRun(SolrDocument solrDocument) {
		SequenceRun oSequenceRun = (SequenceRun)this;
		saves = (List<String>)solrDocument.get("saves_docvalues_strings");
		if(saves != null) {
		}

		super.populateBaseModel(solrDocument);
	}

	public void indexSequenceRun(SolrInputDocument document) {
		if(runId != null) {
			document.addField("runId_docvalues_string", runId);
		}
		if(runReleaseDate != null) {
			document.addField("runReleaseDate_docvalues_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(runReleaseDate.toInstant(), ZoneId.of("UTC"))));
		}
		if(runLoadDate != null) {
			document.addField("runLoadDate_docvalues_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(runLoadDate.toInstant(), ZoneId.of("UTC"))));
		}
		if(projectId != null) {
			document.addField("projectId_docvalues_string", projectId);
		}
		if(bioSampleId != null) {
			document.addField("bioSampleId_docvalues_string", bioSampleId);
		}
		super.indexBaseModel(document);

	}

	public static String varIndexedSequenceRun(String entityVar) {
		switch(entityVar) {
			case "runId":
				return "runId_docvalues_string";
			case "runReleaseDate":
				return "runReleaseDate_docvalues_date";
			case "runLoadDate":
				return "runLoadDate_docvalues_date";
			case "projectId":
				return "projectId_docvalues_string";
			case "bioSampleId":
				return "bioSampleId_docvalues_string";
			default:
				return BaseModel.varIndexedBaseModel(entityVar);
		}
	}

	public static String varSearchSequenceRun(String entityVar) {
		switch(entityVar) {
			default:
				return BaseModel.varSearchBaseModel(entityVar);
		}
	}

	public static String varSuggestedSequenceRun(String entityVar) {
		switch(entityVar) {
			default:
				return BaseModel.varSuggestedBaseModel(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSequenceRun(solrDocument);
	}
	public void storeSequenceRun(SolrDocument solrDocument) {
		SequenceRun oSequenceRun = (SequenceRun)this;

		oSequenceRun.setRunId(Optional.ofNullable(solrDocument.get("runId_docvalues_string")).map(v -> v.toString()).orElse(null));
		oSequenceRun.setRunReleaseDate(Optional.ofNullable(solrDocument.get("runReleaseDate_docvalues_date")).map(v -> v.toString()).orElse(null));
		oSequenceRun.setRunLoadDate(Optional.ofNullable(solrDocument.get("runLoadDate_docvalues_date")).map(v -> v.toString()).orElse(null));
		oSequenceRun.setProjectId(Optional.ofNullable(solrDocument.get("projectId_docvalues_string")).map(v -> v.toString()).orElse(null));
		oSequenceRun.setBioSampleId(Optional.ofNullable(solrDocument.get("bioSampleId_docvalues_string")).map(v -> v.toString()).orElse(null));

		super.storeBaseModel(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSequenceRun() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SequenceRun) {
			SequenceRun original = (SequenceRun)o;
			if(!Objects.equals(runId, original.getRunId()))
				apiRequest.addVars("runId");
			if(!Objects.equals(runReleaseDate, original.getRunReleaseDate()))
				apiRequest.addVars("runReleaseDate");
			if(!Objects.equals(runLoadDate, original.getRunLoadDate()))
				apiRequest.addVars("runLoadDate");
			if(!Objects.equals(projectId, original.getProjectId()))
				apiRequest.addVars("projectId");
			if(!Objects.equals(bioSampleId, original.getBioSampleId()))
				apiRequest.addVars("bioSampleId");
			super.apiRequestBaseModel();
		}
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(Optional.ofNullable(runId).map(v -> "runId: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(runReleaseDate).map(v -> "runReleaseDate: " + v + "\n").orElse(""));
		sb.append(Optional.ofNullable(runLoadDate).map(v -> "runLoadDate: " + v + "\n").orElse(""));
		sb.append(Optional.ofNullable(projectId).map(v -> "projectId: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleId).map(v -> "bioSampleId: \"" + v + "\"\n" ).orElse(""));
		return sb.toString();
	}

	public static final String VAR_runId = "runId";
	public static final String VAR_runReleaseDate = "runReleaseDate";
	public static final String VAR_runLoadDate = "runLoadDate";
	public static final String VAR_projectId = "projectId";
	public static final String VAR_bioSampleId = "bioSampleId";
}
