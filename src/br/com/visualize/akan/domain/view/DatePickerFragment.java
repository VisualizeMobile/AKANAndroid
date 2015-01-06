package br.com.visualize.akan.domain.view;

import java.text.DateFormat.Field;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements 
DatePickerDialog.OnDateSetListener{

	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);
	        DatePickerDialog datepicker = new DatePickerDialog(getActivity(), this, year, month, day);
	        datepicker.setTitle("Período");
	        hideyearCalendar(datepicker);
	        // Create a new instance of DatePickerDialog and return it
	        return datepicker;
	    }
	 
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			
			
		}
		
		private Dialog hideyearCalendar(DatePickerDialog datePickerDialog) {
		    try
		    {
		         java.lang.reflect.Field[] datePickerDialogFields = datePickerDialog.getClass().getDeclaredFields();
		         for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) { 
		                if (datePickerDialogField.getName().equals("mDatePicker")) {
		                    datePickerDialogField.setAccessible(true);
		                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(datePickerDialog);
		                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
		                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
		                       if ("mDayPicker".equals(datePickerField.getName())|"mDaySpinner".equals(datePickerField.getName())) {
		                          datePickerField.setAccessible(true);
		                          Object dayPicker= new Object();
		                          dayPicker = datePickerField.get(datePicker);
		                          ((View) dayPicker).setVisibility(View.GONE);
		                          
		                       }
		                       datePickerDialog.setTitle("Periodo");
		                    }
		                 }

		              }
		        
		         datePickerDialog.getDatePicker().setCalendarViewShown(false);
		        return datePickerDialog;    
		    }
		    catch(Exception e)
		    {
		        
		    return datePickerDialog;    
		        
		    }
		    
		}

		
	}
