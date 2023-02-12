/*
 * Easy Dice - An application for rolling dice of your choosing.
 * Copyright (C) 2011-2014 Slobodan Pejic (slobo@pejici.net)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
