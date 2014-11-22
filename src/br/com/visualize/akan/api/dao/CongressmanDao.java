package br.com.visualize.akan.api.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.visualize.akan.api.helper.DatabaseHelper;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Deputy;


/**
 * This class represents the Data Access Object for the Congressman,
 * responsible for all data base operations like selections, insertions
 * and updates. 
 */
public class CongressmanDao extends Dao {
	
	private static CongressmanDao instance;
	private static String tableName = "CONGRESSMAN";
	
	private static String tableColumns[] = { 
		"ID_CONGRESSMAN", 
		"ID_UPDATE", 
		"NAME_CONGRESSMAN",
		"PARTY", 
		"UF_CONGRESSMAN", 
		"TOTAL_SPENT_CONGRESSMAN", 
		"STATUS_CONGRESSMAN", 
		"PHOTO_CONGRESSMAN", 
		"RANKING_CONGRESSMAN" 
		};
	
	private CongressmanDao( Context context ) {
		CongressmanDao.database = new DatabaseHelper( context );
	}
	
	public static CongressmanDao getInstance( Context context ) {
		if( CongressmanDao.instance == null ) {
			CongressmanDao.instance = new CongressmanDao( context );
		}
		return CongressmanDao.instance;
	}
	
	public boolean checkEmptyLocalDb() {
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery( "select 1 from CONGRESSMAN;", null );
		if( cursor != null ) {
			if( cursor.getCount() <= 0 ) {
				cursor.moveToFirst();
				
				return true;
				
			} else {
				/*! Nothing To Do */
			}
			
		} else {
			
			return true;
		}
		
		return false;
	}
	
	private boolean insertCongressman( Congressman congressman ) {
		sqliteDatabase = database.getWritableDatabase();
		ContentValues content = new ContentValues();
		
		content.put( tableColumns[0], congressman.getIdCongressman() );
		content.put( tableColumns[1], congressman.getIdUpdateCongressman() );
		content.put( tableColumns[2], congressman.getNameCongressman() );
		content.put( tableColumns[3], congressman.getPartyCongressman() );
		content.put( tableColumns[4], congressman.getUfCongressman() );
		content.put( tableColumns[5], congressman.getTotalSpentCongressman() );
		content.put( tableColumns[6], congressman.isStatusCogressman() );
		content.put( tableColumns[7], congressman.getPhotoCongressman() );
		content.put( tableColumns[8], congressman.getRankingCongressman() );

		boolean result = ( insertAndClose(sqliteDatabase, tableName, content ) > 0 );			
		return result;
	}
	
	public boolean insertAllCongressman( List<Congressman> congressmanList ) {
		Iterator<Congressman> i = congressmanList.iterator();
		boolean result = true;
		
		while( i.hasNext() ) {
			result = insertCongressman(i.next());
		}
		
		return result; 
	}
	
	public List<Congressman> getAll() {

		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
				"SELECT * FROM CONGRESSMAN order by TOTAL_SPENT_CONGRESSMAN DESC", null );

		List<Congressman> listParlamentares = new ArrayList<Congressman>();

		while ( cursor.moveToNext() ) {

			Congressman deputy = new Deputy();
			
			deputy.setIdCongressman( cursor.getInt( cursor
					.getColumnIndex( "ID_CONGRESSMAN" ) ) );
			
			deputy.setNameCongressman( cursor.getString( cursor
					.getColumnIndex( "NAME_CONGRESSMAN" ) ) );
			
			deputy.setStatusCogressman( Boolean.parseBoolean( cursor.
					getString( cursor.getColumnIndex( "STATUS_CONGRESSMAN" ) ) ) );
			
			deputy.setPartyCongressman( cursor.getString( cursor
					.getColumnIndex( "PARTY" ) ) );
			
			deputy.setUfCongressman( cursor.getString( cursor
					.getColumnIndex( "UF_CONGRESSMAN" ) ) );
			
			deputy.setTotalSpentCongressman( cursor.getDouble( cursor
					.getColumnIndex( "TOTAL_SPENT_CONGRESSMAN" ) ) );
			
			deputy.setRankingCongressman( cursor.getInt( cursor
					.getColumnIndex( "RANKING_CONGRESSMAN" ) ) );
			
			deputy.setIdUpdateCongressman( cursor.getInt( cursor
					.getColumnIndex( "ID_UPDATE" ) ) );
			
			listParlamentares.add( deputy );
		}
		
		sqliteDatabase.close();
		return listParlamentares;
	}
	
	public List<Congressman> selectCongressmanByName( String congressmanName ) {

		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
				"SELECT * FROM CONGRESSMAN WHERE NAME_CONGRESSMAN LIKE '%" + congressmanName + "%'", null );

		List<Congressman> listParlamentares = new ArrayList<Congressman>();

		while ( cursor.moveToNext() ) {

			Congressman deputy = new Deputy();
			
			deputy.setIdCongressman( cursor.getInt( cursor
					.getColumnIndex( "ID_CONGRESSMAN" ) ) );
			
			deputy.setNameCongressman( cursor.getString( cursor
					.getColumnIndex( "NAME_CONGRESSMAN" ) ) );
			
			deputy.setStatusCogressman( Boolean.parseBoolean( cursor.
					getString( cursor.getColumnIndex( "STATUS_CONGRESSMAN" ) ) ) );
			
			deputy.setPartyCongressman( cursor.getString( cursor
					.getColumnIndex( "PARTY" ) ) );
			
			deputy.setUfCongressman( cursor.getString( cursor
					.getColumnIndex( "UF_CONGRESSMAN" ) ) );
			
			deputy.setTotalSpentCongressman( cursor.getDouble( cursor
					.getColumnIndex( "TOTAL_SPENT_CONGRESSMAN" ) ) );
			
			deputy.setRankingCongressman( cursor.getInt( cursor
					.getColumnIndex( "RANKING_CONGRESSMAN" ) ) );
			
			deputy.setIdUpdateCongressman( cursor.getInt( cursor
					.getColumnIndex( "ID_UPDATE" ) ) );
			
			listParlamentares.add( deputy );
		}
		
		sqliteDatabase.close();
		return listParlamentares;
	}
}
