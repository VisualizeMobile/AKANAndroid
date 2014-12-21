/*
 * File: ListScreen.java
 * Purpose: Brings the implementation of ListScreen, a class that 
 * serves to interface with the user.
 */
package br.com.visualize.akan.domain.view;


import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter;
import br.com.visualize.akan.domain.adapters.RankingAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.model.Congressman;

/**
 * To manipulate congressman lists of the most varied. Through it is 
 * possible to give visibility to the elements of lists, allowing the 
 * user access to them.
 */
@SuppressLint( "NewApi" )
public class ListScreen extends Activity {
	CongressmanController congressmanController;
	CongressmenListAdapter listAdapter;
	RankingAdapter rankingAdapter;
	
	ListView listView;
	SearchView search;
	
	String query;
	Button btn_search;
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		final Button btn_ranking = (Button) findViewById( R.id.btn_ranking );
		
		setContentView( R.layout.list_screen_activity );
		
		btn_search = (Button) findViewById( R.id.btn_search );
		
		search = new SearchView( getApplicationContext() );
		
		congressmanController = CongressmanController
		      .getInstance( getBaseContext() );
		
		final List<Congressman> congressmen;
		congressmen = congressmanController.getAllCongressman();
		
		rankingAdapter = new RankingAdapter( this, R.layout.ranking_layout,
		      congressmen );
		
		listAdapter = new CongressmenListAdapter( this,
		      R.layout.congressmen_list_layout, congressmen );
		
		listView = (ListView) findViewById( R.id.listView );
		
		listView.setAdapter( listAdapter );
		listView.setOnItemClickListener( new OnItemClickListenerListViewItem() );
		
		listAdapter.notifyDataSetChanged();
		listView.setTextFilterEnabled( false );
		
		final LayoutAnimationController controller = AnimationUtils
		      .loadLayoutAnimation( this, R.anim.layout_animation );
		
		btn_ranking.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				btn_ranking.setSelected( !btn_ranking.isSelected() );
				
				if( btn_ranking.isSelected() ) {
					btn_ranking.setBackgroundResource( R.drawable.ranking_ativado );
					
					listView.setAdapter( rankingAdapter );
					listView.setLayoutAnimation( controller );
					
				} else {
					btn_ranking
					      .setBackgroundResource( R.drawable.ranking_desativado );
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
					btn_search.setBackgroundResource( R.drawable.busca_ativada );
					search.setIconified( false );
					
				} else {
					btn_search.setBackgroundResource( R.drawable.busca_desativada );
					search.setIconified( true );			
				}
			}
		} );
		
		listView.setOnItemClickListener( new OnItemClickListener() {
			
			@Override
			public void onItemClick( AdapterView<?> parent, View view,
			      int position, long id ) {
				
				Congressman congressman = (Congressman) parent
				      .getItemAtPosition( position );
				
				congressmanController.setCongressman( congressman );
				
				Intent intent = new Intent( ListScreen.this, DescriptionScreen.class );
				
				startActivity( intent );				
			}
		} );	
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
			public boolean onQueryTextChange( String newText ) {
				listAdapter.getFilter().filter( newText );
				rankingAdapter.getFilter().filter( newText );
				
				return true;
			}
		} );
		
		return super.onCreateOptionsMenu( menu );
	}
}
