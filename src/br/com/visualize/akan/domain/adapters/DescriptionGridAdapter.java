/*
 * File: QuotaListAdapter.java Purpose: Brings the implementation of class
 * QuotaListAdapter.
 */
package br.com.visualize.akan.domain.adapters;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.visualize.akan.R;
import br.com.visualize.akan.domain.model.Quota;


/**
 * Represents a list of congressman adapting its entirety a particular form
 * described by the class.
 */
@SuppressLint("ViewHolder")
public class DescriptionGridAdapter extends ArrayAdapter<Quota> {
	
	private final int WHITE = 0xffF1F1F2;
	private final int GREEN = 0xff00A69A;
	private final int GRAY = 0xff536571;
	private final int YELLOW = 0xffF3D171;
	private final int RED = 0xffF16068;
	
	
	private final Context context;
	public List<Quota> quotas;
	private List<Quota> filteredList;
	private int layoutInflated;
	
	public DescriptionGridAdapter( Context context, int textViewResourceId,
	        List<Quota> quotasList ) {
		
		super( context, textViewResourceId, quotasList );
		
		this.layoutInflated = textViewResourceId;
		this.context = context;
		this.quotas = new ArrayList<Quota>();
		this.filteredList = new ArrayList<Quota>();
		
		quotas.addAll( quotasList );
		filteredList.addAll( quotas );
		
		getFilter();
	}
	
	/**
	 * Sets a view that will associated the list generated by the Adapter, as
	 * the parameters passed, and returns.
	 */
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		Quota quota = quotas.get( position );
		
		LayoutInflater inflater = (LayoutInflater)context
		        .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View view;
		view = inflater.inflate( layoutInflated, null );
		setDetailsQuota(quota, view);
		
