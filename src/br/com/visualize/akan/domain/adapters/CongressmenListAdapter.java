/*
 * File: CongressmanListAdapter.java 
 * Purpose: Brings the implementation of class CongressmanListAdapter.
 */
package br.com.visualize.akan.domain.adapters;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.model.Congressman;

import com.squareup.picasso.Picasso;

/**
 * Represents a list of congressman adapting its entirety a particular 
 * form described by the class.
 */
@SuppressLint( { "InflateParams", "ViewHolder" } )
public class CongressmenListAdapter extends ArrayAdapter<Congressman> implements
      Filterable {
	
	private final Context context;
	private List<Congressman> congressmens;
	private List<Congressman> filteredList;
	private CongressmanFilter congressmanFilter;
	
	public CongressmenListAdapter( Context context, int textViewResourceId,
	      List<Congressman> congressmensList ) {
		
		super( context, textViewResourceId, congressmensList );
		
		this.context = context;
		this.congressmens = new ArrayList<Congressman>();
		this.filteredList = new ArrayList<Congressman>();
		
		congressmens.addAll( congressmensList );
		filteredList.addAll( congressmens );
		
		getFilter();
	}
	
	/**
	 * Sets a view that will associated the list generated by the Adapter, 
	 * as the parameters passed, and returns.
	 */
	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		String idCongressman = Integer.toString( congressmens.get( position )
		      .getIdCongressman() );
		
		final String URL_PHOTOS = "http://www.camara.gov.br/internet/deputado/bandep/";
		
		LayoutInflater inflater = (LayoutInflater) context
		      .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		
		View view = inflater.inflate( R.layout.congressmen_list_layout, null );
		
		TextView textViewName = (TextView) view
		      .findViewById( R.id.ranking_layout_txt_nome );
		
		textViewName.setText( congressmens.get( position ).getNameCongressman() );
		
		TextView textViewParty = (TextView) view
		      .findViewById( R.id.ranking_layout_txt_partido );
		
		textViewParty
		      .setText( congressmens.get( position ).getPartyCongressman() );
		
		TextView textViewUf = (TextView) view
		      .findViewById( R.id.ranking_layout_txt_uf );
		
		textViewUf.setText( congressmens.get( position ).getUfCongressman() );
		
		ImageView congressmanImage = (ImageView) view
		      .findViewById( R.id.layout_ranking_imagem_parlamentar );
		
		Picasso.with( context )
		      .load( URL_PHOTOS + idCongressman + ".jpg" )
		      .error( R.drawable.default_photo ).into( congressmanImage );
		
		return view;
	}
	
	/**
	 * Returns the list item requested.
	 * <p>
	 * @param position Position of the item to be returned.
	 * <p>
	 * @return Congressman associated with position.
	 */
	@Override
	public Congressman getItem( int position ) {
		return congressmens.get( position );
	}
	
	/**
	 * Returns the number of elements associated with the list.
	 * <p>
	 * @return Number of elements.
	 */
	@Override
	public int getCount() {
		return congressmens.size();
	}
	
	@Override
	public boolean isEnabled( int position ) {
		return true;
	}
	
	/**
	 * Sets a new filter based on CongressmanFilter class, for Adapter.
	 * <p>
	 * @return new filter, based on CongressmanFilter.
	 */
	@Override
	public Filter getFilter() {
		
		if( congressmanFilter != null ) {
			/*! Nothing To Do. */
			
		} else {
			congressmanFilter = new CongressmanFilter();
		}
		
		return congressmanFilter;
	}
	
	/* TODO: Review.
	 * 	
	 * 	According to the code convention, a .java file should not have more 
	 * 	than one class public.
	 */
	public class CongressmanFilter extends Filter {
		
		@Override
		protected FilterResults performFiltering( CharSequence constraint ) {
			
			constraint = constraint.toString().toLowerCase();
			FilterResults filterResults = new FilterResults();
			
			if( constraint != null && constraint.toString().length() > 0 ) {
				List<Congressman> filteredItems = new ArrayList<Congressman>();
				List<Congressman> currentItems = new ArrayList<Congressman>();
				
				currentItems.addAll( filteredList );
				
				for( int i = 0, l = currentItems.size(); i < l; i++ ) {
					Congressman m = currentItems.get( i );
					
					if( m.getNameCongressman().toLowerCase()
							.contains( constraint ) ) {
						
						filteredItems.add( m );
						
					} else {
						/*! Nothing To Do. */
					}
				}
				
				filterResults.count = filteredItems.size();
				filterResults.values = filteredItems;
				
			} else {
				synchronized( this ) {
					filterResults.values = filteredList;
					filterResults.count = filteredList.size();
				}
			}
			
			return filterResults;
		}
		
		@SuppressWarnings( "unchecked" )
      @Override
		protected void publishResults( CharSequence constraint,
		      FilterResults results ) {
			
			congressmens = (List<Congressman>)results.values;
			
			notifyDataSetChanged();
			notifyDataSetInvalidated();
		}
	}
}
