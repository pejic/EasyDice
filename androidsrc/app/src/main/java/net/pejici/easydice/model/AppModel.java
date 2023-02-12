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
package net.pejici.easydice.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import android.util.Log;

/**
 * Represents the state of the application.
 *
 * @author slobo
 *
 */
public class AppModel {

	private static AppModel instance = null;

	private DieHandList handList = null;
	static private File filesDir = null;

	private AppModel() {
	}

	static public AppModel getInstance(Context context) {
		if (instance == null) {
			instance = new AppModel();
			filesDir = context.getFilesDir();
			instance.load();
		}
		return instance;
	}

	public DieHandList getHandList() {
		return this.handList;
	}

	private DieHand loadHand() {
		File handFile = handFile();
		DieHand hand = null;
		if (handFile.isFile()) {
			try {
				FileReader fileReader = new FileReader(handFile);
				JsonReader json = new JsonReader(fileReader);
				hand = new DieHand(json);
			}
			catch (IOException e) {
				Log.d("AppModel", e.toString());
			}
			catch (IllegalStateException e) {
				Log.d("AppModel", e.toString());
			}
		}
		else {
			hand = new DieHand();
		}
		return hand;
	}

	private void load() {
		File handFile = handFile();
		handList = null;
		if (handFile.isFile()) {
			try {
				FileReader fileReader = new FileReader(handFile);
				JsonReader json = new JsonReader(fileReader);
				handList = new DieHandList(json);
			}
			catch (IOException e) {
				Log.d("AppModel", e.toString());
			}
			catch (IllegalStateException e) {
				DieHand hand = loadHand();
				if (hand != null) {
					handList = new DieHandList(hand);
				}
				else {
					Log.d("AppModel", e.toString());
				}
			}
		}
		if (null == handList) {
			handList = new DieHandList();
			handList.make(0);
		}
	}

	public void save() {
		File handFile = handFile();
		boolean success = false;
		try {
			FileWriter fileWriter = new FileWriter(handFile);
			JsonWriter json = new JsonWriter(fileWriter);
			handList.serialize(json);
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

	private File handFile() {
		File handFile = new File(filesDir, "hand.json");
		return handFile;
	}

}
