/*
 * File: DescriptionScreen.java 
 * Purpose: Brings the implementation of DescriptionScreen, a class that 
 * serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import br.com.visualize.akan.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;


/**
 * Serves to bring the visibility of the detailed data of a congressman. Serves 
 * to bring the visibility of the detailed data of a congressman. Through this 
 * class you can give the user a description of the expenses of the share 
 * congressman.
 */
public class DescriptionScreen extends Activity {
	private static final String EMPTY_VALUE_QUOTA = "0,00";  // Measured in real.
	
	private static final int HIGHER_LIMIT_WHITE_BAR = 500;   // Measured in real.
	private static final int HIGHER_LIMIT_GRAY_BAR = 1500;   // Measured in real.
	private static final int HIGHER_LIMIT_GREEN_BAR = 3000; 	// Measured in real.
	private static final int HIGHER_LIMIT_YELLOW_BAR = 5000; // Measured in real.
	private static final int LOWER_LIMIT_RED_BAR = 5000; 		// Measured in real.
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.description_screen_activity );
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	/**
	 * Sets the value of each parliamentary quota regarding the Congressman.
	 */
	public void setValuesQuotas() {
		resetValuesQuotas();
		
		/*! Write Instructions Here. */
		
	}
	
	/**
	 * Sets the size of the bar representing the parliamentary quota.
	 * <p>
	 * @param bar Image of the bar should be set.
	 * @param valueQuota Current value of parliamentary quota you  
	 * 						want set the bar.
	 */
	public void setSizeQuotasBar( ImageView bar, double valueQuota ) {
		/*! Write Instructions Here. */
		
	}
	
	/**
	 * Resets the value of parliamentary quotas, making them equal to zero.
	 */
	private void resetValuesQuotas() {
		/*! Write Instructions Here. */
		
	}
}
