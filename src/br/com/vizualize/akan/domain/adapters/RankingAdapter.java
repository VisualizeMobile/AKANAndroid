package br.com.vizualize.akan.domain.adapters;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.model.Congressman;

public class RankingAdapter extends ArrayAdapter<Congressman>
{
	private final Context context;
	private final List<Congressman> congressmens;
	private int rankingPosition = 0;

	public RankingAdapter( Context context, int textViewResourceId, List<Congressman> congressmens )
	{
		super( context, textViewResourceId, congressmens );
		this.context = context;
		this.congressmens = congressmens;

	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		rankingPosition += 1;
		LayoutInflater inflater = ( LayoutInflater )context
				.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View view = inflater.inflate( R.layout.ranking_layout, null );
		
		TextView textViewName = ( TextView )view
				.findViewById( R.id.ranking_layout_txt_nome );
		textViewName.setText( congressmens.get( position ).getNameCongressman( ) );
		
		TextView textViewParty = (TextView)view
				.findViewById( R.id.ranking_layout_txt_partido );
		textViewParty.setText( congressmens.get( position ).getPartyCongressman( ) );
		
		TextView textViewUf = (TextView)view
				.findViewById( R.id.ranking_layout_txt_uf );
		textViewUf.setText( congressmens.get( position ).getUfCongressman( ).getDescriptionUf( ) );
		
		TextView textViewValue = (TextView)view
				.findViewById( R.id.ranking_layout_txt_valor );
		DecimalFormat formatValue = new DecimalFormat("#,###.00");
		textViewValue.setText(""
				+ formatValue.format(congressmens.get( position ).getTotalSpentCongressman( ) ));
		
		TextView textViewPosition = (TextView)view
				.findViewById( R.id.layout_ranking_position );
		textViewPosition.setText( rankingPosition );
		return parent;

	}
}
