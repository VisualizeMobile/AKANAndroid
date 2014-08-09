package br.com.vizualize.akan.domain.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import br.com.visualize.akan.domain.model.Congressman;

public class RankingAdapter extends ArrayAdapter<Congressman>
{
	private final Context context;
	private final List<Congressman> congressmens;

	public RankingAdapter( Context context, int textViewResourceId, List<Congressman> congressmens )
	{
		super( context, textViewResourceId, congressmens );
		this.context = context;
		this.congressmens = congressmens;

	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		return parent;

	}
}
