package br.com.visualize.akan.api.dao;

import java.util.List;

import br.com.visualize.akan.domain.model.Quota;
import android.content.Context;

public class NullQuotaDao extends QuotaDao{
	private static NullQuotaDao instanceNullQuotaDao = null;


	private NullQuotaDao(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public boolean checkEmptyLocalDb() {
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

	@Override
	public boolean insertQuotasById( List<Quota> insertedQuotas ){
		return false;
	}
}
