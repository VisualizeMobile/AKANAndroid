/*
 * File: QuotaController.java 
 * Purpose: Brings the implementation of class QuotaController.
 */
package br.com.visualize.akan.domain.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.util.Log;
import br.com.visualize.akan.api.dao.QuotaDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to define the methods that are responsible for generating actions,
 * calculate results and everything that is requested by layer 'View' referring
 * to parliamentary quotas.
 */
public class QuotaController {
	private static QuotaController instanceQuotaController = null;
	private static List<Quota> quotaList = null;
	private Context context;

	private UrlController urlController;
	private QuotaDao quotaDao;
	private CongressmanController congressmanController;
	
	private QuotaController( Context context ) {
		congressmanController = CongressmanController.getInstance(context);
		urlController = UrlController.getInstance( context );
		quotaDao = QuotaDao.getInstance( context );
		
		this.context = context;
	}
	
	/**
	 * Return the unique instance of QuotaController active in the project.
	 * <p>
	 * @return The unique instance of QuotaController.
	 */
	public static QuotaController getInstance( Context context ) {
		
		if( instanceQuotaController != null ) {
			/* ! Nothing To Do. */
			
		} else {
			instanceQuotaController = new QuotaController( context );
			
		}
		
		return instanceQuotaController;
	}
	
	/**
	 * Inserts in the database quotas, referring to a congressman in particular,
	 * passed as parameter in the local database of the application.
	 * <p>
	 * @param insertedQuotas
	 *           List of quotas to be inserted.
	 */
	public void insertQuotasOnCongressman( List<Quota> insertedQuotas ) {
		/* ! Write Instructions Here. */
	}
	
	/**
	 * Deletes all quotas of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * <p>
	 * @param idCongressman
	 *           Numeric identifier of congressman that must have deleted the
	 *           quotas.
	 */
	public void deleteQuotasFromCongressman( int idCongressman ) {
		quotaDao.deleteQuotasFromCongressman( idCongressman );
	}
	
	/**
	 * Search the database all quotas related to the referenced congressman and
	 * returns them as a list.
	 * <p>
	 * 
	 * @param idCongressman
	 *           Numeric identifier of congressman that must have deleted the
	 *           quotas.
	 * <p>
	 * @return The list of referenced quotas belonging to the congressman.
	 */
	public List<Quota> getQuotasByIdCongressman( int idCongressman ) {
		List<Quota> foundQuotas = new ArrayList<Quota>();

		foundQuotas = quotaDao.getQuotasByIdCongressman( idCongressman );

		return foundQuotas;
	}
	
	/**
	 * Asks the server for quotas for a particular Congressman. This Congressman
	 * is identified by ID.
	 * <P>
	 * @param id
	 *           Congressman identifier related to Quotas.
	 * @param responseHandler
	 *           Handler of welcoming server responses.
	 * <p>
	 * @return List of Quotas resulting from the server.
	 * <p>
	 * @throws Exception
	 */
	public List<Quota> getQuotaById( int id,
	      ResponseHandler<String> responseHandler ) throws Exception {
		Context context;
		
		
		if( responseHandler != null ) {
			
			if ( !congressmanController.getCongresman().isStatusCogressman()){
				
			
				String url = urlController.quotasWithCongressmanIdUrl( id );
				
				String jsonQuota = HttpConnection.request( responseHandler, url );
				
				setQuotaList( JsonHelper
				      .listQuotaByIdCongressmanFromJSON( jsonQuota ) );
				
				quotaDao.insertQuotasById( getQuotaList() );
			
			
		} else {
			/* ! Nothing To Do. */
		}	
		
		}
		return getQuotaList();
	}
	
	/* TODO: Write JAVADOC. */
	public void setQuotaFromCongressmanSelected( Integer id,
	      ResponseHandler<String> responseHandler ) throws Exception {
		
		String url = urlController.quotasWithCongressmanIdUrl( id );
		
		String jsonQuota;
		
		jsonQuota = HttpConnection.request( responseHandler, url );
		
		setQuotaList( JsonHelper.listQuotaByIdCongressmanFromJSON( jsonQuota ) );
	}
	
	/**
	 * Returns the list of quotas associated with QuotaController.
	 * <p>
	 * @return List of quotas associated with QuotaController.
	 */
	private List<Quota> getQuotaList() {
		
		return quotaList;
	}
	
	/**
	 * Returns the list of quotas filtered by month and year
	 * <p>
	 * @return List of quotas filtered by month and year .
	 */
	public List<Quota> getQuotaByDate(int month , int year){
		List<Quota> quotasByDate = new ArrayList<Quota>();
		Iterator<Quota> iterator = getQuotaList().iterator();
		
		while(iterator.hasNext()){
			Quota quota = iterator.next();
			
			if((quota.getMonthReferenceQuota().getvalueMonth() == month) && (quota.getYearReferenceQuota() == year)){
				quotasByDate.add(quota);
			}			
		}
				
		return quotasByDate;
	}
	
	/**
	 * 
	 * @return Return a list with max and min Date to show date spinner
	 * @throws ParseException
	 */
	public List<Long> getMinMaxDate() throws ParseException{
		List<Integer> listYears = new ArrayList<Integer>();
		List<Integer> monthsMajorYear = new ArrayList<Integer>();
		List<Long> periodDate = new ArrayList<Long>();
		Long dateMin;
		Long dateMax;
		String dateToConversion;
		Date date = new Date();
		
		Integer majorYear;
		Integer minorYear;
		Integer majorMonth;
		
		//get list of all years available
		listYears = quotaDao.getYears();
		
		//get interval date
		majorYear = Collections.max(listYears);
		minorYear = Collections.min(listYears);
		Log.e("Maior ano", ""+majorYear);
		Log.e("Menor ano", ""+minorYear);
		monthsMajorYear = quotaDao.getMonthsFromCurrentYear(majorYear);
		majorMonth = Collections.max(monthsMajorYear);
		Log.e("Maior mes", ""+majorMonth);
		//convert to date format
		dateToConversion = minorYear+"-01-01 00:00:00";
		Log.e("Data a ser convertida", dateToConversion);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			date = dateFormat.parse(dateToConversion);
			
			dateMin = date.getTime();
			Log.e("dentro do maxmin", ""+dateMin);
			dateToConversion = majorYear+"-"+majorMonth+"-01 00:00:01";
			Log.e("Data a ser convertida", dateToConversion);
			date = dateFormat.parse(dateToConversion);
			dateMax = date.getTime();	
		
			periodDate.add(dateMax);
			periodDate.add(dateMin);
			return periodDate;
	}
	
	/**
	 * Associates the list of quotas on QuotaController.
	 * <p>
	 * @param listQuotaByIdCongressmanFromJSON
	 */
	private static void setQuotaList(
	      List<Quota> listQuotaByIdCongressmanFromJSON ) {
	
		QuotaController.quotaList = listQuotaByIdCongressmanFromJSON;
		
	}
}
