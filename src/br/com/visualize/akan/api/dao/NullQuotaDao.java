package br.com.visualize.akan.api.dao;

import android.content.Context;

public class NullQuotaDao extends Dao{
	private static NullQuotaDao instanceNullQuotaDao = null;

	private NullQuotaDao (Context context) {
		this.context = context;
	}

	@Override
	protected boolean checkEmptyLocalDb() {
		return false;
	}


	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static NullQuotaDao getInstance(Context context) {
		if( instanceNullQuotaDao != null ) {
			/*! Nothing To Do. */

		} else {
			instanceNullQuotaDao = new NullQuotaDao(context);
		}
		return instanceNullQuotaDao;
	}
}
