package net.pejici.easydice;

import net.pejici.easydice.adapter.DieViewArrayAdapter;
import net.pejici.easydice.model.Die;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

public class DiceRollerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dice_roller);
		GridView buttonGrid = (GridView) findViewById(R.id.dice_buttons_grid);
		DieViewArrayAdapter<Die> adapter = new DieViewArrayAdapter<Die>(
				this, 0, Die.allLargestSizeDice());
		buttonGrid.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dice_roller, menu);
		return true;
	}

}
