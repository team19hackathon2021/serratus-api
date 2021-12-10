package io.serratus.api.enus.model.taxonomy; 

import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Page: true
 * SuperPage.enUS: BaseModelPage
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 8
 * 
 * ApiTag.enUS: Taxonomy
 * ApiUri.enUS: /api/taxonomy
 * 
 * ApiMethod.enUS: PUTImport

 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: TaxonomyPage
 * PageSuperSearchPage.enUS: BaseModelPage
 * ApiUriSearchPage.enUS: /taxonomy
 * 
 * AName.enUS: a taxonomy
 * NamePlural.enUS: taxonomies
 * Color: pale-green
 * IconGroup: regular
 * IconName: project-diagram
 * NameVar.enUS: taxonomy
 * 
 * Role.enUS: SiteAdmin
 * PublicRead: true
 * 
 * Map.hackathonMission: to create a new Java class Taxonomy to store information about taxonomies related to RNA seqence runs. 
 * Map.hackathonColumn: Develop Taxonomy API
 * Map.hackathonLabels: Java
 * Map.hackathonMissionGen: to create a generated Java class that can be extended and override these methods to store information about taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnGen: Develop Taxonomy API
 * Map.hackathonLabelsGen: Java
 * Map.hackathonMissionGenApiServiceImpl: to create a new Java API implementation class to persist information about taxonomies related to RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumnGenApiServiceImpl: Develop Taxonomy API
 * Map.hackathonLabelsGenApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenApiService: to create a new Java API interface to define API methods for adding, updating and querying taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnGenApiService: Develop Taxonomy API
 * Map.hackathonLabelsGenApiService: Java,Vert.x
 * Map.hackathonMissionApiServiceImpl: to create a new Java API implementation class to extend and override any generated API functionality about taxonomies related to RNA seqence runs in a database and a search engine. 
 * Map.hackathonColumnApiServiceImpl: Develop Taxonomy API
 * Map.hackathonLabelsApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenPageHbs: to create an Handlebars HTML template for the code to render a page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnGenPageHbs: Develop Taxonomy UI
 * Map.hackathonLabelsGenPageHbs: HTML,Handlebars
 * Map.hackathonMissionPageHbs: to create an Handlebars HTML template to override the generated code to render a page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnPageHbs: Develop Taxonomy UI
 * Map.hackathonLabelsPageHbs: HTML, Handlebars
 * Map.hackathonMissionPageJs: to create JavaScript to submit requests to the API for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnPageJs: Develop Taxonomy UI
 * Map.hackathonLabelsPageJs: JavaScript,JQuery
 * Map.hackathonMissionPage: to create the a new Java backend class to override the generated methods to render the page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnPage: Develop Taxonomy UI
 * Map.hackathonLabelsPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionPageGen: to create the a new Java backend class to render the page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnPageGen: Develop Taxonomy UI
 * Map.hackathonLabelsPageGen: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPage: to create the a new base Java backend class to render the page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnGenPage: Develop Taxonomy UI
 * Map.hackathonLabelsGenPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPageGen: to create the a new base Java backend class to render the page for taxonomies related to RNA seqence runs. 
 * Map.hackathonColumnGenPageGen: Develop Taxonomy UI
 * Map.hackathonLabelsGenPageGen: Java,Vert.x,Handlebars
 **/          
public class Taxonomy extends TaxonomyGen<BaseModel> {

        /////////////////////
        // Taxonomy fields //
        /////////////////////

        /**    
         * {@inheritDoc}
         * DocValues: true
         * Define: true
         * DisplayName.enUS: Taxonomy ID
         */
        protected void _taxonomyId(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * DocValues: true
         * Define: true
         * DisplayName.enUS: Taxonomy Name
         */
        protected void _taxonomyName(Wrap<String> w) {
        }

        /**    
         * {@inheritDoc}
         * DocValues: true
         * Define: true
         * DisplayName.enUS: Taxonomy Unique Name
         */
        protected void _taxonomyUniqueName(Wrap<String> w) {
        }

        /**     
         * {@inheritDoc}
         * DocValues: true
         * Define: true
         * DisplayName.enUS: Taxonomy Name Class
         */
        protected void _taxonomyNameClass(Wrap<String> w) {
        }

        @Override
        protected void _objectTitle(Wrap<String> w) {
                w.o(toString());
        }
}
