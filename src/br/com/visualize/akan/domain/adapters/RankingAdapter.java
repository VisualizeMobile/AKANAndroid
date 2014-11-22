package br.com.visualize.akan.domain.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.adapters.CongressmenListAdapter.CongressmanFilter;
import br.com.visualize.akan.domain.model.Congressman;

public class RankingAdapter extends ArrayAdapter<Congressman>
{
	private final Context context;
	private  List<Congressman> congressmens;
	private  List<Congressman> filteredList;
	private CongressmanFilter congressmanFilter;

	public RankingAdapter( Context context, int textViewResourceId, List<Congressman> congressmensList )
	{
		super( context, textViewResourceId, congressmensList );
		this.context = context;
		this.congressmens = new ArrayList<Congressman>();
	    congressmens.addAll(congressmensList);
	    this.filteredList = new ArrayList<Congressman>();
	    filteredList.addAll(congressmens);
	    getFilter();

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
		
		ImageView congressmanImage = (ImageView)view.findViewById(R.id.layout_ranking_imagem_parlamentar);
		Picasso.with(context).load(photoCongressmanUrl+idCongressman+".jpg").error(R.drawable.parlamentar_foto_mini).into(congressmanImage);
		Log.e("",photoCongressmanUrl+idCongressman);
		return view;

	}
	
	@Override
	public int getCount() {
	    return congressmens.size();
	}
	@Override
    public Filter getFilter() {
		
        if (congressmanFilter == null) {
            congressmanFilter = new CongressmanFilter();
        }
        
        Log.e("Entrei no getFIlter","getFilter");
        
        return congressmanFilter;
    }

	public class CongressmanFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			
			constraint = constraint.toString().toLowerCase();
			FilterResults filterResults = new FilterResults();
			 if(constraint != null && constraint.toString().length() > 0)
             {
                 List<Congressman> filteredItems = new ArrayList<Congressman>();
                 List<Congressman> currentItems = new ArrayList<Congressman>();
                 currentItems.addAll(filteredList);
                 for(int i = 0, l = currentItems.size(); i < l; i++)
                 {
                     Congressman m = currentItems.get(i);
                     if(m.getNameCongressman().toLowerCase().contains(constraint))
                         filteredItems.add(m);
                 }
                 filterResults.count = filteredItems.size();
                 filterResults.values = filteredItems;
             }
             else
             {
                 synchronized(this)
                 {
                     filterResults.values = congressmens;
                     filterResults.count = congressmens.size();
                 }
             }
	        return filterResults;
			
			
			
		}
		
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			
            congressmens = (List<Congressman>)results.values;
            Log.e("publicando", "publicando");
            notifyDataSetChanged();
            
            
            notifyDataSetInvalidated();
            
         
            //Log.e(congressmens.get(0).getNameCongressman(),"parlamentar escolhido");
		}
		
	}

}
