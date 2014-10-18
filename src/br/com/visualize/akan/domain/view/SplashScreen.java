package br.com.visualize.akan.domain.view;

import android.app.Activity;
import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.exception.ConnectionFailedException;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.exception.RequestFailedException;

public class SplashScreen extends Activity {
	
	public CongressmanController congressmanController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		congressmanController = CongressmanController
				.getInstance(getBaseContext());

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				ResponseHandler<String> responseHandler = HttpConnection
						.getResponseHandler();
				try {
					congressmanController
							.getAllCongressman(responseHandler);
				} catch (ConnectionFailedException e) {
					//TODO launch error alert
					e.printStackTrace();
				} catch (RequestFailedException e) {
					//TODO launch error alert	
					e.printStackTrace();
				} catch (NullCongressmanException e) {
					//TODO launch error alert
					e.printStackTrace();
				}
				Looper.loop();
			}
		}).start();
		
		Log.i("antes intent", "antes intent");
		Intent myAction = new Intent(SplashScreen.this, ListScreen.class);
		
		SplashScreen.this.startActivity(myAction);
		Log.i("depois intent", "depois intent");
		SplashScreen.this.finish();
	}


}
