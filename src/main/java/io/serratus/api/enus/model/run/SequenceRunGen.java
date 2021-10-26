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
import io.serratus.api.enus.model.biosample.BioSample;
import java.time.LocalDateTime;
import java.util.HashMap;
import io.serratus.api.enus.model.taxonomy.Taxonomy;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.serratus.api.enus.config.ConfigKeys;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.serratus.api.enus.search.SearchList;
import java.lang.String;
import org.slf4j.Logger;
import io.serratus.api.enus.writer.AllWriter;
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
 **/

public abstract class SequenceRunGen<DEV> extends BaseModel {
	public Wrap<String> runIdWrap = new Wrap<String>().var("runId").o(runId);
	public Wrap<ZonedDateTime> runReleaseDateWrap = new Wrap<ZonedDateTime>().var("runReleaseDate").o(runReleaseDate);
	public Wrap<ZonedDateTime> runLoadDateWrap = new Wrap<ZonedDateTime>().var("runLoadDate").o(runLoadDate);
	public Wrap<String> projectIdWrap = new Wrap<String>().var("projectId").o(projectId);
	public Wrap<String> bioSampleIdWrap = new Wrap<String>().var("bioSampleId").o(bioSampleId);
	public Wrap<SearchList<BioSample>> bioSampleSearchWrap = new Wrap<SearchList<BioSample>>().var("bioSampleSearch").o(bioSampleSearch);
	public Wrap<BioSample> bioSample_Wrap = new Wrap<BioSample>().var("bioSample_").o(bioSample_);
	public Wrap<String> bioSampleIsolateWrap = new Wrap<String>().var("bioSampleIsolate").o(bioSampleIsolate);
	public Wrap<String> bioSampleHostWrap = new Wrap<String>().var("bioSampleHost").o(bioSampleHost);
	public Wrap<String> bioSampleLabHostWrap = new Wrap<String>().var("bioSampleLabHost").o(bioSampleLabHost);
	public Wrap<String> bioSampleIsolationSourceWrap = new Wrap<String>().var("bioSampleIsolationSource").o(bioSampleIsolationSource);
	public Wrap<String> bioSampleGeographicLocationWrap = new Wrap<String>().var("bioSampleGeographicLocation").o(bioSampleGeographicLocation);
	public Wrap<String> bioSampleLatitudeLongitudeWrap = new Wrap<String>().var("bioSampleLatitudeLongitude").o(bioSampleLatitudeLongitude);
	public Wrap<String> bioSampleReferenceForBioMaterialWrap = new Wrap<String>().var("bioSampleReferenceForBioMaterial").o(bioSampleReferenceForBioMaterial);
	public Wrap<String> bioSampleMaterialProcessingWrap = new Wrap<String>().var("bioSampleMaterialProcessing").o(bioSampleMaterialProcessing);
	public Wrap<String> bioSampleMaterialIdentifiersWrap = new Wrap<String>().var("bioSampleMaterialIdentifiers").o(bioSampleMaterialIdentifiers);
	public Wrap<String> bioSampleDiseaseWrap = new Wrap<String>().var("bioSampleDisease").o(bioSampleDisease);
	public Wrap<String> bioSampleHostTissueSampledWrap = new Wrap<String>().var("bioSampleHostTissueSampled").o(bioSampleHostTissueSampled);
	public Wrap<String> bioSampleCollectedByWrap = new Wrap<String>().var("bioSampleCollectedBy").o(bioSampleCollectedBy);
	public Wrap<String> taxonomyIdWrap = new Wrap<String>().var("taxonomyId").o(taxonomyId);
	public Wrap<SearchList<Taxonomy>> taxonomySearchWrap = new Wrap<SearchList<Taxonomy>>().var("taxonomySearch").o(taxonomySearch);
	public Wrap<Taxonomy> taxonomy_Wrap = new Wrap<Taxonomy>().var("taxonomy_").o(taxonomy_);
	public Wrap<String> taxonomyNameWrap = new Wrap<String>().var("taxonomyName").o(taxonomyName);
	public Wrap<String> taxonomyUniqueNameWrap = new Wrap<String>().var("taxonomyUniqueName").o(taxonomyUniqueName);
	protected boolean alreadyInitializedSequenceRun = false;
	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");
	public String solrRunId() {
		return SequenceRun.staticSolrRunId(siteRequest_, runId);
	}
	public String strRunId() {
		return runId == null ? "" : runId;
	}
	public String jsonRunId() {
		return runId == null ? "" : runId;
	}
	public String htmTooltipRunId() {
		return null;
	}
	public String htmRunId() {
		return runId == null ? "" : StringEscapeUtils.escapeHtml4(strRunId());
	}
	public Date solrRunReleaseDate() {
		return SequenceRun.staticSolrRunReleaseDate(siteRequest_, runReleaseDate);
	}
	public String strRunReleaseDate() {
		return runReleaseDate == null ? "" : runReleaseDate.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}
	public String jsonRunReleaseDate() {
		return runReleaseDate == null ? "" : runReleaseDate.format(DateTimeFormatter.ISO_DATE_TIME);
	}
	public String htmTooltipRunReleaseDate() {
		return null;
	}
	public String htmTooltipRunReleaseDate() {
		return null;
	}
	public String htmRunReleaseDate() {
		return runReleaseDate == null ? "" : StringEscapeUtils.escapeHtml4(strRunReleaseDate());
	}
	public Date solrRunLoadDate() {
		return SequenceRun.staticSolrRunLoadDate(siteRequest_, runLoadDate);
	}
	public String strRunLoadDate() {
		return runLoadDate == null ? "" : runLoadDate.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}
	public String htmTooltipRunLoadDate() {
		return null;
	}
	public String htmRunLoadDate() {
		return runLoadDate == null ? "" : StringEscapeUtils.escapeHtml4(strRunLoadDate());
	}
	public String solrProjectId() {
		return SequenceRun.staticSolrProjectId(siteRequest_, projectId);
	}
	public String strProjectId() {
		return projectId == null ? "" : projectId;
	}
	public String jsonProjectId() {
		return projectId == null ? "" : projectId;
	}
	public String htmTooltipProjectId() {
		return null;
	}
	public String htmProjectId() {
		return projectId == null ? "" : StringEscapeUtils.escapeHtml4(strProjectId());
	}
	public String solrBioSampleId() {
		return SequenceRun.staticSolrBioSampleId(siteRequest_, bioSampleId);
	}
	public String strBioSampleId() {
		return bioSampleId == null ? "" : bioSampleId;
	}
	public String jsonBioSampleId() {
		return bioSampleId == null ? "" : bioSampleId;
	}

