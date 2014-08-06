/*
 * File: 	QuotaDao.java 
 * Purpose: Brings the implementation of QuotaDao class.
 */
package br.com.visualize.akan.api.dao;


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
	 * Serves to return the unique instance of QuotaDao active in the project.
	 * 
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
}
