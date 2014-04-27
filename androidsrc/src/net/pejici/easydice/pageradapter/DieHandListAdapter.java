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

import net.pejici.easydice.fragment.DiceRollerFragment;
import net.pejici.easydice.model.DieHandList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DieHandListAdapter extends FragmentStatePagerAdapter {
	DieHandList list = null;

	public DieHandListAdapter(FragmentManager fm, DieHandList list) {
		super(fm);
		this.list = list;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return String.valueOf(position);
	}

	@Override
	public Fragment getItem(int position) {
		DiceRollerFragment frag = new DiceRollerFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(DiceRollerFragment.ARGS_HAND, position);
		frag.setArguments(bundle);
		return frag;
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