	public String htmTooltipBioSampleId() {
		return null;
	}
	public String solrBioSampleIsolate() {
		return SequenceRun.staticSolrBioSampleIsolate(siteRequest_, bioSampleIsolate);
	}
	public String strBioSampleIsolate() {
		return bioSampleIsolate == null ? "" : bioSampleIsolate;
	}
	public String jsonBioSampleIsolate() {
		return bioSampleIsolate == null ? "" : bioSampleIsolate;
	}
	public String htmTooltipBioSampleIsolate() {
		return null;
	}
	public String htmBioSampleIsolate() {
		return bioSampleIsolate == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleIsolate());
	}
	public String solrBioSampleHost() {
		return SequenceRun.staticSolrBioSampleHost(siteRequest_, bioSampleHost);
	}
	public String strBioSampleHost() {
		return bioSampleHost == null ? "" : bioSampleHost;
	}
	public String jsonBioSampleHost() {
		return bioSampleHost == null ? "" : bioSampleHost;
	}
	public String htmTooltipBioSampleHost() {
		return null;
	}
	public String solrBioSampleLabHost() {
		return SequenceRun.staticSolrBioSampleLabHost(siteRequest_, bioSampleLabHost);
	}
	public String strBioSampleLabHost() {
		return bioSampleLabHost == null ? "" : bioSampleLabHost;
	}
	public String jsonBioSampleLabHost() {
		return bioSampleLabHost == null ? "" : bioSampleLabHost;
	}
	public String htmTooltipBioSampleLabHost() {
		return null;
	}
	public String htmBioSampleLabHost() {
		return bioSampleLabHost == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleLabHost());
	}
	public String solrBioSampleIsolationSource() {
		return SequenceRun.staticSolrBioSampleIsolationSource(siteRequest_, bioSampleIsolationSource);
	}
	public String strBioSampleIsolationSource() {
		return bioSampleIsolationSource == null ? "" : bioSampleIsolationSource;
	}
	public String jsonBioSampleIsolationSource() {
		return bioSampleIsolationSource == null ? "" : bioSampleIsolationSource;
	}
	public String htmTooltipBioSampleIsolationSource() {
		return null;
	}
	public String htmBioSampleIsolationSource() {
		return bioSampleIsolationSource == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleIsolationSource());
	}
	public String solrBioSampleGeographicLocation() {
		return SequenceRun.staticSolrBioSampleGeographicLocation(siteRequest_, bioSampleGeographicLocation);
	}
	public String strBioSampleGeographicLocation() {
		return bioSampleGeographicLocation == null ? "" : bioSampleGeographicLocation;
	}
	public String jsonBioSampleGeographicLocation() {
		return bioSampleGeographicLocation == null ? "" : bioSampleGeographicLocation;
	}
	public String htmTooltipBioSampleGeographicLocation() {
		return null;
	}
	public String htmBioSampleGeographicLocation() {
		return bioSampleGeographicLocation == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleGeographicLocation());
	}
	public String solrBioSampleLatitudeLongitude() {
		return SequenceRun.staticSolrBioSampleLatitudeLongitude(siteRequest_, bioSampleLatitudeLongitude);
	}
	public String strBioSampleLatitudeLongitude() {
		return bioSampleLatitudeLongitude == null ? "" : bioSampleLatitudeLongitude;
	}
	public String jsonBioSampleLatitudeLongitude() {
		return bioSampleLatitudeLongitude == null ? "" : bioSampleLatitudeLongitude;
	}
	public String htmTooltipBioSampleLatitudeLongitude() {
		return null;
	}
	public String htmBioSampleLatitudeLongitude() {
		return bioSampleLatitudeLongitude == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleLatitudeLongitude());
	}
	public String solrBioSampleReferenceForBioMaterial() {
		return SequenceRun.staticSolrBioSampleReferenceForBioMaterial(siteRequest_, bioSampleReferenceForBioMaterial);
	}
	public String strBioSampleReferenceForBioMaterial() {
		return bioSampleReferenceForBioMaterial == null ? "" : bioSampleReferenceForBioMaterial;
	}
	public String jsonBioSampleReferenceForBioMaterial() {
		return bioSampleReferenceForBioMaterial == null ? "" : bioSampleReferenceForBioMaterial;
	}
	public String htmTooltipBioSampleReferenceForBioMaterial() {
		return null;
	}
	public String htmBioSampleReferenceForBioMaterial() {
		return bioSampleReferenceForBioMaterial == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleReferenceForBioMaterial());
	}
	public String solrBioSampleMaterialProcessing() {
		return SequenceRun.staticSolrBioSampleMaterialProcessing(siteRequest_, bioSampleMaterialProcessing);
	}
	public String strBioSampleMaterialProcessing() {
		return bioSampleMaterialProcessing == null ? "" : bioSampleMaterialProcessing;
	}
	public String jsonBioSampleMaterialProcessing() {
		return bioSampleMaterialProcessing == null ? "" : bioSampleMaterialProcessing;
	}
	public String htmTooltipBioSampleMaterialProcessing() {
		return null;
	}
	public String htmBioSampleMaterialProcessing() {
		return bioSampleMaterialProcessing == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleMaterialProcessing());
	}
	public String solrBioSampleMaterialIdentifiers() {
		return SequenceRun.staticSolrBioSampleMaterialIdentifiers(siteRequest_, bioSampleMaterialIdentifiers);
	}
	public String strBioSampleMaterialIdentifiers() {
		return bioSampleMaterialIdentifiers == null ? "" : bioSampleMaterialIdentifiers;
	}
	public String jsonBioSampleMaterialIdentifiers() {
		return bioSampleMaterialIdentifiers == null ? "" : bioSampleMaterialIdentifiers;
	}
	public String htmTooltipBioSampleMaterialIdentifiers() {
		return null;
	}
	public String htmBioSampleMaterialIdentifiers() {
		return bioSampleMaterialIdentifiers == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleMaterialIdentifiers());
	}
	public String solrBioSampleDisease() {
		return SequenceRun.staticSolrBioSampleDisease(siteRequest_, bioSampleDisease);
	}
	public String strBioSampleDisease() {
		return bioSampleDisease == null ? "" : bioSampleDisease;
	}
	public String jsonBioSampleDisease() {
		return bioSampleDisease == null ? "" : bioSampleDisease;
	}
	public String htmTooltipBioSampleDisease() {
		return null;
	}
	public String htmBioSampleDisease() {
		return bioSampleDisease == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleDisease());
	}
	public String solrBioSampleHostTissueSampled() {
		return SequenceRun.staticSolrBioSampleHostTissueSampled(siteRequest_, bioSampleHostTissueSampled);
	}
	public String strBioSampleHostTissueSampled() {
		return bioSampleHostTissueSampled == null ? "" : bioSampleHostTissueSampled;
	}
	public String jsonBioSampleHostTissueSampled() {
		return bioSampleHostTissueSampled == null ? "" : bioSampleHostTissueSampled;
	}
	public String htmTooltipBioSampleHostTissueSampled() {
		return null;
	}
	public String htmBioSampleHostTissueSampled() {
		return bioSampleHostTissueSampled == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleHostTissueSampled());
	}
	public String solrBioSampleCollectedBy() {
		return SequenceRun.staticSolrBioSampleCollectedBy(siteRequest_, bioSampleCollectedBy);
	}
	public String strBioSampleCollectedBy() {
		return bioSampleCollectedBy == null ? "" : bioSampleCollectedBy;
	}
	public String jsonBioSampleCollectedBy() {
		return bioSampleCollectedBy == null ? "" : bioSampleCollectedBy;
	}
	public String htmTooltipBioSampleCollectedBy() {
		return null;
	}
	public String htmBioSampleCollectedBy() {
		return bioSampleCollectedBy == null ? "" : StringEscapeUtils.escapeHtml4(strBioSampleCollectedBy());
	}
	public String solrTaxonomyId() {
		return SequenceRun.staticSolrTaxonomyId(siteRequest_, taxonomyId);
	}
	public String strTaxonomyId() {
		return taxonomyId == null ? "" : taxonomyId;
	}
	public String jsonTaxonomyId() {
		return taxonomyId == null ? "" : taxonomyId;
	}
	public String htmTooltipTaxonomyId() {
		return null;
	}
	public String htmTaxonomyId() {
		return taxonomyId == null ? "" : StringEscapeUtils.escapeHtml4(strTaxonomyId());
	}
	public String solrTaxonomyName() {
		return SequenceRun.staticSolrTaxonomyName(siteRequest_, taxonomyName);
	}
	public String strTaxonomyName() {
		return taxonomyName == null ? "" : taxonomyName;
	}
	public String jsonTaxonomyName() {
		return taxonomyName == null ? "" : taxonomyName;
	}
	public String htmTooltipTaxonomyName() {
		return null;
	}
	public String htmTaxonomyName() {
		return taxonomyName == null ? "" : StringEscapeUtils.escapeHtml4(strTaxonomyName());
	}
	public String solrTaxonomyUniqueName() {
		return SequenceRun.staticSolrTaxonomyUniqueName(siteRequest_, taxonomyUniqueName);
	}
	public String strTaxonomyUniqueName() {
		return taxonomyUniqueName == null ? "" : taxonomyUniqueName;
	}
	public String jsonTaxonomyUniqueName() {
		return taxonomyUniqueName == null ? "" : taxonomyUniqueName;
	}
	public String htmTooltipTaxonomyUniqueName() {
		return null;
	}
	public String htmTaxonomyUniqueName() {
		return taxonomyUniqueName == null ? "" : StringEscapeUtils.escapeHtml4(strTaxonomyUniqueName());
	}
	public String solrTaxonomyNameClass() {
		return SequenceRun.staticSolrTaxonomyNameClass(siteRequest_, taxonomyNameClass);
	}
	public String strTaxonomyNameClass() {
		return taxonomyNameClass == null ? "" : taxonomyNameClass;
	}
	public String jsonTaxonomyNameClass() {
		return taxonomyNameClass == null ? "" : taxonomyNameClass;
	}
	public String htmTooltipTaxonomyNameClass() {
		return null;
	}
	public String htmTaxonomyNameClass() {
		return taxonomyNameClass == null ? "" : StringEscapeUtils.escapeHtml4(strTaxonomyNameClass());
	}
	public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeSequenceRun(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSequenceRun(String var, Object val) {
		SequenceRun oSequenceRun = (SequenceRun)this;
		switch(var) {
			default:
				return super.attributeBaseModel(var, val);
		}
	}
	public boolean defineForClass(String var, String val) {
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
	public Object defineSequenceRun(String var, String val) {
		switch(var.toLowerCase()) {
			case "runid":
				if(val != null)
					setRunId(val);
				saves.add("runId");
				return val;
			case "runreleasedate":
				if(val != null)
					setRunReleaseDate(val);
				saves.add("runReleaseDate");
				return val;
			case "runloaddate":
				if(val != null)
					setRunLoadDate(val);
				saves.add("runLoadDate");
				return val;
			case "projectid":
				if(val != null)
					setProjectId(val);
				saves.add("projectId");
				return val;
			case "biosampleid":
				if(val != null)
					setBioSampleId(val);
				saves.add("bioSampleId");
				return val;
			case "biosampleisolate":
				if(val != null)
					setBioSampleIsolate(val);
				saves.add("bioSampleIsolate");
				return val;
			case "biosamplehost":
				if(val != null)
					setBioSampleHost(val);
				saves.add("bioSampleHost");
				return val;
			case "biosamplelabhost":
				if(val != null)
					setBioSampleLabHost(val);
				saves.add("bioSampleLabHost");
				return val;
			case "biosampleisolationsource":
				if(val != null)
					setBioSampleIsolationSource(val);
				saves.add("bioSampleIsolationSource");
				return val;
			case "biosamplegeographiclocation":
				if(val != null)
					setBioSampleGeographicLocation(val);
				saves.add("bioSampleGeographicLocation");
				return val;
			case "biosamplelatitudelongitude":
				if(val != null)
					setBioSampleLatitudeLongitude(val);
				saves.add("bioSampleLatitudeLongitude");
				return val;
			case "biosamplereferenceforbiomaterial":
				if(val != null)
					setBioSampleReferenceForBioMaterial(val);
				saves.add("bioSampleReferenceForBioMaterial");
				return val;
			case "biosamplematerialprocessing":
				if(val != null)
					setBioSampleMaterialProcessing(val);
				saves.add("bioSampleMaterialProcessing");
				return val;
			case "biosamplematerialidentifiers":
				if(val != null)
					setBioSampleMaterialIdentifiers(val);
				saves.add("bioSampleMaterialIdentifiers");
				return val;
			case "biosampledisease":
				if(val != null)
					setBioSampleDisease(val);
				saves.add("bioSampleDisease");
				return val;
			case "biosamplehosttissuesampled":
				if(val != null)
					setBioSampleHostTissueSampled(val);
				saves.add("bioSampleHostTissueSampled");
				return val;
			case "biosamplecollectedby":
				if(val != null)
					setBioSampleCollectedBy(val);
				saves.add("bioSampleCollectedBy");
				return val;
			case "taxonomyid":
				if(val != null)
					setTaxonomyId(val);
				saves.add("taxonomyId");
				return val;
			case "taxonomyname":
				if(val != null)
					setTaxonomyName(val);
				saves.add("taxonomyName");
				return val;
			case "taxonomyuniquename":
				if(val != null)
					setTaxonomyUniqueName(val);
				saves.add("taxonomyUniqueName");
				return val;
			case "taxonomynameclass":
				if(val != null)
					setTaxonomyNameClass(val);
				saves.add("taxonomyNameClass");
				return val;
			default:
				return super.defineBaseModel(var, val);
		}
	}
	
	public int hashCode() {
		return Objects.hash(super.hashCode(), runId, runReleaseDate, runLoadDate, projectId, bioSampleId, bioSampleIsolate, bioSampleHost, bioSampleLabHost, bioSampleIsolationSource, bioSampleGeographicLocation, bioSampleLatitudeLongitude, bioSampleReferenceForBioMaterial, bioSampleMaterialProcessing, bioSampleMaterialIdentifiers, bioSampleDisease, bioSampleHostTissueSampled, bioSampleCollectedBy, taxonomyId, taxonomyName, taxonomyUniqueName, taxonomyNameClass);
	}
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SequenceRun))
			return false;
		SequenceRun that = (SequenceRun)o;
		return super.equals(o)
				&& Objects.equals( runId, that.runId )
				&& Objects.equals( runReleaseDate, that.runReleaseDate )
				&& Objects.equals( runLoadDate, that.runLoadDate )
				&& Objects.equals( projectId, that.projectId )
				&& Objects.equals( bioSampleId, that.bioSampleId )
				&& Objects.equals( bioSampleIsolate, that.bioSampleIsolate )
				&& Objects.equals( bioSampleHost, that.bioSampleHost )
				&& Objects.equals( bioSampleLabHost, that.bioSampleLabHost )
				&& Objects.equals( bioSampleIsolationSource, that.bioSampleIsolationSource )
				&& Objects.equals( bioSampleGeographicLocation, that.bioSampleGeographicLocation )
				&& Objects.equals( bioSampleLatitudeLongitude, that.bioSampleLatitudeLongitude )
				&& Objects.equals( bioSampleReferenceForBioMaterial, that.bioSampleReferenceForBioMaterial )
				&& Objects.equals( bioSampleMaterialProcessing, that.bioSampleMaterialProcessing )
				&& Objects.equals( bioSampleMaterialIdentifiers, that.bioSampleMaterialIdentifiers )
				&& Objects.equals( bioSampleDisease, that.bioSampleDisease )
				&& Objects.equals( bioSampleHostTissueSampled, that.bioSampleHostTissueSampled )
				&& Objects.equals( bioSampleCollectedBy, that.bioSampleCollectedBy )
				&& Objects.equals( taxonomyId, that.taxonomyId )
				&& Objects.equals( taxonomyName, that.taxonomyName )
				&& Objects.equals( taxonomyUniqueName, that.taxonomyUniqueName )
				&& Objects.equals( taxonomyNameClass, that.taxonomyNameClass );
	}
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
	protected String runId;
	protected abstract void _runId(Wrap<String> w) {}
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
	protected ZonedDateTime runReleaseDate;
	protected abstract void _runReleaseDate(Wrap<ZonedDateTime> w) {}
	public ZonedDateTime getRunReleaseDate() {
		return runReleaseDate;
	}

	public void setRunReleaseDate(ZonedDateTime runReleaseDate) {
		this.runReleaseDate = runReleaseDate;
	}
	public void setRunReleaseDate(Instant o) {
		this.runReleaseDate = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setRunReleaseDate(String o) {
		this.runReleaseDate = SequenceRun.staticSetRunReleaseDate(siteRequest_, o);
	}
	public static ZonedDateTime staticSetRunReleaseDate(SiteRequestEnUS siteRequest_, String o) {
		if(StringUtils.endsWith(o, "Z"))
			return o == null ? null : Instant.parse(o).atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		else
			return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
	}
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
	protected ZonedDateTime runLoadDate;
	protected abstract void _runLoadDate(Wrap<ZonedDateTime> w) {}
	public ZonedDateTime getRunLoadDate() {
		return runLoadDate;
	}
	public void setRunLoadDate(ZonedDateTime runLoadDate) {
		this.runLoadDate = runLoadDate;
	}
	public void setRunLoadDate(Instant o) {
		this.runLoadDate = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setRunLoadDate(String o) {
		this.runLoadDate = SequenceRun.staticSetRunLoadDate(siteRequest_, o);
	}
	public static ZonedDateTime staticSetRunLoadDate(SiteRequestEnUS siteRequest_, String o) {
		if(StringUtils.endsWith(o, "Z"))
			return o == null ? null : Instant.parse(o).atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		else
			return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
	}
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
	public OffsetDateTime sqlRunLoadDate() {
		return runLoadDate == null ? null : runLoadDate.toOffsetDateTime();
	}
	protected String projectId;
	protected abstract void _projectId(Wrap<String> w) {}
	public String getProjectId() {
		return projectId;
	}

	
	
}
