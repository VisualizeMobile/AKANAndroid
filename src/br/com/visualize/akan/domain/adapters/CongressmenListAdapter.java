package br.com.visualize.akan.domain.adapters;


import java.text.DecimalFormat;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.model.Congressman;

public class CongressmenListAdapter extends ArrayAdapter<Congressman> {

	private final Context context;
	private final List<Congressman> congressmens;
	

	public CongressmenListAdapter( Context context, int textViewResourceId, List<Congressman> congressmens )
	{
		super( context, textViewResourceId, congressmens );
		this.context = context;
		this.congressmens = congressmens;

	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{	
		String idCongressman = Integer.toString(congressmens.get(position).getIdCongressman());
		String photoCongressmanUrl = "http://www.camara.gov.br/internet/deputado/bandep/";
		Log.e( "Entrou Adapter", "EntrouAdapter" );
		
		LayoutInflater inflater = ( LayoutInflater )context
				.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		Log.e( "Entrou Adapter", "Entrou Inflate service" );
		View view = inflater.inflate( R.layout.congressmen_list_layout, null );
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
		
		
		ImageView congressmanImage = (ImageView)view.findViewById(R.id.layout_ranking_imagem_parlamentar);
		Picasso.with(context).load(photoCongressmanUrl+idCongressman+".jpg").error(R.drawable.parlamentar_foto_mini).into(congressmanImage);
		Log.e("",photoCongressmanUrl+idCongressman);
		return view;

	}




}
