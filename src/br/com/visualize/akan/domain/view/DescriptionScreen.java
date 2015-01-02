/*
 * File: DescriptionScreen.java 
 * Purpose: Brings the implementation of DescriptionScreen, a class that 
 * serves to interface with the user.
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
	private static final int BACKGROUND = 4;
	
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
		
		resetSubQuotaAccordingType();
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
		
		TextView textViewCongressmanName = (TextView) findViewById( 
				R.id.description_txt_congressman_name );
		
		textViewCongressmanName.setText( congressman.getNameCongressman() );
		
		TextView textViewCongressmanParitdo = (TextView) findViewById( 
				R.id.description_txt_congressman_party );
		
		textViewCongressmanParitdo.setText( congressman.getPartyCongressman() );
		
		TextView textViewRankingPosition = (TextView) findViewById( 
				R.id.description_txt_ranking_position );
		
		textViewRankingPosition.setText( Integer.toString( congressman
		      .getRankingCongressman() ) );
		
		ImageView congressmanImage = (ImageView) findViewById( 
				R.id.description_congressman_photo );
		
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
			
			setSubQuotaAccordingType( typeSubQuota, analyzedQuota );
			
			totalAmountSpent = totalAmountSpent + analyzedQuota.getValueQuota();
		}
	}
	
	/**
	 * Assigns the correct sub-quota values as well as the size of the graph bar
	 * indicative of spending, according to the type associated with it.
	 * 
	 * @param typeSubQuota
	 *           The type of sub-quota.
	 * @param quota
	 *           Amount spent associated with sub-quota.
	 */
	private void setSubQuotaAccordingType( SubQuota typeSubQuota,
	      Quota quota ) {
		
		switch( typeSubQuota ) {
		
			case ACCOMMODATION:
				String accommodation = SubQuota.ACCOMMODATION
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( accommodation, quota );
				break;
			
			case AIR_FREIGHT:
				String airFreight = SubQuota.AIR_FREIGHT
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( airFreight, quota );
				break;
			
			case ALIMENTATION:
				String alimentation = SubQuota.ALIMENTATION
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( alimentation, quota );
				break;
			
			case DISCLOSURE_PARLIAMENTARY_ACTIVITY:
				String disclosureParliamentaryActivity = SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( disclosureParliamentaryActivity, quota );
				break;
			
			case FUEL:
				String fuel = SubQuota.FUEL.getRepresentativeNameQuota();
				
				setDetailsQuota( fuel, quota );
				break;
			
			case ISSUANCE_OF_AIR_TICKETS:
				String inssuanceAirTickets = SubQuota.ISSUANCE_OF_AIR_TICKETS
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( inssuanceAirTickets, quota );
				break;
			
			case LEASE_OF_VEHICLES:
				/* ! Write Instructions Here. */
				break;
			
			case OFFICE:
				String office = SubQuota.OFFICE.getRepresentativeNameQuota();
				
				setDetailsQuota( office, quota );
				break;
			
			case POSTAL_SERVICES:
				String postalServices = SubQuota.POSTAL_SERVICES
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( postalServices, quota );
				break;
			
			case SAFETY:
				String safety = SubQuota.SAFETY.getRepresentativeNameQuota();
				
				setDetailsQuota( safety, quota );
				break;
			
			case SIGNATURE_OF_PUBLICATION:
				/* ! Write Instructions Here. */
				break;
			
			case TECHNICAL_WORK_AND_CONSULTING:
				String technicalWorkConsulting = SubQuota.TECHNICAL_WORK_AND_CONSULTING
				      .getRepresentativeNameQuota();
				
				setDetailsQuota( technicalWorkConsulting, quota );
				break;
			
			case TELEPHONY:
				String telephony = SubQuota.TELEPHONY.getRepresentativeNameQuota();
				
				setDetailsQuota( telephony, quota );
				break;
			
			default:
				/* ! Nothing To Do. */
		}
	}
	
	/**
	 * Sets the information of a quota that will be presented, either graphically
	 * or numerically.
	 * 
	 * @param quotaName
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 * @param valueQuota
	 *           Amount spent associated with sub-quota
	 */
	private void setDetailsQuota( String quotaName, Quota quota ) {
		int idBackgroundResource = getResourceID( BACKGROUND, quotaName );
		int idImageResource = getResourceID( BUTTON, quotaName );
		int idTextResource = getResourceID( TEXT, quotaName );
		int idBarResource = getResourceID( BAR, quotaName );
		
		ImageView backgroundQuota = (ImageView) findViewById( idBackgroundResource );
		ImageView imageQuota = (ImageView) findViewById( idImageResource );
		ImageView barQuota = (ImageView) findViewById( idBarResource );
		TextView txtQuota = (TextView) findViewById( idTextResource );
		
		setBackgroundQuota( backgroundQuota, quota );
		setImageQuota( imageQuota, quotaName );
		setBarQuota( barQuota, quota );
		setTextValueQuota( txtQuota, quota );
	}
	
	/**
	 * Sets the picture that represents the background of the quota.
	 * 
	 * @param background
	 *           ImagemView representing the background of the quota.
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void setBackgroundQuota( ImageView background, Quota quota ) {
		changeColorResource( background, exponentialProbability( quota ) );
	}
	
	/**
	 * Sets the picture that represents on the screen a quota.
	 * 
	 * @param image
	 *           ImagemView representing the the quota.
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void setImageQuota( ImageView image, String quota ) {
		/*! Write Instructions Here. */
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
	private void setBarQuota( ImageView bar, Quota quota ) {
		changeColorResource( bar, exponentialProbability( quota ) );
	}
	
	/**
	 * Sets the text that represents on the screen the spending level of a quota.
	 * 
	 * @param text
	 *           TextView numerically representing the spending on a quota.
	 * @param valueQuota
	 *           Amount spent associated with sub-quota.
	 */
	private void setTextValueQuota( TextView text, Quota quota ) {
		DecimalFormat valueQuotaFormat = new DecimalFormat( "#,###.00" );
		
		if( quota != null ) {
			text.setText( valueQuotaFormat.format( quota.getValueQuota() ) );
			
			changeColorResource( text, exponentialProbability( quota ) );
			
		} else {
			text.setText( valueQuotaFormat.format( EMPTY_VALUE_QUOTA ) );
		}
	}
	
	/**
	 * Resets the value of parliamentary quotas, making them equal to zero.
	 */
	private void resetSubQuotaAccordingType() {
		final String accommodation = SubQuota.ACCOMMODATION
		      .getRepresentativeNameQuota();
		resetDetailsQuota( accommodation );
		
		final String airFreight = SubQuota.AIR_FREIGHT
		      .getRepresentativeNameQuota();
		resetDetailsQuota( airFreight );
		
		final String alimentation = SubQuota.ALIMENTATION
		      .getRepresentativeNameQuota();
		resetDetailsQuota( alimentation );
		
		final String disclosureParliamentaryActivity = SubQuota.DISCLOSURE_PARLIAMENTARY_ACTIVITY
		      .getRepresentativeNameQuota();
		resetDetailsQuota( disclosureParliamentaryActivity );
		
		final String fuel = SubQuota.FUEL.getRepresentativeNameQuota();
		resetDetailsQuota( fuel );
		
		final String inssuanceAirTickets = SubQuota.ISSUANCE_OF_AIR_TICKETS
		      .getRepresentativeNameQuota();
		resetDetailsQuota( inssuanceAirTickets );
		
		final String office = SubQuota.OFFICE.getRepresentativeNameQuota();
		resetDetailsQuota( office );
		
		final String postalServices = SubQuota.POSTAL_SERVICES
		      .getRepresentativeNameQuota();
		resetDetailsQuota( postalServices );
		
		final String safety = SubQuota.SAFETY.getRepresentativeNameQuota();
		resetDetailsQuota( safety );
		
		final String technicalWorkConsulting = SubQuota.TECHNICAL_WORK_AND_CONSULTING
		      .getRepresentativeNameQuota();
		resetDetailsQuota( technicalWorkConsulting );
		
		final String telephony = SubQuota.TELEPHONY.getRepresentativeNameQuota();
		resetDetailsQuota( telephony );
	}
	
	/**
	 * Resets the settings of the elements related with a quota on the screen.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void resetDetailsQuota( String quota ) {
		resetBackgroundQuota( quota );
		resetImageQuota( quota );
		resetBarQuota( quota );
		resetTextValueQuota( quota );
	}
	
	/**
	 * Resets the color of the image represents a quota.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void resetBackgroundQuota( String quota ) {
		int idBackgroundResource = getResourceID( BACKGROUND, quota );
		
		ImageView backgroundQuota = (ImageView) findViewById( idBackgroundResource );
		
		Drawable background = backgroundQuota.getBackground();
		background.clearColorFilter();
	}
	
	/**
	 * Resets the color of the image represents a quota.
	 * 
	 * @param quota
	 *           Name of the quota. Should be given only with lowercase letters
	 *           and spaced names with underscore.
	 */
	private void resetImageQuota( String quota ) {
		int idImageResource = getResourceID( BUTTON, quota );
		
		ImageView imageQuota = (ImageView) findViewById( idImageResource );
		
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
	private void resetTextValueQuota( String quota ) {
		int idTextResource = getResourceID( TEXT, quota );
		
		TextView txtQuota = (TextView) findViewById( idTextResource );
		
		setTextValueQuota( txtQuota, null );
	}
	
	/**
	 * Calculates the level of the corresponding spending bar to the amount
	 * actually spent in relation to the average of congressmen.
	 * 
	 * @return level of bar corresponding to the amount spent.
	 */
	private double exponentialProbability( Quota quota ) {
		double lambda =  1/ quota.getStatisticQuota().getAverage() ;
		
		double result = 1 - Math.exp( - lambda * quota.getValueQuota() );
		
		return result;
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
		final String RESOURCE_BKG = "bkg_quota_" + quota;
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
			
			case BACKGROUND:
				resource = RESOURCE_BKG;
				break;
			
			default:
				/* ! Nothing To Do. */
		}
		
		int idResource = getResources().getIdentifier( resource, "id",
		      getPackageName() );
		
		return idResource;
	}
}
