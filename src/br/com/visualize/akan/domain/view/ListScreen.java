package br.com.visualize.akan.domain.view;

import java.util.ArrayList;
import java.util.List;

import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter;
import br.com.visualize.akan.domain.adapters.RankingAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.enumeration.UF;
import br.com.visualize.akan.domain.model.Congressman;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;


public class ListScreen extends Activity
{
	CongressmanController congressmanController;
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
		
		congressmanController =CongressmanController.getInstance(getBaseContext());
		    
			congressmanController.getAll();
			 List<Congressman> congressmen;
			congressmen = congressmanController.getCongressmanList();
			
		   
			
		     rankingAdapter = new RankingAdapter(this,R.layout.ranking_layout,congressmen);
		     listAdapter = new CongressmenListAdapter(this,R.layout.congressmen_list_layout, congressmen);
		     listView.setAdapter(listAdapter);
		     listAdapter.notifyDataSetChanged();
		     listView.setTextFilterEnabled(false);
		    //final LayoutTransition transitioner = new LayoutTransition();
		    //transitioner.enableTransitionType(LayoutTransition.APPEARING);
		    //listView.setLayoutTransition(transitioner);
		    final LayoutAnimationController controller 
		    = AnimationUtils.loadLayoutAnimation(
		      this, R.anim.layout_animation);
		    		    
		    btn_ranking.setOnClickListener(new View.OnClickListener() {
		    		
				@Override
				public void onClick(View v) {
					
					btn_ranking.setSelected(!btn_ranking.isSelected());
					
					if(btn_ranking.isSelected()){
						
						btn_ranking.setBackgroundResource(R.drawable.ranking_ativado);
						listView.setAdapter(rankingAdapter);
						listView.setLayoutAnimation(controller);
					}
					else{
						btn_ranking.setBackgroundResource(R.drawable.ranking_desativado);
						listView.setAdapter(listAdapter);
						listView.setLayoutAnimation(controller);
					}																	
					
					
				}
			});
		    
		    
		    btn_search.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 onSearchRequested();
					
				
					 					
				}
			});

	}
	/*
	@Override
	public boolean onSearchRequested() {
	  //   ListScreen.TEXT_SERVICES_MANAGER_SERVICE.g
	   //  startSearch(null, false, appData, false);
	     return true;
	 }
	
	public void onNewIntent(Intent intent) { 
        setIntent(intent); 
        handleIntent(intent); 
     } 

     public void onListItemClick(ListView l, View v, int position, long id) { 
        // call the appropriate detail activity
     } 

     private void handleIntent(Intent intent) { 
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
            query = intent.getStringExtra(SearchManager.QUERY); 
           doSearch(query); 
        } 
     }    

     private void doSearch(String queryStr) { 
    	 Log.e("entrei no dosearch", "pesquisei agora");
    	
    	congressmanController.getByName(queryStr);
    	listAdapter.clear();
    	listAdapter.addAll(congressmanController.getByName(queryStr));
    	listAdapter.notifyDataSetChanged();
    	 
    	 
    	  	 
         
     }
     */
     @Override
     public boolean onCreateOptionsMenu(Menu menu){
    	 MenuInflater inflater = getMenuInflater();
    	 inflater.inflate(R.menu.menu, menu);
    	 
    	 SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    	
    	 
    	 
    	  search = (SearchView) menu.findItem(R.id.action_search).getActionView();
    	  
    	 search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    	 search.setIconifiedByDefault(false);
    	 search.setSubmitButtonEnabled(true);
    	 search.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String newText) {
				
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				Log.e(newText, "peguei" );
				
				listAdapter.getFilter().filter(newText);
				
				Log.e(listAdapter.getItem(0).getNameCongressman(),"view");
					return true;
			}
		});
    	 return super.onCreateOptionsMenu(menu);
     }
	
	private String filter (String source, int start, int end ){
		StringBuilder sb = new StringBuilder(end - start);
		for(int i = start; i < end; i++){
			char c = source.charAt(i);
			if(isCharAllowed(c))
				sb.append(c);
			else{
				// do nothing here
			}
		}
		String sp = new String(sb);
		return sp;
	}
	
	private boolean isCharAllowed(char c){
		return Character.isLetterOrDigit(c)||Character.isSpace(c);
	}
	
}
