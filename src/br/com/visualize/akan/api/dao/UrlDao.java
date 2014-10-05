/*
 * File: 	Url.java
 * Purpose: Bringing the implementation of modelUrl, a class that 
 * directly references the business domain.
 */
package br.com.visualize.akan.api.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.visualize.akan.domain.model.Url;

public class UrlDao {
	
	private int idUpdateUrl = 0;
	private int updateVerifierUrl = 0;
	
	private String defaultUrl = "";
	private String firstAlternativeUrl = "";
	private String secondAlternativeUrl = "";
	

	private static UrlDao instanceUrlDao = null;
	private static String tableUrl = "Url";
	private static String[] columnsUrl = {"ID_UPDATE_URL,UPDATE_VERIFIER_URL,DEFAULT_URL,FIRST_ALTERNATIVE_URL,SECOND_ALTERNATIVE_URL"};
	private static LocalDatabase database;
	private static SQLiteDatabase sqliteDatabase;


	private UrlDao(Context context) {
		UrlDao.database = new LocalDatabase(context);
	}
	
	/**
	 * Return the unique instance of UrlDao active in the project.
	 * <p>
	 * @return The unique instance of UrlDao.
	 */
	public static UrlDao getInstance(Context context) {
		
		if( instanceUrlDao != null ) {
			/*! Nothing To Do. */
			
		} else {
			instanceUrlDao = new UrlDao(context);
			
		}
		
		return instanceUrlDao;
	}
	
	/**
	 * Inserts in the database Url, referring to a congressman in particular, 
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedUrls List of Url to be inserted.
	 */
	public void insertUrl(Url url) {
		sqliteDatabase = database.getWritableDatabase();
		
		ContentValues content = new ContentValues();

		content.put("ID_UPDATE_URL", url.getIdUpdateUrl());
		content.put("UPDATE_VERIFIER_URL", url.getUpdateVerifierUrl());
		content.put("DEFAULT_URL", url.getDefaultUrl());
		content.put("FIRST_ALTERNATIVE_URL", url.getFirstAlternativeUrl());
		content.put("SECOND_ALTERNATIVE_URL", url.getSecondAlternativeUrl());
		sqliteDatabase.insert(tableUrl, null, content);
		sqliteDatabase.close();
	}
	
	/**
	 * Deletes all Urls of the database relating to the past as parameter 
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idUrl Numeric identifier of congressman that must have 
	 * 		 					deleted the Urls.
	 */
	public void deleteUrl(Url url) {
		sqliteDatabase = database.getWritableDatabase();
		sqliteDatabase.delete(tableUrl,"ID_UPDATE_URL=?", new String[] { url.getIdUpdateUrl() + "" });
		sqliteDatabase.close();
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
