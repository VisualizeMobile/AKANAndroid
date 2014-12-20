package br.com.visualize.akan.domain.view;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;

public class SplashScreen extends Activity {
	
	public CongressmanController deputyController;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		deputyController = CongressmanController
				.getInstance( getApplicationContext() );

		new Thread( new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				ResponseHandler<String> responseHandler = HttpConnection
						.getResponseHandler();
				try {
					
					deputyController
							.requestAllCongressman( responseHandler );
					
				} catch ( Exception e ) {
					//TODO launch error alert
					e.printStackTrace();
					
				} 
				
				Looper.loop();
			}
		} ).start();
		
		Intent myAction = new Intent( SplashScreen.this, ListScreen.class );
		
		SplashScreen.this.startActivity( myAction );
		SplashScreen.this.finish();
	}


}
