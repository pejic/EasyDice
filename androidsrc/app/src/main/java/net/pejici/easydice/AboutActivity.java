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

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import androidx.core.app.NavUtils;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		insertLicence();
		// Show the Up button in the action bar.
		setupActionBar();
	}

	private void insertLicence() {
		InputStream gplLicenceIn = getResources().openRawResource(R.raw.gpl_licence);
		ByteArrayOutputStream gplLicenceBaos = new ByteArrayOutputStream();
		TextView textView = findViewById(R.id.licence_text_view);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		try {
			IOUtils.copy(gplLicenceIn, gplLicenceBaos);
			String gplLicenceHtml = new String(
					gplLicenceBaos.toByteArray(), StandardCharsets.UTF_8);
			textView.setText(Html.fromHtml(gplLicenceHtml, 0));
		} catch (IOException e) {
			textView.setText(Html.fromHtml(
					"<a href=\"https://www.gnu.org/licenses/gpl-3.0.en.html\">GNU General Public Licence</a>",
					0));
		}

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		Optional.ofNullable(getActionBar())
				.ifPresent(actionBar -> {
					actionBar.setDisplayHomeAsUpEnabled(true);
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
