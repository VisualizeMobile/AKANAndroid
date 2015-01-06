package br.com.visualize.akan.domain.view;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.style.SuperscriptSpan;
import android.widget.DatePicker;

public class MyDatePickerDialog extends DatePickerDialog {
	

    public MyDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		// TODO Auto-generated constructor stub
	}

    
	private CharSequence title;

   

    public void setPermanentTitle(CharSequence title) {
        this.title = title;
        
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        setTitle("Período");
        
    }

}
