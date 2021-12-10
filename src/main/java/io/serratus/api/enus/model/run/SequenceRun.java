package io.serratus.api.enus.model.run; 

import java.time.ZonedDateTime;
import java.util.Optional;

import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Page: true
 * SuperPage.enUS: BaseModelPage
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 7
 * 
 * ApiTag.enUS: Sequence Run
 * ApiUri.enUS: /api/run
 * 
 * ApiMethod.enUS: PUTImport

 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: SequenceRunPage
 * PageSuperSearchPage.enUS: BaseModelPage
 * ApiUriSearchPage.enUS: /run
 * 
 * AName.enUS: a sequence run
 * Color: pale-green
 * IconGroup: regular
 * IconName: dna
 * NameVar.enUS: run
 * 
 * Role.enUS: SiteAdmin
 * PublicRead: true
 * 
 * Map.hackathonMission: to create a new Java class SequenceRun to store information about RNA seqence runs. 
 * Map.hackathonColumn: Develop SequenceRun API
 * Map.hackathonLabels: Java
 * Map.hackathonMissionGen: to create a generated Java class that can be extended and override these methods to store information about RNA seqence runs. 
 * Map.hackathonColumnGen: Develop SequenceRun API
 * Map.hackathonLabelsGen: Java
 * Map.hackathonMissionGenApiServiceImpl: to create a new Java API implementation class to persist information about RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumnGenApiServiceImpl: Develop SequenceRun API
 * Map.hackathonLabelsGenApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenApiService: to create a new Java API interface to define API methods for adding, updating and querying RNA seqence runs. 
 * Map.hackathonColumnGenApiService: Develop SequenceRun API
 * Map.hackathonLabelsGenApiService: Java,Vert.x
 * Map.hackathonMissionApiServiceImpl: to create a new Java API implementation class to extend and override any generated API functionality about RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumnApiServiceImpl: Develop SequenceRun API
 * Map.hackathonLabelsApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenPageHbs: to create an Handlebars HTML template for the code to render a page for RNA sequence runs. 
 * Map.hackathonColumnGenPageHbs: Develop SequenceRun UI
 * Map.hackathonLabelsGenPageHbs: HTML,Handlebars
 * Map.hackathonMissionPageHbs: to create an Handlebars HTML template to override the generated code to render a page for RNA sequence runs. 
 * Map.hackathonColumnPageHbs: Develop SequenceRun UI
 * Map.hackathonLabelsPageHbs: HTML, Handlebars
 * Map.hackathonMissionPageJs: to create JavaScript to submit requests to the API for RNA sequence runs. 
 * Map.hackathonColumnPageJs: Develop SequenceRun UI
 * Map.hackathonLabelsPageJs: JavaScript,JQuery
 * Map.hackathonMissionPage: to create the a new Java backend class to override the generated methods to render the page for RNA sequence runs. 
 * Map.hackathonColumnPage: Develop SequenceRun UI
 * Map.hackathonLabelsPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionPageGen: to create the a new Java backend class to render the page for RNA sequence runs. 
 * Map.hackathonColumnPageGen: Develop SequenceRun UI
 * Map.hackathonLabelsPageGen: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPage: to create the a new base Java backend class to render the page for RNA sequence runs. 
 * Map.hackathonColumnGenPage: Develop SequenceRun UI
 * Map.hackathonLabelsGenPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPageGen: to create the a new base Java backend class to render the page for RNA sequence runs. 
 * Map.hackathonColumnGenPageGen: Develop SequenceRun UI
 * Map.hackathonLabelsGenPageGen: Java,Vert.x,Handlebars
 **/             
public class SequenceRun extends SequenceRunGen<BaseModel> {

	////////////////////////
	// SequenceRun fields //
	////////////////////////

	/**    
	 * {@inheritDoc}
	 * DocValues: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: Run ID
	 */
	protected void _runId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * DocValues: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 2
	 * DisplayName.enUS: run release date
	 */ 
	protected void _runReleaseDate(Wrap<ZonedDateTime> w) {
	}

	/**    
	 * {@inheritDoc}
	 * DocValues: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 3
	 * DisplayName.enUS: run load date/time
	 */ 
	protected void _runLoadDate(Wrap<ZonedDateTime> w) {
	}

