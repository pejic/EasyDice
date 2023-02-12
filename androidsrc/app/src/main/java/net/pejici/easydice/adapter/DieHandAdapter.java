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

import java.util.Observable;
import java.util.Observer;

import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.widget.ListAdapter;

public abstract class DieHandAdapter extends DataSetObservable
		implements Observer, ListAdapter
{

	private DieHand hand;
	private Context context;

	public DieHandAdapter(Context context, DieHand hand) {
		super();
		this.context = context;
		this.hand = hand;
		this.hand.addObserver(this);
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		return hand.getDice().size();
	}

	@Override
	public Object getItem(int position) {
		return getDie(position);
	}

	public Die getDie(int position) {
		return hand.getDice().get(position);
	}

	public boolean getSelected(int position) {
		return hand.isSelected(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return hand.getDice().isEmpty();
	}

	@Override
	public void update(Observable observable, Object data) {
		notifyChanged();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		super.unregisterObserver(observer);
	}

}
