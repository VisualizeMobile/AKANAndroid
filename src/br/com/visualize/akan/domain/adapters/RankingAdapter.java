package br.com.visualize.akan.domain.adapters;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.util.Log;
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
	

	public RankingAdapter( Context context, int textViewResourceId, List<Congressman> congressmens )
	{
		super( context, textViewResourceId, congressmens );
		this.context = context;
		this.congressmens = congressmens;

	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{	
		
		Log.e( "Entrou Adapter", "EntrouAdapter" );
		
		LayoutInflater inflater = ( LayoutInflater )context
				.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		Log.e( "Entrou Adapter", "Entrou Inflate service" );
		View view = inflater.inflate( R.layout.ranking_layout, null );
		Log.e( "Entrou Adapter", "pegou layout" );
		TextView textViewName = ( TextView )view
				.findViewById( R.id.ranking_layout_txt_nome );
		textViewName.setText( congressmens.get( position ).getNameCongressman( ) );
		Log.e( "Entrou Adapter", "setou nome" );
		TextView textViewParty = (TextView)view
				.findViewById( R.id.ranking_layout_txt_partido );
		textViewParty.setText( congressmens.get( position ).getPartyCongressman( ) );
		Log.e("","Setou partido");
		TextView textViewUf = (TextView)view
				.findViewById( R.id.ranking_layout_txt_uf );
		textViewUf.setText( congressmens.get( position ).getUfCongressman( ));
		Log.e("","Setou UF");
		TextView textViewValue = (TextView)view
				.findViewById( R.id.ranking_layout_txt_valor );
		DecimalFormat formatValue = new DecimalFormat("#,###.00");
		textViewValue.setText(""
				+ formatValue.format(congressmens.get( position ).getTotalSpentCongressman( ) ));
		Log.e("","Setou valor");
		TextView textViewPosition = (TextView)view
				.findViewById( R.id.layout_ranking_position );
		//Log.e("","pegou posição");
		textViewPosition.setText(Integer.toString(congressmens.get(position).getRankingCongressman()));
		
		Log.e("","Setou posição");
		return view;

	}
}
