package br.com.visualize.akan.api.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class NullDao extends Dao {
	private static NullDao instanceDao = null;
	private long NullDatabaseError = -1;

	private NullDao(Context context) {
		this.context = context;
	}

	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static NullDao getInstance(Context context) {
		if( instanceDao != null ) {
			/*! Nothing To Do. */

		} else {
			instanceDao = new NullDao(context);
		}
		return instanceDao;
	}

	public boolean checkEmptyLocalDb()	{
		return false;
	}

	@Override
	public long insertAndClose(SQLiteDatabase sqliteDatabase, String table, ContentValues values){
		return NullDatabaseError;
	}
}
