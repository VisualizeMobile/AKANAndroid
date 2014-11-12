package br.com.visualize.akan.domain.view;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter;
import br.com.visualize.akan.domain.adapters.RankingAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.controller.DeputyController;
import br.com.visualize.akan.domain.model.Congressman;


public class ListScreen extends Activity
{
	CongressmanController deputyController;
	ListView listView ;
	RankingAdapter rankingAdapter;
	CongressmenListAdapter listAdapter;
	String query;
	SearchView search;
	 
	
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView(R.layout.list_screen_activity);
		
		//handleIntent(getIntent()); 
	    listView = (ListView) findViewById( R.id.listView );
		final Button btn_ranking = (Button) findViewById(R.id.btn_ranking);
		final Button btn_search = (Button) findViewById(R.id.btn_search);
		
		search = new SearchView(getApplicationContext());
		
		deputyController = DeputyController.getInstance(getBaseContext());
		    
		deputyController.getAll();
		List<Congressman> congressmen;
		congressmen = DeputyController.getCongressmanList();
				
		rankingAdapter = new RankingAdapter(this,R.layout.ranking_layout,congressmen);
		listAdapter = new CongressmenListAdapter(this,R.layout.congressmen_list_layout, congressmen);
		listView.setAdapter(listAdapter);
		listAdapter.notifyDataSetChanged();
		listView.setTextFilterEnabled(false);
		
		final LayoutAnimationController controller = 
				AnimationUtils.loadLayoutAnimation( this, R.anim.layout_animation );
		    		    
		btn_ranking.setOnClickListener(new View.OnClickListener() {
		    		
			@Override
			public void onClick(View v) {
				btn_ranking.setSelected(!btn_ranking.isSelected());
				
				if( btn_ranking.isSelected() ) {
					btn_ranking.setBackgroundResource(R.drawable.ranking_ativado);
					
					listView.setAdapter(rankingAdapter);
					listView.setLayoutAnimation(controller);
					
				} else {
					btn_ranking.setBackgroundResource(R.drawable.ranking_desativado);
					listView.setAdapter(listAdapter);
					listView.setLayoutAnimation(controller);
				}
			}
		});

		btn_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				 onSearchRequested();		 					
			}
		});
	}
	
     @Override
     public boolean onCreateOptionsMenu( Menu menu ) {
    	 MenuInflater inflater = getMenuInflater();
    	 inflater.inflate( R.menu.menu, menu );
    	 
    	 SearchManager searchManager = 
    			 (SearchManager) getSystemService( Context.SEARCH_SERVICE );
    	 
    	 search = (SearchView) menu.findItem( R.id.action_search ).getActionView();
    	  
    	 search.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
    	 
    	 search.setIconifiedByDefault(false);
    	 search.setSubmitButtonEnabled(true);
    	 
    	 search.setOnQueryTextListener(new OnQueryTextListener() {
			
	    	 @Override
			 public boolean onQueryTextSubmit(String newText) {
					
	    		 return false;
	    	 }
				
			 @Override
			 public boolean onQueryTextChange(String newText) {
				 listAdapter.getFilter().filter(newText);
					return true;
				}
    	 });
    	 
    	 return super.onCreateOptionsMenu(menu);
     }
}
