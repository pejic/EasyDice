package net.pejici.easydice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Die {

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
		return dice;
	}

}
