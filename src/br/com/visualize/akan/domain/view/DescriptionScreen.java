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
	private static final double EMPTY_VALUE_QUOTA = 0;  // Measured in Real.
	
	private static final int HIGHER_LIMIT_WHITE_BAR = 500;   // Measured in Real.
	private static final int HIGHER_LIMIT_GRAY_BAR = 1500;   // Measured in Real.
	private static final int HIGHER_LIMIT_GREEN_BAR = 3000; 	// Measured in Real.
	private static final int HIGHER_LIMIT_YELLOW_BAR = 5000; // Measured in Real.
	private static final int LOWER_LIMIT_RED_BAR = 5000; 		// Measured in Real.
	
	private static final int BUTTON = 1;
	private static final int TEXT = 2;
	
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
	private void setSizeQuotasBar( ImageView bar, double valueQuota ) {
		if( valueQuota <= HIGHER_LIMIT_WHITE_BAR ) {
			/*! Write Instructions Here. */
			
		} else if( valueQuota <= HIGHER_LIMIT_GRAY_BAR ) {
			/*! Write Instructions Here. */
			
		} else if( valueQuota <= HIGHER_LIMIT_GREEN_BAR ) {
			/*! Write Instructions Here. */
			
		} else if( valueQuota <= HIGHER_LIMIT_YELLOW_BAR ) {
			/*! Write Instructions Here. */
			
		} else if( valueQuota <= LOWER_LIMIT_RED_BAR ) {
			/*! Write Instructions Here. */
			
		} else {
			/*! Write Instructions Here. */
			
		}		
	}
	
	/**
	 * Resets the value of parliamentary quotas, making them equal to zero.
	 */
	private void resetValuesQuotas() {
		String accommodation = SubQuota.ACCOMMODATION.getRepresentativeNameQuota();
		setBarQuota( accommodation, EMPTY_VALUE_QUOTA );
		
		String airFreight = SubQuota.AIR_FREIGHT.getRepresentativeNameQuota();
		setBarQuota( airFreight, EMPTY_VALUE_QUOTA );

		String alimentation = SubQuota.ALIMENTATION.getRepresentativeNameQuota();
		setBarQuota( alimentation, EMPTY_VALUE_QUOTA );

		String disclosureParliamentaryActivity = SubQuota
				.DISCLOSURE_PARLIAMENTARY_ACTIVITY.getRepresentativeNameQuota();
		setBarQuota( disclosureParliamentaryActivity, EMPTY_VALUE_QUOTA );

		String fuel = SubQuota.FUEL.getRepresentativeNameQuota();
		setBarQuota( fuel, EMPTY_VALUE_QUOTA );
		
		String inssuanceAirTickets = SubQuota.ISSUANCE_OF_AIR_TICKETS
				.getRepresentativeNameQuota();
		setBarQuota( inssuanceAirTickets, EMPTY_VALUE_QUOTA );

		String office = SubQuota.OFFICE.getRepresentativeNameQuota();
		setBarQuota( office, EMPTY_VALUE_QUOTA );
			
		String postalServices = SubQuota.POSTAL_SERVICES
				.getRepresentativeNameQuota();
		setBarQuota( postalServices, EMPTY_VALUE_QUOTA );

		String safety = SubQuota.SAFETY.getRepresentativeNameQuota();
		setBarQuota( safety, EMPTY_VALUE_QUOTA );
			
		String technicalWorkConsulting = SubQuota.TECHNICAL_WORK_AND_CONSULTING
				.getRepresentativeNameQuota();
		setBarQuota( technicalWorkConsulting, EMPTY_VALUE_QUOTA );
		
		String telephony = SubQuota.TELEPHONY.getRepresentativeNameQuota();
		setBarQuota( telephony, EMPTY_VALUE_QUOTA );
	}
	
	/**
	 * Sets the characteristics of the representation of a dimension in 
	 * the user interface by changing the image and its value.
	 * <p>
	 * @param typeResource Reference to the resource type. Within the 
	 * 		 application can be a button - BTN - or text - TXT.
	 * @param quota Name of the quota.
	 * @param valueQuota Value to be inserted into the layout of the quota.
	 */
	private void setBarQuota( String quota, double valueQuota ) {
		
		int idButtonResource = getResourceID( BUTTON, quota );
		int idTextResource = getResourceID( TEXT, quota );
		
		DecimalFormat valueQuotaFormat = new DecimalFormat("#.##,00");
		
		ImageView barQuota = (ImageView)findViewById( idButtonResource );
		TextView txtValue = (TextView)findViewById( idTextResource );
		
		txtValue.setText( valueQuotaFormat.format( valueQuota ) );
		setSizeQuotasBar( barQuota, valueQuota );
	}
	
	/**
	 * Return the ID of a resource by a string, that representing a resource.
	 * <p>
	 * @param typeResource Reference to the resource type. Within the 
	 * 		 application can be a button - BTN - or text - TXT.
	 * @param quota Name of the dimension. Should be given only with 
	 * 		 lowercase letters and spaced names with underscore.
	 * <p>
	 * @return Resource identifier, an ID.
	 */
	private int getResourceID( int typeResource, String quota ) {
		final String RESOURCE_BTN = "btn_quota_" + quota;
		final String RESOURCE_TXT = "txt_quota_" + quota;
		
		String resource = "";
		
		switch( typeResource ) {
			case BUTTON:
				resource = RESOURCE_BTN;
				break;
				
			case TEXT:
				resource = RESOURCE_TXT;
				break;
				
			default:
				/*! Nothing To Do. */
		}
		
		int idResource = getResources().getIdentifier( resource, 
				"id", getPackageName() );
		
		return idResource;
	}
}
