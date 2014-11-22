package br.com.visualize.akan.api.dao;

public class NullQuotaDao extends Dao{

	@Override
	protected boolean checkEmptyLocalDb() {
		return false;
	}

}
