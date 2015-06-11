/*
 * File: DescriptionScreen.java Purpose: Brings the implementation of
 * DescriptionScreen, a class that serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ResponseHandler;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.api.helper.RoundedImageView;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.adapters.DescriptionGridAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;
import br.com.visualize.akan.domain.model.Quota;

import com.squareup.picasso.Picasso;


/**
 * Serves to bring the visibility of the detailed data of a congressman. Through
 * this class you can give the user a description of the expenses of the share
 * congressman.
 */
public class DescriptionScreen extends FragmentActivity implements
        OnDateSetListener {

	private Context context;
	private TextView referenceMonth;
	
	private QuotaController quotaController = null;
	private CongressmanController congressmanController;
	private Calendar calendar = Calendar.getInstance();
	private CustomDialog customDialog;
	
	private int year;
	private int month;
	private DescriptionGridAdapter gridAdapter;
	
	static List<Quota> quotas;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		this.context = getApplicationContext();
		
		setContentView( R.layout.description_screen_activity );

		congressmanController = CongressmanController
		        .getInstance( context );
		final int idCongressman = congressmanController.getCongresman().getIdCongressman();
		quotaController = QuotaController.getInstance( context );

		year = calendar.get( Calendar.YEAR );
		month = calendar.get( Calendar.MONTH );
		
		//quotas = quotaController.getQuotasByIdCongressman(idCongressman);
		quotas = quotaController.getQuotaByDate(month, year, idCongressman);
		
		GridView gridview = (GridView) findViewById(R.id.quota_gridview);
	    gridAdapter = new DescriptionGridAdapter(getBaseContext(), R.layout.quota_grid_item, quotas);
		gridview.setAdapter(gridAdapter);

	    gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Quota quota = (Quota)parent.getItemAtPosition( position );
				quotaController.getQuota(quota.getIdQuota());
				Intent i = new Intent( DescriptionScreen.this, QuotaGraphScreen.class );
				startActivity( i );
			}
	    });
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setDescriptionCongressman();
		
		if( !congressmanController.getCongresman().isStatusCogressman() ) {
			requestQuotas();
			
		} else {
			month = quotaController.initializeDateFromQuotas();
			setValuesQuotas();
		}
		
		referenceMonth = (TextView)findViewById( R.id.reference_month );
		
		setReferenceMonth();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if( !congressmanController.getCongresman().isStatusCogressman() ) {
			quotaController.deleteQuotasFromCongressman( congressmanController
			        .getCongresman().getIdCongressman() );
		} else {
			/* ! Nothing To Do. */
		}
		
		//resetSubQuotaAccordingType();
	}
	
	/**
	 * Get date chosen by user to filtered quotas
	 * 
	 * @param monthOfYear
	 *            month chosen by user
	 * @param year
	 *            year chosen by user
	 * @param datapicker
	 *            current datepicker
	 * 
	 */
	@Override
	public void onDateSet( DatePicker datapicker, int year, int monthOfYear,
	        int dayOfMonth ) {
		this.year = year;
		
		this.month = monthOfYear + 1;
		
		//resetSubQuotaAccordingType();
		
		setReferenceMonth();
		
		setValuesQuotas();
	}
	
	/**
	 * Sets the congressman-related data to be displayed in the description.
	 */
	private void setDescriptionCongressman() {
		Congressman congressman;
		
		congressman = congressmanController.getCongresman();
		String idCongressman = Integer
		        .toString( congressman.getIdCongressman() );
		String photoCongressmanUrl = "http://www.camara.gov.br/internet/deputado/bandep/";
		
		TextView textViewCongressmanName = (TextView)findViewById( R.id.description_txt_congressman_name );
		
		textViewCongressmanName.setText( congressman.getNameCongressman() );
		
		TextView textViewCongressmanParitdo = (TextView)findViewById( R.id.description_txt_congressman_party );
		
		textViewCongressmanParitdo.setText( congressman.getPartyCongressman() + 
				" - " + congressman.getUfCongressman());
		
		TextView textViewRankingPosition = (TextView)findViewById( R.id.description_txt_ranking_position );
		
		textViewRankingPosition.setText( Integer.toString( congressman
		        .getRankingCongressman() ) );
		
		TextView textViewTopBarName = (TextView)findViewById( R.id.topbar_congressman );
		
		String name = congressman.getNameCongressman().replace("PROFESSOR", "PROF.");
		name = name.replace("PROF.A", "PROF.");
		textViewTopBarName.setText( name );
		
		RoundedImageView congressmanImage = (RoundedImageView)findViewById( R.id.description_congressman_photo );
		
		Picasso.with( context )
		        .load( photoCongressmanUrl + idCongressman + ".jpg" )
		        .placeholder(R.drawable.default_photo)
		        .error( R.drawable.default_photo ).into( congressmanImage );
		
		Button followCongressman = (Button)findViewById( R.id.description_btn_follow );
		TextView textViewFollow = (TextView)findViewById( R.id.description_txt_follow );
		
		if( congressmanController.getCongresman().isStatusCogressman() ) {
			followCongressman.setBackgroundResource( R.drawable.following );
			
			textViewFollow.setText( "Seguido" );
			textViewFollow.setTextColor( Color.parseColor( "#008e8e" ) );
			
		} else {
			followCongressman.setBackgroundResource( R.drawable.not_following );
			
			textViewFollow.setText( "Seguir" );
			textViewFollow.setTextColor( Color.parseColor( "#536571" ) );
			
		}
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
					
					quotaController.getQuotaById( congressmanController
					        .getCongresman().getIdCongressman(),
					        responseHandler );
					
				} catch( Exception e ) {
					/* ! TODO: Handle Exception. */
				}
				
				runOnUiThread( new Runnable() {
					
					@Override
					public void run() {
						progress.setMessage( "Dados carregados" );
						
						month = quotaController.initializeDateFromQuotas();
						
						setReferenceMonth();
						
						setValuesQuotas();
						
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
	 *            Numeric identifier of congressman that must have deleted the
	 *            quotas.
	 */
	public void setValuesQuotas() {
		
		int idCongressman = congressmanController.getCongresman().getIdCongressman();
		quotas = quotaController.getQuotaByDate( month, year, idCongressman);
		
		gridAdapter.quotas = quotas;
		
		gridAdapter.notifyDataSetChanged();

	}
	
	
	/**
	 * Instantiates DatePickerFragment and show in screen
	 * 
	 * @param view
	 *            receives current view
	 */
	
	public void showDatePickerDialog( View view ) {
		DatePickerFragment newFragment = new DatePickerFragment();
		
		newFragment.show( getSupportFragmentManager(), "datePicker" );
	}
	
	/**
	 * Set up reference month text
	 */
	public void setReferenceMonth() {
		
		String monthText = getApplication().getResources().getStringArray(
		        R.array.month_names )[ month - 1 ];
		
		referenceMonth.setText( monthText + " de " + year );
	}
	
	/**
	 * Back to list of the congressmen
	 * 
	 * @param view
	 *            current View
	 */
	public void backToList( View view ) {
		this.finish();
	}
	
	public void onFollowedCongressman( View view ) {
		customDialog = new CustomDialog( this );
		int timeToDismis = 2000;
		
		customDialog.setMessage( "Parlamentar "
		        + congressmanController.getCongresman().getNameCongressman()
		        + " seguido" );
		
		if( congressmanController.getCongresman().isStatusCogressman() ) {
			
			congressmanController.getCongresman().setStatusCogressman( false );
			
			try {
				congressmanController.updateStatusCongressman();
				
			} catch( NullCongressmanException e ) {
				// TODO: Show a Alert about the exception
			}
			
			setDescriptionCongressman();
			
		} else {
			congressmanController.getCongresman().setStatusCogressman( true );
			
			try {
				congressmanController.updateStatusCongressman();
				
			} catch( NullCongressmanException e ) {
				// TODO: Show a Alert about the exception
			}
			
			setDescriptionCongressman();
			
			customDialog.getWindow().clearFlags(
			        WindowManager.LayoutParams.FLAG_DIM_BEHIND );
			customDialog.show();
			
			final Timer timer = new Timer();
			
			timer.schedule( new TimerTask() {
				
				@Override
				public void run() {
					customDialog.dismiss();
					timer.cancel();
				}
				
			}, timeToDismis );
		}
	}
}
