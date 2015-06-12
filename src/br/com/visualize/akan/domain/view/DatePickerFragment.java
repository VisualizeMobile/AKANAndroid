package br.com.visualize.akan.domain.view;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import br.com.visualize.akan.domain.controller.QuotaController;

public class DatePickerFragment extends DialogFragment {
	 MyDatePickerDialog customDatePicker;
	 DatePickerDialog datepicker;
	 QuotaController quotaController; 
	 final Calendar calendar = Calendar.getInstance();
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
		 quotaController = QuotaController.getInstance((DescriptionScreen)getActivity());
	        int year = calendar.get(Calendar.YEAR);
	        int month = calendar.get(Calendar.MONTH);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);
	        customDatePicker =  new MyDatePickerDialog(getActivity(),(DescriptionScreen)getActivity(), year, month, day);
	        customDatePicker.setTitle("Período"); 
	        
	        hideDayCalendar(customDatePicker);
	          datepicker = customDatePicker;
	          limitDateSpinner();
	          
	        // Create a new instance of DatePickerDialog and return it
	        return datepicker;
	    }
	 
		/**
		 * Hide day in date spinner from datepicker
		 * 
		 * @param datePickerDialog
		 * @return Dialog with day hide
		 */
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
		/**
		 * Limit period date in date spinner
		 */
		public void limitDateSpinner(){
			
			try {
				List<Long> periodDate = new ArrayList<Long>();
				periodDate = quotaController.getMinMaxDate();
				if(periodDate.size()>0){
					Long dateMin = Collections.min(periodDate);
					Long dateMax = Collections.max(periodDate);
					datepicker.getDatePicker().setMinDate(dateMin);
			        datepicker.getDatePicker().setMaxDate(dateMax);
				}
		          
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				dismiss();
			}
		}
		
	}

