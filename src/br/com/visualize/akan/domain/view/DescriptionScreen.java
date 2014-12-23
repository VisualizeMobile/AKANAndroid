/*
 * File: DescriptionScreen.java
 * Purpose: Brings the implementation of DescriptionScreen, a class that 
 * serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import br.com.visualize.akan.domain.controller.CongressmanController;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.enumeration.SubQuota;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Serves to bring the visibility of the detailed data of a congressman. 
 * Through this class you can give the user a description of the expenses 
 * of the share congressman.
 */
public class DescriptionScreen extends Activity {
	private static final int EMPTY_VALUE_QUOTA = 0;				// Measured in Real.
	private static final int HIGHER_LIMIT_WHITE_BAR = 500;	// Measured in Real.
	private static final int HIGHER_LIMIT_GRAY_BAR = 1500;	// Measured in Real.
	private static final int HIGHER_LIMIT_GREEN_BAR = 3000;	// Measured in Real.
	private static final int HIGHER_LIMIT_YELLOW_BAR = 5000; // Measured in Real.
	private static final int LOWER_LIMIT_RED_BAR = 5000;		// Measured in Real.
	
	private static final int BUTTON = 1;
	private static final int TEXT = 2;
	

	Context context;
	
	private QuotaController controllerQuota = null;
	CongressmanController congressmanController;
	static List<Quota> quota;

	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.description_screen_activity );

		controllerQuota = QuotaController.getInstance(getApplicationContext());
