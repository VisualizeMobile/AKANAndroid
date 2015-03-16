/*
 * File: CongressmanRequest.java Purpose: Brings the implementation of class
 * CongressmanRequest.
 */
package br.com.visualize.akan.api.request;


import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.content.Context;
import br.com.visualize.akan.api.helper.JsonHelper;
import br.com.visualize.akan.domain.controller.UrlController;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.model.Congressman;


/**
 * Makes a request of Congressman to the server.
 */
public class CongressmanRequest {
	
	private UrlController urlController;
	
	private CongressmanRequest( Context context ) {
		this.urlController = UrlController.getInstance( context );
	}
	
	/**
	 * Makes a request of Congressman to the server.
	 * 
	 * @param responseHandler
	 *            The response to process.
	 * 
	 * @return The list of Congressman requested the server.
	 * 
	 * @throws Exception
	 */
	public List<Congressman> doRequest( ResponseHandler<String> responseHandler )
	        throws Exception {
		
		List<Congressman> congressmanList = null;
		
		if( responseHandler != null ) {
			String url = urlController.getUrl().getDefaultUrl();
			url.concat( "/parlamentares" );
			
			String jsonCongressmanList = HttpConnection.request(
			        responseHandler, url );
			
			congressmanList = JsonHelper
			        .listCongressmanFromJSON( jsonCongressmanList );
			
			return congressmanList;
			
		} else {
			throw new ConnectionFailedException();
		}
	}
}
