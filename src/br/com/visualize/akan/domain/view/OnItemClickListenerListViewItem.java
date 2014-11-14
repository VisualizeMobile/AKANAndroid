package br.com.visualize.akan.domain.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class OnItemClickListenerListViewItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Context context = view.getContext();
Intent myAction = new Intent(context , DescriptionScreen.class );
Toast.makeText(context, "Cliquei", Toast.LENGTH_SHORT).show();
		
((ListScreen) context).startActivity( myAction );
((ListScreen) context).finish();
		
	}
	
	

}
