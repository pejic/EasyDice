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

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class DieHand extends WeakObservable {

	public static final String diceKey = "dice";

	private static JsonReader nextName(JsonReader json, String key)
			throws IOException {
		if (!json.nextName().equals(key)) {
			throw new IllegalStateException(key + " expected in json.");
		}
		return json;
	}

	private static class SelectableDie {
		public static final String dieKey = "die";
		public static final String selectedKey = "selected";

		public boolean selected;
		public Die die;
		public SelectableDie(Die die) {
			this.die = die;
			this.selected = false;
		}

		public SelectableDie(JsonReader json) throws IOException {
			json.beginObject();
			this.die = new Die(DieHand.nextName(json, dieKey));
			this.selected = DieHand.nextName(json, selectedKey).nextBoolean();
			json.endObject();
		}

		public SelectableDie roll() {
			Die newDie = die.roll();
			SelectableDie sDie = new SelectableDie(newDie);
			sDie.selected = this.selected;
			return sDie;
		}
		@Override
		public int hashCode() {
			return die.hashCode() + (selected ? 1 : 1337);
		}
		@Override
		public boolean equals(Object o) {
			if (o instanceof SelectableDie) {
				SelectableDie right = (SelectableDie)o;
				return (this.selected == right.selected
						&& this.die.equals(right.die));
			}
			return false;
		}

		public void serialize(JsonWriter json) throws IOException {
			json.beginObject();
			json.name(dieKey);
			die.serialize(json);
			json.name(selectedKey);
			json.value(selected);
			json.endObject();
		}
	}

	private List<SelectableDie> dice = new ArrayList<SelectableDie>();


	public DieHand() {
		super();
	}

	public DieHand(JsonReader json) throws IOException {
		super();
		json.beginObject();
		nextName(json, "dice");
		json.beginArray();
		while (json.peek() != JsonToken.END_ARRAY) {
			dice.add(new SelectableDie(json));
		}
		json.endArray();
		json.endObject();
	}

	public List<Die> getDice() {
		ArrayList<Die> newDice = new ArrayList<Die>();
		for (SelectableDie sDie : dice){
			newDice.add(sDie.die);
		}
		return newDice;
	}

	public void addDie(Die die) {
		dice.add(new SelectableDie(die));
		setChanged();
		notifyObservers();
	}

	public void clear() {
		dice.clear();
		setChanged();
		notifyObservers();
	}

	public void roll() {
		boolean hasSelected = false;
		for (SelectableDie die : dice) {
			if (die.selected) {
				hasSelected = true;
				break;
			}
		}
		for (int i = 0; i < dice.size(); i++) {
			SelectableDie die = dice.get(i);
			if (!hasSelected || die.selected) {
				dice.set(i, die.roll());
			}
		}
		setChanged();
		notifyObservers();
	}

	public void setSelected(int position, boolean selected) {
		dice.get(position).selected = selected;
		setChanged();
		notifyObservers();
	}

	public boolean isSelected(int position) {
		return dice.get(position).selected;
	}

	public int sum() {
		int sum = 0;
		for (SelectableDie die : dice) {
			sum += die.die.value();
		}
		return sum;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (SelectableDie die : dice) {
			hash += die.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DieHand) {
			DieHand right = (DieHand) o;
			return this.dice.equals(right.dice);
		}
		return false;
	}

	public void serialize(JsonWriter json) throws IOException {
		json.beginObject();
		json.name(diceKey);
		json.beginArray();
		for (SelectableDie die : dice) {
			die.serialize(json);
		}
		json.endArray();
		json.endObject();
	}
}
