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
	};

}
