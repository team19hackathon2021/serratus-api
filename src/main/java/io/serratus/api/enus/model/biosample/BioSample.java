package io.serratus.api.enus.model.biosample;

import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.model.taxonomy.Taxonomy;
import io.serratus.api.enus.search.SearchList;
import io.serratus.api.enus.wrap.Wrap;
import io.vertx.core.Promise;

/**
 * Model: true
 * Api: true
 * Page: true
 * SuperPage.enUS: BaseModelPage
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 8
 * 
 * ApiTag.enUS: BioSample
 * ApiUri.enUS: /api/biosample
 * 
 * ApiMethod.enUS: PUTImport

 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: BioSamplePage
 * PageSuperSearchPage.enUS: BaseModelPage
 * ApiUriSearchPage.enUS: /biosample
 * 
 * AName.enUS: a biosample
 * Color: pale-green
 * IconGroup: regular
 * IconName: map-pin
 * NameVar.enUS: biosample
 * 
 * Role.enUS: SiteAdmin
 * PublicRead: true
 * 
 * Map.hackathonMission: to create a new Java class to store information about BioSamples related to RNA seqence runs. 
 * Map.hackathonColumn: Develop BioSample API
 * Map.hackathonLabels: Java
 * Map.hackathonMissionGen: to create a generated Java class that can be extended and override these methods to store information about BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnGen: Develop BioSample API
 * Map.hackathonLabelsGen: Java
 * Map.hackathonMissionGenApiServiceImpl: to create a new Java API implementation class to persist information about BioSamples related to RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumnGenApiServiceImpl: Develop BioSample API
 * Map.hackathonLabelsGenApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenApiService: to create a new Java API interface to define API methods for adding, updating and querying BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnGenApiService: Develop BioSample API
 * Map.hackathonLabelsGenApiService: Java,Vert.x
 * Map.hackathonMissionApiServiceImpl: to create a new Java API implementation class to extend and override any generated API functionality about BioSamples related to RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumnApiServiceImpl: Develop BioSample API
 * Map.hackathonLabelsApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenPageHbs: to create an Handlebars HTML template for the code to render a page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnGenPageHbs: Develop BioSample UI
 * Map.hackathonLabelsGenPageHbs: HTML,Handlebars
 * Map.hackathonMissionPageHbs: to create an Handlebars HTML template to override the generated code to render a page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnPageHbs: Develop BioSample UI
 * Map.hackathonLabelsPageHbs: HTML, Handlebars
 * Map.hackathonMissionPageJs: to create JavaScript to submit requests to the API for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnPageJs: Develop BioSample UI
 * Map.hackathonLabelsPageJs: JavaScript,JQuery
 * Map.hackathonMissionPage: to create the a new Java backend class to override the generated methods to render the page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnPage: Develop BioSample UI
 * Map.hackathonLabelsPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionPageGen: to create the a new Java backend class to render the page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnPageGen: Develop BioSample UI
 * Map.hackathonLabelsPageGen: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPage: to create the a new base Java backend class to render the page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnGenPage: Develop BioSample UI
 * Map.hackathonLabelsGenPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPageGen: to create the a new base Java backend class to render the page for BioSamples related to RNA seqence runs. 
 * Map.hackathonColumnGenPageGen: Develop BioSample UI
 * Map.hackathonLabelsGenPageGen: Java,Vert.x,Handlebars
 **/                     
public class BioSample extends BioSampleGen<BaseModel> {

        //////////////////////
        // BioSample fields //
        //////////////////////

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample ID
         */
        protected void _bioSampleId(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Isolate
         */
        protected void _bioSampleIsolate(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Host
         */
        protected void _bioSampleHost(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Lab Host
         */
        protected void _bioSampleLabHost(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Isolation Source
         */
        protected void _bioSampleIsolationSource(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Collection Date
         */
        protected void _bioSampleGeographicLocation(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Latitude and Longitude
         */
        protected void _bioSampleLatitudeLongitude(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Reference for BioMaterial
         */
        protected void _bioSampleReferenceForBioMaterial(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Material Processing
         */
        protected void _bioSampleMaterialProcessing(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Material Identifiers
         */
        protected void _bioSampleMaterialIdentifiers(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Disease
         */
        protected void _bioSampleDisease(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Host Tissue Sampled
         */
        protected void _bioSampleHostTissueSampled(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: BioSample Collected By
         */
        protected void _bioSampleCollectedBy(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: Taxonomy ID
         */
        protected void _taxonomyId(Wrap<String> w) {
        }

        /**
         * {@inheritDoc}
         * Ignore: true
         */ 
        protected void _taxonomySearch(Promise<SearchList<Taxonomy>> promise) {
                SearchList<Taxonomy> l = new SearchList<>();
                if(taxonomyId != null) {
                        l.setQuery("*:*");
                        l.addFilterQuery("taxonomyId_indexedstored_string:" + taxonomyId);
                        l.setC(Taxonomy.class);
                        l.setStore(true);
                }
                promise.complete(l);
        }

        protected void _taxonomy_(Wrap<Taxonomy> c) {
                if(taxonomySearch.size() > 0) {
                        c.o(taxonomySearch.get(0));
                }
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: Taxonomy ID
         */
        protected void _taxonomyName(Wrap<String> w) {
                if(taxonomy_ != null)
                        w.o(taxonomy_.getTaxonomyName());
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: Taxonomy Unique Name
         */
        protected void _taxonomyUniqueName(Wrap<String> w) {
                if(taxonomy_ != null)
                        w.o(taxonomy_.getTaxonomyUniqueName());
        }

        /**    
         * {@inheritDoc}
         * Indexed: true
         * Stored: true
         * Define: true
         * DisplayName.enUS: Taxonomy Name Class
         */ 
        protected void _taxonomyNameClass(Wrap<String> w) {
                if(taxonomy_ != null)
                        w.o(taxonomy_.getTaxonomyNameClass());
        }

        @Override
        protected void _objectTitle(Wrap<String> w) {
                w.o(toString());
        }
}
