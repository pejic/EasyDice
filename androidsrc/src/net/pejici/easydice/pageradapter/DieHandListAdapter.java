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
