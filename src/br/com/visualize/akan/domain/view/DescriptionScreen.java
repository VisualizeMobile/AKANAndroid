/*
 * File: DescriptionScreen.java 
 * Purpose: Brings the implementation of DescriptionScreen, a class that 
 * serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import br.com.visualize.akan.R;
import android.app.Activity;
import android.os.Bundle;


/**
 * Serves to bring the visibility of the detailed data of a congressman. Serves 
 * to bring the visibility of the detailed data of a congressman. Through this 
 * class you can give the user a description of the expenses of the share 
 * congressman.
 */
public class DescriptionScreen extends Activity {
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.description_screen_activity );
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}