	/**    
	 * {@inheritDoc}
	 * DocValues: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 1
	 * DisplayName.enUS: Project ID
	 */
	protected void _projectId(Wrap<String> w) {
	}

	//////////////////////
	// BioSample fields //
	//////////////////////

	/**    
	 * {@inheritDoc}
	 * DocValues: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 2
	 * DisplayName.enUS: BioSample ID
	 */
	protected void _bioSampleId(Wrap<String> w) {
	}
//
//	/**
//	 * {@inheritDoc}
//	 * Ignore: true
//	 */ 
//	protected void _bioSampleSearch(Promise<SearchList<BioSample>> promise) {
//		SearchList<BioSample> l = new SearchList<>();
//		if(bioSampleId != null) {
//			l.setQuery("*:*");
//			l.addFilterQuery("bioSampleId_docvalues_string:" + bioSampleId);
//			l.setC(BioSample.class);
//			l.setStore(true);
//		}
//		promise.complete(l);
//	}
//
//	protected void _bioSample_(Wrap<BioSample> c) {
//		if(bioSampleSearch.size() > 0) {
//			c.o(bioSampleSearch.get(0));
//		}
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 5
//	 * HtmlCell: 1
//	 * DisplayName.enUS: BioSample Isolate
//	 */
//	protected void _bioSampleIsolate(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleIsolate).ifPresent(bioSampleIsolate -> {
//			w.o(bioSampleIsolate);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 5
//	 * HtmlCell: 2
//	 * DisplayName.enUS: BioSample Host
//	 */
//	protected void _bioSampleHost(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleHost).ifPresent(bioSampleHost -> {
//			w.o(bioSampleHost);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 5
//	 * HtmlCell: 3
//	 * DisplayName.enUS: BioSample Lab Host
//	 */
//	protected void _bioSampleLabHost(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleLabHost).ifPresent(bioSampleLabHost -> {
//			w.o(bioSampleLabHost);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 5
//	 * HtmlCell: 4
//	 * DisplayName.enUS: BioSample Isolation Source
//	 */
//	protected void _bioSampleIsolationSource(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleIsolationSource).ifPresent(bioSampleIsolationSource -> {
//			w.o(bioSampleIsolationSource);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 6
//	 * HtmlCell: 1
//	 * DisplayName.enUS: BioSample Collection Date
//	 */
//	protected void _bioSampleGeographicLocation(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleGeographicLocation).ifPresent(bioSampleGeographicLocation -> {
//			w.o(bioSampleGeographicLocation);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 6
//	 * HtmlCell: 2
//	 * DisplayName.enUS: BioSample Latitude and Longitude
//	 */
//	protected void _bioSampleLatitudeLongitude(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleLatitudeLongitude).ifPresent(bioSampleLatitudeLongitude -> {
//			w.o(bioSampleLatitudeLongitude);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 6
//	 * HtmlCell: 3
//	 * DisplayName.enUS: BioSample Reference for BioMaterial
//	 */
//	protected void _bioSampleReferenceForBioMaterial(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleReferenceForBioMaterial).ifPresent(bioSampleReferenceForBioMaterial -> {
//			w.o(bioSampleReferenceForBioMaterial);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 6
//	 * HtmlCell: 4
//	 * DisplayName.enUS: BioSample Material Processing
//	 */
//	protected void _bioSampleMaterialProcessing(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleMaterialProcessing).ifPresent(bioSampleMaterialProcessing -> {
//			w.o(bioSampleMaterialProcessing);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 7
//	 * HtmlCell: 1
//	 * DisplayName.enUS: BioSample Material Identifiers
//	 */
//	protected void _bioSampleMaterialIdentifiers(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleMaterialIdentifiers).ifPresent(bioSampleMaterialIdentifiers -> {
//			w.o(bioSampleMaterialIdentifiers);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 7
//	 * HtmlCell: 2
//	 * DisplayName.enUS: BioSample Disease
//	 */
//	protected void _bioSampleDisease(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleDisease).ifPresent(bioSampleDisease -> {
//			w.o(bioSampleDisease);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 7
//	 * HtmlCell: 3
//	 * DisplayName.enUS: BioSample Host Tissue Sampled
//	 */
//	protected void _bioSampleHostTissueSampled(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleHostTissueSampled).ifPresent(bioSampleHostTissueSampled -> {
//			w.o(bioSampleHostTissueSampled);
//		});
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 7
//	 * HtmlCell: 4
//	 * DisplayName.enUS: BioSample Collected By
//	 */
//	protected void _bioSampleCollectedBy(Wrap<String> w) {
//		Optional.ofNullable(bioSample_).map(BioSample::getBioSampleCollectedBy).ifPresent(bioSampleCollectedBy -> {
//			w.o(bioSampleCollectedBy);
//		});
//	}
//
//	/////////////////////
//	// Taxonomy Fields //
//	/////////////////////
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 8
//	 * HtmlCell: 1
//	 * DisplayName.enUS: Taxonomy ID
//	 */
//	protected void _taxonomyId(Wrap<String> w) {
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * Ignore: true
//	 */ 
//	protected void _taxonomySearch(Promise<SearchList<Taxonomy>> promise) {
//		SearchList<Taxonomy> l = new SearchList<>();
//		if(taxonomyId != null) {
//			l.setQuery("*:*");
//			l.addFilterQuery("taxonomyId_docvalues_string:" + taxonomyId);
//			l.setC(Taxonomy.class);
//			l.setStore(true);
//		}
//		promise.complete(l);
//	}
//
//	protected void _taxonomy_(Wrap<Taxonomy> c) {
//		if(taxonomySearch.size() > 0) {
//			c.o(taxonomySearch.get(0));
//		}
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 8
//	 * HtmlCell: 2
//	 * DisplayName.enUS: Taxonomy ID
//	 */
//	protected void _taxonomyName(Wrap<String> w) {
//		if(taxonomy_ != null) {
//			w.o(taxonomy_.getTaxonomyName());
//		} else {
//			Optional.ofNullable(bioSample_).map(BioSample::getTaxonomyId).ifPresent(taxonomyId -> {
//				w.o(taxonomyId);
//			});
//		}
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 8
//	 * HtmlCell: 3
//	 * DisplayName.enUS: Taxonomy Unique Name
//	 */
//	protected void _taxonomyUniqueName(Wrap<String> w) {
//		if(taxonomy_ != null) {
//			w.o(taxonomy_.getTaxonomyUniqueName());
//		} else {
//			Optional.ofNullable(bioSample_).map(BioSample::getTaxonomyUniqueName).ifPresent(taxonomyUniqueName -> {
//				w.o(taxonomyUniqueName);
//			});
//		}
//	}
//
//	/**    
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * Define: true
//	 * HtmlRow: 8
//	 * HtmlCell: 4
//	 * DisplayName.enUS: Taxonomy Name Class
//	 */     
//	protected void _taxonomyNameClass(Wrap<String> w) {
//		if(taxonomy_ != null) {
//			w.o(taxonomy_.getTaxonomyNameClass());
//		} else {
//			Optional.ofNullable(bioSample_).map(BioSample::getTaxonomyNameClass).ifPresent(taxonomyNameClass -> {
//				w.o(taxonomyNameClass);
//			});
//		}
//	}

