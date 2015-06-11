/*
 * File: Dao.java 
 * Purpose: Brings the implementation of class DAO.
 */
package br.com.visualize.akan.api.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.com.visualize.akan.api.helper.DatabaseHelper;


/**
 * Serves to keep details shared between classes Data Access Object.
 */
public abstract class Dao {
	
	protected static final boolean EMPTY = true;
	protected static final boolean NOT_EMPTY = false;
	
	protected static DatabaseHelper database;
	protected static SQLiteDatabase sqliteDatabase;
	protected static Context context;
	
	/**
	 * Checks if database is empty.
	 */
	protected abstract boolean checkEmptyLocalDb();
	
	/**
	 * Opens the database so that you can insert content within a given table in
	 * a given database present in the application.
	 * 
	 * @param sqlitedatabase
	 *           Database where the table.
	 * @param table
	 *           Table to alter
	 * @param values
	 *           Set of values to be added.
	 * 
	 * @return the row ID of the newly inserted row, or -1 if an error occurred.
	 */
	protected long insertAndClose( SQLiteDatabase sqliteDatabase, String table,
	      ContentValues values ) {
		
		long resultInsert = sqliteDatabase.insert( table, null, values );
		
		sqliteDatabase.close();
		
		return resultInsert;
	}
}
