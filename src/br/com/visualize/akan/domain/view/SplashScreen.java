/*
 * File: SplashScreen.java Purpose: Provides access to an application's initial
 * presentation.
 */
package br.com.visualize.akan.domain.view;


import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import br.com.visualize.akan.R;
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
		setContentView( R.layout.splash_screen_activity );
		congressmanController = CongressmanController
		        .getInstance( getApplicationContext() );
		
		requestCongressman();
	}
	
	/**
	 * Providence the actions that should be made to the initial request for the
	 * operation of the Thread.
	 */
	public void requestCongressman() {
		final ProgressDialog progress = new ProgressDialog( this );
		
		progress.setMessage( "Carregando dados..." );
		progress.show();
		
		new Thread() {
			
			public void run() {
				
				Looper.prepare();
				try {
					ResponseHandler<String> responseHandler = HttpConnection
					        .getResponseHandler();
					
					congressmanController
					        .requestAllCongressman( responseHandler );
				} catch( Exception e ) {
					// TODO: handle exception
				}
				
				runOnUiThread( new Runnable() {
					
					@Override
					public void run() {
						progress.setMessage( "Dados carregados" );
						
						Intent myAction = new Intent( SplashScreen.this,
						        ListScreen.class );
						
						SplashScreen.this.startActivity( myAction );
						SplashScreen.this.finish();
						
						progress.dismiss();
						Looper.loop();
					}
				} );
				
			}
		}.start();
	}
	
}
