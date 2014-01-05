package net.pejici.easydice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Die {

	public final int size;
	public final int number;
	public final int multiplier;

	public Die(int size, int number) {
		super();
		this.size = size;
		this.number = number;
		this.multiplier = 1;
	}

	public Die(int size, int number, int multiplier) {
		super ();
		this.size = size;
		this.number = number;
		this.multiplier = multiplier;
	}

	@Override
	public String toString() {
		return number + "/d" + size;
	}

	public Die roll() {
		Random r = getRandom();
		int newNumber = r.nextInt(size) + 1;
		return new Die(size, newNumber, multiplier);
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
		dice.add(new Die(10, 10, 10));
		dice.add(new Die(12, 12));
		dice.add(new Die(20, 20));
		return dice;
	}

}