congressmanController = CongressmanController.getInstance( getApplicationContext() );

		new Thread( new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated methodinstance stub
				Looper.prepare();
				ResponseHandler<String> responseHandler = HttpConnection
						.getResponseHandler();
				try {
					Log.e(congressmanController.getCongresman().getNameCongressman(), "tentando setar quota");
					controllerQuota.getQuotaById(congressmanController.getCongresman().getIdCongressman(), responseHandler);
					
					
				
				} catch ( Exception e ) {
					//TODO launch error alert
					e.printStackTrace();
					
				} 
				Looper.loop();
				
				
			}
			
			
			
		}  ).start();
		
		//setValuesQuotas(congressmanController.getCongresman().getIdCongressman());
		Log.e(congressmanController.getCongresman().getNameCongressman(),"Cotas do parlamentar ");
	}
	
		
	@Override
	public void onResume() {
		super.onResume();
		
		setDescriptionCongressman();
					
		/* Here is part where call setValuesQuota() method, passing as a 
		 * parameter the id of the congressman. */
			
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	/**
	 * Sets the value of each parliamentary quota regarding the Congressman.
	 * <p>
	 * 
	 * @param idCongressman
	 *           Numeric identifier of congressman that must have deleted the
	 *           quotas.
	 */
	public void setValuesQuotas( int idCongressman ) {
		resetValuesQuotas();
		Log.e("Entrei no setValuesQUota", "Entrei no setValues quota");
		double totalAmountSpent = 0.00;
		
		Iterator<Quota> iteratorQuota = controllerQuota.getQuotasByIdCongressman(
		      idCongressman ).iterator();
		Log.e("Linha 129", "Entrei no setValues quota");

		while( iteratorQuota.hasNext() ) {
			Quota analyzedQuota = iteratorQuota.next();
			SubQuota typeSubQuota = analyzedQuota.getTypeQuota();
			
			double valueQuota = analyzedQuota.getValueQuota();
			
			setSubQuotaAccordingType( typeSubQuota, valueQuota );
			
			totalAmountSpent = totalAmountSpent + valueQuota;
		}
	}
	
	/**
	 * Assigns the correct sub-quota values as well as the size of the graph bar
	 * indicative of spending, according to the type associated with it.
	 * <p>
	 * 
	 * @param typeSubQuota
	 *           The type of sub-quota.
	 * @param valueQuota
	 *           Amount spent associated with sub-quota.
	 */
	private void setSubQuotaAccordingType( SubQuota typeSubQuota,
	      double valueQuota ) {
		
		switch( typeSubQuota ) {
		
			case ACCOMMODATION:
				String accommodation = SubQuota.ACCOMMODATION
				      .getRepresentativeNameQuota();
				
				setBarQuota( accommodation, valueQuota );
				break;
		/*	
			case AIR_FREIGHT:
				String airFreight = SubQuota.AIR_FREIGHT
				      .getRepresentativeNameQuota();
				
				setBarQuota( airFreight, valueQuota );
				break;
			*/
			case ALIMENTATION:
				String alimentation = SubQuota.ALIMENTATION
				      .getRepresentativeNameQuota();
				
				setBarQuota( alimentation, valueQuota );
				break;
			
			case DISCLOSURE_PARLIAMENTARY_ACTIVITY:
				String disclosureParliamentaryActivity = SubQuota
				.DISCLOSURE_PARLIAMENTARY_ACTIVITY.getRepresentativeNameQuota();
				
				setBarQuota( disclosureParliamentaryActivity, valueQuota );
				break;
			
			case FUEL:
				String fuel = SubQuota.FUEL.getRepresentativeNameQuota();
				
				setBarQuota( fuel, valueQuota );
				break;
			
			case ISSUANCE_OF_AIR_TICKETS:
				String inssuanceAirTickets = SubQuota.ISSUANCE_OF_AIR_TICKETS
				      .getRepresentativeNameQuota();
				
				setBarQuota( inssuanceAirTickets, valueQuota );
				break;
			
			case LEASE_OF_VEHICLES:
				/*! Write Instructions Here. */
				break;
			
			case OFFICE:
				String office = SubQuota.OFFICE.getRepresentativeNameQuota();
				
				setBarQuota( office, valueQuota );
				break;
			
			case POSTAL_SERVICES:
				String postalServices = SubQuota.POSTAL_SERVICES
				      .getRepresentativeNameQuota();
				
				setBarQuota( postalServices, valueQuota );
				break;
			
			case SAFETY:
				String safety = SubQuota.SAFETY.getRepresentativeNameQuota();
				
				setBarQuota( safety, valueQuota );
				break;
			
			case SIGNATURE_OF_PUBLICATION:
				/*! Write Instructions Here. */
				break;
			
			case TECHNICAL_WORK_AND_CONSULTING:
				String technicalWorkConsulting = SubQuota.TECHNICAL_WORK_AND_CONSULTING
				      .getRepresentativeNameQuota();
				
				setBarQuota( technicalWorkConsulting, valueQuota );
				break;
			
			case TELEPHONY:
				String telephony = SubQuota.TELEPHONY.getRepresentativeNameQuota();
				
				setBarQuota( telephony, valueQuota );
				break;
			
			default:
				/*! Nothing To Do. */
		}
	}
	
	/**
	 * Sets the size of the bar representing the parliamentary quota.
	 * <p>
	 * 
	 * @param bar
	 *           Image of the bar should be set.
	 * @param valueQuota
	 *           Current value of parliamentary quota you want set the bar.
	 */
	private void setSizeQuotasBar( ImageView bar, String quota,
			double valueQuota ) {
		
		final String WHITE = "white";
		final String GRAY = "gray";
		final String GREEN = "green";
		final String YELLOW = "yellow";
		final String RED = "red";
		
		if( valueQuota <= HIGHER_LIMIT_WHITE_BAR ) {
			changeImageQuotaBar( bar, quota, WHITE );
			
		} else if( valueQuota <= HIGHER_LIMIT_GRAY_BAR ) {
			changeImageQuotaBar( bar, quota, GRAY );
			
		} else if( valueQuota <= HIGHER_LIMIT_GREEN_BAR ) {
			changeImageQuotaBar( bar, quota, GREEN );
			
		} else if( valueQuota <= HIGHER_LIMIT_YELLOW_BAR ) {
			changeImageQuotaBar( bar, quota, YELLOW );
			
		} else if( valueQuota > LOWER_LIMIT_RED_BAR ) {
			changeImageQuotaBar( bar, quota, RED );
			
		} else {
			/*! Nothing To Do. */		
		}
	}
	
	/**
	 * Resets the value of parliamentary quotas, making them equal to zero.
	 */
	private void resetValuesQuotas() {
		String accommodation = SubQuota.ACCOMMODATION
		      .getRepresentativeNameQuota();
		setBarQuota( accommodation, EMPTY_VALUE_QUOTA );
		
		//String airFreight = SubQuota.AIR_FREIGHT.getRepresentativeNameQuota();
		//setBarQuota( airFreight, EMPTY_VALUE_QUOTA );
		
		String alimentation = SubQuota.ALIMENTATION.getRepresentativeNameQuota();
		setBarQuota( alimentation, EMPTY_VALUE_QUOTA );
		
		String disclosureParliamentaryActivity = SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY
		      .getRepresentativeNameQuota();
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
	 * Sets the characteristics of the representation of a dimension in the user
	 * interface by changing the image and its value.
	 * <p>
	 * 
	 * @param typeResource
	 *           Reference to the resource type. Within the application can be a
	 *           button - BTN - or text - TXT.
	 * @param quota
	 *           Name of the quota.
	 * @param valueQuota
	 *           Value to be inserted into the layout of the quota.
	 */
	private void setBarQuota( String quota, double valueQuota ) {
		int idButtonResource = getResourceID( BUTTON, quota );
		int idTextResource = getResourceID( TEXT, quota );
		
		DecimalFormat valueQuotaFormat = new DecimalFormat( "#,###.00" );
		
		ImageView barQuota = (ImageView) findViewById( idButtonResource );
		TextView txtValue = (TextView) findViewById( idTextResource );
		
		txtValue.setText( valueQuotaFormat.format( valueQuota ) );
		setSizeQuotasBar( barQuota, quota, valueQuota );
	}
	
	/**
	 * Return the ID of a resource by a string, that representing a resource.
	 * <p>
	 * @param typeResource
	 *           Reference to the resource type. Within the application can be a
	 *           button - BTN - or text - TXT.
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase
	 *           letters and spaced names with underscore.
	 *<p>
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
		
		int idResource = getResources().getIdentifier( resource, "id",
		      getPackageName() );
		
		return idResource;
	}
	
	/**
	 * Change the image in the layout associated with the referenced quota. Uses
	 * color, chosen from the predefined colors, passed as parameter.
	 * <p>
	 * @param bar
	 *           Image of the bar should be set.
	 * @param quota
	 *           Name of the quota.
	 * @param color
	 *           Name of the predefined color.
	 */
	private void changeImageQuotaBar( ImageView bar, String quota, String color ) {
		
		int idImageResource = getImageResourceID( quota, color );
		
		bar.setImageResource( idImageResource );
	}
	
	/**
	 * Return the ID of a image resource by a string, that representing a resource.
	 * <p>
	 * @param typeResource
	 *           Reference to the resource type. Within the application can be a
	 *           button - BTN - or text - TXT.
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase
	 *           letters and spaced names with underscore.
	 *           <p>
	 * @return Resource identifier, an ID.
	 */
	private int getImageResourceID( String quota, String color ) {
		String resource = "quota_" + quota + "_" + color;
		
		int idResource = getResources().getIdentifier( resource, "drawable",
		      getPackageName() );
		
		return idResource;
	}
	
	/**
	 * Sets the congressman-related data to be displayed in the description.
	 */
	private void setDescriptionCongressman() {
		Congressman congressman;

		congressman = congressmanController.getCongresman();
		String idCongressman = Integer.toString(congressman.getIdCongressman());
		String photoCongressmanUrl = "http://www.camara.gov.br/internet/deputado/bandep/";
		
		TextView textViewCongressmanName = ( TextView )findViewById( R.id.congressman_txt_nome );
		textViewCongressmanName.setText(congressman.getNameCongressman());
		
		TextView textViewCongressmanParitdo = (TextView)findViewById(R.id.congressman_txt_partido);
		textViewCongressmanParitdo.setText(congressman.getPartyCongressman());	
		
		TextView textViewRankingPosition = (TextView)findViewById(R.id.description_ranking_position);
		textViewRankingPosition.setText(Integer.toString(congressman.getRankingCongressman()));
		
		ImageView congressmanImage = (ImageView)findViewById(R.id.description_ranking_imagem_parlamentar);
		Picasso.with(getApplicationContext()).load(photoCongressmanUrl+idCongressman+".jpg").error(R.drawable.default_photo).into(congressmanImage);
		

		

	}
}	
