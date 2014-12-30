/*
 * File: DescriptionScreen.java Purpose: Brings the implementation of
 * DescriptionScreen, a class that serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
		
		resetDetailsQuotas();
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate( R.menu.menu, menu );
		
		return super.onCreateOptionsMenu( menu );
	}
	
	/**
	 * Sets the congressman-related data to be displayed in the description.
	 */
	private void setDescriptionCongressman() {
		Congressman congressman;
		
		congressman = congressmanController.getCongresman();
		String idCongressman = Integer.toString( congressman.getIdCongressman() );
		String photoCongressmanUrl = "http://www.camara.gov.br/internet/deputado/bandep/";
		
		TextView textViewCongressmanName = (TextView) findViewById( R.id.congressman_txt_nome );
		
		textViewCongressmanName.setText( congressman.getNameCongressman() );
		
		TextView textViewCongressmanParitdo = (TextView) findViewById( R.id.congressman_txt_partido );
		
		textViewCongressmanParitdo.setText( congressman.getPartyCongressman() );
		
		TextView textViewRankingPosition = (TextView) findViewById( R.id.description_ranking_position );
		
		textViewRankingPosition.setText( Integer.toString( congressman
		      .getRankingCongressman() ) );
		
		ImageView congressmanImage = (ImageView) findViewById( R.id.description_ranking_imagem_parlamentar );
		
		Picasso.with( getApplicationContext() )
		      .load( photoCongressmanUrl + idCongressman + ".jpg" )
		      .error( R.drawable.default_photo ).into( congressmanImage );
	}
	
	/**
	 * Makes a request to the server requesting the data relating to shares
	 * related to the specified congressman.
	 */
	private void requestQuotas() {
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
				
				setDetailsQuota( accommodation, valueQuota );
				break;
			
			case AIR_FREIGHT:
				String airFreight = SubQuota.AIR_FREIGHT
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( airFreight, valueQuota );
				break;
			
			case ALIMENTATION:
				String alimentation = SubQuota.ALIMENTATION
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( alimentation, valueQuota );
				break;
			
			case DISCLOSURE_PARLIAMENTARY_ACTIVITY:
				String disclosureParliamentaryActivity = SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( disclosureParliamentaryActivity, valueQuota );
				break;
			
			case FUEL:
				String fuel = SubQuota.FUEL.getRepresentativeNameQuota();
				
				setDetailsQuota( fuel, valueQuota );
				break;
			
			case ISSUANCE_OF_AIR_TICKETS:
				String inssuanceAirTickets = SubQuota.ISSUANCE_OF_AIR_TICKETS
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( inssuanceAirTickets, valueQuota );
				break;
			
			case LEASE_OF_VEHICLES:
				/* ! Write Instructions Here. */
				break;
			
			case OFFICE:
				String office = SubQuota.OFFICE.getRepresentativeNameQuota();
				
				setDetailsQuota( office, valueQuota );
				break;
			
			case POSTAL_SERVICES:
				String postalServices = SubQuota.POSTAL_SERVICES
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( postalServices, valueQuota );
				break;
			
			case SAFETY:
				String safety = SubQuota.SAFETY.getRepresentativeNameQuota();
				
				setDetailsQuota( safety, valueQuota );
				break;
			
			case SIGNATURE_OF_PUBLICATION:
				/* ! Write Instructions Here. */
				break;
			
			case TECHNICAL_WORK_AND_CONSULTING:
				String technicalWorkConsulting = SubQuota.TECHNICAL_WORK_AND_CONSULTING
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( technicalWorkConsulting, valueQuota );
				break;
			
			case TELEPHONY:
				String telephony = SubQuota.TELEPHONY.getRepresentativeNameQuota();
				
				setDetailsQuota( telephony, valueQuota );
				break;
			
			default:
				/* ! Nothing To Do. */
		}
	}
	
	/**
	 * Calculates the level of the corresponding spending bar to the amount
	 * actually spent in relation to the average of congressmen.
	 * 
	 * @return level of bar corresponding to the amount spent.
	 */
	private double exponentialProbability() {
		Random generator = new Random();
		
		double result = generator.nextDouble();
		
		return result;
	}
	
	/**
	 * Sets the information of a quota that will be presented, either graphically
	 * or numerically.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 * @param valueQuota
	 *           Amount spent associated with sub-quota
	 */
	private void setDetailsQuota( String quota, double valueQuota ) {
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
	
	/**
	 * Sets the picture that represents on the screen a quota.
	 * 
	 * @param image
	 *           ImagemView representing the quota.
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void setImageQuota( ImageView image, String quota ) {
		// changeColorResource( image, exponentialProbability() );
	}
	
	/**
	 * Sets the picture that represents on the screen the spending level of a
	 * quota.
	 * 
	 * @param bar
	 *           ImagemView graphically representing the spending on a quota.
	 * @param valueQuota
	 *           Amount spent associated with sub-quota.
	 */
	private void setBarQuota( ImageView bar, double valueQuota ) {
		changeColorResource( bar, exponentialProbability() );
	}
	
	/**
	 * Sets the text that represents on the screen the spending level of a quota.
	 * 
	 * @param text
	 *           TextView numerically representing the spending on a quota.
	 * @param valueQuota
	 *           Amount spent associated with sub-quota.
	 */
	private void setTextQuota( TextView text, double valueQuota ) {
		DecimalFormat valueQuotaFormat = new DecimalFormat( "#,###.00" );
		
		text.setText( valueQuotaFormat.format( valueQuota ) );
		
		changeColorResource( text, exponentialProbability() );
	}
	
	/**
	 * Resets the value of parliamentary quotas, making them equal to zero.
	 */
	private void resetDetailsQuotas() {
		String accommodation = SubQuota.ACCOMMODATION
		      .getRepresentativeNameQuota();
		resetImageQuota( accommodation );
		resetBarQuota( accommodation );
		resetTextQuota( accommodation );
		
		String airFreight = SubQuota.AIR_FREIGHT.getRepresentativeNameQuota();
		resetImageQuota( airFreight );
		resetBarQuota( airFreight );
		resetTextQuota( airFreight );
		
		String alimentation = SubQuota.ALIMENTATION.getRepresentativeNameQuota();
		resetImageQuota( alimentation );
		resetBarQuota( alimentation );
		resetTextQuota( alimentation );
		
		String disclosureParliamentaryActivity = SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY
		      .getRepresentativeNameQuota();
		resetImageQuota( disclosureParliamentaryActivity );
		resetBarQuota( disclosureParliamentaryActivity );
		resetTextQuota( disclosureParliamentaryActivity );
		
		String fuel = SubQuota.FUEL.getRepresentativeNameQuota();
		resetImageQuota( fuel );
		resetBarQuota( fuel );
		resetTextQuota( fuel );
		
		String inssuanceAirTickets = SubQuota.ISSUANCE_OF_AIR_TICKETS
		      .getRepresentativeNameQuota();
		resetImageQuota( inssuanceAirTickets );
		resetBarQuota( inssuanceAirTickets );
		resetTextQuota( inssuanceAirTickets );
		
		String office = SubQuota.OFFICE.getRepresentativeNameQuota();
		resetImageQuota( office );
		resetBarQuota( office );
		resetTextQuota( office );
		
		String postalServices = SubQuota.POSTAL_SERVICES
		      .getRepresentativeNameQuota();
		resetImageQuota( postalServices );
		resetBarQuota( postalServices );
		resetTextQuota( postalServices );
		
		String safety = SubQuota.SAFETY.getRepresentativeNameQuota();
		resetImageQuota( safety );
		resetBarQuota( safety );
		resetTextQuota( safety );
		
		String technicalWorkConsulting = SubQuota.TECHNICAL_WORK_AND_CONSULTING
		      .getRepresentativeNameQuota();
		resetImageQuota( technicalWorkConsulting );
		resetBarQuota( technicalWorkConsulting );
		resetTextQuota( technicalWorkConsulting );
		
		String telephony = SubQuota.TELEPHONY.getRepresentativeNameQuota();
		resetImageQuota( telephony );
		resetBarQuota( telephony );
		resetTextQuota( telephony );
	}
	
	/**
	 * Resets the color of the image represents a quota.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void resetImageQuota( String quota ) {
		int idButtonResource = getResourceID( BUTTON, quota );
		
		ImageView imageQuota = (ImageView) findViewById( idButtonResource );
		
		Drawable background = imageQuota.getBackground();
		background.clearColorFilter();
	}
	
	/**
	 * Resets the level and color of the bar related of quota.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void resetBarQuota( String quota ) {
		int idBarResource = getResourceID( BAR, quota );
		
		ImageView barQuota = (ImageView) findViewById( idBarResource );
		
		Drawable background = barQuota.getBackground();
		background.clearColorFilter();
	}
	
	/**
	 * Resets the text that represents numerically the spending of quota.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void resetTextQuota( String quota ) {
		int idTextResource = getResourceID( TEXT, quota );
		
		TextView txtQuota = (TextView) findViewById( idTextResource );
		
		setTextQuota( txtQuota, EMPTY_VALUE_QUOTA );
	}
	
	/**
	 * Change the color of a graphic screen feature.
	 * 
	 * @param view
	 *           The interface feature, of the type View, which must have changed
	 *           color.
	 * @param percent
	 *           Value representing the share of spending level.
	 */
	private void changeColorResource( View view, double percent ) {
		
		if( view instanceof TextView ) {
			
			changeColorTextResource( (TextView) view, percent );
			
		} else if( view instanceof ImageView ) {
			changeColorImageResource( (ImageView) view, percent );
			
		} else {
			/* ! Nothing To Do. */
		}
	}
	
	/**
	 * Change the color of a TextView.
	 * 
	 * @param view
	 *           The interface feature, of the type TextView, which must have
	 *           changed color.
	 * @param percent
	 *           Value representing the share of spending level.
	 */
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
	
	/**
	 * Change the color of a ImageView.
	 * 
	 * @param view
	 *           The interface feature, of the type ImageView, which must have
	 *           changed color.
	 * @param percent
	 *           Value representing the share of spending level.
	 */
	private void changeColorImageResource( ImageView image, double percent ) {
		Drawable background = image.getBackground();
		
		if( percent < 0.05 ) {
			background.setColorFilter( Color.parseColor( WHITE_HEX ),
			      Mode.MULTIPLY );
			
		} else if( 0.05 < percent && percent <= 0.25 ) {
			background
			      .setColorFilter( Color.parseColor( GRAY_HEX ), Mode.MULTIPLY );
			
		} else if( 0.25 < percent && percent <= 0.5 ) {
			background.setColorFilter( Color.parseColor( GREEN_HEX ),
			      Mode.MULTIPLY );
			
		} else if( 0.5 < percent && percent <= 0.75 ) {
			background.setColorFilter( Color.parseColor( YELLOW_HEX ),
			      Mode.MULTIPLY );
			
		} else if( 0.75 < percent && percent <= 1.0 ) {
			background.setColorFilter( Color.parseColor( RED_HEX ), Mode.MULTIPLY );
			
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
}
