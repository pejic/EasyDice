package net.pejici.easydice.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;
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
			instance.load();
			filesDir = context.getFilesDir();
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
