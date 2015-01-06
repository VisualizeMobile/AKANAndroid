package br.com.visualize.akan.domain.view;

import java.text.DateFormat.Field;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements 
DatePickerDialog.OnDateSetListener{
	 MyDatePickerDialog datepicker;
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);
	        datepicker =  new MyDatePickerDialog(getActivity(), null, year, month, day);
	        datepicker.setTitle("Período"); 
	        
	        hideDayCalendar(datepicker);
	          
	          
	        // Create a new instance of DatePickerDialog and return it
	        return datepicker;
	    }
	 
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			datepicker.setTitle("Período");
			
		}
		
		private Dialog hideDayCalendar(MyDatePickerDialog datePickerDialog) {
		    try
		    {
		         java.lang.reflect.Field[] datePickerDialogFields = datePickerDialog.getClass().getSuperclass().getDeclaredFields();
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
		                       datePickerDialog.setTitle("Período");
		                    }
		                 }

		              }
		        
		         datePickerDialog.getDatePicker().setCalendarViewShown(false);
		        return datePickerDialog;    
		    }
		    catch(Exception e)
		    {
		        e.printStackTrace();
		        
		        
		    }
		    return datePickerDialog;
		}

		
	}

