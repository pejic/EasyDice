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

import java.util.Observable;
import java.util.Observer;

import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.model.DieHandList;
import net.pejici.easydice.view.DiceRollerView;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class DieHandListAdapter
	extends PagerAdapter
	implements Observer
{
	DieHandList list = null;
	Context ctx;

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
		DiceRollerView view =
				DiceRollerView.instantiate(ctx);
		DieHand h = list.get(position);
		view.setHand(h);
		container.addView(view);
		return view;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		DiceRollerView view = (DiceRollerView)object;
		container.removeView(view);
	}

	@Override
	public boolean isViewFromObject(View view, Object viewObj) {
		return (view == viewObj);
	}

//	@Override
//	public Fragment getItem(int position) {
//		DiceRollerFragment f = null;
//		DieHand h = list.get(position);
//		if ((f = frags.get(h)) == null) {
//			Log.d(logName(), "getItem("+position+") exists");
//			f = DiceRollerFragment.instantiate(position);
//			frags.put(h, f);
//		}
//		else {
//			Log.d(logName(), "getItem("+position+") new");
//		}
//		return f;
//	}

//	@Override
//	public int getItemPosition(Object object) {
//		DiceRollerFragment view = (DiceRollerFragment)object;
//		return list.indexOfIdentical(view.getHand());
//	}

	private String logName() {
		return this.getClass().getCanonicalName().toString();
	}

	@Override
	public void update(Observable observable, Object data) {
		Log.d(logName(), "update()");
		this.notifyDataSetChanged();
	}

}
