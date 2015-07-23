/*
 * File: CongressmanDao.java Purpose: Brings the implementation of class
 * CongressmanDao.
 */
package br.com.visualize.akan.api.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.visualize.akan.api.helper.DatabaseHelper;
import br.com.visualize.akan.domain.enumeration.Order;
import br.com.visualize.akan.domain.exception.DatabaseInvalidOperationException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;


/**
 * This class represents the Data Access Object for the Congressman, responsible
 * for all data base operations like selections, insertions and updates.
 */
public class CongressmanDao extends Dao {
	private static final boolean EMPTY = true;
	private static final boolean NOT_EMPTY = false;
	
	private static CongressmanDao instance;
	private static String tableName = "CONGRESSMAN";
	
	private static String tableColumns[ ] = { "ID_CONGRESSMAN", "ID_UPDATE",
	        "NAME_CONGRESSMAN", "PARTY", "UF_CONGRESSMAN",
	        "TOTAL_SPENT_CONGRESSMAN", "STATUS_CONGRESSMAN",
	        "PHOTO_CONGRESSMAN", "RANKING_CONGRESSMAN" };
	
	private CongressmanDao( Context context ) {
		CongressmanDao.database = new DatabaseHelper( context );
	}
	
	/**
	 * Return the unique instance of CongressmanDao active in the project.
	 * <p>
	 * 
	 * @return The unique instance of CongressmanDao.
	 */
	public static CongressmanDao getInstance( Context context ) {
		if( CongressmanDao.instance != null ) {
			/* !Nothing To Do. */
			
		} else {
			CongressmanDao.instance = new CongressmanDao( context );
		}
		
		return CongressmanDao.instance;
	}
	
	/**
	 * Checks if database is empty.
	 */
	public boolean checkEmptyLocalDb() {
		sqliteDatabase = database.getReadableDatabase();
		
		String query = "SELECT  1 FROM " + tableName;
		
		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		
		boolean isEmpty = NOT_EMPTY;
		
		if( cursor != null ) {
			
			if( cursor.getCount() <= 0 ) {
				cursor.moveToFirst();
				
				isEmpty = EMPTY;
				
			} else {
				/* ! Nothing To Do */
			}
			
		} else {
			
			isEmpty = EMPTY;
		}
		
		return isEmpty;
	}
	
	/**
	 * Checks database version.
	 */
	public boolean checkDbVersion(int version) {
		sqliteDatabase = database.getReadableDatabase();
		
		String query = "SELECT  1 FROM VERSION";
		
		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		cursor.moveToFirst();
		boolean isLastVersion = cursor.getInt( 0 ) <= version;
		return isLastVersion;
	}
	
	/**
	 * Up
	 * 
	 * @param congressman
	 * @return
	 * @throws NullCongressmanException
	 */
	public boolean setFollowedCongressman( Congressman congressman )
	        throws NullCongressmanException {
		
		if( congressman != null ) {
			sqliteDatabase = database.getWritableDatabase();
			
			ContentValues content = new ContentValues();
			content.put( "STATUS_CONGRESSMAN", congressman.isStatusCogressman() );
			
			boolean result = ( sqliteDatabase.update( tableName, content,
			        "ID_CONGRESSMAN=?",
			        new String[] { congressman.getIdCongressman() + "" } ) > 0 );
			
			sqliteDatabase.close();
			
			return result;
			
		} else {
			throw new NullCongressmanException();
		}
	}
	
	/**
	 * Inserts in the database a list of congressman.
	 * 
	 * @param congressmanList
	 *            List of Congressman to be inserted.
	 * 
	 * @return Result if the operation was successful or not.
	 * @throws NullCongressmanException 
	 */
	public boolean insertAllCongressman( List<Congressman> congressmanList ) 
	        throws NullCongressmanException {
		Iterator<Congressman> index = congressmanList.iterator();
		
		boolean result = true;
		
		while( index.hasNext() ) {
			result = insertCongressman( index.next() );
		}
		
		return result;
	}
	
	public boolean deleteAllCongressman() throws NullCongressmanException,
	    DatabaseInvalidOperationException {
		Iterator<Congressman> index = this.getAll(Order.RANKING).iterator();
		
		boolean result = false;
		boolean isEmptyDB = checkEmptyLocalDb();
		
		if( !isEmptyDB ) {
    		while( index.hasNext() ) {
    			result = deleteCongressman( index.next() );
    		}
		} else {
		     throw new DatabaseInvalidOperationException();
		}
		
		return result;
	}
	
