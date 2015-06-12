/*
 * File: QuotaController.java Purpose: Brings the implementation of class
 * QuotaController.
 */
package br.com.visualize.akan.domain.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.http.client.ResponseHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import br.com.visualize.akan.api.dao.QuotaDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.exception.DatabaseInvalidOperationException;
import br.com.visualize.akan.domain.exception.NullQuotaException;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to define the methods that are responsible for generating actions,
 * calculate results and everything that is requested by layer 'View' referring
 * to parliamentary quotas.
 */
public class QuotaController {
	private static QuotaController instanceQuotaController = null;
	private static List<Quota> quotaList = null;
	private static Quota quota = null;

	@SuppressWarnings( "unused" )
	private Context context;

	private UrlController urlController;
	private QuotaDao quotaDao;
	private CongressmanController congressmanController;
	private Calendar calendar;

	private QuotaController( Context context ) {
		congressmanController = CongressmanController.getInstance( context );
		urlController = UrlController.getInstance( context );
		quotaDao = QuotaDao.getInstance( context );
		calendar = Calendar.getInstance();

		this.context = context;
	}

	/**
	 * Return the unique instance of QuotaController active in the project.
	 * <p>
	 *
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
	 *
	 * @param insertedQuotas
	 *            List of quotas to be inserted.
	 * @throws NullQuotaException
	 * @throws DatabaseInvalidOperationException
	 */
	public void insertQuotasOnCongressman( List<Quota> insertedQuotas )
	        throws NullQuotaException, DatabaseInvalidOperationException {
		quotaDao.insertQuotasById(insertedQuotas);
	}

	/**
	 * Deletes all quotas of the database relating to the past as parameter
	 * congressman for his numerical identifier.
	 * <p>
	 *
	 * @param idCongressman
	 *            Numeric identifier of congressman that must have deleted the
	 *            quotas.
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
	 *            Numeric identifier of congressman that must have deleted the
	 *            quotas.
	 *            <p>
	 * @return The list of referenced quotas belonging to the congressman.
	 */
	public List<Quota> getQuotasByIdCongressman( int idCongressman ) {
		List<Quota> foundQuotas = new ArrayList<Quota>();

		foundQuotas = quotaDao.getQuotasByIdCongressman( idCongressman );
		quotaList = foundQuotas;
		return foundQuotas;
	}

	public List<Quota> getQuotasByIdCongressmanAndType( int idCongressman, int subquota ) {
		List<Quota> foundQuotas = new ArrayList<Quota>();

		foundQuotas = quotaDao.getQuotasByIdCongressmanAndType( idCongressman, subquota );
		quotaList = foundQuotas;
		return foundQuotas;
	}

	/**
	 * Asks the server for quotas for a particular Congressman. This Congressman
	 * is identified by ID.
	 * <P>
	 *
	 * @param id
	 *            Congressman identifier related to Quotas.
	 * @param responseHandler
	 *            Handler of welcoming server responses.
	 *            <p>
	 * @return List of Quotas resulting from the server.
	 *         <p>
	 * @throws Exception
	 */
	public List<Quota> getQuotaById( int id,
	        ResponseHandler<String> responseHandler ) throws Exception {

		if( responseHandler != null ) {

			String url = urlController.quotasWithCongressmanIdUrl( id );

			String jsonQuota = HttpConnection.request( responseHandler, url );

			quotaDao.insertQuotasById( JsonHelper
			        .listQuotaByIdCongressmanFromJSON( jsonQuota ) );

		} else {
			/* ! Nothing To Do. */
		}

		return getQuotaList(id);
	}

	/* TODO: Write JAVADOC. */
	public void setQuotaFromCongressmanSelected( Integer id,
	        ResponseHandler<String> responseHandler ) throws Exception {

		String url = urlController.quotasWithCongressmanIdUrl( id );

		String jsonQuota;

		jsonQuota = HttpConnection.request( responseHandler, url );

		quotaDao.insertQuotasById( JsonHelper.listQuotaByIdCongressmanFromJSON( jsonQuota ) );
	}

	/**
	 * Returns the list of quotas associated with QuotaController.
	 * <p>
	 *
	 * @return List of quotas associated with QuotaController.
	 */
	private List<Quota> getQuotaList(int idCongressman) {
		quotaList = quotaDao.getQuotasByIdCongressman(idCongressman);
		return quotaList;
	}

