/*
 * File: CongressmanController.java 
 * Purpose: Brings the implementation of class CongressmanController.
 */
package br.com.visualize.akan.domain.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import android.util.Log;
import br.com.visualize.akan.api.dao.CongressmanDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.enumeration.SubQuota;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to define the methods that are responsible for generating actions,
 * calculate results and everything that is requested by layer 'View' referring
 * to Congressman.
 */
public class CongressmanController {
	
	private static CongressmanController instance = null;
	private static Congressman congressman;
	private static List<Congressman> congressmanList;
	private static List<Congressman> followedCongressmen;
	
	@SuppressWarnings( "unused" )
	private Context context;
	
	@SuppressWarnings( "unused" )
	private QuotaController quotaController;
	
	private UrlController urlController;
	private CongressmanDao congressmanDao;
	
	private CongressmanController( Context context ) {
		
		congressmanDao = CongressmanDao.getInstance( context );
		urlController = UrlController.getInstance( context );
		
		this.context = context;
	}
	
	/**
	 * Return the unique instance of CongressmanController active in the project.
	 * <p>
	 * @return The unique instance of CongressmanController.
	 */
	public static CongressmanController getInstance( Context context ) {
		if( instance == null ) {
			instance = new CongressmanController( context );
		} else {
			/*! Nothing To Do. */
		}
		
		return instance;
	}
	
	/**
	 * Associates a congressman on CongressmanController.
	 * <p>
	 * @param congressman Congressman to be associated
	 */
	public void setCongressman( Congressman congressman ) {
		CongressmanController.congressman = congressman;
	}
	
	/**
	 * Search the database all congressman and returns them as a list.
	 * <p>
	 * @param responseHandler Handler of welcoming server responses
	 * <p>
	 * @return List of Congressman resulting from the server.
	 * <p>
	 * @throws Exception
	 */
	public List<Congressman> requestAllCongressman(
	      ResponseHandler<String> responseHandler ) throws Exception {
		
		if( responseHandler != null ) {
			
			if( congressmanDao.checkEmptyLocalDb() ) {
				
				String url = urlController.getAllCongressmanUrl();

				String jsonCongressman = HttpConnection.request( responseHandler,
				      url );
				
				setCongressmanList( JsonHelper
				      .listCongressmanFromJSON( jsonCongressman ) );
				
				congressmanDao.insertAllCongressman( getCongressmanList() );
				
			} else {
				/* ! Nothing To Do. */
			}
		}
		
		return getCongressmanList();
	}
	
	public boolean updateStatusCongressman(){
		boolean result = false;
		try {
			 result = congressmanDao.setFollowedCongressman(getCongresman());
		} catch (NullCongressmanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * TODO: Investigate Duplication.
	 * 
	 * 	It seems that the methods getCongressmanList() and getAllCongressman() 
	 * 	are repeated. It should be investigated whether there is duplication.
	 */
	
	/**
	 * Returns all congressman stored in the local database.
	 * <p>
	 * @return List of Congressman.
	 */
	public static List<Congressman> getCongressmanList() {	
		return congressmanList;
	}
	
	/**
	 * Returns all congressman stored in the local database.
	 * <p>
	 * @return List of Congressman.
	 */
	public List<Congressman> getAllCongressman() {
		congressmanList = congressmanDao.getAll();
		
		return congressmanList;
	}
	
	/**
	 * Associates a list of congressman on CongressmanController.
	 * <p>
	 * @param congressmanList List of congressman to be associated
	 */
	private static void setCongressmanList( List<Congressman> congressmanList ) {
		CongressmanController.congressmanList = congressmanList;
	}
	
	
	/**
	 * Returns all congressman that match the search and are stored in the 
	 * local database.
	 * <p>
	 * @param congressmanName name of congressman to be searched.
	 * <p>
	 * @return List of congressman that match this search by name.
	 */
	public List<Congressman> getByName( String congressmanName ) {
		congressmanList = congressmanDao
		      .selectCongressmanByName( congressmanName );
		
		return congressmanList;
	}
	
	/**
	 * Returns the congressman associated with CongressmanController.
	 * <p>
	 * @return Congressman associated with CongressmanController.
	 */
	public Congressman getCongresman() {
		return CongressmanController.congressman;
	}
	
	public List<Congressman> getFollowedCongressman(){
		List<Congressman> congressmenAnalized = new ArrayList<Congressman>();
		Iterator<Congressman> iteratorCongressman = congressmanList.iterator();
		
			while( iteratorCongressman.hasNext() ) {
				Congressman congressman = iteratorCongressman.next();
				if ( congressman.isStatusCogressman()){
					Log.e("seguido", congressman.getNameCongressman());
					congressmenAnalized.add(congressman);
					
				}
				else
				{
					//nothing to do
					
				}
			}
			
			return congressmenAnalized;
			
	}

		
	
}