	/**
	 * Retrieves all congressman contained in the database.
	 * 
	 * @return List of all congressman contained in the database.
	 */
	public List<Congressman> getAll(Order order) {
		
		sqliteDatabase = database.getReadableDatabase();
		
		String query = "SELECT * FROM " + tableName
		        + " ORDER BY "+ order.getOrderName() + " ASC";
		
		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		
		List<Congressman> listParlamentares = new ArrayList<Congressman>();
		
		while( cursor.moveToNext() ) {
			
			Congressman congressman = new Congressman();
			
			congressman.setIdCongressman( cursor.getInt( cursor
			        .getColumnIndex( "ID_CONGRESSMAN" ) ) );
			
			congressman.setNameCongressman( cursor.getString( cursor
			        .getColumnIndex( "NAME_CONGRESSMAN" ) ) );
			
			congressman
			        .setStatusCogressman( convertStringToBool( cursor
			                .getString( cursor
			                        .getColumnIndex( "STATUS_CONGRESSMAN" ) ) ) );
			
			congressman.setPartyCongressman( cursor.getString( cursor
			        .getColumnIndex( "PARTY" ) ) );
			
			congressman.setUfCongressman( cursor.getString( cursor
			        .getColumnIndex( "UF_CONGRESSMAN" ) ) );
			
			congressman.setTotalSpentCongressman( cursor.getDouble( cursor
			        .getColumnIndex( "TOTAL_SPENT_CONGRESSMAN" ) ) );
			
			congressman.setRankingCongressman( cursor.getInt( cursor
			        .getColumnIndex( "RANKING_CONGRESSMAN" ) ) );
			
			congressman.setIdUpdateCongressman( cursor.getInt( cursor
			        .getColumnIndex( "ID_UPDATE" ) ) );
			
			listParlamentares.add( congressman );
		}
		
		sqliteDatabase.close();
		
		return listParlamentares;
	}
	
	/**
	 * Retrieves a congressman contained in the database, based on pass name.
	 * <p>
	 * 
	 * @param congressmanName
	 *            The name of the congressman sought.
	 *            <p>
	 * @return A congressman sought.
	 * @throws DatabaseInvalidOperationException 
	 */
	public List<Congressman> selectCongressmanByName( String congressmanName ) {
	    List<Congressman> listCongressmen = new ArrayList<Congressman>();
		
		sqliteDatabase = database.getReadableDatabase();
		
		String[] whereArgs = { congressmanName };
		
		String query = "SELECT * FROM " + tableName
		        + " WHERE NAME_CONGRESSMAN = ?";

		Cursor cursor = sqliteDatabase.rawQuery( query, whereArgs );
		
		while( cursor.moveToNext() ) {
			
			Congressman congressman = new Congressman();
			
			congressman.setIdCongressman( cursor.getInt( cursor
			        .getColumnIndex( "ID_CONGRESSMAN" ) ) );
			
			congressman.setNameCongressman( cursor.getString( cursor
			        .getColumnIndex( "NAME_CONGRESSMAN" ) ) );
			
			congressman
			        .setStatusCogressman( Boolean.parseBoolean( cursor
			                .getString( cursor
			                        .getColumnIndex( "STATUS_CONGRESSMAN" ) ) ) );
			
			congressman.setPartyCongressman( cursor.getString( cursor
			        .getColumnIndex( "PARTY" ) ) );
			
			congressman.setUfCongressman( cursor.getString( cursor
			        .getColumnIndex( "UF_CONGRESSMAN" ) ) );
			
			congressman.setTotalSpentCongressman( cursor.getDouble( cursor
			        .getColumnIndex( "TOTAL_SPENT_CONGRESSMAN" ) ) );
			
			congressman.setRankingCongressman( cursor.getInt( cursor
			        .getColumnIndex( "RANKING_CONGRESSMAN" ) ) );
			
			congressman.setIdUpdateCongressman( cursor.getInt( cursor
			        .getColumnIndex( "ID_UPDATE" ) ) );
			
			listCongressmen.add( congressman );
		}
	
		sqliteDatabase.close();
		
		return listCongressmen;
	}
	
