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
import static com.google.android.material.internal.ViewUtils.dpToPx;
import static net.pejici.androidutil.LayoutUtil.dpToPx;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.view.DieView;


public class DieViewDieHandAdapter extends DieHandAdapter {

	public DieViewDieHandAdapter(Context context, DieHand hand) {
		super(context, hand);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Die die = getDie(position);
		boolean selected = getSelected(position);
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
		dieView.setSelected(selected);
		return dieView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}
}
