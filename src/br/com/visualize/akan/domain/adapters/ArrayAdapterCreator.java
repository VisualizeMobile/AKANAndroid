package br.com.visualize.akan.domain.adapters;

import android.widget.ArrayAdapter;

public class ArrayAdapterCreator<T> implements AdapterCreator<T>{
	public static final int RANKING = 0;
	public static final int CONGRESSMEN = 1;
	@Override
	public ArrayAdapter<T> create(int typeAdapter) {
		return (ArrayAdapter<T>) new RankingAdapter(context, textViewResourceId, congressmens);
		
	}


}
