/*
 * File: 	QuotaDao.java 
 * Purpose: Brings the implementation of class QuotaDao.
 */
package br.com.visualize.akan.api.dao;

import java.util.List;

import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to define the methods that give the basic functionality to the 
 * database - CRUD - related to parliamentary quotas. This class aims to 
 * maintain the ability to generate support to the modification of data 
 * on quotas.
 */
public class QuotaDao {
	private static QuotaDao instanceQuotaDao = null;
	
	private QuotaDao( ) {
		/*! Write instructions Here. */
	}
	
	/**
	 * Return the unique instance of QuotaDao active in the project.
	 * <p>
	 * @return The unique instance of QuotaDao.
	 */
	public static QuotaDao getInstance() {
		
		if( instanceQuotaDao != null ) {
			/*! Nothing To Do. */
			
		} else {
			instanceQuotaDao = new QuotaDao();
			
		}
		
		return instanceQuotaDao;
	}
	
	/**
	 * Inserts in the database quotas, referring to a congressman in particular, 
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedQuotas List of quotas to be inserted.
	 */
	public void insertQuotasOnCongressman( List<Quota> insertedQuotas ) {
		/*! Write instructions Here. */
	}
	
	/**
	 * Deletes all quotas of the database relating to the past as parameter 
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have 
	 * 		 					deleted the quotas.
	 */
	public void deleteQuotasFromCongressman( int idCongressman ) {
		/*! Write instructions Here. */
	}
	
	/**
	 * Search the database all quotas related to the referenced congressman 
	 * and returns them as a list.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have 
	 * 		 					deleted the quotas.
	 * <p>
	 * @return The list of referenced quotas belonging to the congressman.
	 */
	public List<Quota> getQuotasByIdCongressman( int idCongressman ) {
		/*! Write instructions Here. */
		
		return null;
	}
}
