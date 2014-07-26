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
package net.pejici.easydice.view;

import net.pejici.easydice.R;
import net.pejici.easydice.adapter.DieViewArrayAdapter;
import net.pejici.easydice.adapter.DieViewDieHandAdapter;
import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class DiceRollerView extends LinearLayout {

	DieHand hand;
	DieViewDieHandAdapter handAdapter;

	public static final String ARGS_HAND = "hand";

	public DiceRollerView(Context ctx) {
		this(ctx, null);
	}

	public DiceRollerView(Context ctx, AttributeSet attrs) {
		this(ctx, attrs, 0);
	}

	public DiceRollerView(Context ctx, AttributeSet attrs, int style) {
		super(ctx, attrs, style);
	}

	/**
	 * Use this constructor to inflate the view from xml.
	 * @param ctx Usual context getting passed around.
	 * @param hand The die hand to initialize with.
	 * @return
	 */
	static public DiceRollerView instantiate(Context ctx) {
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		DiceRollerView v = (DiceRollerView)
				inflater.inflate(R.layout.fragment_dice_roller, null);
		v.setupDiceButtonsAdapter();
		return v;
	}

	/**
	 * Set the die hand to another one.  View should be reusable.
	 * @param hand The die hand to set.
	 */
	public void setHand(DieHand hand) {
		this.hand = hand;
		setupDiceButtons();
		setupDiceHand();
		setupDiceSum();
		setupResetButton();
		setupRollButton();
	}

	private void setupDiceSum() {
		DieSumTextView view = (DieSumTextView)
				this.findViewById(R.id.hand_sum_text_view);
		view.setDieHand(hand);
	}

	private void setupDiceHand() {
		GridView grid = (GridView) findViewById(R.id.dice_grid);
		OnItemClickListener cl;
		if (null != hand) {
			handAdapter = new DieViewDieHandAdapter(this.getContext(), hand);
			cl = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					DieView dieView = (DieView)view;
					hand.setSelected(position, !dieView.getSelected());
				}
			};
		}
		else {
			handAdapter = null;
			cl = null;
		}
		grid.setAdapter(handAdapter);
		grid.setOnItemClickListener(cl);
	}

	private void setupDiceButtonsAdapter() {
		GridView buttonGrid = (GridView) findViewById(R.id.dice_buttons_grid);
		DieViewArrayAdapter<Die> adapter = new DieViewArrayAdapter<Die>(
				getContext(), 0, Die.allLargestSizeDice());
		buttonGrid.setAdapter(adapter);
	}

	private void setupDiceButtons() {
		GridView buttonGrid = (GridView) findViewById(R.id.dice_buttons_grid);
		OnItemClickListener cl;
		if (hand != null) {
			cl = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View dieView,
						int position, long id) {
					Die die = (Die) parent.getAdapter().getItem(position);
					hand.addDie(die);
				}
			};
		}
		else {
			cl = null;
		}
		buttonGrid.setOnItemClickListener(cl);
	}

	private void setupResetButton() {
		Button resetButton = (Button) findViewById(R.id.reset_button);
		OnClickListener cl;
		if (hand != null) {
			cl = new OnClickListener() {
				@Override
				public void onClick(View v) {
					hand.clear();
				}
			};
		}
		else {
			cl = null;
		}
		resetButton.setOnClickListener(cl);
	}

	private void setupRollButton() {
		Button rollButton = (Button) findViewById(R.id.roll_button);
		OnClickListener cl;
		if (hand != null) {
			cl = new OnClickListener() {
				@Override
				public void onClick(View v) {
					hand.roll();
				}
			};
		}
		else {
			cl = null;
		}
		rollButton.setOnClickListener(cl);
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
