/*
 * File: CongressmanController.java Purpose: Brings the implementation of class
 * CongressmanController.
 */
package br.com.visualize.akan.domain.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.json.JSONException;

import android.content.Context;
import br.com.visualize.akan.api.dao.CongressmanDao;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.enumeration.Order;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;

/**
 * Serves to define the methods that are responsible for generating actions,
 * calculate results and everything that is requested by layer 'View' referring
 * to Congressman.
 */
public class CongressmanController {
	
	private static CongressmanController instance = null;
	private static Congressman congressman;
	private static List<Congressman> congressmanList = new ArrayList<Congressman>();
	
	private Order order = Order.RANKING;
	
	private Map<String,String> partyFilters = new HashMap<String,String>();
	private Map<String,String> spentFilters = new HashMap<String,String>();
	private Map<String,String> stateFilters = new HashMap<String,String>();

	private boolean followedFilter = false;
	
	private UrlController urlController;
	private CongressmanDao congressmanDao;
	
	private CongressmanController( Context context ) {
		congressmanDao = CongressmanDao.getInstance( context );
		urlController = UrlController.getInstance( context );
		spentFilters.put("0","0");
	}
	
	/**
	 * Return the unique instance of CongressmanController active in the
	 * project.
	 * 
	 * @return The unique instance of CongressmanController.
	 */
	public static CongressmanController getInstance( Context context ) {
		if( instance == null ) {
			instance = new CongressmanController( context );
		} else {
			/* ! Nothing To Do. */
		}
		return instance;
	}
	
	/**
	 * Associates a congressman on CongressmanController.
	 * 
	 * @param congressman
	 *            Congressman to be associated
	 */
	public void setCongressman( Congressman congressman ) {
		CongressmanController.congressman = congressman;
	}
	
	/**
	 * Search the database all congressman and returns them as a list.
	 * 
	 * @param responseHandler
	 *            Handler of welcoming server responses
	 * 
	 * @return List of Congressman resulting from the server.
	 * 
	 * @throws Exception
	 */
	public List<Congressman> requestAllCongressman(
	        ResponseHandler<String> responseHandler ) 
	                throws NullCongressmanException, NullCongressmanException, 
	                JSONException, ConnectionFailedException {
		
		if( responseHandler != null ) {
			
			if( congressmanDao.checkEmptyLocalDb() ) {
				
				String url = urlController.getAllCongressmanUrl();
				
				String jsonCongressman = HttpConnection.request(
				        responseHandler, url );
				List<Congressman> list = JsonHelper
				        .listCongressmanFromJSON( jsonCongressman );
				
				congressmanDao.insertAllCongressman( list );
				
			} else {
				/* ! Nothing To Do. */
			}
		}
		return getAllCongressman();
	}
	
	public boolean updateStatusCongressman() throws NullCongressmanException {
		boolean result = false;
		
		try {
			result = congressmanDao.setFollowedCongressman( getCongresman() );
		} catch( NullCongressmanException e ) {
			throw new NullCongressmanException();
		}
		
		return result;
	}
	
	/**
	 * Returns all congressman stored in the local database.
	 * 
	 * @return List of Congressman.
	 */
	public List<Congressman> getAllCongressman() {
		
		congressmanList = new ArrayList<Congressman>();
		
		List<String> filteredParty = new ArrayList<String>(partyFilters.values());
		List<String> filteredState = new ArrayList<String>(stateFilters.values());
		List<String> filteredSpent = new ArrayList<String>(spentFilters.values());
		
		for (Iterator<Congressman> it = congressmanDao.getAll(order).iterator();
				it.hasNext();) {
			Congressman congressman = it.next();
			//verify if parliamentary has all the filtered attributes
			double spent = congressman.getTotalSpentCongressman();
			String party = congressman.getPartyCongressman();
			String state = congressman.getUfCongressman();
			
			if( contains(filteredParty, party) &&
				contains(filteredState, state) &&
				(Double.parseDouble(filteredSpent.get(0))<spent) &&
				(followedFilter == congressman.isStatusCogressman()||
				!followedFilter) ){
				
				congressmanList.add(congressman);
			}
		}
		return congressmanList;
	}
	
	/**
	 * Verify if a parameterized list contains object
	 * return true if list is empty.
	 * 
	 * */
	<T> boolean contains(List<T>list, T value){
		boolean result = false;
		if(list.isEmpty() || list.contains(value)){
			result = true;
		}
		return result;
	}
	
	/**
	 * Returns all congressman that match the search and are stored in the local
	 * database.
	 * 
	 * @param congressmanName
	 *            name of congressman to be searched.
	 *            <p>
	 * @return List of congressman that match this search by name.
	 */
	public List<Congressman> getByName( String congressmanName ) {
		congressmanList = congressmanDao
		        .selectCongressmanByName( congressmanName );
		
		return congressmanList;
	}
	
	/**
	 * Returns the congressman associated with CongressmanController.
	 * 
	 * @return Congressman associated with CongressmanController.
	 */
	public Congressman getCongresman() {
		return CongressmanController.congressman;
	}
	
	public void setOrderBy(Order order){
		this.order = order;
		congressmanList = getAllCongressman();
	}
	
	public Order getOrder(){
		return order;
	}
	
	public List<String> getParties(){
		boolean inList = false;
		ArrayList<String> parties = new ArrayList<String>();
		Iterator<Congressman> i = congressmanDao.getAll(Order.PARTY).iterator();
		while(i.hasNext()){
			Congressman c = i.next();
			for(int j=0; j<parties.size(); j++){
				if (parties.get(j).equals(c.getPartyCongressman())){
					inList = true;
				}
			}
			if(!inList){
				parties.add(c.getPartyCongressman());
			}
			inList = false;
		}
		return parties;
	}

	public Map<String, String> getPartyFilters() {
		return partyFilters;
	}

	public void setPartyFilters(Map<String, String> partyFilters) {
		this.partyFilters = partyFilters;
	}

	public Map<String, String> getSpentFilters() {
		return spentFilters;
	}

	public void setSpentFilters(Map<String, String> spentFilters) {
		this.spentFilters = spentFilters;
	}

	public Map<String, String> getStateFilters() {
		return stateFilters;
	}

	public void setStateFilters(Map<String, String> stateFilters) {
		this.stateFilters = stateFilters;
	}

	public boolean getFollowedFilter() {
		return followedFilter;
	}

	public void setFollowedFilter(boolean followedFilter) {
		this.followedFilter = followedFilter;
	}
}
