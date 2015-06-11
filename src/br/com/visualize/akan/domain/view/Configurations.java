package br.com.visualize.akan.domain.view;

import java.util.ArrayList;
import java.util.List;

import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.ConfigurationsGridAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class Configurations extends Activity {

	private static final int PARTY_FILTER = 0;
	private static final int STATE_FILTER = 1;
	private static final int SPENT_FILTER = 2;
	
	private ConfigurationsGridAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configurations);
		
		GridView gridview = (GridView) findViewById(R.id.filter_gridview);
	    adapter = new ConfigurationsGridAdapter(getBaseContext(), 
	    		R.layout.filter_item, getButtonsTitlesList(PARTY_FILTER) );
		gridview.setAdapter(adapter);

	    gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
	    });

		
		setupFilterButtons();
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
				partyIndicator.setVisibility(View.VISIBLE);
				stateIndicator.setVisibility(View.GONE);
				spentIndicator.setVisibility(View.GONE);
				adapter.texts = getButtonsTitlesList(PARTY_FILTER);
				adapter.notifyDataSetChanged();
			}
		} );
		
		filterState.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				partyIndicator.setVisibility(View.GONE);
				stateIndicator.setVisibility(View.VISIBLE);
				spentIndicator.setVisibility(View.GONE);
				adapter.texts = getButtonsTitlesList(STATE_FILTER);
				adapter.notifyDataSetChanged();
			}
		} );
		
		filterSpent.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick( View v ) {
				partyIndicator.setVisibility(View.GONE);
				stateIndicator.setVisibility(View.GONE);
				spentIndicator.setVisibility(View.VISIBLE);
				adapter.texts = getButtonsTitlesList(SPENT_FILTER);
				adapter.notifyDataSetChanged();
			}
		} );
		
	}
	
	List<String> getButtonsTitlesList(int filter) {
		ArrayList<String> titles = new ArrayList<String>();
		switch(filter){
		case PARTY_FILTER: {
			//TODO: get data from congressman controller
			titles.add("PT");
			titles.add("PTB");
			titles.add("PCdoB");
			break;
		}
		case STATE_FILTER: {
			//TODO: get data from congressman controller
			titles.add("MT");
			titles.add("DF");
			titles.add("GO");
			titles.add("MA");
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
}
