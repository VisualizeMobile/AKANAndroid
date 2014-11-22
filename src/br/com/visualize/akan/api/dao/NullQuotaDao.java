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

	/**
	 * Inserts in the database quotas, referring to a congressman in particular,
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedQuotas List of quotas to be inserted.
	 */
	@Override
	public boolean insertQuotasById( List<Quota> insertedQuotas ){
		return false;
	}

	/**
	 * Deletes all quotas of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have
	 * 		 					deleted the quotas.
	 */
	@Override
	public void deleteQuotasFromCongressman( int idCongressman ) {
		/*! Write instructions Here. */
	}
}
