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
package net.pejici.easydice.fragment;

import net.pejici.easydice.R;
import net.pejici.easydice.adapter.DieViewArrayAdapter;
import net.pejici.easydice.adapter.DieViewDieHandAdapter;
import net.pejici.easydice.model.AppModel;
import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.view.DieSumTextView;
import net.pejici.easydice.view.DieView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class DiceRollerFragment extends Fragment {

	DieHand hand;
	DieViewDieHandAdapter handAdapter;

	public static final String ARGS_HAND = "hand";

	public DiceRollerFragment() {
		// TODO Auto-generated constructor stub
	}

	static public DiceRollerFragment instantiate(int position) {
		DiceRollerFragment frag = new DiceRollerFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(DiceRollerFragment.ARGS_HAND, position);
		frag.setArguments(bundle);
		return frag;
	}

	private void loadHand() {
		int i = getArguments().getInt(ARGS_HAND);
		AppModel model = AppModel.getInstance(getActivity());
		hand = model.getHandList().get(i);
	}

	private void setupDiceSum(View rootView) {
		DieSumTextView view = (DieSumTextView)
				rootView.findViewById(R.id.hand_sum_text_view);
		view.setDieHand(hand);
	}

	private void setupDiceHand(View rootView) {
		GridView grid = (GridView) rootView.findViewById(R.id.dice_grid);
		handAdapter = new DieViewDieHandAdapter(this.getActivity(), hand);
		grid.setAdapter(handAdapter);
		OnItemClickListener cl = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DieView dieView = (DieView)view;
				hand.setSelected(position, !dieView.getSelected());
			}
		};
		grid.setOnItemClickListener(cl);
	}

	private void setupDiceButtons(View rootView) {
		GridView buttonGrid = (GridView) rootView.findViewById(R.id.dice_buttons_grid);
		DieViewArrayAdapter<Die> adapter = new DieViewArrayAdapter<Die>(
				this.getActivity(), 0, Die.allLargestSizeDice());
		buttonGrid.setAdapter(adapter);
		OnItemClickListener cl = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View dieView,
					int position, long id) {
				Die die = (Die) parent.getAdapter().getItem(position);
				hand.addDie(die);
			}
		};
		buttonGrid.setOnItemClickListener(cl);
	}

	private void setupResetButton(View rootView) {
		Button resetButton = (Button) rootView.findViewById(R.id.reset_button);
		OnClickListener cl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				hand.clear();
			}
		};
		resetButton.setOnClickListener(cl);
	}

	private void setupRollButton(View rootView) {
		Button rollButton = (Button) rootView.findViewById(R.id.roll_button);
		OnClickListener cl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				hand.roll();
			}
		};
		rollButton.setOnClickListener(cl);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dice_roller,
				container, false);
		loadHand();
		setupDiceButtons(rootView);
		setupDiceHand(rootView);
		setupDiceSum(rootView);
		setupResetButton(rootView);
		setupRollButton(rootView);
		return rootView;
	}

	/**
	 * Returns the hand that is being shown by this fragment.  The pager
	 * adapter will use this to determine the position of the fragment
	 * especially after an update to the {@link DieHandList}.
	 * @return {@link DieHand} object being shown by this fragment.
	 */
	public DieHand getHand() {
		return hand;
	};

}
