package br.com.visualize.akan.domain.view;

import java.util.ArrayList;
import java.util.List;

import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter;
import br.com.visualize.akan.domain.adapters.RankingAdapter;
import br.com.visualize.akan.domain.controller.CongressmanController;
import br.com.visualize.akan.domain.enumeration.UF;
import br.com.visualize.akan.domain.model.Congressman;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ListScreen extends Activity
{
	CongressmanController congressmanController;
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView(R.layout.list_screen_activity);
		final ListView listView = (ListView) findViewById( R.id.listView );
		final Button btn_ranking = (Button) findViewById(R.id.btn_ranking);
		
		congressmanController =CongressmanController.getInstance(getBaseContext());
		    
			congressmanController.getAll();
			List<Congressman> congressmen;
			congressmen = congressmanController.getCongressmanList();
			
		   
		   
		    final RankingAdapter rankingAdapter = new RankingAdapter(this,R.layout.ranking_layout,congressmen);
		    final CongressmenListAdapter listAdapter = new CongressmenListAdapter(this,R.layout.congressmen_list_layout, congressmen);
		    listView.setAdapter(listAdapter);
		    btn_ranking.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					btn_ranking.setSelected(!btn_ranking.isSelected());
					
					if(btn_ranking.isSelected()){
						btn_ranking.setBackgroundResource(R.drawable.ranking_ativado);
					}
					else{
						btn_ranking.setBackgroundResource(R.drawable.ranking_desativado);
					}																	
					
					
				}
			});

	}
}
