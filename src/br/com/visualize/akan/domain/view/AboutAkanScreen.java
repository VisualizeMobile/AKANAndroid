/*
 * File: AboutAkanScreen.java
 * Purpose: Responsible to set up the application information display screen, 
 * its developers and motivation.
 */
package br.com.visualize.akan.domain.view;


import br.com.visualize.akan.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Brings up screen containing information about the application and 
 * its developers.
 */
public class AboutAkanScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_akan_screen_activity);
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText("This is about");
	}
}
