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

import java.io.ObjectInputStream.GetField;
import java.util.Observable;
import java.util.Observer;

import net.pejici.easydice.fragment.DiceRollerFragment;
import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.model.DieHandList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class DieHandListAdapter
	extends FragmentStatePagerAdapter
	implements Observer
{
	DieHandList list = null;

	public DieHandListAdapter(FragmentManager fm, DieHandList list) {
		super(fm);
		this.list = list;
		this.list.addObserver(this);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return String.valueOf(position);
	}

	@Override
	public Fragment getItem(int position) {
		return DiceRollerFragment.instantiate(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public void update(Observable observable, Object data) {
		this.notifyDataSetChanged();
	}

}
