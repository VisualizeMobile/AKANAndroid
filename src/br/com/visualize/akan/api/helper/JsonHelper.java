/*
 * File: JsonHelper.java Purpose: Brings the implementation of class JsonHelper.
 */
package br.com.visualize.akan.api.helper;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;
import br.com.visualize.akan.domain.model.Statistic;


/**
 * Responsible for supporting to create JSON for server responses.
 */
public class JsonHelper {
	
	private static List<Congressman> congressmanList = new ArrayList<Congressman>();
	
	/**
	 * This server response to produce and return a list of Congressman.
	 * 
	 * @param jsonCongressmanList
	 *            Representation of the JSON of Congressman list.
	 * 
	 * @return List of Congressman
	 * 
	 * @throws NullCongressmanException
	 * @throws JSONException
	 */
	public static List<Congressman> listCongressmanFromJSON(
	        String jsonCongressmanList ) throws NullCongressmanException,
	        JSONException {
		
		JSONArray jArray = new JSONArray( jsonCongressmanList );
		
		try {
			Congressman congressman = null;
			
			for( int index = 0; index < jArray.length(); index++ ) {
				
				congressman = new Congressman();
				
				congressman.setIdCongressman( jArray.getJSONObject( index )
				        .getInt( "pk" ) );
				
				congressman.setTotalSpentCongressman( jArray
				        .getJSONObject( index ).getJSONObject( "fields" )
				        .getDouble( "valor" ) );
				
				congressman.setRankingCongressman( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getInt( "ranking" ) );
				
				congressman
				        .setUfCongressman( jArray.getJSONObject( index )
				                .getJSONObject( "fields" )
				                .getString( "ufparlamentar" ) );
				
				congressman.setPartyCongressman( jArray.getJSONObject( index )
				        .getJSONObject( "fields" )
				        .getString( "partidoparlamentar" ) );
				
				congressman.setNameCongressman( jArray.getJSONObject( index )
				        .getJSONObject( "fields" )
				        .getString( "nomeparlamentar" ) );
				
				congressmanList.add( congressman );
			}
			
		} catch( NullPointerException npe ) {
			throw new NullCongressmanException();
		}
		
		return congressmanList;
	}
	
	/**
	 * This server response to produce and return a list of Quotas.
	 * <p>
	 * 
	 * @param jsonQuotaByIdCongressmanList
	 *            Representation of the JSON of Quotas list.
	 *            <p>
	 * @return List of Quotas
	 *         <p>
	 * @throws NullCongressmanException
	 * @throws JSONException
	 */
	public static List<Quota> listQuotaByIdCongressmanFromJSON(
	        String jsonQuotaByIdCongressmanList )
	        throws NullCongressmanException, JSONException {
		
		List<Quota> quotaList = new ArrayList<Quota>();
		
		Quota quota;
		JSONArray jArray = new JSONArray( jsonQuotaByIdCongressmanList );
		
		try {
			
			for( int index = 0; index < jArray.length(); index++ ) {
				quota = new Quota();
				
				quota.setIdQuota( jArray.getJSONObject( index ).getInt( "pk" ) );
				
				quota.setIdUpdateQuota( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getInt( "versaoupdate" ) );
				
				quota.setYearReferenceQuota( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getInt( "ano" ) );
				
				quota.setValueQuota( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getDouble( "valor" ) );
				
				quota.setIdCongressmanQuota( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getInt( "idparlamentar" ) );
				
				quota.setTypeQuotaByNumber( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getInt( "numsubcota" ) );
				
				quota.setDescriptionQuota( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getString( "descricao" ) );
				
				quota.setTypeMonthByNumber( jArray.getJSONObject( index )
				        .getJSONObject( "fields" ).getInt( "mes" ) );
				
				quotaList.add( quota );
			}
			
		} catch( NullPointerException npe ) {
			throw new NullCongressmanException();
		}
		
		return quotaList;
	}
	
	/**
	 * This server response to produce and return a list of Statistics.
	 * <p>
	 * 
	 * @param jsonStatisticList
	 *            Representation of the JSON of Quotas list.
	 *            <p>
	 * @return List of Quotas
	 *         <p>
	 * @throws JSONException
	 */
	public static List<Statistic> listStatisticsFromJSON(
	        String jsonStatisticList )
	        throws NullCongressmanException, JSONException {
		
		List<Statistic> statisticList = new ArrayList<Statistic>();
		
		Statistic statistic;
		JSONArray jArray = new JSONArray( jsonStatisticList );
		
		try {
			
			for( int index = 0; index < jArray.length(); index++ ) {
				statistic = new Statistic();
				/*
				 * some statistics does not have referenced month
				 * */
				int month;
				try{
					month = jArray.getJSONObject( index ).getInt( "mes" );
				}catch(JSONException e) {
					month = 0;
				}
				statistic.setMonthByNumber(month);
				
				statistic.setSubquotaByNumber(jArray.getJSONObject(index)
						.getInt( "numsubcota"));
				
				/*
				 * some statistics does not have referenced year
				 * */
				int year;
				try{
					year = jArray.getJSONObject( index ).getInt( "ano" );
				}catch(JSONException e) {
					year = 0;
				}
				statistic.setYear(year);
				
				statistic.setAverage( jArray.getJSONObject( index )
				        .getDouble( "valor_medio" ) );
				
				statistic.setMaxValue( jArray.getJSONObject( index )
				        .getDouble( "valor_maximo" ) );
				
				/*
				 * some statistics does not have referenced std deviation
				 * */
				double stdDevition;
				try{
					stdDevition = jArray.getJSONObject( index )
				        .getDouble( "desvio_padrao" );
				}catch(JSONException e) {
					stdDevition = 0.0;
				}
				statistic.setStdDeviation(stdDevition);
							
				statisticList.add( statistic );
			}
			
		} catch( NullPointerException npe ) {
			throw new NullCongressmanException();
		}
		
		return statisticList;
	}
}
