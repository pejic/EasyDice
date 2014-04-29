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
	 * Returns the index of the given hand.
	 */
	public int indexOfIdentical(DieHand hand) {
		int i = 0;
		for (i = 0; i < hands.size(); i++) {
			if (hands.get(i) == hand) {
				return i;
			}
		}
		return -1;
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
