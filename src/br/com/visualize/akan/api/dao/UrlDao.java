/*
 * File: UrlDao.java Purpose: Bringing the implementation of model URL, a class
 * that directly references the business domain.
 */
package br.com.visualize.akan.api.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.visualize.akan.api.helper.DatabaseHelper;
import br.com.visualize.akan.domain.model.Url;


/**
 * This class represents the Data Access Object for the URL, responsible for all
 * data base operations like selections, insertions and updates.
 */
public class UrlDao extends Dao {
	private static final boolean EMPTY = true;
	private static final boolean NOT_EMPTY = false;
	
	private String defaultUrl = "http://192.168.1.4:3000";
	private String tableName = "URL";
	
	private static UrlDao instanceUrlDao = null;
	private static String tableUrl = "Url";
	private static String tableColumns[ ] = { "ID_URL", "ID_UPDATE_URL",
	        "DEFAULT_URL", "FIRST_URL", "SECOND_URL" };
	
	protected UrlDao( Context context ) {
		UrlDao.database = new DatabaseHelper( context );
	}
	
	/**
	 * Return the unique instance of UrlDao active in the project.
	 * <p>
	 * 
	 * @return The unique instance of UrlDao.
	 */
	public static UrlDao getInstance( Context context ) {
		
		if( instanceUrlDao != null ) {
			/* ! Nothing To Do. */
			
		} else {
			instanceUrlDao = new UrlDao( context );
		}
		
		return instanceUrlDao;
	}
	
	/**
	 * Checks if database is empty.
	 */
	protected boolean checkEmptyLocalDb() {
		sqliteDatabase = database.getReadableDatabase();
		
		String query = "SELECT 1 FROM " + tableName;
		
		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		
		boolean isEmpty = NOT_EMPTY;
		
		if( cursor != null ) {
			
			if( cursor.getCount() <= 0 ) {
				isEmpty = EMPTY;
				
			} else {
				/* ! Nothing To Do. */
			}
			
		} else {
			isEmpty = EMPTY;
		}
		
		return isEmpty;
	}
	
	/**
	 * Inserts in the database URL.
	 * 
	 * @param url
	 *            URL to be inserted.
	 */
	public void insertUrl( Url url ) {
		sqliteDatabase = database.getWritableDatabase();
		
		ContentValues content = new ContentValues();
		
		content.put( "ID_UPDATE_URL", url.getIdUpdateUrl() );
		content.put( "UPDATE_VERIFIER_URL", url.getUpdateVerifierUrl() );
		content.put( "DEFAULT_URL", url.getDefaultUrl() );
		content.put( "FIRST_ALTERNATIVE_URL", url.getFirstAlternativeUrl() );
		content.put( "SECOND_ALTERNATIVE_URL", url.getSecondAlternativeUrl() );
		
		insertAndClose( sqliteDatabase, tableUrl, content );
	}
	
	/**
	 * Deletes a URL of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * 
	 * @param url
	 *            URL to be deleted.
	 */
	public void deleteUrl( Url url ) {
		sqliteDatabase = database.getWritableDatabase();
		
		sqliteDatabase.delete( tableUrl, "ID_UPDATE_URL=?",
		        new String[] { url.getIdUpdateUrl() + "" } );
		
		sqliteDatabase.close();
	}
	
	/**
	 * Search the database all URLs related to the referenced congressman and
	 * returns them as a list.
	 * <p>
	 * 
	 * @return The list of referenced URLs belonging to the congressman.
	 */
	public Url getUrl() {
		Url url = new Url( 0, defaultUrl );
		
		if( checkEmptyLocalDb() ) {
			sqliteDatabase = database.getWritableDatabase();
			
			ContentValues content = new ContentValues();
			
			content.put( tableColumns[ 0 ], 1 );
			content.put( tableColumns[ 1 ], 0 );
			content.put( tableColumns[ 2 ], defaultUrl );
			
			insertAndClose( sqliteDatabase, tableName, content );
			
		} else {
			sqliteDatabase = database.getReadableDatabase();
			
			Cursor cursor = sqliteDatabase.rawQuery( "SELECT * FROM URL", null );
			cursor.moveToFirst();
			
			url.setIdUpdateUrl( cursor.getInt( cursor
			        .getColumnIndex( tableColumns[ 1 ] ) ) );
			
			url.setDefaultUrl( cursor.getString( cursor
			        .getColumnIndex( tableColumns[ 2 ] ) ) );
			
			url.setFirstAlternativeUrl( cursor.getString( cursor
			        .getColumnIndex( tableColumns[ 3 ] ) ) );
			
			url.setSecondAlternativeUrl( cursor.getString( cursor
			        .getColumnIndex( tableColumns[ 4 ] ) ) );
		}
		
		return url;
	}
}
