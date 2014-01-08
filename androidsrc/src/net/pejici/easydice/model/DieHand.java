package net.pejici.easydice.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;

public class DieHand extends Observable {

	public static final String diceKey = "dice";

	private static class SelectableDie {
		public static final String dieKey = "die";
		public static final String selectedKey = "selected";

		public boolean selected;
		public Die die;
		public SelectableDie(Die die) {
			this.die = die;
			this.selected = false;
		}

		private JsonReader nextName(JsonReader json, String key)
				throws IOException {
			if (!json.nextName().equals(key)) {
				throw new IllegalArgumentException(key + " not found in json.");
			}
			return json;
		}

		public SelectableDie(JsonReader json) throws IOException {
			json.beginObject();
			this.die = new Die(nextName(json, dieKey));
			this.selected = nextName(json, selectedKey).nextBoolean();
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
		while (json.peek() != JsonToken.END_ARRAY) {
			dice.add(new SelectableDie(json));
		}
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

	void serialize(JsonWriter json) throws IOException {
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
