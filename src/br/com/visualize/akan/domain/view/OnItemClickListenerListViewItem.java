/*
 * File: OnItemClickListenerViewItem.java Purpose: Brings the implementation of
 * the Listener for the DescriptionScreen buttons.
 */
package br.com.visualize.akan.domain.view;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Sets a listener to relate to the DescriptionScreen.
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {
	
	@Override
	public void onItemClick( AdapterView<?> parent, View view, int position,
	        long id ) {
		
		Context context = view.getContext();
		Intent myAction = new Intent( context, DescriptionScreen.class );
		
		( (ListScreen)context ).startActivity( myAction );
		( (ListScreen)context ).finish();
	}
}
