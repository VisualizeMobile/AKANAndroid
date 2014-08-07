/*
 * File: 	QuotaController.java 
 * Purpose: Brings the implementation of class QuotaController.
 */
package br.com.visualize.akan.domain.controller;


/**
 * Serves to define the methods that are responsible for generating actions, 
 * calculate results and everything that is requested by layer 'View' 
 * referring to parliamentary quotas.
 */
public class QuotaController {
	private static QuotaController instanceQuotaController = null;
	
	private QuotaController() {
		/*! Write Instructions Here. */
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
}
