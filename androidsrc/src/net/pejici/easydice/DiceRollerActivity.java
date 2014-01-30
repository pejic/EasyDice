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
package net.pejici.easydice;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.pejici.easydice.adapter.DieViewArrayAdapter;
import net.pejici.easydice.adapter.DieViewDieHandAdapter;
import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.view.DieSumTextView;
import net.pejici.easydice.view.DieView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
		loadHand();
		setContentView(R.layout.activity_dice_roller);
		setupDiceButtons();
		setupDiceHand();
		setupDiceSum();
		setupResetButton();
		setupRollButton();
	}

	private void setupDiceSum() {
		DieSumTextView view = (DieSumTextView)
				findViewById(R.id.hand_sum_text_view);
		view.setDieHand(hand);
	}

	private void setupDiceHand() {
		GridView grid = (GridView) findViewById(R.id.dice_grid);
		handAdapter = new DieViewDieHandAdapter(this, hand);
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

	private File handFile() {
		File filesDir = this.getFilesDir();
		File handFile = new File(filesDir, "hand.json");
		return handFile;
	}

	private void loadHand() {
		File handFile = handFile();
		if (handFile.isFile()) {
			try {
				FileReader fileReader = new FileReader(handFile);
				JsonReader json = new JsonReader(fileReader);
				hand = new DieHand(json);
			}
			catch (IOException e) {
				Log.d("DiceRollerActivity", e.toString());
				hand = new DieHand();
			}
			catch (IllegalStateException e) {
				Log.d("DiceRollerActivity", e.toString());
				hand = new DieHand();
			}
		}
		else {
			hand = new DieHand();
		}
	}

	private void saveHand() {
		File handFile = handFile();
		boolean success = false;
		try {
			FileWriter fileWriter = new FileWriter(handFile);
			JsonWriter json = new JsonWriter(fileWriter);
			hand.serialize(json);
			json.close();
			success = true;
		}
		catch (IOException e) {
			Log.d("DiceRollerActivity", e.toString());
		}
		catch (IllegalStateException e) {
			Log.d("DiceRollerActivity", e.toString());
		}
		finally {
			if (!success) {
				handFile().delete();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dice_roller, menu);
		return true;
	}

	@Override
	protected void onPause() {
		saveHand();
		super.onPause();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.action_about) {
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			return true;
		}
		else {
			return super.onMenuItemSelected(featureId, item);
		}
	}
}
