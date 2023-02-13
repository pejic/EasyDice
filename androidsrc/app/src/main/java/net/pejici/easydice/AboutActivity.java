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

import android.content.Intent;
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
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.core.app.NavUtils;

public class AboutActivity extends Activity {

	static public enum Type {
		OpenSource(R.string.open_source_title, R.raw.open_source, false, true),
		About(R.string.about_activity_title, R.raw.gpl_licence, true, false);

		private Type(@StringRes int title, @RawRes int bigText, boolean isHtml, boolean clearCopyright) {
			this.title = title;
			this.bigText = bigText;
			this.isHtml = isHtml;
			this.clearCopyright = clearCopyright;
		}

		private @StringRes int title;
		private @RawRes int bigText;
		private boolean isHtml;
		private boolean clearCopyright;

		public static Type valueOf(int ordinal) {
			try {
				return Type.values()[ordinal];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IllegalArgumentException(
						"ordinal must be between 0 and " + (Type.values().length - 1));
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getType().title);
		setContentView(R.layout.activity_about);
		clearCopyright();
		insertText();
		// Show the Up button in the action bar.
		setupActionBar();
	}

	private Type getType() {
		return Type.valueOf(this.getIntent().getIntExtra(ABOUT_ACTIVITY_TYPE_KEY, 0));
	}

	public static void prepareIntent(Intent intent, Type type) {
		intent.putExtra(ABOUT_ACTIVITY_TYPE_KEY, type.ordinal());
	}

	private static final String ABOUT_ACTIVITY_TYPE_KEY = "about_activity_type";

	private void clearCopyright() {
		if (getType().clearCopyright) {
			TextView title = findViewById(R.id.copyright_title);
			title.setText("");
			TextView text = findViewById(R.id.copyright_text);
			text.setText("");
		}
	}

	private void insertText() {
		InputStream bigTextIn = getResources().openRawResource(getType().bigText);
		ByteArrayOutputStream bigTextBaos = new ByteArrayOutputStream();
		TextView textView = findViewById(R.id.licence_text_view);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		try {
			IOUtils.copy(bigTextIn, bigTextBaos);
			String bigText = new String(
					bigTextBaos.toByteArray(), StandardCharsets.UTF_8);
			if (getType().isHtml) {
				textView.setText(Html.fromHtml(bigText, 0));
			} else {
				textView.setText(bigText);
			}
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
