package br.com.visualize.akan.domain.view;


import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;


public class MyDatePickerDialog extends DatePickerDialog {
	
	public MyDatePickerDialog( Context context, OnDateSetListener callBack,
	        int year, int monthOfYear, int dayOfMonth ) {
		
		super( context, callBack, year, monthOfYear, dayOfMonth );
	}
	
	@Override
	public void onDateChanged( DatePicker view, int year, int month, int day ) {
		super.onDateChanged( view, year, month, day );
		
		setTitle( "Período" );
	}
}
