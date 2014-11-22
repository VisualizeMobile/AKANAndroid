package br.com.visualize.akan.domain.adapters;

import android.widget.ArrayAdapter;

public interface AdapterCreator<T> {
	
	public abstract ArrayAdapter<T> create(int typeAdapter);

}
