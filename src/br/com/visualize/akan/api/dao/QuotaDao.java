/*
 * File: QuotaDao.java 
 * Purpose: Brings the implementation of class QuotaDao.
 */
package br.com.visualize.akan.api.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to define the methods that give the basic functionality to the
 * database - CRUD - related to parliamentary quotas. This class aims to
 * maintain the ability to generate support to the modification of data on
 * quotas.
 */
public class QuotaDao extends Dao {
	private static final boolean EMPTY = true;
	private static final boolean NOT_EMPTY = false;
	
	private StatisticDao statisticDao = null;
	private static QuotaDao instanceQuotaDao = null;
	private static String tableName = "QUOTA";
	
	private static String tableColumns[] = { "ID_QUOTA", "ID_CONGRESSMAN",
	      "ID_UPDATE", "TYPE_QUOTA", "DESCRIPTION_QUOTA", "MONTH_QUOTA",
	      "YEAR_QUOTA", "VALUE_QUOTA" };
	
	protected QuotaDao( Context context ) {
		QuotaDao.context = context;
		statisticDao = StatisticDao.getInstance( context );
	}
	
	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static QuotaDao getInstance( Context context ) {
		if( instanceQuotaDao != null ) {
			/* !Nothing To Do. */
			
		} else {
			instanceQuotaDao = new QuotaDao( context );
		}
		
		return instanceQuotaDao;
	}
	
	/**
	 * Checks if database is empty.
	 */
	public boolean checkEmptyLocalDb() {
		sqliteDatabase = database.getReadableDatabase();
		
		String query = "SELECT 1 FROM " + tableName;
		
		Cursor cursor = sqliteDatabase.rawQuery( query, null );
		
		boolean isEmpty = NOT_EMPTY;
		
		if( cursor != null ) {
			
			if( cursor.getCount() <= 0 ) {
				cursor.moveToFirst();
				
				isEmpty = EMPTY;
				
			} else {
				/*! Nothing To Do */
			}
			
		} else {
			isEmpty = EMPTY;
		}
		
		return isEmpty;
	}
	
	/**
	 * Inserts in the database quotas, referring to a congressman in particular,
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedQuotas
	 *           List of quotas to be inserted.
	 */
	public boolean insertQuotasById( List<Quota> insertedQuotas ) {
		Iterator<Quota> index = insertedQuotas.iterator();
		
		boolean result = true;
		
		while( index.hasNext() ) {
			result = insertQuota( index.next() );
		}
		
		return result;
	}
	
	/**
	 * Deletes all quotas of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman
	 *           Numeric identifier of congressman that must have deleted the
	 *           quotas.
	 */
	public boolean deleteQuotasFromCongressman( int idCongressman ) {
		sqliteDatabase = database.getWritableDatabase();
		
		boolean result = ( sqliteDatabase.delete( tableName, "ID_CONGRESSMAN=?",
		      new String[] { idCongressman + "" } ) > 0 );

		sqliteDatabase.close();
		
		return result;
	}
	
	/**
	 * Search the database all quotas related to the referenced congressman and
	 * returns them as a list.
	 * <p>
	 * @param idCongressman
	 *           Numeric identifier of congressman that must have deleted the
	 *           quotas.
	 * <p>
	 * @return The list of referenced quotas belonging to the congressman.
	 */
	public List<Quota> getQuotasByIdCongressman( int idCongressman ) {
		sqliteDatabase = database.getReadableDatabase();
		
		Cursor cursor = sqliteDatabase.rawQuery(
		      "SELECT * FROM QUOTA WHERE ID_CONGRESSMAN=" + idCongressman, null );
		
		List<Quota> listQuotas = new ArrayList<Quota>();
		
		while( cursor.moveToNext() ) {
			Quota quota = new Quota();
			
			quota.setIdQuota( cursor.getInt( cursor.getColumnIndex( "ID_QUOTA" ) ) );
			
			quota.setIdCongressmanQuota( cursor.getInt( cursor
			      .getColumnIndex( "ID_CONGRESSMAN" ) ) );
			
			quota.setIdUpdateQuota( cursor.getColumnIndex( "ID_UPDATE" ) );
			
			quota.setTypeQuotaByNumber( cursor.getInt( cursor
			      .getColumnIndex( "TYPE_QUOTA" ) ) );
			
			quota.setDescriptionQuota( cursor.getString( cursor
			      .getColumnIndex( "DESCRIPTION_QUOTA" ) ) );
			
			quota.setTypeMonthByNumber( cursor.getInt( cursor
			      .getColumnIndex( "MONTH_QUOTA" ) ) );
			
			quota.setYearReferenceQuota( cursor.getInt( cursor
			      .getColumnIndex( "YEAR_QUOTA" ) ) );
			
			quota.setValueQuota( cursor.getDouble( cursor
			      .getColumnIndex( "VALUE_QUOTA" ) ) );
			
			quota.setStatisticQuota( statisticDao.getStatisticByYear( 
					quota.getYearReferenceQuota() ));
			
			listQuotas.add( quota );
		}
		
		sqliteDatabase.close();
		
		return listQuotas;
	}
	/**
	 * Search in database the years available of the quotas
	 * 
	 * @return Return a list the years available of the quotas
	 */
	public List<Integer> getYears (){
		
		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
			      "SELECT YEAR_QUOTA FROM QUOTA GROUP BY YEAR_QUOTA", null );
		
		List<Integer> listYears = new ArrayList<Integer>();
		while(cursor.moveToNext()){
			Integer year;
			year = cursor.getInt(cursor.getColumnIndex("YEAR_QUOTA"));
			
			listYears.add(year);
			
		}
		
		return listYears;
		 
		
	}
	/**
	 * Search in database the months available of the quotas in current year
	 * 
	 * @param year
	 * @return The list of the months available of the quotas in current year
	 */
	public List<Integer> getMonthsFromCurrentYear( int year){

		sqliteDatabase = database.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
			      "SELECT MONTH_QUOTA FROM QUOTA WHERE YEAR_QUOTA = "+year+
			      " GROUP BY MONTH_QUOTA", null );
		
		List<Integer> listMonths = new ArrayList<Integer>();
		while(cursor.moveToNext()){
			Integer month;
			month = cursor.getInt(cursor.getColumnIndex("MONTH_QUOTA"));
			
			listMonths.add(month);
			
		}
		return listMonths;
	}
	/**
	 * Inserts in the database a quota.
	 * <p>
	 * @param quota 
	 * 			Quota to be inserted.
	 * <p>
	 * @return Result if the operation was successful or not.
	 */
	private boolean insertQuota( Quota quota ) {
		sqliteDatabase = database.getWritableDatabase();
		ContentValues content = new ContentValues();
		
		content.put( tableColumns[ 0 ], quota.getIdQuota() );
		content.put( tableColumns[ 1 ], quota.getIdCongressmanQuota() );
		content.put( tableColumns[ 2 ], quota.getIdUpdateQuota() );
		content.put( tableColumns[ 3 ], quota.getTypeQuota().getValueSubQuota() );
		content.put( tableColumns[ 4 ], quota.getDescriptionQuota() );
		content.put( tableColumns[ 5 ], quota.getMonthReferenceQuota()
		      .getvalueMonth() );
		
		content.put( tableColumns[ 6 ], quota.getYearReferenceQuota() );
		content.put( tableColumns[ 7 ], quota.getValueQuota() );
		
		boolean result = ( insertAndClose( sqliteDatabase, tableName, content ) > 0 );
		
		return result;
	}
}
