package br.com.visualize.akan.api.dao;

import br.com.visualize.akan.domain.model.Url;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class NullUrlDao extends UrlDao {
	private static NullUrlDao instanceUrlDao = null;

	protected NullUrlDao(Context context) {
		super(context);
	}

	/**
	 * Return the unique instance of UrlDao active in the project.
	 * <p>
	 * @return The unique instance of NullUrlDao.
	 */
	public static NullUrlDao getInstance(Context context) {

		if( instanceUrlDao != null ) {
			/*! Nothing To Do. */

		} else {
			instanceUrlDao = new NullUrlDao(context);

		}

		return instanceUrlDao;
	}

	/**
	 * Checks if database is empty
	 *
	 */
	@Override
	protected boolean checkEmptyLocalDb(){
		return true;
	}

	/**
	 * Inserts in the database Url, referring to a congressman in particular,
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedUrls List of Url to be inserted.
	 */
	@Override
	public void insertUrl(Url url) {

	}

	/**
	 * Deletes all Urls of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idUrl Numeric identifier of congressman that must have
	 * 		 					deleted the Urls.
	 */
	@Override
	public void deleteUrl(Url url) {
	}

}