	@Override
	protected void _objectTitle(Wrap<String> w) {
		w.o(toString());
		StringBuilder sb = new StringBuilder();
		sb.append(Optional.ofNullable(runId).map(v -> "runId: \"" + v + "\"\n" ).orElse(""));
//		sb.append(Optional.ofNullable(bioSampleId).map(v -> "bioSampleId: \"" + v + "\"\n" ).orElse(""));
//		sb.append(Optional.ofNullable(taxonomyId).map(v -> "taxonomyId: \"" + v + "\"\n" ).orElse(""));
		w.o(sb.toString());
	}

/*
Run
ReleaseDate
LoadDate
spots
bases
spots_with_mates
avgLength
size_MB
AssemblyName
download_path
Experiment
LibraryName
LibraryStrategy
LibrarySelection
LibrarySource
LibraryLayout
InsertSize
InsertDev
Platform
Model
SRAStudy
BioProject
Study_Pubmed_id
ProjectID
Sample
BioSample
SampleType
TaxID
ScientificName
SampleName
g1k_pop_code
source
g1k_analysis_group
Subject_ID
Sex
Disease
Tumor
Affection_Status
Analyte_Type
Histological_Type
Body_Site
CenterName
Submission
dbgap_study_accession
Consent
RunHash
ReadHash
*/
}
