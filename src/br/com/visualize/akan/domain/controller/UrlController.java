/*
 * File: UrlController.java
 * Purpose: Implement the indirection class for the data related to Urls.
 */
package br.com.visualize.akan.domain.controller;


import br.com.visualize.akan.api.dao.UrlDao;
import br.com.visualize.akan.domain.model.Url;
import android.content.Context;


/**
 * Serves to define the methods that are responsible for generating actions, 
 * calculate results and everything that is requested by layer 'View' 
 * referring to URLs.
 */
public class UrlController {
	
	private static UrlController instance = null;
	private static Url url;
	
	private UrlDao dao;
	
	private UrlController( Context context ) {
		this.dao = UrlDao.getInstance( context );
	}
	
	/**
	 * Return the unique instance of UrlController active in the project.
	 * <p>
	 * @return The unique instance of UrlController.
	 */
	public static UrlController getInstance( Context context ) {
		if( instance == null ) {
			instance = new UrlController( context );
		}
		
		return instance;
	}

	/**
	 * Search in database the URL in use.
	 * <p>
	 * @return The URL in use.
	 */
	public Url getUrl() {
		url = dao.getUrl();
		
		return url;
	}

	/**
	 * Returns the URL responsible to bring the listing of all congressmen.
	 * <p>
	 * @return The URL that listing all congressmen.
	 */
	public String getAllCongressmanUrl() {
		String url = "http://107.170.177.5/akan/parlamentar/";
		
		return url;
	}
	
	/**
	 * Returns the URL responsible to bring the current version of remote database.
	 * <p>
	 * @return The URL that brings the remote database version.
	 */
	public String getRemoteVersionUrl() {
		String url = "http://107.170.177.5/akan/versao/";
		
		return url;
	}
	
	/**
	 * Returns the URL responsible to bring all quotas of the specific 
	 * congressman.
	 * <p>
	 * @return The URL that listing all quotas.
	 */
	public String quotasWithCongressmanIdUrl( Integer id ) {
		String url = "http://107.170.177.5/akan/cota/parlamentar/"+id+"/";
				
		return url;
	}
	
	public String statisticsPerMonthUrl(){
		String url = "http://107.170.177.5/akan/cota/media-maximo-por-periodo";
		
		return url;
	}
	
	public String statisticsStdDeviationUrl(){
		String url = "http://107.170.177.5/akan/cota/media-maximo-desvio";
		
		return url;
	}
	
}
