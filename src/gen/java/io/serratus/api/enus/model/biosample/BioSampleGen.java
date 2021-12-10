package io.serratus.api.enus.model.biosample;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import io.serratus.api.enus.model.taxonomy.Taxonomy;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.serratus.api.enus.config.ConfigKeys;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.serratus.api.enus.request.api.ApiRequest;
import io.serratus.api.enus.search.SearchList;
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
 * Map.hackathonMission: to create a generated Java class that can be extended and override these methods to store information about BioSamples related to RNA seqence runs. 
 * Map.hackathonColumn: Develop BioSample API
 * Map.hackathonLabels: Java
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class BioSampleGen<DEV> extends BaseModel {
	protected static final Logger LOG = LoggerFactory.getLogger(BioSample.class);

	public static final String BioSample_AName = "a biosample";
	public static final String BioSample_This = "this ";
	public static final String BioSample_ThisName = "this biosample";
	public static final String BioSample_A = "a ";
	public static final String BioSample_TheName = "the biosample";
	public static final String BioSample_NameSingular = "biosample";
	public static final String BioSample_NamePlural = "biosamples";
	public static final String BioSample_NameActual = "current biosample";
	public static final String BioSample_AllName = "all the biosamples";
	public static final String BioSample_SearchAllNameBy = "search biosamples by ";
	public static final String BioSample_Title = "biosamples";
	public static final String BioSample_ThePluralName = "the biosamples";
	public static final String BioSample_NoNameFound = "no biosample found";
	public static final String BioSample_NameVar = "biosample";
	public static final String BioSample_OfName = "of biosample";
	public static final String BioSample_ANameAdjective = "a biosample";
	public static final String BioSample_NameAdjectiveSingular = "biosample";
	public static final String BioSample_NameAdjectivePlural = "biosamples";
	public static final String BioSample_Color = "pale-green";
	public static final String BioSample_IconGroup = "regular";
	public static final String BioSample_IconName = "map-pin";

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleId">Find the entity bioSampleId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleId(Wrap<String> w);

	public String getBioSampleId() {
		return bioSampleId;
	}
	public void setBioSampleId(String o) {
		this.bioSampleId = BioSample.staticSetBioSampleId(siteRequest_, o);
	}
	public static String staticSetBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleIdInit() {
		Wrap<String> bioSampleIdWrap = new Wrap<String>().var("bioSampleId");
		if(bioSampleId == null) {
			_bioSampleId(bioSampleIdWrap);
			setBioSampleId(bioSampleIdWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleId(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleId(siteRequest_, BioSample.staticSolrBioSampleId(siteRequest_, BioSample.staticSetBioSampleId(siteRequest_, o)));
	}

	public String sqlBioSampleId() {
		return bioSampleId;
	}

	//////////////////////
	// bioSampleIsolate //
	//////////////////////

	/**	 The entity bioSampleIsolate
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleIsolate;

	/**	<br/> The entity bioSampleIsolate
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleIsolate">Find the entity bioSampleIsolate in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleIsolate(Wrap<String> w);

	public String getBioSampleIsolate() {
		return bioSampleIsolate;
	}
	public void setBioSampleIsolate(String o) {
		this.bioSampleIsolate = BioSample.staticSetBioSampleIsolate(siteRequest_, o);
	}
	public static String staticSetBioSampleIsolate(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleIsolateInit() {
		Wrap<String> bioSampleIsolateWrap = new Wrap<String>().var("bioSampleIsolate");
		if(bioSampleIsolate == null) {
			_bioSampleIsolate(bioSampleIsolateWrap);
			setBioSampleIsolate(bioSampleIsolateWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleIsolate(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleIsolate(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleIsolate(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleIsolate(siteRequest_, BioSample.staticSolrBioSampleIsolate(siteRequest_, BioSample.staticSetBioSampleIsolate(siteRequest_, o)));
	}

	public String sqlBioSampleIsolate() {
		return bioSampleIsolate;
	}

	///////////////////
	// bioSampleHost //
	///////////////////

	/**	 The entity bioSampleHost
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleHost;

	/**	<br/> The entity bioSampleHost
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleHost">Find the entity bioSampleHost in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleHost(Wrap<String> w);

	public String getBioSampleHost() {
		return bioSampleHost;
	}
	public void setBioSampleHost(String o) {
		this.bioSampleHost = BioSample.staticSetBioSampleHost(siteRequest_, o);
	}
	public static String staticSetBioSampleHost(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleHostInit() {
		Wrap<String> bioSampleHostWrap = new Wrap<String>().var("bioSampleHost");
		if(bioSampleHost == null) {
			_bioSampleHost(bioSampleHostWrap);
			setBioSampleHost(bioSampleHostWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleHost(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleHost(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleHost(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleHost(siteRequest_, BioSample.staticSolrBioSampleHost(siteRequest_, BioSample.staticSetBioSampleHost(siteRequest_, o)));
	}

	public String sqlBioSampleHost() {
		return bioSampleHost;
	}

	//////////////////////
	// bioSampleLabHost //
	//////////////////////

	/**	 The entity bioSampleLabHost
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleLabHost;

	/**	<br/> The entity bioSampleLabHost
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleLabHost">Find the entity bioSampleLabHost in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleLabHost(Wrap<String> w);

	public String getBioSampleLabHost() {
		return bioSampleLabHost;
	}
	public void setBioSampleLabHost(String o) {
		this.bioSampleLabHost = BioSample.staticSetBioSampleLabHost(siteRequest_, o);
	}
	public static String staticSetBioSampleLabHost(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleLabHostInit() {
		Wrap<String> bioSampleLabHostWrap = new Wrap<String>().var("bioSampleLabHost");
		if(bioSampleLabHost == null) {
			_bioSampleLabHost(bioSampleLabHostWrap);
			setBioSampleLabHost(bioSampleLabHostWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleLabHost(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleLabHost(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleLabHost(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleLabHost(siteRequest_, BioSample.staticSolrBioSampleLabHost(siteRequest_, BioSample.staticSetBioSampleLabHost(siteRequest_, o)));
	}

	public String sqlBioSampleLabHost() {
		return bioSampleLabHost;
	}

	//////////////////////////////
	// bioSampleIsolationSource //
	//////////////////////////////

	/**	 The entity bioSampleIsolationSource
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleIsolationSource;

	/**	<br/> The entity bioSampleIsolationSource
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleIsolationSource">Find the entity bioSampleIsolationSource in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleIsolationSource(Wrap<String> w);

	public String getBioSampleIsolationSource() {
		return bioSampleIsolationSource;
	}
	public void setBioSampleIsolationSource(String o) {
		this.bioSampleIsolationSource = BioSample.staticSetBioSampleIsolationSource(siteRequest_, o);
	}
	public static String staticSetBioSampleIsolationSource(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleIsolationSourceInit() {
		Wrap<String> bioSampleIsolationSourceWrap = new Wrap<String>().var("bioSampleIsolationSource");
		if(bioSampleIsolationSource == null) {
			_bioSampleIsolationSource(bioSampleIsolationSourceWrap);
			setBioSampleIsolationSource(bioSampleIsolationSourceWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleIsolationSource(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleIsolationSource(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleIsolationSource(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleIsolationSource(siteRequest_, BioSample.staticSolrBioSampleIsolationSource(siteRequest_, BioSample.staticSetBioSampleIsolationSource(siteRequest_, o)));
	}

	public String sqlBioSampleIsolationSource() {
		return bioSampleIsolationSource;
	}

	/////////////////////////////////
	// bioSampleGeographicLocation //
	/////////////////////////////////

	/**	 The entity bioSampleGeographicLocation
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleGeographicLocation;

	/**	<br/> The entity bioSampleGeographicLocation
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleGeographicLocation">Find the entity bioSampleGeographicLocation in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleGeographicLocation(Wrap<String> w);

	public String getBioSampleGeographicLocation() {
		return bioSampleGeographicLocation;
	}
	public void setBioSampleGeographicLocation(String o) {
		this.bioSampleGeographicLocation = BioSample.staticSetBioSampleGeographicLocation(siteRequest_, o);
	}
	public static String staticSetBioSampleGeographicLocation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleGeographicLocationInit() {
		Wrap<String> bioSampleGeographicLocationWrap = new Wrap<String>().var("bioSampleGeographicLocation");
		if(bioSampleGeographicLocation == null) {
			_bioSampleGeographicLocation(bioSampleGeographicLocationWrap);
			setBioSampleGeographicLocation(bioSampleGeographicLocationWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleGeographicLocation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleGeographicLocation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleGeographicLocation(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleGeographicLocation(siteRequest_, BioSample.staticSolrBioSampleGeographicLocation(siteRequest_, BioSample.staticSetBioSampleGeographicLocation(siteRequest_, o)));
	}

	public String sqlBioSampleGeographicLocation() {
		return bioSampleGeographicLocation;
	}

	////////////////////////////////
	// bioSampleLatitudeLongitude //
	////////////////////////////////

	/**	 The entity bioSampleLatitudeLongitude
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleLatitudeLongitude;

	/**	<br/> The entity bioSampleLatitudeLongitude
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleLatitudeLongitude">Find the entity bioSampleLatitudeLongitude in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleLatitudeLongitude(Wrap<String> w);

	public String getBioSampleLatitudeLongitude() {
		return bioSampleLatitudeLongitude;
	}
	public void setBioSampleLatitudeLongitude(String o) {
		this.bioSampleLatitudeLongitude = BioSample.staticSetBioSampleLatitudeLongitude(siteRequest_, o);
	}
	public static String staticSetBioSampleLatitudeLongitude(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleLatitudeLongitudeInit() {
		Wrap<String> bioSampleLatitudeLongitudeWrap = new Wrap<String>().var("bioSampleLatitudeLongitude");
		if(bioSampleLatitudeLongitude == null) {
			_bioSampleLatitudeLongitude(bioSampleLatitudeLongitudeWrap);
			setBioSampleLatitudeLongitude(bioSampleLatitudeLongitudeWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleLatitudeLongitude(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleLatitudeLongitude(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleLatitudeLongitude(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleLatitudeLongitude(siteRequest_, BioSample.staticSolrBioSampleLatitudeLongitude(siteRequest_, BioSample.staticSetBioSampleLatitudeLongitude(siteRequest_, o)));
	}

	public String sqlBioSampleLatitudeLongitude() {
		return bioSampleLatitudeLongitude;
	}

	//////////////////////////////////////
	// bioSampleReferenceForBioMaterial //
	//////////////////////////////////////

	/**	 The entity bioSampleReferenceForBioMaterial
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleReferenceForBioMaterial;

	/**	<br/> The entity bioSampleReferenceForBioMaterial
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleReferenceForBioMaterial">Find the entity bioSampleReferenceForBioMaterial in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleReferenceForBioMaterial(Wrap<String> w);

	public String getBioSampleReferenceForBioMaterial() {
		return bioSampleReferenceForBioMaterial;
	}
	public void setBioSampleReferenceForBioMaterial(String o) {
		this.bioSampleReferenceForBioMaterial = BioSample.staticSetBioSampleReferenceForBioMaterial(siteRequest_, o);
	}
	public static String staticSetBioSampleReferenceForBioMaterial(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleReferenceForBioMaterialInit() {
		Wrap<String> bioSampleReferenceForBioMaterialWrap = new Wrap<String>().var("bioSampleReferenceForBioMaterial");
		if(bioSampleReferenceForBioMaterial == null) {
			_bioSampleReferenceForBioMaterial(bioSampleReferenceForBioMaterialWrap);
			setBioSampleReferenceForBioMaterial(bioSampleReferenceForBioMaterialWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleReferenceForBioMaterial(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleReferenceForBioMaterial(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleReferenceForBioMaterial(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleReferenceForBioMaterial(siteRequest_, BioSample.staticSolrBioSampleReferenceForBioMaterial(siteRequest_, BioSample.staticSetBioSampleReferenceForBioMaterial(siteRequest_, o)));
	}

	public String sqlBioSampleReferenceForBioMaterial() {
		return bioSampleReferenceForBioMaterial;
	}

	/////////////////////////////////
	// bioSampleMaterialProcessing //
	/////////////////////////////////

	/**	 The entity bioSampleMaterialProcessing
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleMaterialProcessing;

	/**	<br/> The entity bioSampleMaterialProcessing
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleMaterialProcessing">Find the entity bioSampleMaterialProcessing in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleMaterialProcessing(Wrap<String> w);

	public String getBioSampleMaterialProcessing() {
		return bioSampleMaterialProcessing;
	}
	public void setBioSampleMaterialProcessing(String o) {
		this.bioSampleMaterialProcessing = BioSample.staticSetBioSampleMaterialProcessing(siteRequest_, o);
	}
	public static String staticSetBioSampleMaterialProcessing(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleMaterialProcessingInit() {
		Wrap<String> bioSampleMaterialProcessingWrap = new Wrap<String>().var("bioSampleMaterialProcessing");
		if(bioSampleMaterialProcessing == null) {
			_bioSampleMaterialProcessing(bioSampleMaterialProcessingWrap);
			setBioSampleMaterialProcessing(bioSampleMaterialProcessingWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleMaterialProcessing(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleMaterialProcessing(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleMaterialProcessing(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleMaterialProcessing(siteRequest_, BioSample.staticSolrBioSampleMaterialProcessing(siteRequest_, BioSample.staticSetBioSampleMaterialProcessing(siteRequest_, o)));
	}

	public String sqlBioSampleMaterialProcessing() {
		return bioSampleMaterialProcessing;
	}

	//////////////////////////////////
	// bioSampleMaterialIdentifiers //
	//////////////////////////////////

	/**	 The entity bioSampleMaterialIdentifiers
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleMaterialIdentifiers;

	/**	<br/> The entity bioSampleMaterialIdentifiers
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleMaterialIdentifiers">Find the entity bioSampleMaterialIdentifiers in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleMaterialIdentifiers(Wrap<String> w);

	public String getBioSampleMaterialIdentifiers() {
		return bioSampleMaterialIdentifiers;
	}
	public void setBioSampleMaterialIdentifiers(String o) {
		this.bioSampleMaterialIdentifiers = BioSample.staticSetBioSampleMaterialIdentifiers(siteRequest_, o);
	}
	public static String staticSetBioSampleMaterialIdentifiers(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleMaterialIdentifiersInit() {
		Wrap<String> bioSampleMaterialIdentifiersWrap = new Wrap<String>().var("bioSampleMaterialIdentifiers");
		if(bioSampleMaterialIdentifiers == null) {
			_bioSampleMaterialIdentifiers(bioSampleMaterialIdentifiersWrap);
			setBioSampleMaterialIdentifiers(bioSampleMaterialIdentifiersWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleMaterialIdentifiers(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleMaterialIdentifiers(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleMaterialIdentifiers(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleMaterialIdentifiers(siteRequest_, BioSample.staticSolrBioSampleMaterialIdentifiers(siteRequest_, BioSample.staticSetBioSampleMaterialIdentifiers(siteRequest_, o)));
	}

	public String sqlBioSampleMaterialIdentifiers() {
		return bioSampleMaterialIdentifiers;
	}

	//////////////////////
	// bioSampleDisease //
	//////////////////////

	/**	 The entity bioSampleDisease
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleDisease;

	/**	<br/> The entity bioSampleDisease
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleDisease">Find the entity bioSampleDisease in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleDisease(Wrap<String> w);

	public String getBioSampleDisease() {
		return bioSampleDisease;
	}
	public void setBioSampleDisease(String o) {
		this.bioSampleDisease = BioSample.staticSetBioSampleDisease(siteRequest_, o);
	}
	public static String staticSetBioSampleDisease(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleDiseaseInit() {
		Wrap<String> bioSampleDiseaseWrap = new Wrap<String>().var("bioSampleDisease");
		if(bioSampleDisease == null) {
			_bioSampleDisease(bioSampleDiseaseWrap);
			setBioSampleDisease(bioSampleDiseaseWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleDisease(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleDisease(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleDisease(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleDisease(siteRequest_, BioSample.staticSolrBioSampleDisease(siteRequest_, BioSample.staticSetBioSampleDisease(siteRequest_, o)));
	}

	public String sqlBioSampleDisease() {
		return bioSampleDisease;
	}

	////////////////////////////////
	// bioSampleHostTissueSampled //
	////////////////////////////////

	/**	 The entity bioSampleHostTissueSampled
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleHostTissueSampled;

	/**	<br/> The entity bioSampleHostTissueSampled
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleHostTissueSampled">Find the entity bioSampleHostTissueSampled in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleHostTissueSampled(Wrap<String> w);

	public String getBioSampleHostTissueSampled() {
		return bioSampleHostTissueSampled;
	}
	public void setBioSampleHostTissueSampled(String o) {
		this.bioSampleHostTissueSampled = BioSample.staticSetBioSampleHostTissueSampled(siteRequest_, o);
	}
	public static String staticSetBioSampleHostTissueSampled(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleHostTissueSampledInit() {
		Wrap<String> bioSampleHostTissueSampledWrap = new Wrap<String>().var("bioSampleHostTissueSampled");
		if(bioSampleHostTissueSampled == null) {
			_bioSampleHostTissueSampled(bioSampleHostTissueSampledWrap);
			setBioSampleHostTissueSampled(bioSampleHostTissueSampledWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleHostTissueSampled(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleHostTissueSampled(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleHostTissueSampled(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleHostTissueSampled(siteRequest_, BioSample.staticSolrBioSampleHostTissueSampled(siteRequest_, BioSample.staticSetBioSampleHostTissueSampled(siteRequest_, o)));
	}

	public String sqlBioSampleHostTissueSampled() {
		return bioSampleHostTissueSampled;
	}

	//////////////////////////
	// bioSampleCollectedBy //
	//////////////////////////

	/**	 The entity bioSampleCollectedBy
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String bioSampleCollectedBy;

	/**	<br/> The entity bioSampleCollectedBy
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:bioSampleCollectedBy">Find the entity bioSampleCollectedBy in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _bioSampleCollectedBy(Wrap<String> w);

	public String getBioSampleCollectedBy() {
		return bioSampleCollectedBy;
	}
	public void setBioSampleCollectedBy(String o) {
		this.bioSampleCollectedBy = BioSample.staticSetBioSampleCollectedBy(siteRequest_, o);
	}
	public static String staticSetBioSampleCollectedBy(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample bioSampleCollectedByInit() {
		Wrap<String> bioSampleCollectedByWrap = new Wrap<String>().var("bioSampleCollectedBy");
		if(bioSampleCollectedBy == null) {
			_bioSampleCollectedBy(bioSampleCollectedByWrap);
			setBioSampleCollectedBy(bioSampleCollectedByWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrBioSampleCollectedBy(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrBioSampleCollectedBy(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqBioSampleCollectedBy(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrBioSampleCollectedBy(siteRequest_, BioSample.staticSolrBioSampleCollectedBy(siteRequest_, BioSample.staticSetBioSampleCollectedBy(siteRequest_, o)));
	}

	public String sqlBioSampleCollectedBy() {
		return bioSampleCollectedBy;
	}

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyId">Find the entity taxonomyId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyId(Wrap<String> w);

	public String getTaxonomyId() {
		return taxonomyId;
	}
	public void setTaxonomyId(String o) {
		this.taxonomyId = BioSample.staticSetTaxonomyId(siteRequest_, o);
	}
	public static String staticSetTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample taxonomyIdInit() {
		Wrap<String> taxonomyIdWrap = new Wrap<String>().var("taxonomyId");
		if(taxonomyId == null) {
			_taxonomyId(taxonomyIdWrap);
			setTaxonomyId(taxonomyIdWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyId(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrTaxonomyId(siteRequest_, BioSample.staticSolrTaxonomyId(siteRequest_, BioSample.staticSetTaxonomyId(siteRequest_, o)));
	}

	public String sqlTaxonomyId() {
		return taxonomyId;
	}

	////////////////////
	// taxonomySearch //
	////////////////////

	/**	 The entity taxonomySearch
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<Taxonomy> taxonomySearch;

	/**	<br/> The entity taxonomySearch
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomySearch">Find the entity taxonomySearch in Solr</a>
	 * <br/>
	 * @param promise is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomySearch(Promise<SearchList<Taxonomy>> promise);

	public SearchList<Taxonomy> getTaxonomySearch() {
		return taxonomySearch;
	}

	public void setTaxonomySearch(SearchList<Taxonomy> taxonomySearch) {
		this.taxonomySearch = taxonomySearch;
	}
	public static SearchList<Taxonomy> staticSetTaxonomySearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected Future<SearchList<Taxonomy>> taxonomySearchPromise() {
		Promise<SearchList<Taxonomy>> promise = Promise.promise();
		Promise<SearchList<Taxonomy>> promise2 = Promise.promise();
		_taxonomySearch(promise2);
		promise2.future().onSuccess(o -> {
			if(o != null && taxonomySearch == null) {
				o.promiseDeepForClass(siteRequest_).onSuccess(a -> {
					setTaxonomySearch(o);
					promise.complete(o);
				}).onFailure(ex -> {
					promise.fail(ex);
				});
			} else {
				promise.complete(o);
			}
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomy_">Find the entity taxonomy_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomy_(Wrap<Taxonomy> c);

	public Taxonomy getTaxonomy_() {
		return taxonomy_;
	}

	public void setTaxonomy_(Taxonomy taxonomy_) {
		this.taxonomy_ = taxonomy_;
	}
	public static Taxonomy staticSetTaxonomy_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected BioSample taxonomy_Init() {
		Wrap<Taxonomy> taxonomy_Wrap = new Wrap<Taxonomy>().var("taxonomy_");
		if(taxonomy_ == null) {
			_taxonomy_(taxonomy_Wrap);
			setTaxonomy_(taxonomy_Wrap.o);
		}
		return (BioSample)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyName">Find the entity taxonomyName in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyName(Wrap<String> w);

	public String getTaxonomyName() {
		return taxonomyName;
	}
	public void setTaxonomyName(String o) {
		this.taxonomyName = BioSample.staticSetTaxonomyName(siteRequest_, o);
	}
	public static String staticSetTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample taxonomyNameInit() {
		Wrap<String> taxonomyNameWrap = new Wrap<String>().var("taxonomyName");
		if(taxonomyName == null) {
			_taxonomyName(taxonomyNameWrap);
			setTaxonomyName(taxonomyNameWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyName(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrTaxonomyName(siteRequest_, BioSample.staticSolrTaxonomyName(siteRequest_, BioSample.staticSetTaxonomyName(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyUniqueName">Find the entity taxonomyUniqueName in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyUniqueName(Wrap<String> w);

	public String getTaxonomyUniqueName() {
		return taxonomyUniqueName;
	}
	public void setTaxonomyUniqueName(String o) {
		this.taxonomyUniqueName = BioSample.staticSetTaxonomyUniqueName(siteRequest_, o);
	}
	public static String staticSetTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample taxonomyUniqueNameInit() {
		Wrap<String> taxonomyUniqueNameWrap = new Wrap<String>().var("taxonomyUniqueName");
		if(taxonomyUniqueName == null) {
			_taxonomyUniqueName(taxonomyUniqueNameWrap);
			setTaxonomyUniqueName(taxonomyUniqueNameWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyUniqueName(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrTaxonomyUniqueName(siteRequest_, BioSample.staticSolrTaxonomyUniqueName(siteRequest_, BioSample.staticSetTaxonomyUniqueName(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:io.serratus.api.enus.model.biosample.BioSample&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:taxonomyNameClass">Find the entity taxonomyNameClass in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _taxonomyNameClass(Wrap<String> w);

	public String getTaxonomyNameClass() {
		return taxonomyNameClass;
	}
	public void setTaxonomyNameClass(String o) {
		this.taxonomyNameClass = BioSample.staticSetTaxonomyNameClass(siteRequest_, o);
	}
	public static String staticSetTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected BioSample taxonomyNameClassInit() {
		Wrap<String> taxonomyNameClassWrap = new Wrap<String>().var("taxonomyNameClass");
		if(taxonomyNameClass == null) {
			_taxonomyNameClass(taxonomyNameClassWrap);
			setTaxonomyNameClass(taxonomyNameClassWrap.o);
		}
		return (BioSample)this;
	}

	public static String staticSolrTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTaxonomyNameClass(SiteRequestEnUS siteRequest_, String o) {
		return BioSample.staticSolrStrTaxonomyNameClass(siteRequest_, BioSample.staticSolrTaxonomyNameClass(siteRequest_, BioSample.staticSetTaxonomyNameClass(siteRequest_, o)));
	}

	public String sqlTaxonomyNameClass() {
		return taxonomyNameClass;
	}

	//////////////
	// initDeep //
	//////////////

	public Future<Void> promiseDeepBioSample(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		return promiseDeepBioSample();
	}

	public Future<Void> promiseDeepBioSample() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseBioSample(promise2);
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

	public Future<Void> promiseBioSample(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				bioSampleIdInit();
				bioSampleIsolateInit();
				bioSampleHostInit();
				bioSampleLabHostInit();
				bioSampleIsolationSourceInit();
				bioSampleGeographicLocationInit();
				bioSampleLatitudeLongitudeInit();
				bioSampleReferenceForBioMaterialInit();
				bioSampleMaterialProcessingInit();
				bioSampleMaterialIdentifiersInit();
				bioSampleDiseaseInit();
				bioSampleHostTissueSampledInit();
				bioSampleCollectedByInit();
				taxonomyIdInit();
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			taxonomySearchPromise().onSuccess(taxonomySearch -> {
				promise2.complete();
			}).onFailure(ex -> {
				promise2.fail(ex);
			});
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				taxonomy_Init();
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
		return promiseDeepBioSample(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestBioSample(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModel(siteRequest_);
		if(taxonomySearch != null)
			taxonomySearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestBioSample(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainBioSample(v);
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
	public Object obtainBioSample(String var) {
		BioSample oBioSample = (BioSample)this;
		switch(var) {
			case "bioSampleId":
				return oBioSample.bioSampleId;
			case "bioSampleIsolate":
				return oBioSample.bioSampleIsolate;
			case "bioSampleHost":
				return oBioSample.bioSampleHost;
			case "bioSampleLabHost":
				return oBioSample.bioSampleLabHost;
			case "bioSampleIsolationSource":
				return oBioSample.bioSampleIsolationSource;
			case "bioSampleGeographicLocation":
				return oBioSample.bioSampleGeographicLocation;
			case "bioSampleLatitudeLongitude":
				return oBioSample.bioSampleLatitudeLongitude;
			case "bioSampleReferenceForBioMaterial":
				return oBioSample.bioSampleReferenceForBioMaterial;
			case "bioSampleMaterialProcessing":
				return oBioSample.bioSampleMaterialProcessing;
			case "bioSampleMaterialIdentifiers":
				return oBioSample.bioSampleMaterialIdentifiers;
			case "bioSampleDisease":
				return oBioSample.bioSampleDisease;
			case "bioSampleHostTissueSampled":
				return oBioSample.bioSampleHostTissueSampled;
			case "bioSampleCollectedBy":
				return oBioSample.bioSampleCollectedBy;
			case "taxonomyId":
				return oBioSample.taxonomyId;
			case "taxonomySearch":
				return oBioSample.taxonomySearch;
			case "taxonomy_":
				return oBioSample.taxonomy_;
			case "taxonomyName":
				return oBioSample.taxonomyName;
			case "taxonomyUniqueName":
				return oBioSample.taxonomyUniqueName;
			case "taxonomyNameClass":
				return oBioSample.taxonomyNameClass;
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
				o = relateBioSample(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.relateForClass(v, val);
			}
		}
		return o != null;
	}
	public Object relateBioSample(String var, Object val) {
		BioSample oBioSample = (BioSample)this;
		switch(var) {
			default:
				return super.relateBaseModel(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetBioSample(entityVar,  siteRequest_, o);
	}
	public static Object staticSetBioSample(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "bioSampleId":
			return BioSample.staticSetBioSampleId(siteRequest_, o);
		case "bioSampleIsolate":
			return BioSample.staticSetBioSampleIsolate(siteRequest_, o);
		case "bioSampleHost":
			return BioSample.staticSetBioSampleHost(siteRequest_, o);
		case "bioSampleLabHost":
			return BioSample.staticSetBioSampleLabHost(siteRequest_, o);
		case "bioSampleIsolationSource":
			return BioSample.staticSetBioSampleIsolationSource(siteRequest_, o);
		case "bioSampleGeographicLocation":
			return BioSample.staticSetBioSampleGeographicLocation(siteRequest_, o);
		case "bioSampleLatitudeLongitude":
			return BioSample.staticSetBioSampleLatitudeLongitude(siteRequest_, o);
		case "bioSampleReferenceForBioMaterial":
			return BioSample.staticSetBioSampleReferenceForBioMaterial(siteRequest_, o);
		case "bioSampleMaterialProcessing":
			return BioSample.staticSetBioSampleMaterialProcessing(siteRequest_, o);
		case "bioSampleMaterialIdentifiers":
			return BioSample.staticSetBioSampleMaterialIdentifiers(siteRequest_, o);
		case "bioSampleDisease":
			return BioSample.staticSetBioSampleDisease(siteRequest_, o);
		case "bioSampleHostTissueSampled":
			return BioSample.staticSetBioSampleHostTissueSampled(siteRequest_, o);
		case "bioSampleCollectedBy":
			return BioSample.staticSetBioSampleCollectedBy(siteRequest_, o);
		case "taxonomyId":
			return BioSample.staticSetTaxonomyId(siteRequest_, o);
		case "taxonomyName":
			return BioSample.staticSetTaxonomyName(siteRequest_, o);
		case "taxonomyUniqueName":
			return BioSample.staticSetTaxonomyUniqueName(siteRequest_, o);
		case "taxonomyNameClass":
			return BioSample.staticSetTaxonomyNameClass(siteRequest_, o);
			default:
				return BaseModel.staticSetBaseModel(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrBioSample(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrBioSample(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "bioSampleId":
			return BioSample.staticSolrBioSampleId(siteRequest_, (String)o);
		case "bioSampleIsolate":
			return BioSample.staticSolrBioSampleIsolate(siteRequest_, (String)o);
		case "bioSampleHost":
			return BioSample.staticSolrBioSampleHost(siteRequest_, (String)o);
		case "bioSampleLabHost":
			return BioSample.staticSolrBioSampleLabHost(siteRequest_, (String)o);
		case "bioSampleIsolationSource":
			return BioSample.staticSolrBioSampleIsolationSource(siteRequest_, (String)o);
		case "bioSampleGeographicLocation":
			return BioSample.staticSolrBioSampleGeographicLocation(siteRequest_, (String)o);
		case "bioSampleLatitudeLongitude":
			return BioSample.staticSolrBioSampleLatitudeLongitude(siteRequest_, (String)o);
		case "bioSampleReferenceForBioMaterial":
			return BioSample.staticSolrBioSampleReferenceForBioMaterial(siteRequest_, (String)o);
		case "bioSampleMaterialProcessing":
			return BioSample.staticSolrBioSampleMaterialProcessing(siteRequest_, (String)o);
		case "bioSampleMaterialIdentifiers":
			return BioSample.staticSolrBioSampleMaterialIdentifiers(siteRequest_, (String)o);
		case "bioSampleDisease":
			return BioSample.staticSolrBioSampleDisease(siteRequest_, (String)o);
		case "bioSampleHostTissueSampled":
			return BioSample.staticSolrBioSampleHostTissueSampled(siteRequest_, (String)o);
		case "bioSampleCollectedBy":
			return BioSample.staticSolrBioSampleCollectedBy(siteRequest_, (String)o);
		case "taxonomyId":
			return BioSample.staticSolrTaxonomyId(siteRequest_, (String)o);
		case "taxonomyName":
			return BioSample.staticSolrTaxonomyName(siteRequest_, (String)o);
		case "taxonomyUniqueName":
			return BioSample.staticSolrTaxonomyUniqueName(siteRequest_, (String)o);
		case "taxonomyNameClass":
			return BioSample.staticSolrTaxonomyNameClass(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrBioSample(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrBioSample(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "bioSampleId":
			return BioSample.staticSolrStrBioSampleId(siteRequest_, (String)o);
		case "bioSampleIsolate":
			return BioSample.staticSolrStrBioSampleIsolate(siteRequest_, (String)o);
		case "bioSampleHost":
			return BioSample.staticSolrStrBioSampleHost(siteRequest_, (String)o);
		case "bioSampleLabHost":
			return BioSample.staticSolrStrBioSampleLabHost(siteRequest_, (String)o);
		case "bioSampleIsolationSource":
			return BioSample.staticSolrStrBioSampleIsolationSource(siteRequest_, (String)o);
		case "bioSampleGeographicLocation":
			return BioSample.staticSolrStrBioSampleGeographicLocation(siteRequest_, (String)o);
		case "bioSampleLatitudeLongitude":
			return BioSample.staticSolrStrBioSampleLatitudeLongitude(siteRequest_, (String)o);
		case "bioSampleReferenceForBioMaterial":
			return BioSample.staticSolrStrBioSampleReferenceForBioMaterial(siteRequest_, (String)o);
		case "bioSampleMaterialProcessing":
			return BioSample.staticSolrStrBioSampleMaterialProcessing(siteRequest_, (String)o);
		case "bioSampleMaterialIdentifiers":
			return BioSample.staticSolrStrBioSampleMaterialIdentifiers(siteRequest_, (String)o);
		case "bioSampleDisease":
			return BioSample.staticSolrStrBioSampleDisease(siteRequest_, (String)o);
		case "bioSampleHostTissueSampled":
			return BioSample.staticSolrStrBioSampleHostTissueSampled(siteRequest_, (String)o);
		case "bioSampleCollectedBy":
			return BioSample.staticSolrStrBioSampleCollectedBy(siteRequest_, (String)o);
		case "taxonomyId":
			return BioSample.staticSolrStrTaxonomyId(siteRequest_, (String)o);
		case "taxonomyName":
			return BioSample.staticSolrStrTaxonomyName(siteRequest_, (String)o);
		case "taxonomyUniqueName":
			return BioSample.staticSolrStrTaxonomyUniqueName(siteRequest_, (String)o);
		case "taxonomyNameClass":
			return BioSample.staticSolrStrTaxonomyNameClass(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrStrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqBioSample(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqBioSample(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "bioSampleId":
			return BioSample.staticSolrFqBioSampleId(siteRequest_, o);
		case "bioSampleIsolate":
			return BioSample.staticSolrFqBioSampleIsolate(siteRequest_, o);
		case "bioSampleHost":
			return BioSample.staticSolrFqBioSampleHost(siteRequest_, o);
		case "bioSampleLabHost":
			return BioSample.staticSolrFqBioSampleLabHost(siteRequest_, o);
		case "bioSampleIsolationSource":
			return BioSample.staticSolrFqBioSampleIsolationSource(siteRequest_, o);
		case "bioSampleGeographicLocation":
			return BioSample.staticSolrFqBioSampleGeographicLocation(siteRequest_, o);
		case "bioSampleLatitudeLongitude":
			return BioSample.staticSolrFqBioSampleLatitudeLongitude(siteRequest_, o);
		case "bioSampleReferenceForBioMaterial":
			return BioSample.staticSolrFqBioSampleReferenceForBioMaterial(siteRequest_, o);
		case "bioSampleMaterialProcessing":
			return BioSample.staticSolrFqBioSampleMaterialProcessing(siteRequest_, o);
		case "bioSampleMaterialIdentifiers":
			return BioSample.staticSolrFqBioSampleMaterialIdentifiers(siteRequest_, o);
		case "bioSampleDisease":
			return BioSample.staticSolrFqBioSampleDisease(siteRequest_, o);
		case "bioSampleHostTissueSampled":
			return BioSample.staticSolrFqBioSampleHostTissueSampled(siteRequest_, o);
		case "bioSampleCollectedBy":
			return BioSample.staticSolrFqBioSampleCollectedBy(siteRequest_, o);
		case "taxonomyId":
			return BioSample.staticSolrFqTaxonomyId(siteRequest_, o);
		case "taxonomyName":
			return BioSample.staticSolrFqTaxonomyName(siteRequest_, o);
		case "taxonomyUniqueName":
			return BioSample.staticSolrFqTaxonomyUniqueName(siteRequest_, o);
		case "taxonomyNameClass":
			return BioSample.staticSolrFqTaxonomyNameClass(siteRequest_, o);
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
					o = defineBioSample(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineBioSample(String var, Object val) {
		switch(var.toLowerCase()) {
			case "biosampleid":
				if(val instanceof String)
					setBioSampleId((String)val);
				saves.add("bioSampleId");
				return val;
			case "biosampleisolate":
				if(val instanceof String)
					setBioSampleIsolate((String)val);
				saves.add("bioSampleIsolate");
				return val;
			case "biosamplehost":
				if(val instanceof String)
					setBioSampleHost((String)val);
				saves.add("bioSampleHost");
				return val;
			case "biosamplelabhost":
				if(val instanceof String)
					setBioSampleLabHost((String)val);
				saves.add("bioSampleLabHost");
				return val;
			case "biosampleisolationsource":
				if(val instanceof String)
					setBioSampleIsolationSource((String)val);
				saves.add("bioSampleIsolationSource");
				return val;
			case "biosamplegeographiclocation":
				if(val instanceof String)
					setBioSampleGeographicLocation((String)val);
				saves.add("bioSampleGeographicLocation");
				return val;
			case "biosamplelatitudelongitude":
				if(val instanceof String)
					setBioSampleLatitudeLongitude((String)val);
				saves.add("bioSampleLatitudeLongitude");
				return val;
			case "biosamplereferenceforbiomaterial":
				if(val instanceof String)
					setBioSampleReferenceForBioMaterial((String)val);
				saves.add("bioSampleReferenceForBioMaterial");
				return val;
			case "biosamplematerialprocessing":
				if(val instanceof String)
					setBioSampleMaterialProcessing((String)val);
				saves.add("bioSampleMaterialProcessing");
				return val;
			case "biosamplematerialidentifiers":
				if(val instanceof String)
					setBioSampleMaterialIdentifiers((String)val);
				saves.add("bioSampleMaterialIdentifiers");
				return val;
			case "biosampledisease":
				if(val instanceof String)
					setBioSampleDisease((String)val);
				saves.add("bioSampleDisease");
				return val;
			case "biosamplehosttissuesampled":
				if(val instanceof String)
					setBioSampleHostTissueSampled((String)val);
				saves.add("bioSampleHostTissueSampled");
				return val;
			case "biosamplecollectedby":
				if(val instanceof String)
					setBioSampleCollectedBy((String)val);
				saves.add("bioSampleCollectedBy");
				return val;
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
		populateBioSample(solrDocument);
	}
	public void populateBioSample(SolrDocument solrDocument) {
		BioSample oBioSample = (BioSample)this;
		saves = (List<String>)solrDocument.get("saves_indexedstored_strings");
		if(saves != null) {

			if(saves.contains("bioSampleId")) {
				String bioSampleId = (String)solrDocument.get("bioSampleId_indexedstored_string");
				if(bioSampleId != null)
					oBioSample.setBioSampleId(bioSampleId);
			}

			if(saves.contains("bioSampleIsolate")) {
				String bioSampleIsolate = (String)solrDocument.get("bioSampleIsolate_indexedstored_string");
				if(bioSampleIsolate != null)
					oBioSample.setBioSampleIsolate(bioSampleIsolate);
			}

			if(saves.contains("bioSampleHost")) {
				String bioSampleHost = (String)solrDocument.get("bioSampleHost_indexedstored_string");
				if(bioSampleHost != null)
					oBioSample.setBioSampleHost(bioSampleHost);
			}

			if(saves.contains("bioSampleLabHost")) {
				String bioSampleLabHost = (String)solrDocument.get("bioSampleLabHost_indexedstored_string");
				if(bioSampleLabHost != null)
					oBioSample.setBioSampleLabHost(bioSampleLabHost);
			}

			if(saves.contains("bioSampleIsolationSource")) {
				String bioSampleIsolationSource = (String)solrDocument.get("bioSampleIsolationSource_indexedstored_string");
				if(bioSampleIsolationSource != null)
					oBioSample.setBioSampleIsolationSource(bioSampleIsolationSource);
			}

			if(saves.contains("bioSampleGeographicLocation")) {
				String bioSampleGeographicLocation = (String)solrDocument.get("bioSampleGeographicLocation_indexedstored_string");
				if(bioSampleGeographicLocation != null)
					oBioSample.setBioSampleGeographicLocation(bioSampleGeographicLocation);
			}

			if(saves.contains("bioSampleLatitudeLongitude")) {
				String bioSampleLatitudeLongitude = (String)solrDocument.get("bioSampleLatitudeLongitude_indexedstored_string");
				if(bioSampleLatitudeLongitude != null)
					oBioSample.setBioSampleLatitudeLongitude(bioSampleLatitudeLongitude);
			}

			if(saves.contains("bioSampleReferenceForBioMaterial")) {
				String bioSampleReferenceForBioMaterial = (String)solrDocument.get("bioSampleReferenceForBioMaterial_indexedstored_string");
				if(bioSampleReferenceForBioMaterial != null)
					oBioSample.setBioSampleReferenceForBioMaterial(bioSampleReferenceForBioMaterial);
			}

			if(saves.contains("bioSampleMaterialProcessing")) {
				String bioSampleMaterialProcessing = (String)solrDocument.get("bioSampleMaterialProcessing_indexedstored_string");
				if(bioSampleMaterialProcessing != null)
					oBioSample.setBioSampleMaterialProcessing(bioSampleMaterialProcessing);
			}

			if(saves.contains("bioSampleMaterialIdentifiers")) {
				String bioSampleMaterialIdentifiers = (String)solrDocument.get("bioSampleMaterialIdentifiers_indexedstored_string");
				if(bioSampleMaterialIdentifiers != null)
					oBioSample.setBioSampleMaterialIdentifiers(bioSampleMaterialIdentifiers);
			}

			if(saves.contains("bioSampleDisease")) {
				String bioSampleDisease = (String)solrDocument.get("bioSampleDisease_indexedstored_string");
				if(bioSampleDisease != null)
					oBioSample.setBioSampleDisease(bioSampleDisease);
			}

			if(saves.contains("bioSampleHostTissueSampled")) {
				String bioSampleHostTissueSampled = (String)solrDocument.get("bioSampleHostTissueSampled_indexedstored_string");
				if(bioSampleHostTissueSampled != null)
					oBioSample.setBioSampleHostTissueSampled(bioSampleHostTissueSampled);
			}

			if(saves.contains("bioSampleCollectedBy")) {
				String bioSampleCollectedBy = (String)solrDocument.get("bioSampleCollectedBy_indexedstored_string");
				if(bioSampleCollectedBy != null)
					oBioSample.setBioSampleCollectedBy(bioSampleCollectedBy);
			}

			if(saves.contains("taxonomyId")) {
				String taxonomyId = (String)solrDocument.get("taxonomyId_indexedstored_string");
				if(taxonomyId != null)
					oBioSample.setTaxonomyId(taxonomyId);
			}

			if(saves.contains("taxonomyName")) {
				String taxonomyName = (String)solrDocument.get("taxonomyName_indexedstored_string");
				if(taxonomyName != null)
					oBioSample.setTaxonomyName(taxonomyName);
			}

			if(saves.contains("taxonomyUniqueName")) {
				String taxonomyUniqueName = (String)solrDocument.get("taxonomyUniqueName_indexedstored_string");
				if(taxonomyUniqueName != null)
					oBioSample.setTaxonomyUniqueName(taxonomyUniqueName);
			}

			if(saves.contains("taxonomyNameClass")) {
				String taxonomyNameClass = (String)solrDocument.get("taxonomyNameClass_indexedstored_string");
				if(taxonomyNameClass != null)
					oBioSample.setTaxonomyNameClass(taxonomyNameClass);
			}
		}

		super.populateBaseModel(solrDocument);
	}

	public void indexBioSample(SolrInputDocument document) {
		if(bioSampleId != null) {
			document.addField("bioSampleId_indexedstored_string", bioSampleId);
		}
		if(bioSampleIsolate != null) {
			document.addField("bioSampleIsolate_indexedstored_string", bioSampleIsolate);
		}
		if(bioSampleHost != null) {
			document.addField("bioSampleHost_indexedstored_string", bioSampleHost);
		}
		if(bioSampleLabHost != null) {
			document.addField("bioSampleLabHost_indexedstored_string", bioSampleLabHost);
		}
		if(bioSampleIsolationSource != null) {
			document.addField("bioSampleIsolationSource_indexedstored_string", bioSampleIsolationSource);
		}
		if(bioSampleGeographicLocation != null) {
			document.addField("bioSampleGeographicLocation_indexedstored_string", bioSampleGeographicLocation);
		}
		if(bioSampleLatitudeLongitude != null) {
			document.addField("bioSampleLatitudeLongitude_indexedstored_string", bioSampleLatitudeLongitude);
		}
		if(bioSampleReferenceForBioMaterial != null) {
			document.addField("bioSampleReferenceForBioMaterial_indexedstored_string", bioSampleReferenceForBioMaterial);
		}
		if(bioSampleMaterialProcessing != null) {
			document.addField("bioSampleMaterialProcessing_indexedstored_string", bioSampleMaterialProcessing);
		}
		if(bioSampleMaterialIdentifiers != null) {
			document.addField("bioSampleMaterialIdentifiers_indexedstored_string", bioSampleMaterialIdentifiers);
		}
		if(bioSampleDisease != null) {
			document.addField("bioSampleDisease_indexedstored_string", bioSampleDisease);
		}
		if(bioSampleHostTissueSampled != null) {
			document.addField("bioSampleHostTissueSampled_indexedstored_string", bioSampleHostTissueSampled);
		}
		if(bioSampleCollectedBy != null) {
			document.addField("bioSampleCollectedBy_indexedstored_string", bioSampleCollectedBy);
		}
		if(taxonomyId != null) {
			document.addField("taxonomyId_indexedstored_string", taxonomyId);
		}
		if(taxonomyName != null) {
			document.addField("taxonomyName_indexedstored_string", taxonomyName);
		}
		if(taxonomyUniqueName != null) {
			document.addField("taxonomyUniqueName_indexedstored_string", taxonomyUniqueName);
		}
		if(taxonomyNameClass != null) {
			document.addField("taxonomyNameClass_indexedstored_string", taxonomyNameClass);
		}
		super.indexBaseModel(document);

	}

	public static String varIndexedBioSample(String entityVar) {
		switch(entityVar) {
			case "bioSampleId":
				return "bioSampleId_indexedstored_string";
			case "bioSampleIsolate":
				return "bioSampleIsolate_indexedstored_string";
			case "bioSampleHost":
				return "bioSampleHost_indexedstored_string";
			case "bioSampleLabHost":
				return "bioSampleLabHost_indexedstored_string";
			case "bioSampleIsolationSource":
				return "bioSampleIsolationSource_indexedstored_string";
			case "bioSampleGeographicLocation":
				return "bioSampleGeographicLocation_indexedstored_string";
			case "bioSampleLatitudeLongitude":
				return "bioSampleLatitudeLongitude_indexedstored_string";
			case "bioSampleReferenceForBioMaterial":
				return "bioSampleReferenceForBioMaterial_indexedstored_string";
			case "bioSampleMaterialProcessing":
				return "bioSampleMaterialProcessing_indexedstored_string";
			case "bioSampleMaterialIdentifiers":
				return "bioSampleMaterialIdentifiers_indexedstored_string";
			case "bioSampleDisease":
				return "bioSampleDisease_indexedstored_string";
			case "bioSampleHostTissueSampled":
				return "bioSampleHostTissueSampled_indexedstored_string";
			case "bioSampleCollectedBy":
				return "bioSampleCollectedBy_indexedstored_string";
			case "taxonomyId":
				return "taxonomyId_indexedstored_string";
			case "taxonomyName":
				return "taxonomyName_indexedstored_string";
			case "taxonomyUniqueName":
				return "taxonomyUniqueName_indexedstored_string";
			case "taxonomyNameClass":
				return "taxonomyNameClass_indexedstored_string";
			default:
				return BaseModel.varIndexedBaseModel(entityVar);
		}
	}

	public static String varSearchBioSample(String entityVar) {
		switch(entityVar) {
			default:
				return BaseModel.varSearchBaseModel(entityVar);
		}
	}

	public static String varSuggestedBioSample(String entityVar) {
		switch(entityVar) {
			default:
				return BaseModel.varSuggestedBaseModel(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeBioSample(solrDocument);
	}
	public void storeBioSample(SolrDocument solrDocument) {
		BioSample oBioSample = (BioSample)this;

		oBioSample.setBioSampleId(Optional.ofNullable(solrDocument.get("bioSampleId_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleIsolate(Optional.ofNullable(solrDocument.get("bioSampleIsolate_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleHost(Optional.ofNullable(solrDocument.get("bioSampleHost_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleLabHost(Optional.ofNullable(solrDocument.get("bioSampleLabHost_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleIsolationSource(Optional.ofNullable(solrDocument.get("bioSampleIsolationSource_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleGeographicLocation(Optional.ofNullable(solrDocument.get("bioSampleGeographicLocation_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleLatitudeLongitude(Optional.ofNullable(solrDocument.get("bioSampleLatitudeLongitude_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleReferenceForBioMaterial(Optional.ofNullable(solrDocument.get("bioSampleReferenceForBioMaterial_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleMaterialProcessing(Optional.ofNullable(solrDocument.get("bioSampleMaterialProcessing_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleMaterialIdentifiers(Optional.ofNullable(solrDocument.get("bioSampleMaterialIdentifiers_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleDisease(Optional.ofNullable(solrDocument.get("bioSampleDisease_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleHostTissueSampled(Optional.ofNullable(solrDocument.get("bioSampleHostTissueSampled_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setBioSampleCollectedBy(Optional.ofNullable(solrDocument.get("bioSampleCollectedBy_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setTaxonomyId(Optional.ofNullable(solrDocument.get("taxonomyId_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setTaxonomyName(Optional.ofNullable(solrDocument.get("taxonomyName_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setTaxonomyUniqueName(Optional.ofNullable(solrDocument.get("taxonomyUniqueName_indexedstored_string")).map(v -> v.toString()).orElse(null));
		oBioSample.setTaxonomyNameClass(Optional.ofNullable(solrDocument.get("taxonomyNameClass_indexedstored_string")).map(v -> v.toString()).orElse(null));

		super.storeBaseModel(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestBioSample() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof BioSample) {
			BioSample original = (BioSample)o;
			if(!Objects.equals(bioSampleId, original.getBioSampleId()))
				apiRequest.addVars("bioSampleId");
			if(!Objects.equals(bioSampleIsolate, original.getBioSampleIsolate()))
				apiRequest.addVars("bioSampleIsolate");
			if(!Objects.equals(bioSampleHost, original.getBioSampleHost()))
				apiRequest.addVars("bioSampleHost");
			if(!Objects.equals(bioSampleLabHost, original.getBioSampleLabHost()))
				apiRequest.addVars("bioSampleLabHost");
			if(!Objects.equals(bioSampleIsolationSource, original.getBioSampleIsolationSource()))
				apiRequest.addVars("bioSampleIsolationSource");
			if(!Objects.equals(bioSampleGeographicLocation, original.getBioSampleGeographicLocation()))
				apiRequest.addVars("bioSampleGeographicLocation");
			if(!Objects.equals(bioSampleLatitudeLongitude, original.getBioSampleLatitudeLongitude()))
				apiRequest.addVars("bioSampleLatitudeLongitude");
			if(!Objects.equals(bioSampleReferenceForBioMaterial, original.getBioSampleReferenceForBioMaterial()))
				apiRequest.addVars("bioSampleReferenceForBioMaterial");
			if(!Objects.equals(bioSampleMaterialProcessing, original.getBioSampleMaterialProcessing()))
				apiRequest.addVars("bioSampleMaterialProcessing");
			if(!Objects.equals(bioSampleMaterialIdentifiers, original.getBioSampleMaterialIdentifiers()))
				apiRequest.addVars("bioSampleMaterialIdentifiers");
			if(!Objects.equals(bioSampleDisease, original.getBioSampleDisease()))
				apiRequest.addVars("bioSampleDisease");
			if(!Objects.equals(bioSampleHostTissueSampled, original.getBioSampleHostTissueSampled()))
				apiRequest.addVars("bioSampleHostTissueSampled");
			if(!Objects.equals(bioSampleCollectedBy, original.getBioSampleCollectedBy()))
				apiRequest.addVars("bioSampleCollectedBy");
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
		sb.append(Optional.ofNullable(bioSampleId).map(v -> "bioSampleId: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleIsolate).map(v -> "bioSampleIsolate: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleHost).map(v -> "bioSampleHost: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleLabHost).map(v -> "bioSampleLabHost: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleIsolationSource).map(v -> "bioSampleIsolationSource: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleGeographicLocation).map(v -> "bioSampleGeographicLocation: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleLatitudeLongitude).map(v -> "bioSampleLatitudeLongitude: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleReferenceForBioMaterial).map(v -> "bioSampleReferenceForBioMaterial: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleMaterialProcessing).map(v -> "bioSampleMaterialProcessing: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleMaterialIdentifiers).map(v -> "bioSampleMaterialIdentifiers: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleDisease).map(v -> "bioSampleDisease: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleHostTissueSampled).map(v -> "bioSampleHostTissueSampled: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(bioSampleCollectedBy).map(v -> "bioSampleCollectedBy: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyId).map(v -> "taxonomyId: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyName).map(v -> "taxonomyName: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyUniqueName).map(v -> "taxonomyUniqueName: \"" + v + "\"\n" ).orElse(""));
		sb.append(Optional.ofNullable(taxonomyNameClass).map(v -> "taxonomyNameClass: \"" + v + "\"\n" ).orElse(""));
		return sb.toString();
	}

	public static final String VAR_bioSampleId = "bioSampleId";
	public static final String VAR_bioSampleIsolate = "bioSampleIsolate";
	public static final String VAR_bioSampleHost = "bioSampleHost";
	public static final String VAR_bioSampleLabHost = "bioSampleLabHost";
	public static final String VAR_bioSampleIsolationSource = "bioSampleIsolationSource";
	public static final String VAR_bioSampleGeographicLocation = "bioSampleGeographicLocation";
	public static final String VAR_bioSampleLatitudeLongitude = "bioSampleLatitudeLongitude";
	public static final String VAR_bioSampleReferenceForBioMaterial = "bioSampleReferenceForBioMaterial";
	public static final String VAR_bioSampleMaterialProcessing = "bioSampleMaterialProcessing";
	public static final String VAR_bioSampleMaterialIdentifiers = "bioSampleMaterialIdentifiers";
	public static final String VAR_bioSampleDisease = "bioSampleDisease";
	public static final String VAR_bioSampleHostTissueSampled = "bioSampleHostTissueSampled";
	public static final String VAR_bioSampleCollectedBy = "bioSampleCollectedBy";
	public static final String VAR_taxonomyId = "taxonomyId";
	public static final String VAR_taxonomySearch = "taxonomySearch";
	public static final String VAR_taxonomy_ = "taxonomy_";
	public static final String VAR_taxonomyName = "taxonomyName";
	public static final String VAR_taxonomyUniqueName = "taxonomyUniqueName";
	public static final String VAR_taxonomyNameClass = "taxonomyNameClass";
}
