/*
 * File: DescriptionScreen.java 
 * Purpose: Brings the implementation of DescriptionScreen, a class that 
 * serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import java.text.DecimalFormat;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.enumeration.SubQuota;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to bring the visibility of the detailed data of a congressman. Serves 
 * to bring the visibility of the detailed data of a congressman. Through this 
 * class you can give the user a description of the expenses of the share 
 * congressman.
 */
public class DescriptionScreen extends Activity {
	private static final String EMPTY_VALUE_QUOTA = "0,00";  // Measured in Real.
	
	private static final int HIGHER_LIMIT_WHITE_BAR = 500;   // Measured in Real.
	private static final int HIGHER_LIMIT_GRAY_BAR = 1500;   // Measured in Real.
	private static final int HIGHER_LIMIT_GREEN_BAR = 3000; 	// Measured in Real.
	private static final int HIGHER_LIMIT_YELLOW_BAR = 5000; // Measured in Real.
	private static final int LOWER_LIMIT_RED_BAR = 5000; 		// Measured in Real.
	
	private QuotaController controllerQuota = null;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.description_screen_activity );
		controllerQuota = QuotaController.getInstance();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	/*
	 * TODO: This method, setValuesQuotas, must be break in two or three 
	 * 	   others methods.
	 */
	
	/**
	 * Sets the value of each parliamentary quota regarding the Congressman.
	 * <p>
	 * @param idCongressman Numeric identifier of congressman that must have 
	 * 		 					deleted the quotas.
	 */
	public void setValuesQuotas( int idCongressman ) {
		resetValuesQuotas();
		
		DecimalFormat valueQuotaFormat = new DecimalFormat("#.##,00");
		
		double totalAmountSpent = 0.00;
		
		Iterator<Quota> iteratorQuota = controllerQuota
				.getQuotasByIdCongressman( idCongressman ).iterator();
		
		while( iteratorQuota.hasNext() ) {
			Quota analyzedQuota = iteratorQuota.next();
			SubQuota typeSubQuota = analyzedQuota.getTypeQuota();
			
			double valueQuota = analyzedQuota.getValueQuota();
			
			switch( typeSubQuota ) {
				
				case ACCOMMODATION:
					ImageView barAccommodation = 
							(ImageView)findViewById( R.id.btn_quota_accommodation );
					
					TextView txtAccommodation = 
							(TextView)findViewById( R.id.txt_quota_accommodation );
					
					txtAccommodation
							.setText( valueQuotaFormat.format( valueQuota ) );
					
					setSizeQuotasBar( barAccommodation, valueQuota );
					break;
					
				case AIR_FREIGHT:
					ImageView barAirFreight = 
							(ImageView)findViewById( R.id.btn_quota_air_freight );
			
					TextView txtAirFreight = 
							(TextView)findViewById( R.id.txt_quota_air_freight );
			
					txtAirFreight
							.setText( valueQuotaFormat.format( valueQuota ) );
			
					setSizeQuotasBar( barAirFreight, valueQuota );
					break;
					
				case ALIMENTATION:
					ImageView barAlimentation = 
							(ImageView)findViewById( R.id.btn_quota_alimentation );
	
					TextView txtAlimentation = 
							(TextView)findViewById( R.id.txt_quota_alimentation );
	
					txtAlimentation
							.setText( valueQuotaFormat.format( valueQuota ) );
	
					setSizeQuotasBar( barAlimentation, valueQuota );
					break;
					
				case DISCLOSURE_PARLIAMENTARY_ACTIVITY:
					ImageView barDisclosureParliamentaryActivity = 
							(ImageView)findViewById( R.id.btn_quota_disclosure_parliamentary_activity );

					TextView txtDisclosureParliamentaryActivity = 
							(TextView)findViewById( R.id.txt_quota_disclosure_parliamentary_activity );

					txtDisclosureParliamentaryActivity
							.setText( valueQuotaFormat.format( valueQuota ) );

					setSizeQuotasBar( barDisclosureParliamentaryActivity, valueQuota );
					break;
					
				case FUEL:
					ImageView barFuel = 
							(ImageView)findViewById( R.id.btn_quota_fuel );

					TextView txtFuel = 
							(TextView)findViewById( R.id.txt_quota_fuel );

					txtFuel
							.setText( valueQuotaFormat.format( valueQuota ) );

					setSizeQuotasBar( barFuel, valueQuota );
					break;
					
				case ISSUANCE_OF_AIR_TICKETS:
					ImageView barIssuanceAirTickets = 
							(ImageView)findViewById( R.id.btn_quota_issuance_of_air_tickets );

					TextView txtInssuanceAirTickets = 
							(TextView)findViewById( R.id.txt_quota_issuance_of_air_tickets );

					txtInssuanceAirTickets
							.setText( valueQuotaFormat.format( valueQuota ) );

					setSizeQuotasBar( barIssuanceAirTickets, valueQuota );
					break;
					
				case LEASE_OF_VEHICLES:
					/*! Write Instructions Here. */
					break;
					
				case OFFICE:
					ImageView barOffice = 
							(ImageView)findViewById( R.id.btn_quota_office );

					TextView txtOffice = 
							(TextView)findViewById( R.id.txt_quota_office );

					txtOffice
							.setText( valueQuotaFormat.format( valueQuota ) );

					setSizeQuotasBar( barOffice, valueQuota );
					break;
					
				case POSTAL_SERVICES:
					ImageView barPostalServices = 
							(ImageView)findViewById( R.id.btn_quota_postal_services );

					TextView txtPostalServices = 
							(TextView)findViewById( R.id.txt_quota_postal_services );

					txtPostalServices
							.setText( valueQuotaFormat.format( valueQuota ) );

					setSizeQuotasBar( barPostalServices, valueQuota );
					break;
					
				case SAFETY:
					ImageView barSafety = 
						(ImageView)findViewById( R.id.btn_quota_safety );

					TextView txtSafety = 
							(TextView)findViewById( R.id.txt_quota_safety );
		
					txtSafety
							.setText( valueQuotaFormat.format( valueQuota ) );
		
					setSizeQuotasBar( barSafety, valueQuota );
					break;
					
				case SIGNATURE_OF_PUBLICATION:
					/*! Write Instructions Here. */
					break;
					
				case TECHNICAL_WORK_AND_CONSULTING:
						ImageView barTechnicalWorkConsulting = 
							(ImageView)findViewById( R.id.btn_quota_technical_work_and_consulting );
	
					TextView txtTechnicalWorkConsulting = 
							(TextView)findViewById( R.id.txt_quota_technical_work_and_consulting );
		
					txtTechnicalWorkConsulting
							.setText( valueQuotaFormat.format( valueQuota ) );
		
					setSizeQuotasBar( barTechnicalWorkConsulting, valueQuota );
					break;
					
				case TELEPHONY:
					ImageView barTelephony = 
					(ImageView)findViewById( R.id.btn_quota_telephony );

					TextView txtTelephony = 
							(TextView)findViewById( R.id.txt_quota_telephony );
		
					txtTelephony
							.setText( valueQuotaFormat.format( valueQuota ) );
		
					setSizeQuotasBar( barTelephony, valueQuota );
					break;
					
				default:
			}
			
			totalAmountSpent = totalAmountSpent + valueQuota;
		}
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
