package br.com.visualize.akan.domain.view;

import java.util.ArrayList;
import java.util.List;

import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.RankingAdapter;
import br.com.visualize.akan.domain.enumeration.UF;
import br.com.visualize.akan.domain.model.Congressman;
import android.app.Activity;

import android.os.Bundle;

import android.widget.ListView;

public class ListScreen extends Activity
{
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView(R.layout.list_screen_activity);
		
		final ListView listView = (ListView) findViewById( R.id.listView );
		
		
		    final List<Congressman> congressmens = new ArrayList<Congressman>();
		    Congressman parlamentar1 = new Congressman();
		    parlamentar1.setNameCongressman( "Romario" );
		    parlamentar1.setIdCongressman( 2 );
		    parlamentar1.setPartyCongressman( "PT" );
		    parlamentar1.setTotalSpentCongressman( 3300000 );
		    parlamentar1.setUfCongressman( UF.GO );
		    
		    Congressman parlamentar2 = new Congressman();
		    parlamentar2.setNameCongressman( "Arruda" );
		    parlamentar2.setIdCongressman( 3 );
		    parlamentar2.setPartyCongressman( "PSDB" );
		    parlamentar2.setTotalSpentCongressman( 500.000 );
		    parlamentar2.setUfCongressman( UF.DF );
		    
		    Congressman parlamentar3 = new Congressman();
		    parlamentar3.setNameCongressman( "Tiririca" );
		    parlamentar3.setIdCongressman( 4 );
		    parlamentar3.setPartyCongressman( "PT" );
		    parlamentar3.setTotalSpentCongressman( 600.000 );
		    parlamentar3.setUfCongressman( UF.SP );
		    
		    congressmens.add( parlamentar1 );
		    congressmens.add( parlamentar2 );
		    congressmens.add( parlamentar3 );
		   
		    final RankingAdapter rankingAdapter = new RankingAdapter(this,R.layout.ranking_layout,congressmens);
		    
		    listView.setAdapter(rankingAdapter);

	}
}
