/*
 * File: 	Url.java
 * Purpose: Bringing the implementation of modelUrl, a class that
 * directly references the business domain.
 */
package br.com.visualize.akan.api.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.visualize.akan.api.helper.DatabaseHelper;
import br.com.visualize.akan.api.helper.QueryHelper;
import br.com.visualize.akan.api.helper.QuerySelect;
import br.com.visualize.akan.domain.model.Url;

public class UrlDao extends Dao{

	private int idUpdateUrl = 0;
	private int updateVerifierUrl = 0;

	private String defaultUrl = "http://192.168.1.4:3000";
	private String tableName = "URL";
	private QueryHelper queryHelper = null;
	
	

	private static UrlDao instanceUrlDao = null;
	private static String tableUrl = "Url";
	private static String tableColumns[] = {"ID_URL",
		"ID_UPDATE_URL", 
		"DEFAULT_URL",
		"FIRST_URL",
		"SECOND_URL"
		};


	protected UrlDao(Context context) {
		UrlDao.database = new DatabaseHelper(context);
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
	 * Checks if database is empty
	 *
	 */
	protected boolean checkEmptyLocalDb(){
		sqliteDatabase = database.getReadableDatabase();
		
		/* TODO: Method to allow as argument String instead String[] in
		 * 		 class QueryStrategy. */
		String[] column = { "1" };
		
		queryHelper = new QueryHelper( new QuerySelect() );
		String query = queryHelper.executeQuery(tableName, column, null, 
				null, null, null, null, null );
		
		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		Log.i("dataBase insetition", "url1");
		if(cursor != null){
			if(cursor.getCount() <= 0){
				return true;
			}
		}
		else{
			return true;
		}
		return false;
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
		insertAndClose(sqliteDatabase, tableUrl, content);
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
		sqliteDatabase.delete(tableUrl,"ID_UPDATE_URL=?", 
				new String[] { url.getIdUpdateUrl() + "" });
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
	public Url getUrl(){
		Url url = new Url(0,defaultUrl);
		if (checkEmptyLocalDb()) {
			sqliteDatabase = database.getWritableDatabase();

			ContentValues content = new ContentValues();
			content.put(tableColumns[0], 1);
			content.put(tableColumns[1], 0);
			content.put(tableColumns[2], defaultUrl);

			insertAndClose(sqliteDatabase, tableName, content);
		}
		else{
			sqliteDatabase = database.getReadableDatabase();
			Cursor cursor = sqliteDatabase.rawQuery("SELECT * FROM URL", null);
			cursor.moveToFirst();
			url.setIdUpdateUrl(cursor.getInt(cursor.getColumnIndex(tableColumns[1])));
			url.setDefaultUrl(cursor.getString(cursor.getColumnIndex(tableColumns[2])));
			url.setFirstAlternativeUrl(cursor.getString(cursor.getColumnIndex(tableColumns[3])));
			url.setSecondAlternativeUrl(cursor.getString(cursor.getColumnIndex(tableColumns[4])));
		}
		return url;
	}
}
