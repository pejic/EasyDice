package net.pejici.easydice.model;

import junit.framework.TestCase;

public class DieTests extends TestCase {

	public void testSetProps() {
		Die die = new Die(6, 2, 10, -10);
		assertEquals(6, die.size);
		assertEquals(2, die.number);
		assertEquals(10, die.multiplier);
		assertEquals(-10, die.offset);
		die = new Die(6, 2);
		assertEquals(6, die.size);
		assertEquals(2, die.number);
		assertEquals(1, die.multiplier);
		assertEquals(0, die.offset);
	}

	public void testInvalidSize() {
		try {
			new Die(0, 2, 10, -10);
		}
		catch (IllegalArgumentException e) {
			return; // OK
		}
		assertTrue(false); // no exception
	}

	public void testInvalidNumber0() {
		try {
			new Die(6, 0, 10, -10);
		}
		catch (IllegalArgumentException e) {
			return; // OK
		}
		assertTrue(false); // no exception
	}

	public void testInvalidNumberBiggerThanSize() {
		try {
			new Die(6, 7, 10, -10);
		}
		catch (IllegalArgumentException e) {
			return; // OK
		}
		assertTrue(false); // no exception
	}

	public void testValidNumber1() {
		new Die(6, 1, 10, -10);
		// should not throw an exception
	}

	public void testValidNumberEqualToSize() {
		new Die(6, 6, 10, -10);
		// should not throw an exception
	}

	public void testInvalidMultiplier() {
		try {
			new Die(6, 2, 0, -10);
		}
		catch (IllegalArgumentException e) {
			return; // OK
		}
		assertTrue(false); // no exception
	}

	public void testEquals() {
		Die die1 = new Die(6, 2, 10, -10);
		Die die2 = new Die(6, 2, 10, -10);
		assertEquals(die1, die2);
	}

	public void testUnequals() {
		Die die1 = new Die(6, 2, 1, -10);
		Die die2 = new Die(6, 3, 1, -10);
		assertTrue(!die1.equals(die2));
	}

	public void testValueMult1() {
		assertEquals(2, new Die(6, 2).value());
	}

	public void testValueMult10_1() {
		assertEquals(0, new Die(10, 1, 10, -10).value());
	}

	public void testValueMult10_2() {
		assertEquals(10, new Die(10, 2, 10, -10).value());
	}

	public void testValueMult10_10() {
		assertEquals(90, new Die(10, 10, 10, -10).value());
	}

}
