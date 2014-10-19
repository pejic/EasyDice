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
import java.util.Random;

import android.util.JsonReader;
import android.util.JsonWriter;

public class Die {

	public static final String sizeKey = "size";
	public static final String numberKey = "number";
	public static final String multiplierKey = "multiplier";
	public static final String offsetKey = "offset";

	public final int size;
	public final int number;
	public final int multiplier;
	public final int offset;

	public Die(int size, int number) {
		super();
		this.size = size;
		this.number = number;
		this.multiplier = 1;
		this.offset = 0;
		testIllegalState();
	}

	public Die(int size, int number, int multiplier, int offset) {
		super();
		this.size = size;
		this.number = number;
		this.multiplier = multiplier;
		this.offset = offset;
		testIllegalState();
	}

	private JsonReader nextName(JsonReader json, String key)
			throws IOException {
		if (!json.nextName().equals(key)) {
			throw new IllegalStateException(key + " expected in json.");
		}
		return json;
	}

	public Die(JsonReader json) throws IOException {
		super();
		json.beginObject();
		this.size = nextName(json, sizeKey).nextInt();
		this.number = nextName(json, numberKey).nextInt();
		this.multiplier = nextName(json, multiplierKey).nextInt();
		this.offset = nextName(json, offsetKey).nextInt();
		json.endObject();
	}

	private void testIllegalState() {
		if (size == 0) {
			throw new IllegalArgumentException("Size cannot be 0");
		}
		if (multiplier == 0) {
			throw new IllegalArgumentException("Multiplier cannot be 0");
		}
		if (number < 1) {
			throw new IllegalArgumentException("Number cannot be less than 1");
		}
		if (number > size) {
			throw new IllegalArgumentException("Number cannot greater than size");
		}
	}

	@Override
	public String toString() {
		return number + "/d" + size;
	}

	public Die roll() {
		Random r = getRandom();
		int newNumber = r.nextInt(size) + 1;
		return new Die(size, newNumber, multiplier, offset);
	}

	public int value() {
		return (number * multiplier) + offset;
	}

	@Override
	public int hashCode() {
		return number * 307 + this.size * 211 + this.offset *23 + multiplier;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Die) {
			Die right = (Die) o;
			return this.multiplier == right.multiplier
					&& this.size == right.size
					&& this.number == right.number
					&& this.offset == right.offset;
		}
		return false;
	}

	public void serialize(JsonWriter json) throws IOException {
		json.beginObject();
		json.name(sizeKey).value(size);
		json.name(numberKey).value(number);
		json.name(multiplierKey).value(multiplier);
		json.name(offsetKey).value(offset);
		json.endObject();
	}

	static private Random random;
	static private Random getRandom() {
		if (null == random) {
			random = new Random();
		}
		return random;
	}

	static public List<Die> allLargestSizeDice() {
		List<Die> dice = new ArrayList<Die>();
		dice.add(new Die(4,4));
		dice.add(new Die(6,6));
		dice.add(new Die(8,8));
		dice.add(new Die(10, 10));
		dice.add(new Die(10, 1, 10, -10));
		dice.add(new Die(12, 12));
		dice.add(new Die(20, 20));
		dice.add(new Die(30, 30));
		return dice;
	}

}
