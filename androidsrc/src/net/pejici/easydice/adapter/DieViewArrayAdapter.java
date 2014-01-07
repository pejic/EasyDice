package net.pejici.easydice.adapter;

import java.util.List;

import net.pejici.easydice.model.Die;
import net.pejici.easydice.view.DieView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.LayoutParams;

import static net.pejici.androidutil.LayoutUtil.*;

public class DieViewArrayAdapter<T> extends ArrayAdapter<T> {

	public DieViewArrayAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	public DieViewArrayAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public DieViewArrayAdapter(Context context, int resource, T[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public DieViewArrayAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public DieViewArrayAdapter(Context context, int resource,
			int textViewResourceId, T[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public DieViewArrayAdapter(Context context, int resource,
			int textViewResourceId, List<T> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Die die = (Die) getItem(position);
		if (null == die) {
			return null;
		}
		DieView dieView = (DieView) convertView;
		if (null == dieView) {
			dieView = new DieView(getContext());
			int px = dpToPx(dieView, 66);
			dieView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, px));
		}
		dieView.setDie(die);
		return dieView;
	}

}