	/**
	 * Returns the quota associated with QuotaController.
	 * <p>
	 *
	 * @return Instance of quota associated with QuotaController.
	 */
	public Quota getQuota(int idQuota) {
		quota = quotaDao.getQuotaById(idQuota);
		return quota;
	}
	/**
	 * Returns the quota associated with QuotaController.
	 * <p>
	 *
	 * @return Instance of quota associated with QuotaController.
	 */
	public Quota getQuota() {
		return quota;
	}

	/**
	 * Returns the list of quotas filtered by month and year
	 * <p>
	 *
	 * @return List of quotas filtered by month and year .
	 */
	public List<Quota> getQuotaByDate( int month, int year, int id ) {
		List<Quota> quotasByDate = new ArrayList<Quota>();
		Iterator<Quota> iterator = getQuotaList(id).iterator();

		while( iterator.hasNext() ) {
			Quota quota = iterator.next();

			if( ( quota.getMonthReferenceQuota().getvalueMonth() == month )
			        && ( quota.getYearReferenceQuota() == year ) ) {
				quotasByDate.add( quota );
			}
		}
		return quotasByDate;
	}

	/**
	 * Returns the list of quotas filtered by type
	 * <p>
	 *
	 * @return List of quotas filtered by month and year .
	 */
	public List<Quota> getQuotasByTypeAndYear( int subquota, int year ) {
		List<Quota> quotasByType = new ArrayList<Quota>();
		Iterator<Quota> iterator = getQuotaList(quota.getIdCongressmanQuota()).iterator();

		while( iterator.hasNext() ) {
			Quota quota = iterator.next();

			if( quota.getYearReferenceQuota() == year && quota.getTypeQuota().getValueSubQuota() == subquota) {
				quotasByType.add( quota );
			}
		}
		return quotasByType;
	}

	/**
	 *
	 * @return Return a list with max and min Date to show date spinner
	 * @throws ParseException
	 */
	@SuppressLint( "SimpleDateFormat" )
	public List<Long> getMinMaxDate() throws ParseException, 
	    NoSuchElementException {
		List<Integer> listYears = new ArrayList<Integer>();
		List<Integer> monthsMajorYear = new ArrayList<Integer>();
		List<Long> periodDate = new ArrayList<Long>();
		
		int idCongressman = congressmanController.getCongresman()
		        .getIdCongressman();
		
		Long dateMin;
		Long dateMax;
		String dateToConversion;
		Date date = new Date();

		Integer majorYear;
		Integer minorYear;
		Integer majorMonth;

		// get list of all years available
		listYears = quotaDao.getYears();
		// get interval date
		if( listYears != null ) {
    		majorYear = Collections.max( listYears );
    		minorYear = Collections.min( listYears );
		} else {
		    throw new NoSuchElementException();
		}

		monthsMajorYear = quotaDao.getMonthsFromCurrentYear( majorYear,
		        idCongressman );
		majorMonth = Collections.max( monthsMajorYear );

		// convert to date format
		dateToConversion = minorYear + "-01-01 00:00:00";


		SimpleDateFormat dateFormat = new SimpleDateFormat(
		        "yyyy-MM-dd HH:mm:ss" );

		date = dateFormat.parse( dateToConversion );

		dateMin = date.getTime();

		dateToConversion = majorYear + "-" + majorMonth + "-01 00:00:01";

		date = dateFormat.parse( dateToConversion );
		dateMax = date.getTime();

		periodDate.add( dateMax );
		periodDate.add( dateMin );
		return periodDate;
	}

	public double getMaxQuotaValue(List<Quota> quotas){
		double maxValue = 0.0;
		Iterator<Quota> i = quotas.iterator();
		
		while(i.hasNext()){
			Quota quota = i.next();
			maxValue= (quota.getValueQuota()>maxValue)? quota.getValueQuota() : maxValue;
		}
		
		return maxValue;
	}

	public int initializeDateFromQuotas() {
		int majorMonth = 1;

		try {
			int idCongressman = congressmanController.getCongresman()
			        .getIdCongressman();

			int majorYear = Collections.max( quotaDao.getYears() );
			majorMonth = Collections.max( quotaDao.getMonthsFromCurrentYear(
			        majorYear, idCongressman ) );
			calendar.set( majorYear, majorMonth, 1 );

		} catch( Exception e ) {

		}

		return majorMonth;
	}
}
