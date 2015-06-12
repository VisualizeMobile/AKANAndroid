package br.com.visualize.akan.domain.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.ConfigurationsGridAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.enumeration.Order;
import br.com.visualize.akan.domain.enumeration.Status;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Configurations extends Activity {

	private static final int PARTY_FILTER = 0;
	private static final int STATE_FILTER = 1;
	private static final int SPENT_FILTER = 2; 
	
	private ConfigurationsGridAdapter adapter;
	private CongressmanController congressmanController;
	private int currentFilter = PARTY_FILTER;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configurations);
		
		congressmanController = CongressmanController.getInstance(getBaseContext());
		
		GridView gridview = (GridView) findViewById(R.id.filter_gridview);
	    adapter = new ConfigurationsGridAdapter(getBaseContext(), 
	    		R.layout.filter_item, getButtonsTitlesList() );

	    adapter.activeIds = getDictionaryKeys();
		gridview.setAdapter(adapter);
		
	    gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				TextView filter = (TextView)view.findViewById(R.id.filter_button);
				
				if(filter.getTag() == Status.INACTIVE){
					view.setBackgroundResource(R.drawable.active_option);
					filter.setTag(Status.ACTIVE);
					addFilter(id+"",((String)filter.getText()).replace("+ ",""));

				}
				else {
					view.setBackgroundResource(R.drawable.inactive_option);
					filter.setTag(Status.INACTIVE);
					removeFilter(id+"");
				}
				adapter.activeIds = getDictionaryKeys();
				adapter.notifyDataSetChanged();
			}
	    });
		setupFilterButtons();
		setupOrderButtons();
	}
	
	private void setupOrderButtons(){
		final ImageButton orderParty = (ImageButton)findViewById(R.id.order_party);
		final ImageButton orderState = (ImageButton)findViewById(R.id.order_state);
		final ImageButton orderAlphabetic = (ImageButton)findViewById(R.id.order_alphabetic);
		final ImageButton orderRanking = (ImageButton)findViewById(R.id.order_ranking);
		
		switch (congressmanController.getOrder()) {
		case PARTY:
			setButtomPressed(orderParty, true);
			setButtomPressed(orderState, false);
			setButtomPressed(orderAlphabetic, false);
			setButtomPressed(orderRanking, false);
			congressmanController.setOrderBy(Order.PARTY);
			break;
		case STATE:
			setButtomPressed(orderParty, false);
			setButtomPressed(orderState, true);
			setButtomPressed(orderAlphabetic, false);
			setButtomPressed(orderRanking, false);
			congressmanController.setOrderBy(Order.STATE);
			break;	
		case ALPHABETIC:
			setButtomPressed(orderParty, false);
			setButtomPressed(orderState, false);
			setButtomPressed(orderAlphabetic, true);
			setButtomPressed(orderRanking, false);
			congressmanController.setOrderBy(Order.ALPHABETIC);
			break;
		case RANKING:
			setButtomPressed(orderParty, false);
			setButtomPressed(orderState, false);
			setButtomPressed(orderAlphabetic, false);
			setButtomPressed(orderRanking, true);
			congressmanController.setOrderBy(Order.RANKING);
			break;
		default:
			break;
		}
		
		orderParty.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				setButtomPressed(orderParty, true);
				setButtomPressed(orderState, false);
				setButtomPressed(orderAlphabetic, false);
				setButtomPressed(orderRanking, false);
				congressmanController.setOrderBy(Order.PARTY);
			}
		} );
		
		orderState.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				setButtomPressed(orderParty, false);
				setButtomPressed(orderState, true);
				setButtomPressed(orderAlphabetic, false);
				setButtomPressed(orderRanking, false);
				congressmanController.setOrderBy(Order.STATE);
			}
		} );
		
		orderAlphabetic.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				setButtomPressed(orderParty, false);
				setButtomPressed(orderState, false);
				setButtomPressed(orderAlphabetic, true);
				setButtomPressed(orderRanking, false);
				congressmanController.setOrderBy(Order.ALPHABETIC);
			}
		} );
		
		orderRanking.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				setButtomPressed(orderParty, false);
				setButtomPressed(orderState, false);
				setButtomPressed(orderAlphabetic, false);
				setButtomPressed(orderRanking, true);
				congressmanController.setOrderBy(Order.RANKING);
			}
		} );
	}
	
	private void setButtomPressed(ImageButton button, boolean op) {
		String description = (String) button.getContentDescription();
		description = (op) ? description.replace("_","_active_") : description.replace("_","_inactive_");
		int id = getResources().getIdentifier(description, "drawable",  getPackageName());
		button.setImageResource(id);
	}
	
	private void setupFilterButtons(){
		ImageButton filterParty = (ImageButton)findViewById(R.id.filter_party);
		ImageButton filterState = (ImageButton)findViewById(R.id.filter_state);
		ImageButton filterSpent = (ImageButton)findViewById(R.id.filter_spent);
		
		final ImageView partyIndicator = (ImageView)findViewById(R.id.party_indicator);
		final ImageView stateIndicator = (ImageView)findViewById(R.id.state_indicator);
		final ImageView spentIndicator = (ImageView)findViewById(R.id.spent_indicator);
		
		filterParty.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				currentFilter = PARTY_FILTER;
				partyIndicator.setVisibility(View.VISIBLE);
				stateIndicator.setVisibility(View.GONE);
				spentIndicator.setVisibility(View.GONE);
				adapter.texts = getButtonsTitlesList();
				adapter.activeIds = getDictionaryKeys();
				adapter.notifyDataSetChanged();
			}
		} );
		
		filterState.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				currentFilter = STATE_FILTER;
				partyIndicator.setVisibility(View.GONE);
				stateIndicator.setVisibility(View.VISIBLE);
				spentIndicator.setVisibility(View.GONE);
				adapter.texts = getButtonsTitlesList();
				adapter.activeIds = getDictionaryKeys();
				adapter.notifyDataSetChanged();
			}
		} );
		
		filterSpent.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				currentFilter = SPENT_FILTER;
				partyIndicator.setVisibility(View.GONE);
				stateIndicator.setVisibility(View.GONE);
				spentIndicator.setVisibility(View.VISIBLE);
				adapter.texts = getButtonsTitlesList();
				adapter.activeIds = getDictionaryKeys();
				adapter.notifyDataSetChanged();
			}
		} );
		
	}
	
	List<String> getButtonsTitlesList() {
		ArrayList<String> titles = new ArrayList<String>();
		switch(currentFilter){
		case PARTY_FILTER: {
			titles.addAll(congressmanController.getParties());
			break;
		}
		case STATE_FILTER: {
			//TODO: get data from congressman controller
			titles.add("AC");
			titles.add("AL");
			titles.add("AM");
			titles.add("AP");
			titles.add("BA");
			titles.add("CE");
			titles.add("DF");
			titles.add("ES");
			titles.add("GO");
			titles.add("MA");
			titles.add("MG");
			titles.add("MS");
			titles.add("MT");
			titles.add("PA");
			titles.add("PB");
			titles.add("PE");
			titles.add("PI");
			titles.add("PR");
			titles.add("RJ");
			titles.add("RN");
			titles.add("RO");
			titles.add("RR");
			titles.add("RS");
			titles.add("SC");
			titles.add("SE");
			titles.add("SP");
			titles.add("TO");
			break;
		}
		case SPENT_FILTER: {
			titles.add("+ 0");
			titles.add("+ 10.000");
			titles.add("+ 30.000");
			titles.add("+ 50.000");
			titles.add("+ 100.000");
			titles.add("+ 150.000");
			titles.add("+ 200.000");
			titles.add("+ 300.000");
			break;
		}
		default:{
			/*nothing to do*/
		}
		}
		return titles;
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
	
	private void addFilter(String key, String value){
		switch (currentFilter) {
		case PARTY_FILTER:
			congressmanController.getPartyFilters().put(key,value);
			break;
		case STATE_FILTER:
			congressmanController.getStateFilters().put(key,value);
			break;
		case SPENT_FILTER:
			congressmanController.setSpentFilters(new Hashtable<String,String>());
			congressmanController.getSpentFilters().put(key,value.replace("+ ","").replace(".",""));
			break;
		default:
			break;
		}

		congressmanController.getAllCongressman();

	}
	
	private void removeFilter(String key){
		switch (currentFilter) {
		case PARTY_FILTER:
			congressmanController.getPartyFilters().remove(key);
			break;
		case STATE_FILTER:
			congressmanController.getStateFilters().remove(key);
			break;
		case SPENT_FILTER:
			congressmanController.getSpentFilters().remove(key);
			congressmanController.getSpentFilters().put("0","0");
			break;
		default:
			break;
		}
		congressmanController.getAllCongressman();
	}
	
	private List<String> getDictionaryKeys(){
		Map<String,String> filters;
		switch (currentFilter) {
		case PARTY_FILTER:
			filters = congressmanController.getPartyFilters();
			break;
		case STATE_FILTER:
			filters = congressmanController.getStateFilters();
			break;
		case SPENT_FILTER:
			filters = congressmanController.getSpentFilters();
			break;
		default:
			filters = new Hashtable<String, String>();
			break;
		}
		List<String> keys = new ArrayList<String>(filters.keySet());
		return keys;
	}
}
