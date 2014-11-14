package br.com.visualize.akan.api.dao;

import br.com.visualize.akan.api.helper.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class Dao {
	
	protected static DatabaseHelper database;
	protected static SQLiteDatabase sqliteDatabase;
	protected static Context context;
	
	protected abstract boolean checkEmptyLocalDb();
	
	protected long insertAndClose(SQLiteDatabase sqliteDatabase, String table, ContentValues values){
		long resultInsert = sqliteDatabase.insert(table, null, values);
		sqliteDatabase.close();
		return resultInsert;
	}

}
