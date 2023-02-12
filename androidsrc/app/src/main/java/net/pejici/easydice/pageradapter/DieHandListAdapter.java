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
package net.pejici.easydice.pageradapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.model.DieHandList;
import net.pejici.easydice.view.DiceRollerView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public class DieHandListAdapter
	extends PagerAdapter
	implements Observer
{
	DieHandList list = null;
	List<DiceRollerView> queue = new ArrayList<DiceRollerView>();
	Context ctx;

	static class DieViewModelPair {
		final DiceRollerView view;
		final DieHand model;
		DieViewModelPair(DiceRollerView view, DieHand model) {
			this.view = view;
			this.model = model;
		}
	}

	public DieHandListAdapter(Context ctx, DieHandList list) {
		super();
		this.ctx = ctx;
		this.list = list;
		this.list.addObserver(this);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return String.valueOf(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		DiceRollerView view;
		if (queue.size() > 0) {
			view = queue.remove(0);
		}
		else {
			view = DiceRollerView.instantiate(ctx);
		}
		DieHand h = list.get(position);
		view.setHand(h);
		container.addView(view);
		return new DieViewModelPair(view, h);
	}

	@Override
	public int getCount() {
		int size = list.size();
		return size;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		DieViewModelPair p = (DieViewModelPair)object;
		p.view.setHand(null);
		queue.add(p.view);
		container.removeView(p.view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		DieViewModelPair p = (DieViewModelPair)object;
		return (view == p.view);
	}

	@Override
	public int getItemPosition(Object object) {
		DieViewModelPair p = (DieViewModelPair)object;
		if (queue.contains(object)) {
			return POSITION_NONE;
		}
		int position = list.indexOfIdentical(p.model);
		if (position < 0) {
			return POSITION_NONE;
		}
		return position;
	}

	@Override
	public void update(Observable observable, Object data) {
		this.notifyDataSetChanged();
	}

}
