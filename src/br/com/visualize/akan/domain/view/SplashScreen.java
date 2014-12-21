/*
 * File: SplashScreen.java
 * Purpose: Provides access to an application's initial presentation.
 */
package br.com.visualize.akan.domain.view;


import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.controller.CongressmanController;


/**
 * Sets the initial application screen presentation.
 */
public class SplashScreen extends Activity {
	
	public CongressmanController congressmanController;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		congressmanController = CongressmanController
		      .getInstance( getApplicationContext() );
		
		new Thread( new InitialRequest() ).start();
		
		Intent myAction = new Intent( SplashScreen.this, ListScreen.class );
		
		SplashScreen.this.startActivity( myAction );
		SplashScreen.this.finish();
	}
	
	
	/**
	 * Providence the actions that should be made to the initial request for 
	 * the operation of the Thread.
	 */
	private class InitialRequest implements Runnable {
		
		@Override
		public void run() {
			Looper.prepare();
			
			ResponseHandler<String> responseHandler = HttpConnection
			      .getResponseHandler();
			
			try {
				congressmanController.requestAllCongressman( responseHandler );
				
			} catch( Exception e ) {
				e.printStackTrace();
			}
			
			Looper.loop();
		}
	}
}
