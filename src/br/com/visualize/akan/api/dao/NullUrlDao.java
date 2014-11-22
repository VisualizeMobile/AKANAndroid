package br.com.visualize.akan.api.dao;

import android.content.Context;

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
}