		return view;
	}
	
	/**
	 * Sets the information of a quota that will be presented, either
	 * graphically or numerically.
	 * 
	 * @param quotaName
	 *            Name of the quota. Should be given only with lowercase letters
	 *            and spaced names with underscore.
	 * @param valueQuota
	 *            Amount spent associated with sub-quota
	 */
	private void setDetailsQuota( Quota quota, View view ) {
		String quotaName = quota.getTypeQuota().getRepresentativeNameQuota();
		
		ImageView btnQuota = (ImageView) view.findViewById(R.id.btn_quota_imageview);
		ImageView barQuota = (ImageView) view.findViewById(R.id.bar_quota_imageview);
		TextView valueQuota = (TextView) view.findViewById(R.id.quota_text);
		
		int btnId = context.getResources().getIdentifier( "quota_"+quotaName, "drawable",
		        context.getPackageName() );
		int barId = context.getResources().getIdentifier( "level_bars_"+quotaName, "anim",
		        context.getPackageName() );
		btnQuota.setImageResource(btnId);
		barQuota.setImageResource(barId);
		
		double percent = exponentialProbability( quota );
		
		animateBackgroundColor( btnQuota, percent );
		animateBarColor( barQuota, percent );
		animateBarHeight( barQuota, percent );
		animateTextColor(valueQuota,percent );
		countTo(valueQuota, quota.getValueQuota()*0.5 , quota.getValueQuota());
		
	}
	
	/**
	 * Returns the list item requested.
	 * 
	 * @param position
	 *            Position of the item to be returned.
	 * 
	 * @return Quota associated with position.
	 */
	@Override
	public Quota getItem( int position ) {
		return quotas.get( position );
	}
	
	/**
	 * Returns the number of elements associated with the list.
	 * 
	 * @return Number of elements.
	 */
	@Override
	public int getCount() {
		return quotas.size();
	}
	
	@Override
	public boolean isEnabled( int position ) {
		return true;
	}
	
	/**
	 * Sets the text that represents on the screen the spending level of a
	 * quota.
	 * 
	 * @param text
	 *            TextView numerically representing the spending on a quota.
	 * @param valueQuota
	 *            Amount spent associated with sub-quota.
	 */
	private void setTextValueQuota( TextView text, double value ) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
		DecimalFormat df = (DecimalFormat)nf;
		text.setText("R$ "+ df.format( value ) );
	}
	
	/**
	 * Calculates the level of the corresponding spending bar to the amount
	 * actually spent in relation to the average of congressmen.
	 * 
	 * @return level of bar corresponding to the amount spent.
	 */
	private double exponentialProbability( Quota quota ) {
		double lambda = 1 / quota.getStatisticQuota().getAverage();
		double result = 1 - Math.exp( -lambda * quota.getValueQuota() );
		return result;
	}
	
	/**
	 * Change the color of a ImageView.
	 * 
	 * @param view
	 *            The interface feature, of the type ImageView, which must have
	 *            changed color.
	 * @param percent
	 *            Value representing the share of spending level.
	 */
	private int[ ] selectImageColor( double percent ) {
		int[ ] colors = new int[ 5 ];
		
		if( 0.0 < percent && percent <= 0.25 ) {
			colors[ 0 ] = WHITE;
			colors[ 1 ] = GREEN;
			colors[ 2 ] = GREEN;
			colors[ 3 ] = GREEN;
			colors[ 4 ] = GREEN;
			
		} else if( 0.25 < percent && percent <= 0.5 ) {
			colors[ 0 ] = WHITE;
			colors[ 1 ] = GREEN;
			colors[ 2 ] = GRAY;
			colors[ 3 ] = GRAY;
			colors[ 4 ] = GRAY;
			
		} else if( 0.5 < percent && percent <= 0.75 ) {
			colors[ 0 ] = WHITE;
			colors[ 1 ] = GREEN;
			colors[ 2 ] = GRAY;
			colors[ 3 ] = YELLOW;
			colors[ 4 ] = YELLOW;
			
		} else if( 0.75 < percent && percent <= 1.0 ) {
			colors[ 0 ] = WHITE;
			colors[ 1 ] = GREEN;
			colors[ 2 ] = GRAY;
			colors[ 3 ] = YELLOW;
			colors[ 4 ] = RED;
			
		} else {
			/* ! Nothing To Do. */
		}
		
		return colors;
	}
	
	private void animateBackgroundColor( ImageView image, double percent ) {
		int[ ] colors = selectImageColor( percent );
		Log.e("imageId","id: "+ image.getId() + "; quotaPercent: " + percent);
		ValueAnimator colorAnimator = ObjectAnimator.ofInt( image,
		        "backgroundColor", colors );
		
		colorAnimator.setDuration( 1000 );
		colorAnimator.setEvaluator( new ArgbEvaluator() );
		colorAnimator.setInterpolator( new DecelerateInterpolator() );
		
		colorAnimator.start();
	}
	
	private void animateBarColor( ImageView bar, double percent ) {
		int[ ] colors = selectImageColor( percent );
		Log.e("barId","id: "+ bar.getX() + "; quotaPercent: " + percent);
		ValueAnimator colorAnimator = ObjectAnimator.ofInt( bar, "colorFilter",
		        colors );
		
		colorAnimator.setDuration( 1000 );
		colorAnimator.setEvaluator( new ArgbEvaluator() );
		colorAnimator.setInterpolator( new DecelerateInterpolator() );
		
		colorAnimator.start();
	}
	
	private void animateBarHeight( ImageView bar, double newHeight ) {
		
		/*
		 * The Mathematical Expression:
		 * 
		 * ( newHeight * 1000000 )/ 100 = newHeight * 10000 
		 * 
		 * Serves to maintain the proportionality between the last decimal value
		 * and the total possible for the size of a ImagemView. A ImageView may
		 * have a level value between 0 and 10000, where 10000 corresponds to
		 * 100%.
		 * 
		 * Becomes the last decimal value for percentage and then does the
		 * proportion between this value and the equivalent value in the range
		 * 0-10000.
		 */
		
		int height = (int)( newHeight * 10000 );
		
		Drawable level = bar.getDrawable();
		
		ValueAnimator heightAnimator = ObjectAnimator.ofInt( level, "level",
		        height );
		
		heightAnimator.setDuration( 1000 );
		heightAnimator.setInterpolator( new DecelerateInterpolator() );
		
		heightAnimator.start();
	}
	
	private void animateTextColor( TextView image, double percent ) {
		int[ ] colors = selectImageColor( percent );
		ValueAnimator colorAnimator = ObjectAnimator.ofInt( image,
		        "textColor", colors );
		
		colorAnimator.setDuration( 1000 );
		colorAnimator.setEvaluator( new ArgbEvaluator() );
		colorAnimator.setInterpolator( new DecelerateInterpolator() );
		
		colorAnimator.start();
	}
	
	private void countTo(final TextView tv, final double count, final double amount) {
		   if (count >= amount) { 
			   setTextValueQuota( tv, amount );
			   return;
		   }
		   setTextValueQuota( tv, count );
		   AlphaAnimation animation = new AlphaAnimation(1.0f, 0.9f);
		   animation.setDuration(50);
		   animation.setAnimationListener(new AnimationListener() {
			   @Override
			   public void onAnimationStart(Animation animation) {
				   // TODO Auto-generated method stub
			   }
				
			   @Override
			   public void onAnimationEnd(Animation animation) {
				   countTo(tv, count+amount*0.05, amount);
			   }
				
			   @Override
			   public void onAnimationRepeat(Animation animation) {
				   // TODO Auto-generated method stub
			   }		     
			});
		 tv.startAnimation(animation);
		 }
}
