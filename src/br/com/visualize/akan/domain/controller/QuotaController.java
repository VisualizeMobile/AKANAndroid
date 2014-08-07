/*
 * File: 	QuotaController.java 
 * Purpose: Brings the implementation of class QuotaController.
 */
package br.com.visualize.akan.domain.controller;

import java.util.List;

import br.com.visualize.akan.api.dao.QuotaDao;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to define the methods that are responsible for generating actions, 
 * calculate results and everything that is requested by layer 'View' 
 * referring to parliamentary quotas.
 */
public class QuotaController {
	private static QuotaController instanceQuotaController = null;
	private QuotaDao daoQuota = null;
	
	private QuotaController() {
		daoQuota = QuotaDao.getInstance();
	}
	
	/**
	 * Return the unique instance of QuotaController active in the project.
	 * <p>
	 * @return The unique instance of QuotaController.
	 */
	public static QuotaController getInstance() {
		
		if( instanceQuotaController != null ) {
			/*! Nothing To Do. */
			
		} else {
			instanceQuotaController = new QuotaController();
			
		}
		
		return instanceQuotaController;
	}
	
	/**
	 * Inserts in the database quotas, referring to a congressman in particular, 
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedQuotas List of quotas to be inserted.
	 */
	public void insertQuotasOnCongressman( List<Quota> insertedQuotas ) {
		daoQuota.insertQuotasOnCongressman( insertedQuotas );
	}
	
	/**
	 * Deletes all quotas of the database relating to the past as parameter 
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have 
	 * 		 					deleted the quotas.
	 */
	public void deleteQuotasFromCongressman( int idCongressman ) {
		daoQuota.deleteQuotasFromCongressman( idCongressman );
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
		List<Quota> foundQuotas = null;
		
		foundQuotas = daoQuota.getQuotasByIdCongressman( idCongressman );
		return foundQuotas;
	}
}
