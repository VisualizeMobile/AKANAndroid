/*
 * File: 	QuotaDao.java
 * Purpose: Brings the implementation of class QuotaDao.
 */
package br.com.visualize.akan.api.dao;

import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.visualize.akan.domain.model.Quota;

/**
 * Serves to define the methods that give the basic functionality to the
 * database - CRUD - related to parliamentary quotas. This class aims to
 * maintain the ability to generate support to the modification of data
 * on quotas.
 */
public class QuotaDao extends Dao {
	
	private static QuotaDao instanceQuotaDao = null;
	private static String tableName = "QUOTA";
	
	private static String tableColumns[] = {
		"ID_QUOTA", 
		"ID_CONGRESSMAN", 
		"ID_UPDATE",
		"TYPE_QUOTA", 
		"DESCRIPTION_QUOTA", 
		"MONTH_QUOTA", 
		"", 
		"VALUE_QUOTA"
		};
	
	protected QuotaDao( Context context ) {

		this.context = context;
	}

	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static QuotaDao getInstance( Context context ) {
		if( instanceQuotaDao != null ) {
			/*! Nothing To Do. */

		} else {
			instanceQuotaDao = new QuotaDao( context );
		}
		
		return instanceQuotaDao;
	}

	public boolean checkEmptyLocalDb() {
		sqliteDatabase = database.getReadableDatabase();

		String query = "SELECT 1 FROM " + tableName;
		
		Cursor cursor = sqliteDatabase.rawQuery( query ,null );
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

	/**
	 * Inserts in the database quotas, referring to a congressman in particular,
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedQuotas List of quotas to be inserted.
	 */
	public boolean insertQuotasById( List<Quota> insertedQuotas ) {
		Iterator<Quota> i = insertedQuotas.iterator();
		boolean result = true;
		
		while( i.hasNext() ) {
			result = insertQuota( i.next() );
		}

		return result; 

	}

	/**
	 * Deletes all quotas of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have
	 * 		 					deleted the quotas.
	 */
	public void deleteQuotasFromCongressman( int idCongressman ) {
		/*! Write instructions Here. */
	}

	/**
	 * Search the database all quotas related to the referenced congressman
	 * and returns them as a list.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have
	 * 		 					deleted the quotas.
	 * <p>
	 * @return The list of referenced quotas belonging to the congressman.
	 */
	public List<Quota> getQuotasByIdCongressman( int idCongressman ) {
		/*! Write instructions Here. */

		return null;
	}

	
	private boolean insertQuota( Quota quota ) {
		sqliteDatabase = database.getWritableDatabase();
		ContentValues content = new ContentValues();
		
		content.put( tableColumns[0], quota.getIdQuota() );
		content.put( tableColumns[1], quota.getIdCongressmanQuota() );
		content.put( tableColumns[2], quota.getIdUpdateQuota() );
		content.put( tableColumns[3], quota.getTypeQuota().getRepresentativeNameQuota() );
		content.put( tableColumns[4], quota.getDescriptionQuota() );
		content.put( tableColumns[5], quota.getMonthReferenceQuota().getvalueMonth() );
		content.put( tableColumns[6], quota.getYearReferenceQuota() );
		content.put( tableColumns[7], quota.getValueQuota() );


		boolean result = ( insertAndClose(sqliteDatabase, tableName, content ) > 0 );
		
		return result;
	}
}
