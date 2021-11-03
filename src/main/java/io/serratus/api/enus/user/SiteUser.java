package io.serratus.api.enus.user;

import java.util.List;

import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.wrap.Wrap;


/**
 * Model: true
 * Api: true
 * Page: true
 * SuperPage.enUS: BaseModelPage
 * Indexed: true
 * Map.Integer.sqlSort: 3
 * 
 * ApiTag.enUS: User
 * ApiUri.enUS: /api/user
 * 
 * ApiMethod.enUS: Search
 * ApiMethod: PATCH
 * ApiMethod: POST
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: SiteUserPage
 * PageSuperSearchPage.enUS: BaseModelPage
 * ApiUriSearchPage.enUS: /user
 * 
 * Keyword: classSimpleNameSiteUser
 * Role.enUS: SiteAdmin
 * Filter: userId
 * Saves: true
 * 
 * AName.enUS: a site user
 * Color: gray
 * IconGroup: regular
 * IconName: user-cog
 * 
 * RoleUser: true
 * Role.enUS: SiteAdmin
 * 
 * Map.hackathonMission: to create a new Java class to store information about site users in the system. 
 * Map.hackathonColumn: Develop SiteUser API
 * Map.hackathonLabels: Java
 * Map.hackathonMissionGen: to create a generated Java class that can be extended and override these methods to store information about site users in the system. 
 * Map.hackathonColumnGen: Develop SiteUser API
 * Map.hackathonLabelsGen: Java
 * Map.hackathonMissionGenApiServiceImpl: to create a new Java API implementation class to persist information about site users in a database and a search engine. 
 * Map.hackathonColumnGenApiServiceImpl: Develop SiteUser API
 * Map.hackathonLabelsGenApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenApiService: to create a new Java API interface to define API methods for adding, updating and querying site users. 
 * Map.hackathonColumnGenApiService: Develop SiteUser API
 * Map.hackathonLabelsGenApiService: Java,Vert.x
 * Map.hackathonMissionApiServiceImpl: to create a new Java API implementation class to extend and override any generated API functionality about site users in a database and a search engine. 
 * Map.hackathonColumnApiServiceImpl: Develop SiteUser API
 * Map.hackathonLabelsApiServiceImpl: Java,Vert.x
 * Map.hackathonMissionGenPageHbs: to create an Handlebars HTML template for the code to render a page for site users. 
 * Map.hackathonColumnGenPageHbs: Develop SiteUser UI
 * Map.hackathonLabelsGenPageHbs: HTML,Handlebars
 * Map.hackathonMissionPageHbs: to create an Handlebars HTML template to override the generated code to render a page for site users. 
 * Map.hackathonColumnPageHbs: Develop SiteUser UI
 * Map.hackathonLabelsPageHbs: HTML, Handlebars
 * Map.hackathonMissionPageJs: to create JavaScript to submit requests to the API for site users. 
 * Map.hackathonColumnPageJs: Develop SiteUser UI
 * Map.hackathonLabelsPageJs: JavaScript,JQuery
 * Map.hackathonMissionPage: to create the a new Java backend class to override the generated methods to render the page for site users. 
 * Map.hackathonColumnPage: Develop SiteUser UI
 * Map.hackathonLabelsPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionPageGen: to create the a new Java backend class to render the page for site users. 
 * Map.hackathonColumnPageGen: Develop SiteUser UI
 * Map.hackathonLabelsPageGen: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPage: to create the a new base Java backend class to render the page for site users. 
 * Map.hackathonColumnGenPage: Develop SiteUser UI
 * Map.hackathonLabelsGenPage: Java,Vert.x,Handlebars
 * Map.hackathonMissionGenPageGen: to create the a new base Java backend class to render the page for site users. 
 * Map.hackathonColumnGenPageGen: Develop SiteUser UI
 * Map.hackathonLabelsGenPageGen: Java,Vert.x,Handlebars
 */   
public class SiteUser extends SiteUserGen<BaseModel> {

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */
	protected void _userKeys(List<Long> l) {
		l.add(pk);
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * Modify: false
	 * DisplayName: user ID
	 */
	protected void _userId(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userEmail(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userFirstName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userLastName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userFullName(Wrap<String> c) {
	}

	@Override
	protected void _objectTitle(Wrap<String> c) {
		c.o(String.format("%s (%s) <%s>", userFullName, userName, userEmail));
	}
}
