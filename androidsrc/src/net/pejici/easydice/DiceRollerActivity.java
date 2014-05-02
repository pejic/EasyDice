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

import net.pejici.easydice.model.AppModel;
import net.pejici.easydice.model.DieHandList;
import net.pejici.easydice.pageradapter.DieHandListAdapter;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class DiceRollerActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppModel model = AppModel.getInstance(this); // loads if not loaded
		setContentView(R.layout.activity_dice_roller);
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		DieHandListAdapter adapter = new DieHandListAdapter(
				getSupportFragmentManager(), model.getHandList());
		pager.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dice_roller, menu);
		return true;
	}

	@Override
	protected void onPause() {
		AppModel.getInstance(this).save();
		super.onPause();
	}

	private DieHandList getDieHandList() {
		AppModel model = AppModel.getInstance(this);
		DieHandList list = model.getHandList();
		return list;
	}

	private ViewPager getViewPager() {
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		return pager;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.action_add_hand) {
			int count = getDieHandList().size();
			if (count > 0) {
				getDieHandList().make(getViewPager().getCurrentItem()+1);
			}
			else {
				getDieHandList().make(0);
			}
			return true;
		}
		else if (item.getItemId() == R.id.action_remove_hand) {
			int count = getDieHandList().size();
			if (count > 0) {
				getDieHandList().remove(getViewPager().getCurrentItem());
			}
			else {
				// TODO: show some message, but for now at least don't crash.
			}
			return true;
		}
		else if (item.getItemId() == R.id.action_about) {
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			return true;
		}
		else {
			return super.onMenuItemSelected(featureId, item);
		}
	}
}
