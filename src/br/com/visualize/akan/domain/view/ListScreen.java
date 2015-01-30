/*
 * File: ListScreen.java
 * Purpose: Brings the implementation of ListScreen, a class that 
 * serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter;
import br.com.visualize.akan.domain.adapters.RankingAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.QuotaController;
import br.com.visualize.akan.domain.model.Congressman;

@SuppressLint("NewApi")
public class ListScreen extends Activity 
{
	CongressmanController congressmanController;
	QuotaController quotaController;
	CongressmenListAdapter listAdapter;
	RankingAdapter rankingAdapter;
//	CongressmenListAdapter followAdapter;
	ListView listView;
	SearchView search;
	String query;
	Button btn_search;
	Button btn_ranking;
	Button btn_follow;
	CustomDialog customDialog = null; 
	List<Congressman> congressmen;
	List<Congressman> followedCongressmen;
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.list_screen_activity );
		
		btn_ranking = (Button) findViewById( R.id.btn_ranking );
		btn_search = (Button) findViewById( R.id.btn_search );
		search = new SearchView( getApplicationContext() );
		congressmanController = CongressmanController
		      .getInstance( getBaseContext() );
		quotaController = QuotaController.getInstance(getApplicationContext());
		 
		congressmen = congressmanController.getAllCongressman();
		
		rankingAdapter = new RankingAdapter( this, R.layout.ranking_layout,
		      congressmen );
		
		listAdapter = new CongressmenListAdapter( this,
		      R.layout.congressmen_list_layout, congressmen );
		followedCongressmen = congressmanController.getFollowedCongressman();
//		listAdapter = new CongressmenListAdapter(this, R.layout.congressmen_list_layout, followedCongressmen);

		
		
		listView = (ListView) findViewById( R.id.listView );

		listView.setAdapter( listAdapter );
	
		
		listAdapter.notifyDataSetChanged();
		listView.setTextFilterEnabled( false );
		
		final LayoutAnimationController controller = AnimationUtils
		      .loadLayoutAnimation( this, R.anim.layout_animation );
		
		btn_ranking.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				btn_ranking.setSelected( !btn_ranking.isSelected() );

				if( btn_ranking.isSelected() ) {
					btn_ranking.setBackgroundResource( R.drawable.active_ranking );

					listView.setAdapter( rankingAdapter );
					listView.setLayoutAnimation( controller );
					
				} else {
					btn_ranking
					      .setBackgroundResource( R.drawable.inactive_ranking );
					listView.setAdapter( listAdapter );
					listView.setLayoutAnimation( controller );
					
				}
			}
		} );
		
		btn_search.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				btn_search.setSelected( !btn_search.isSelected() );
				
				if( btn_search.isSelected() ) {
					btn_search.setBackgroundResource( R.drawable.active_search );
					search.setIconified( false );
					
				} else {
					btn_search.setBackgroundResource( R.drawable.inactive_search );
					search.setIconified( true );			
				}
			}
		} );
		
		btn_follow  = (Button)findViewById(R.id.btn_follow);
		
		listView.setOnItemClickListener( new OnItemClickListener() {
			
			@Override
			public void onItemClick( AdapterView<?> parent, View view,
			      int position, long id ) {

				 final Congressman congressman = (Congressman) parent.getItemAtPosition(position);
				 congressmanController.setCongressman(congressman);
						 
				 Intent i = new Intent(ListScreen.this, DescriptionScreen.class);
				 startActivity(i);
				Toast toast=Toast.makeText(getApplicationContext(), congressman.getNameCongressman(), Toast.LENGTH_SHORT);
		            toast.show();
			}
		} );	
	}
	public void onFollowList(View view){
		btn_follow.setSelected(!btn_follow.isSelected());
		listAdapter = null;
		listAdapter =  new CongressmenListAdapter(this, R.layout.congressmen_list_layout, followedCongressmen);
		if (btn_follow.isSelected()){
			btn_follow.setBackgroundResource(R.drawable.active_followed);
			
			listView.setAdapter(listAdapter);
			Iterator<Congressman> teste = followedCongressmen.iterator();
			
			while (teste.hasNext()){
				
				Congressman congressman = teste.next();
				Log.e("parlamentares seguidos listscreen",congressman.getNameCongressman() );
			}
			
			
			
		}else{
		
			btn_follow.setBackgroundResource(R.drawable.inactive_followed);
			listAdapter = null;
			listAdapter = new CongressmenListAdapter(this, R.layout.congressmen_list_layout, congressmen);
			listView.setAdapter(listAdapter);
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listAdapter.notifyDataSetChanged();
		rankingAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu( final Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.menu, menu );
		
		SearchManager searchManager = (SearchManager) getSystemService( 
				Context.SEARCH_SERVICE );
		
		search = (SearchView) menu.findItem( R.id.action_search ).getActionView();
		
		search.setSearchableInfo( searchManager
		      .getSearchableInfo( getComponentName() ) );
		
		search.setIconifiedByDefault( true );
		search.setOnQueryTextListener( new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit( String newText ) {
			return false;	
			}
			 @Override
			 public boolean onQueryTextChange(String newText) {
				 listAdapter.getFilter().filter(newText);
				 rankingAdapter.getFilter().filter(newText);
					return true;
				}
    	 });
 			
 		
    	 
    	 return super.onCreateOptionsMenu(menu);
	}
/**
 * Listener click event to follow/unFollow congressman
 * @param view
 */
	public void followedCongressman( View view ) {
		customDialog = new CustomDialog(this);
		int timeToDismis = 2000;
		Congressman congressman = (Congressman)view.getTag();
		customDialog.setMessage("Parlamentar "+congressman.getNameCongressman()+" seguido");
		congressmanController.setCongressman(congressman);
		if(congressmanController.getCongresman().isStatusCogressman()) {
			congressmanController.getCongresman().setStatusCogressman(false);
			congressmanController.updateStatusCongressman();
			followedCongressmen.remove(congressman);
			if(btn_follow.isSelected()){
				listAdapter = new CongressmenListAdapter(this, R.layout.congressmen_list_layout, followedCongressmen);
			}
			else{
				listAdapter = new CongressmenListAdapter(this, R.layout.congressmen_list_layout, congressmen);
			}
			listView.setAdapter(listAdapter);
			Log.e("Cheguei no followed", "cheguei no followed: "+followedCongressmen.size());
			quotaController.deleteQuotasFromCongressman(congressman.getIdCongressman());
//			rankingAdapter.notifyDataSetChanged();
			
		} else
		{
			congressmanController.getCongresman().setStatusCogressman(true);
			congressmanController.updateStatusCongressman();
			followedCongressmen.add(congressman);
			listAdapter.notifyDataSetChanged();
//			rankingAdapter.notifyDataSetChanged();
			
			customDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			customDialog.show();
			
			/**
			 * timer to dismis Dialog
			 */
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					customDialog.dismiss();
					timer.cancel();
					
				}
			}, timeToDismis);
			
		}
	}
	
}
