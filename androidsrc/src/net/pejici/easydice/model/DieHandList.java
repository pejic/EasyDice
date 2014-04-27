package net.pejici.easydice.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.pejici.java.WeakObservable;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;

/**
 * Represents the list of die hands
 * @author slobo
 *
 */
public class DieHandList extends WeakObservable {

	private List<DieHand> hands = new ArrayList<DieHand>();

	public DieHandList() {
	}

	/**
	 * Initializes with a single hand object.
	 */
	public DieHandList(DieHand dieHand) {
		this.hands.add(dieHand);
	}

	/**
	 * Loads from a serialized json.
	 * @param json The JsonReader for this object.
	 * @throws IOException 
	 */
	public DieHandList(JsonReader json) throws IOException {
		json.beginObject();
		if (!json.nextName().equals("hands")) {
			throw new IllegalStateException("Expected 'hands' in DieHandList");
		}
		json.beginArray();
		while (json.peek() != JsonToken.END_ARRAY) {
			hands.add(new DieHand(json));
		}
		json.endArray();
	}

	/**
	 * Creates a new die hand at index.
	 * @param index The index at which the die hand will be inserted.
	 * @return The newly created die hand.
	 */
	public void make(int index) {
		DieHand hand = new DieHand();
		hands.add(index, hand);
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the hand at the given index.
	 * @return The hand.
	 */
	public DieHand get(int index) {
		return hands.get(index);
	}

	/**
	 * Removes the die hand at the given index.
	 * @param index The index at which to remove the hand from.
	 * @return The hand removed.
	 */
	public void remove(int index) {
		hands.remove(index);
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns the number of hands.
	 */
	public int size() {
		return hands.size();
	}

	/**
	 * Writes the list out as json.
	 * @throws IOException 
	 */
	public void serialize(JsonWriter json) throws IOException {
		json.beginObject();
		json.name("hands");
		json.beginArray();
		for (DieHand hand : hands) {
			hand.serialize(json);
		}
		json.endArray();
		json.endObject();
	}
}
