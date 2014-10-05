package br.com.visualize.akan.api.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String dbName ="AKAN.db";
	private static final String congressmanTable = "CREATE TABLE [CONGRESSMAN] ([ID_CONGRESSMAN] VARCHAR(10), [ID_UPDATE] INT, [NAME_CONGRESSMAN] VARCHAR(40), [PARTY] VARCHAR(10), [UF_CONGRESSMAN] VARCHAR (2), [TOTAL_SPENT_CONGRESSMAN] DOUBLE, [STATUS_CONGRESSMAN] BOOLEAN, [PHOTO_CONGRESSMAN] VARCHAR(255), [RANKING_CONGRESSMAN] VARCHAR(10));";
	private static final String quotaTable = "CREATE TABLE [QUOTA] ([ID_QUOTA] VARCHAR(10), [ID_CONGRESSMAN] VARCHAR(10), [ID_UPDATE] INT, [TYPE_QUOTA] INT(10), [DESCRIPTION_QUOTA] VARCHAR (40), [MONTH_QUOTA] INT, [YEAR_QUOTA] INT, [VALUE_QUOTA] DOUBLE);";
	private static final String urlTable = "CREATE TABLE [URL] ([ID_URL] VARCHAR(10), [ID_UPDATE_URL] VARCHAR(255), [DEFAULT_URL] VARCHAR (255), [FIRST_URL] VARCHAR (255), [SECOND_URL] VARCHAR (255));";
	
	public DatabaseHelper(Context context){
		super(context, dbName, null, 1);
	}
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(congressmanTable);
		db.execSQL(quotaTable);
		db.execSQL(urlTable);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