	/**
	 * Retrieves all followed congressman. 
	 * <p>
	 * 
	 * @return All followed congressman.
	 * @throws DatabaseInvalidOperationException 
	 */
	public List<Congressman> getFollowedCongressman() {
	    List<Congressman> listFollowedCongressmen = new ArrayList<Congressman>();
		
		sqliteDatabase = database.getReadableDatabase();
		
		String query = "SELECT * FROM " + tableName
		        + " WHERE STATUS_CONGRESSMAN = 1";

		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		
		while( cursor.moveToNext() ) {
			
			Congressman congressman = new Congressman();
			
			congressman.setIdCongressman( cursor.getInt( cursor
			        .getColumnIndex( "ID_CONGRESSMAN" ) ) );
			
			congressman.setNameCongressman( cursor.getString( cursor
			        .getColumnIndex( "NAME_CONGRESSMAN" ) ) );
			
			congressman
			        .setStatusCogressman( Boolean.parseBoolean( cursor
			                .getString( cursor
			                        .getColumnIndex( "STATUS_CONGRESSMAN" ) ) ) );
			
			congressman.setPartyCongressman( cursor.getString( cursor
			        .getColumnIndex( "PARTY" ) ) );
			
			congressman.setUfCongressman( cursor.getString( cursor
			        .getColumnIndex( "UF_CONGRESSMAN" ) ) );
			
			congressman.setTotalSpentCongressman( cursor.getDouble( cursor
			        .getColumnIndex( "TOTAL_SPENT_CONGRESSMAN" ) ) );
			
			congressman.setRankingCongressman( cursor.getInt( cursor
			        .getColumnIndex( "RANKING_CONGRESSMAN" ) ) );
			
			congressman.setIdUpdateCongressman( cursor.getInt( cursor
			        .getColumnIndex( "ID_UPDATE" ) ) );
			
			listFollowedCongressmen.add( congressman );
		}
	
		sqliteDatabase.close();
		
		return listFollowedCongressmen;
	}
	
	/* TODO: JAVADOC. */
	/* TODO: Unit Test. */
	public boolean checkCongressmanExist( int idCongressman ) {
	    List<Congressman> congressmanList = this.getAll(Order.RANKING);
	    
	    boolean result = false;
	    
	    for( Congressman congressman : congressmanList ) {
	        int idCurrentCongressman = congressman.getIdCongressman();
	        
	        if( idCongressman == idCurrentCongressman ) {
	            result = true;
	        } else {
	            /*! Nothing To Do. */
	        }
	    }
	    
	    return result;
	}
	
	private static boolean convertStringToBool( String string ) {
		if( string.equals( "1" ) )
			return true;
		if( string.equals( "0" ) )
			return false;
		throw new IllegalArgumentException( string
		        + " is not a bool. Only 1 and 0 are." );
	}
	
	/**
	 * Inserts in the database a congressman.
	 * 
	 * @param congressman
	 *            Congressman to be inserted.
	 * 
	 * @return Result if the operation was successful or not.
	 * @throws NullCongressmanException 
	 */
	private boolean insertCongressman( Congressman congressman ) 
	        throws NullCongressmanException {
	    boolean result = false;
	    
	    if( congressman != null ) {
    		sqliteDatabase = database.getWritableDatabase();
    		
    		ContentValues content = new ContentValues();
    		
    		content.put( tableColumns[ 0 ], congressman.getIdCongressman() );
    		content.put( tableColumns[ 1 ], congressman.
    		        getIdUpdateCongressman() );
    		content.put( tableColumns[ 2 ], congressman.getNameCongressman() );
    		content.put( tableColumns[ 3 ], congressman.getPartyCongressman() );
    		content.put( tableColumns[ 4 ], congressman.getUfCongressman() );
    		content.put( tableColumns[ 5 ], congressman.
    		        getTotalSpentCongressman() );
    		content.put( tableColumns[ 6 ], congressman.isStatusCogressman() );
    		content.put( tableColumns[ 7 ], congressman.getPhotoCongressman() );
    		content.put( tableColumns[ 8 ], congressman.
    		        getRankingCongressman() );
    		
    		result = ( 
    		        insertAndClose( sqliteDatabase, tableName, content ) > 0 );
	    } else {
	        throw new NullCongressmanException();
	    }
		
		return result;
	}
	
	/**
	 * Inserts in the database a version of remote 
	 * database.
	 * 
	 * @param int version of remote database.
	 * 
	 * @return Result if the operation was successful or not. 
	 */
	public boolean insertDbVersion( int version ) {
	   
	    boolean result = false;
    
		sqliteDatabase = database.getWritableDatabase();
		
		ContentValues content = new ContentValues();
		
		content.put( "ID_VERSION", version );
		
		result = (insertAndClose( sqliteDatabase, "VERSION", content ) > 0 );
	
		return result;
	}
	
	private boolean deleteCongressman( Congressman congressman )
	        throws NullCongressmanException {
		
		if( congressman != null ) {
			sqliteDatabase = database.getWritableDatabase();
			
			boolean result = ( sqliteDatabase.delete( tableName,
			        "ID_CONGRESSMAN=?",
			        new String[] { congressman.getIdCongressman() + "" } ) > 0 );
			
			sqliteDatabase.close();
			
			return result;
		} else {
			throw new NullCongressmanException();
		}
	}
}
