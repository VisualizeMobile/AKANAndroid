/*
 * File: DescriptionScreen.java Purpose: Brings the implementation of
 * DescriptionScreen, a class that serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.enumeration.SubQuota;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;

import com.squareup.picasso.Picasso;


/**
 * Serves to bring the visibility of the detailed data of a congressman. Through
 * this class you can give the user a description of the expenses of the share
 * congressman.
 */
public class DescriptionScreen extends Activity {
	private static final int EMPTY_VALUE_QUOTA = 0; // Measured in Real.
	
	private final String GRAY_HEX = "#536571";
	private final String YELLOW_HEX = "#F3D171";
	private final String GREEN_HEX = "#00A69A";
	private final String WHITE_HEX = "#F1F1F2";
	private final String RED_HEX = "#F16068";
	
	private static final int BUTTON = 1;
	private static final int TEXT = 2;
	private static final int BAR = 3;
	
	Context context;
	
	private QuotaController controllerQuota = null;
	CongressmanController congressmanController;
	static List<Quota> quota;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.description_screen_activity );
		
		controllerQuota = QuotaController.getInstance( getApplicationContext() );
		congressmanController = CongressmanController
		      .getInstance( getApplicationContext() );
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setDescriptionCongressman();
		requestQuotas();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if( !congressmanController.getCongresman().isStatusCogressman() ) {
			controllerQuota.deleteQuotasFromCongressman( congressmanController
			      .getCongresman().getIdCongressman() );
		} else {
			/* ! Nothing To Do. */
		}
		
	}
	
	/**
	 * Sets the congressman-related data to be displayed in the description.
	 */
	private void setDescriptionCongressman() {
		Congressman congressman;
		
		congressman = congressmanController.getCongresman();
		String idCongressman = Integer.toString( congressman.getIdCongressman() );
		String photoCongressmanUrl = "http://www.camara.gov.br/internet/deputado/bandep/";
		
		TextView textViewCongressmanName = (TextView) findViewById( 
				R.id.congressman_txt_nome );
		
		textViewCongressmanName.setText( congressman.getNameCongressman() );
		
		TextView textViewCongressmanParitdo = (TextView) findViewById(
				R.id.congressman_txt_partido );
		
		textViewCongressmanParitdo.setText( congressman.getPartyCongressman() );
		
		TextView textViewRankingPosition = (TextView) findViewById( 
				R.id.description_ranking_position );
		
		textViewRankingPosition.setText( Integer.toString( congressman
		      .getRankingCongressman() ) );
		
		ImageView congressmanImage = (ImageView) findViewById( 
				R.id.description_ranking_imagem_parlamentar );
		
		Picasso.with( getApplicationContext() )
		      .load( photoCongressmanUrl + idCongressman + ".jpg" )
		      .error( R.drawable.default_photo ).into( congressmanImage );
	}
	
	public void requestQuotas() {
		final ProgressDialog progress = new ProgressDialog( this );
		
		progress.setMessage( "Carregando dados..." );
		progress.show();
		
		new Thread() {
			
			public void run() {
				
				Looper.prepare();
				try {
					ResponseHandler<String> responseHandler = HttpConnection
					      .getResponseHandler();
					
					controllerQuota.getQuotaById( congressmanController
							.getCongresman().getIdCongressman(), responseHandler );
					
				} catch( Exception e ) {
					// TODO: handle exception
				}
				
				runOnUiThread( new Runnable() {
					
					@Override
					public void run() {
						progress.setMessage( "Dados carregados" );
						
						setValuesQuotas( congressmanController.getCongresman()
						      .getIdCongressman() );
						
						progress.dismiss();
						Looper.loop();
					}
				} );	
			}
		}.start();
	}
	
	/**
	 * Sets the value of each parliamentary quota regarding the Congressman.
	 * 
	 * @param idCongressman
	 *           Numeric identifier of congressman that must have deleted the
	 *           quotas.
	 */
	public void setValuesQuotas( int idCongressman ) {
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get( Calendar.YEAR );
		int month = calendar.get( Calendar.MONTH ) + 1;
		
		resetValuesQuotas();
		
		double totalAmountSpent = 0.00;
		
		Iterator<Quota> iteratorQuota = controllerQuota.getQuotaByDate( month,
		      year ).iterator();
		
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
			
			case AIR_FREIGHT:
				String airFreight = SubQuota.AIR_FREIGHT
				      .getRepresentativeNameQuota();
				
				setBarQuota( airFreight, valueQuota );
				break;
			
			case ALIMENTATION:
				String alimentation = SubQuota.ALIMENTATION
				      .getRepresentativeNameQuota();
				
				setBarQuota( alimentation, valueQuota );
				break;
			
			case DISCLOSURE_PARLIAMENTARY_ACTIVITY:
				String disclosureParliamentaryActivity = SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY
				      .getRepresentativeNameQuota();
				
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
				/* ! Write Instructions Here. */
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
				/* ! Write Instructions Here. */
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
				/* ! Nothing To Do. */
		}
	}

	private void setBarQuota( String quota, double valueQuota ) {
		int idButtonResource = getResourceID( BUTTON, quota );
		int idTextResource = getResourceID( TEXT, quota );
		int idBarResource = getResourceID( BAR, quota );
		
		ImageView imageQuota = (ImageView) findViewById( idButtonResource );
		ImageView barQuota = (ImageView) findViewById( idBarResource );
		TextView txtQuota = (TextView) findViewById( idTextResource );
		
		setImageQuota( imageQuota, quota );
		setBarQuota( barQuota, valueQuota );
		setTextQuota( txtQuota, valueQuota );
	}
	
	private void setImageQuota( ImageView image, String quota ) {
		changeColorResource( image, 0.43 );
	}
	
	private void setBarQuota( ImageView bar, double valueQuota ) {
		changeColorResource( bar, 0.43 );
	}
	
	private void setTextQuota( TextView txt, double valueQuota ) {
		DecimalFormat valueQuotaFormat = new DecimalFormat( "#,###.00" );
		
		txt.setText( valueQuotaFormat.format( valueQuota ) );
		
		changeColorResource( txt, 0.43 );
	}
	
	/**
	 * Resets the value of parliamentary quotas, making them equal to zero.
	 */
	private void resetValuesQuotas() {
		String accommodation = SubQuota.ACCOMMODATION.getRepresentativeNameQuota();
		setBarQuota( accommodation, EMPTY_VALUE_QUOTA );
		
		String airFreight =
		SubQuota.AIR_FREIGHT.getRepresentativeNameQuota();
		setBarQuota( airFreight, EMPTY_VALUE_QUOTA );
		
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
	
	private void changeColorResource( View view, double percent ) {
		
		if( view instanceof TextView ) {
			
			changeColorTextResource( (TextView) view, percent );
			
		} else if( view instanceof ImageView ) {
			changeColorImageResource( (ImageView) view, percent );
			
		} else {
			/* ! Nothing To Do. */
		}
	}
	
private void changeColorTextResource( TextView text, double percent ) {
		
		if( percent < 0.05 ) {
			text.setTextColor( Color.parseColor( WHITE_HEX ) );
			
		} else if( 0.05 < percent && percent <= 0.25 ) {
			text.setTextColor( Color.parseColor( GRAY_HEX ) );
			
		} else if( 0.25 < percent && percent <= 0.5 ) {
			text.setTextColor( Color.parseColor( GREEN_HEX ) );
			
		} else if( 0.5 < percent && percent <= 0.75 ) {
			text.setTextColor( Color.parseColor( YELLOW_HEX ) );
			
		} else if( 0.75 < percent && percent <= 1.0 ) {
			text.setTextColor( Color.parseColor( RED_HEX ) );
			
		} else {
			/* ! Nothing To Do. */
		}
	}
	
	private void changeColorImageResource( ImageView image, double percent ) {
		Drawable background = image.getBackground();
		
		if( percent < 0.05 ) {
			background.setColorFilter( Color.parseColor( WHITE_HEX ), Mode.MULTIPLY );;
			
		} else if( 0.05 < percent && percent <= 0.25 ) {
			background.setColorFilter( Color.parseColor( GRAY_HEX ), Mode.MULTIPLY );;
			
		} else if( 0.25 < percent && percent <= 0.5 ) {
			background.setColorFilter( Color.parseColor( GREEN_HEX ), Mode.MULTIPLY );;
			
		} else if( 0.5 < percent && percent <= 0.75 ) {
			background.setColorFilter( Color.parseColor( YELLOW_HEX ), Mode.MULTIPLY );;
			
		} else if( 0.75 < percent && percent <= 1.0 ) {
			background.setColorFilter( Color.parseColor( RED_HEX ), Mode.MULTIPLY );;
			
		} else {
			/* ! Nothing To Do. */
		}
	}
	
	/**
	 * Return the ID of a resource by a string, that representing a resource.
	 * 
	 * @param typeResource
	 *           Reference to the resource type. Within the application can be a
	 *           button - BTN - or text - TXT.
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 * 
	 * @return Resource identifier, an ID.
	 */
	private int getResourceID( int typeResource, String quota ) {
		final String RESOURCE_BTN = "btn_quota_" + quota;
		final String RESOURCE_TXT = "txt_quota_" + quota;
		final String RESOURCE_BAR = "bar_quota_" + quota;
		
		String resource = "";
		
		switch( typeResource ) {
			case BUTTON:
				resource = RESOURCE_BTN;
				break;
			
			case TEXT:
				resource = RESOURCE_TXT;
				break;
				
			case BAR:
				resource = RESOURCE_BAR;
				break;
			
			default:
				/* ! Nothing To Do. */
		}
		
		int idResource = getResources().getIdentifier( resource, "id",
		      getPackageName() );
		
		return idResource;
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate( R.menu.menu, menu );
		
		return super.onCreateOptionsMenu( menu );
	}
}
