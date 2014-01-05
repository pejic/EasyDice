package net.pejici.easydice;

import net.pejici.easydice.adapter.DieViewArrayAdapter;
import net.pejici.easydice.adapter.DieViewDieHandAdapter;
import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class DiceRollerActivity extends Activity {

	DieHand hand;
	DieViewDieHandAdapter handAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hand = new DieHand();
		setContentView(R.layout.activity_dice_roller);
		setupDiceButtons();
		setupDiceHand();
		setupResetButton();
		setupRollButton();
	}

	private void setupDiceHand() {
		GridView grid = (GridView) findViewById(R.id.dice_grid);
		handAdapter = new DieViewDieHandAdapter(this, hand);
		grid.setAdapter(handAdapter);
	}

	private void setupDiceButtons() {
		GridView buttonGrid = (GridView) findViewById(R.id.dice_buttons_grid);
		DieViewArrayAdapter<Die> adapter = new DieViewArrayAdapter<Die>(
				this, 0, Die.allLargestSizeDice());
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

	private void setupResetButton() {
		Button resetButton = (Button) findViewById(R.id.reset_button);
		OnClickListener cl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				hand.clear();
			}
		};
		resetButton.setOnClickListener(cl);
	}

	private void setupRollButton() {
		Button rollButton = (Button) findViewById(R.id.roll_button);
		OnClickListener cl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				hand.roll();
			}
		};
		rollButton.setOnClickListener(cl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dice_roller, menu);
		return true;
	}

}
