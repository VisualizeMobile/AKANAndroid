package br.com.visualize.akan.domain.controller;

import java.util.List;

import org.apache.http.client.ResponseHandler;

import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;
import br.com.visualize.akan.domain.model.Congressman;
import android.content.Context;

public class CongressmanFacade {
	private DeputyController deputyController;
	private static CongressmanFacade instance = null;
	
	private CongressmanFacade( Context context ) {
		deputyController = DeputyController.getInstance(context);
	}
	
	public static CongressmanFacade getInstance( Context context ) {
		if ( instance == null ) {
			instance = new CongressmanFacade( context );
		}
		
		return instance;
	}
	
	/* Deputy's Block */
	public List<Congressman> requestAllDeputy( 
			ResponseHandler<String> responseHandler ) throws 
			ConnectionFailedException, RequestFailedException, 
			NullCongressmanException {
		
		List<Congressman> deputyList;
				
		deputyList = deputyController.requestAllCongressman(responseHandler);
		
		return deputyList;
	}
	
	public List<Congressman> getAllDeputy() {
		List<Congressman> deputyList;
		
		deputyList = deputyController.getAllCongressman();
		
		return deputyList;
	}
	
	public List<Congressman> getDeputyByName ( String deputyName ) {
		List<Congressman> deputyList;
		
		deputyList = deputyController.getByName(deputyName);
		
		return deputyList;
	}
	
	public List<Congressman> getDeputyList() {
		List<Congressman> deputyList;
		
		deputyList = DeputyController.getCongressmanList();
		
		return deputyList;
	}
}
