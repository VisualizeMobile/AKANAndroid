/*
 * File: ListScreen.java Purpose: Brings the implementation of ListScreen, a
 * class that serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ResponseHandler;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.api.request.HttpConnection;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.exception.NullCongressmanException;
import br.com.visualize.akan.domain.model.Congressman;


public class ListScreen extends Activity {
	CongressmanController congressmanController;
	QuotaController quotaController;
	CongressmenListAdapter listAdapter;
	ListView listView;
	SearchView search;
	RelativeLayout searchLayer;
	String query;
	Button btn_search;
	Button btn_ranking;
	Button btn_follow;
	CustomDialog customDialog = null;
	List<Congressman> congressmen;
	List<Congressman> followedCongressmen;
	LayoutAnimationController listAnimation;
	List<Congressman> currentListCongressmen;
	int currentLayout;
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.list_screen_activity );
		
		searchLayer = (RelativeLayout)findViewById(R.id.topbar_search_layer);
		
		btn_ranking = (Button)findViewById( R.id.btn_ranking );
		btn_search = (Button)findViewById( R.id.btn_search );
		search = new SearchView( getApplicationContext() );
		congressmanController = CongressmanController
		        .getInstance( getBaseContext() );
		quotaController = QuotaController.getInstance( getApplicationContext() );
		
		congressmen = congressmanController.getAllCongressman();
		currentListCongressmen = congressmen;
		
		listAdapter = new CongressmenListAdapter( this,
		        R.layout.congressmen_list_layout, congressmen );
		currentLayout = R.layout.congressmen_list_layout;
		followedCongressmen = congressmanController.getFollowedCongressman();
		
		listView = (ListView)findViewById( R.id.listView );
		
		listView.setAdapter( listAdapter );
		
		listAdapter.notifyDataSetChanged();
		listView.setTextFilterEnabled( false );
		
		listAnimation = AnimationUtils.loadLayoutAnimation( this,
		        R.anim.layout_animation );
		
		btn_ranking.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				btn_ranking.setSelected( !btn_ranking.isSelected() );
				
				if( btn_ranking.isSelected() ) {
					btn_ranking
					        .setBackgroundResource( R.drawable.active_ranking );
					listAdapter = new CongressmenListAdapter( ListScreen.this,
					        R.layout.ranking_layout, currentListCongressmen );
					listView.setAdapter( listAdapter );
					listView.setLayoutAnimation( listAnimation );
					currentLayout = R.layout.ranking_layout;
					
				} else {
					btn_ranking
					        .setBackgroundResource( R.drawable.inactive_ranking );
					listAdapter = new CongressmenListAdapter( ListScreen.this,
					        R.layout.congressmen_list_layout,
					        currentListCongressmen );
					listView.setAdapter( listAdapter );
					listView.setLayoutAnimation( listAnimation );
					currentLayout = R.layout.congressmen_list_layout;
					
				}
			}
		} );
		
		btn_search.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				btn_search.setSelected( !btn_search.isSelected() );
				
				if( btn_search.isSelected() ) {
					btn_search.setBackgroundResource( R.drawable.active_search );
					listAdapter.getFilter().filter( search.getQuery().toString());
					animateSearchView(1);
					showInputMethod(search);
					
				} else {
					btn_search
					        .setBackgroundResource( R.drawable.inactive_search );
					listAdapter.getFilter().filter( "" );
					animateSearchView(-1);
					search.clearFocus();
				}
			}
		} );
		
		btn_follow = (Button)findViewById( R.id.btn_follow );
		
		listView.setOnItemClickListener( new OnItemClickListener() {
			
			@Override
			public void onItemClick( AdapterView<?> parent, View view,
			        int position, long id ) {
				search.clearFocus();
				final Congressman congressman = (Congressman)parent
				        .getItemAtPosition( position );
				congressmanController.setCongressman( congressman );
				
				Intent i = new Intent( ListScreen.this, DescriptionScreen.class );
				startActivity( i );
			}
		} );
		
		configureSearchView();
	}
	
	public void onFollowList( View view ) {
		Animation followAnimation = AnimationUtils.loadAnimation( this,
		        R.anim.up_from_bottom );
		btn_follow.setSelected( !btn_follow.isSelected() );
		listAdapter = null;
		listAdapter = new CongressmenListAdapter( this, currentLayout,
		        followedCongressmen );
		if( btn_follow.isSelected() ) {
			btn_follow.setBackgroundResource( R.drawable.active_followed );
			listView.setAdapter( listAdapter );
			listView.setAnimation( followAnimation );
			currentListCongressmen = followedCongressmen;
		} else {
			
			btn_follow.setBackgroundResource( R.drawable.inactive_followed );
			listAdapter = null;
			listAdapter = new CongressmenListAdapter( this, currentLayout,
			        congressmen );
			
			listView.setAdapter( listAdapter );
			listView.setAnimation( followAnimation );
			currentListCongressmen = congressmen;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		followedCongressmen = congressmanController.getFollowedCongressman();
		listAdapter.notifyDataSetChanged();
		
	}
	
	/**
	 * Listener click event to follow/unFollow congressman
	 * 
	 * @param view
	 */
	public void followedCongressman( View view ) {
		search.clearFocus();
		customDialog = new CustomDialog( this );
		
		int timeToDismis = 2000;
		final Congressman congressman = (Congressman)view.getTag();
		
		customDialog.setMessage( "Parlamentar "
		        + congressman.getNameCongressman() + " seguido" );
		congressmanController.setCongressman( congressman );
		
		if( congressmanController.getCongresman().isStatusCogressman() ) {
			congressmanController.getCongresman().setStatusCogressman( false );
			
			try {
	            congressmanController.updateStatusCongressman();
	            
            } catch( NullCongressmanException nce ) {
            	// TODO: Show a Alert about the exception
            }
			
			followedCongressmen = congressmanController
			        .getFollowedCongressman();
			
			currentListCongressmen = followedCongressmen;
			
			if( btn_follow.isSelected() ) {
				listAdapter = new CongressmenListAdapter( this,
				        R.layout.congressmen_list_layout, followedCongressmen );
			} else {
				listAdapter = new CongressmenListAdapter( this,
				        R.layout.congressmen_list_layout, congressmen );
			}
			listView.setAdapter( listAdapter );
			
			Log.e( "Cheguei no followed", "cheguei no followed: "
			        + followedCongressmen.size() );
			
			quotaController.deleteQuotasFromCongressman( congressman
			        .getIdCongressman() );
			
		} else {
			congressmanController.getCongresman().setStatusCogressman( true );
			
			try {
	            congressmanController.updateStatusCongressman();
	            
            } catch( NullCongressmanException nce ) {
            	// TODO: Show a Alert about the exception
            }
			
			followedCongressmen.add( congressman );
			listAdapter.notifyDataSetChanged();
			
			
			customDialog.getWindow().clearFlags(
			        WindowManager.LayoutParams.FLAG_DIM_BEHIND );
			
			customDialog.show();
			
			new Thread( new Runnable() {
				
				@Override
				public void run() {

					Looper.prepare();
					ResponseHandler<String> responseHandler = HttpConnection
					        .getResponseHandler();
					try {
						
						quotaController.getQuotaById(
						        congressman.getIdCongressman(), responseHandler );
						
						Log.e( "" + congressman.getIdCongressman(),
						        "REQUISITEI QUOTA NO SEGUIR" );
						
					} catch( Exception e ) {
						// TODO launch error alert
						e.printStackTrace();
					}
					
					Looper.loop();
				}
			} ).start();
			
			/**
			 * timer to dismiss Dialog
			 */
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
	
	private void configureSearchView(){
		SearchManager searchManager = (SearchManager)getSystemService( Context.SEARCH_SERVICE );
		search = (SearchView)findViewById( R.id.topbar_searchview );
		
		search.setSearchableInfo( searchManager
		        .getSearchableInfo( getComponentName() ) );
		
		search.setIconified(false);
		
		int closeButtonId = search.getContext().getResources()
				.getIdentifier("android:id/search_close_btn", null, null);
        final ImageView closeButton = (ImageView) search.findViewById(closeButtonId);
        closeButton.getLayoutParams().height = 0;	
		
        search.setOnQueryTextListener( new OnQueryTextListener() {
        	
			@Override
			public boolean onQueryTextSubmit( String newText ) {
				return false;
			}
			
			@Override
			public boolean onQueryTextChange( String newText ) {
				listAdapter.getFilter().filter( newText );
				return true;
			}
		} );
        
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                	showInputMethod(v);
                }
            }
        });
        
		int searchPlateId = search.getContext().getResources()
		        .getIdentifier( "android:id/search_plate", null, null );
		View searchPlate = search.findViewById( searchPlateId );
		if( searchPlate != null ) {
			searchPlate.setBackgroundColor( 0xFFF1F1F2 );
			int searchTextId = searchPlate.getContext().getResources()
			        .getIdentifier( "android:id/search_src_text", null, null );
			TextView searchText = (TextView)searchPlate
			        .findViewById( searchTextId );
			if( searchText != null ) {
				searchText.setTextColor( Color.GRAY );
				searchText.setHintTextColor( Color.GRAY );
			}			
		}
	}
	public void animateSearchView(final int direction){
		TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 50*direction);
		anim.setDuration(300);

		anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) { 
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)searchLayer.getLayoutParams();
		    params.bottomMargin = 0;
		    searchLayer.setLayoutParams(params);
		}
			
		@Override
		public void onAnimationRepeat(Animation animation) { }

		@Override
		public void onAnimationEnd(Animation animation) {
		    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)searchLayer.getLayoutParams();
		    params.topMargin = (50*direction>0) ? 50*direction : 0;
		    params.bottomMargin = 65;
		    searchLayer.setLayoutParams(params);
		}
		});
		searchLayer.startAnimation(anim);
	}
	
	/**
	 * Open configure screen
	 * 
	 */
	public void goToConfiguration(View view) {
		search.clearFocus();
		Intent i = new Intent( ListScreen.this, Configurations.class );
		startActivity( i );
	}
	
	/**
	 * Force open keyborad to serach view
	 * 
	 */
	private void showInputMethod(final View view) {
	    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
	
	public void onClickAbout( View view)
	{
		Intent intentAbout = new Intent( ListScreen.this, AboutAkanScreen.class );
		startActivity( intentAbout );
	}
}
