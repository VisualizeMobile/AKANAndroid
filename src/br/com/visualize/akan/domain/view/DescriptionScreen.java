package br.com.visualize.akan.domain.view;

import br.com.visualize.akan.R;
import android.app.Activity;
import android.os.Bundle;

public class DescriptionScreen extends Activity {
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
	   super.onCreate( savedInstanceState );
	   setContentView( R.layout.description_screen_activity );
	}
	
	@Override
	protected void onPause() {
	   super.onPause();
	}
}
