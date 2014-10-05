/*
 * File: 	Url.java
 * Purpose: Bringing the implementation of modelUrl, a class that 
 * directly references the business domain.
 */
package br.com.visualize.akan.api.dao;

import java.util.List;

import br.com.visualize.akan.domain.model.Url;

public class UrlDao {
	private static UrlDao instanceUrlDao = null;
	

	private UrlDao( ) {
		/*! Write instructions Here. */
	}
	
	/**
	 * Return the unique instance of UrlDao active in the project.
	 * <p>
	 * @return The unique instance of UrlDao.
	 */
	public static UrlDao getInstance() {
		
		if( instanceUrlDao != null ) {
			/*! Nothing To Do. */
			
		} else {
			instanceUrlDao = new UrlDao();
			
		}
		
		return instanceUrlDao;
	}
	
	/**
	 * Inserts in the database Urls, referring to a congressman in particular, 
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedUrls List of Urls to be inserted.
	 */
	public void insertUrlsOnCongressman( List<Url> insertedUrls ) {
		/*! Write instructions Here. */
	}
	
	/**
	 * Deletes all Urls of the database relating to the past as parameter 
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have 
	 * 		 					deleted the Urls.
	 */
	public void deleteUrlsFromCongressman( int idCongressman ) {
		/*! Write instructions Here. */
	}
	
	/**
	 * Search the database all Urls related to the referenced congressman 
	 * and returns them as a list.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have 
	 * 		 					deleted the Urls.
	 * <p>
	 * @return The list of referenced Urls belonging to the congressman.
	 */
	public List<Url> getUrlsByIdCongressman( int idCongressman ) {
		/*! Write instructions Here. */
		
		return null;
	}
}
