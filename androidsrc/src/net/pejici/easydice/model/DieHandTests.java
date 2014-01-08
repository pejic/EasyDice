package net.pejici.easydice.model;

import java.util.List;

import junit.framework.TestCase;

public class DieHandTests extends TestCase {

	public void testAddDie() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 2));
		List<Die> dice = hand.getDice();
		assertEquals(1, dice.size());
		assertEquals(new Die(6, 2), dice.get(0));
	}

	public void testClear() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 2));
		hand.addDie(new Die(6, 2));
		hand.clear();
		assertTrue(hand.getDice().isEmpty());
	}

	public void testClearEmpty() {
		DieHand hand = new DieHand();
		hand.clear();
		assertTrue(hand.getDice().isEmpty());
	}

	private static class MockDie extends Die {
		public MockDie(int size, int number) {
			super(size, number);
		}

		public MockDie(int size, int number, int multiplier, int offset) {
			super(size, number, multiplier, offset);
		}

		public int rollCalls = 0;
		public Die rolledDie = null;

		public Die roll() {
			rollCalls++;
			return (rolledDie = super.roll());
		}
	}
	
	public void testRoll() {
		MockDie die = new MockDie(6, 2);
		DieHand hand = new DieHand();
		hand.addDie(die);
		hand.roll();
		assertTrue(die.rollCalls >= 1);
		List<Die> dice = hand.getDice();
		assertEquals(1, dice.size());
		assertSame(die.rolledDie, dice.get(0));
	}

	public void testSumEmpty() {
		DieHand hand = new DieHand();
		assertEquals(0, hand.sum());
	}

	public void testSum1() {
		Die die1 = new Die(6, 2);
		DieHand hand = new DieHand();
		hand.addDie(die1);
		assertEquals(2, hand.sum());
	}

	public void testSum100_1() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(10, 1)); // "1"
		hand.addDie(new Die(10, 1, 10, -10)); // "00"
		assertEquals(1, hand.sum());
	}

	public void testSum100_100() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(10, 10)); // "10"
		hand.addDie(new Die(10, 10, 10, -10)); // "90"
		assertEquals(100, hand.sum());
	}

	public void testSum100_99() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(10, 9)); // "10"
		hand.addDie(new Die(10, 10, 10, -10)); // "90"
		assertEquals(99, hand.sum());
	}

	public void testDefaultSelection() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 2));
		assertFalse(hand.isSelected(0));
	}

	public void testSetSelected() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 2));
		hand.addDie(new Die(6, 2));
		hand.addDie(new Die(6, 3));
		hand.setSelected(1, true);
		assertFalse(hand.isSelected(0));
		assertTrue(hand.isSelected(1));
		assertFalse(hand.isSelected(2));
	}

	public void testRollSelectedOne() {
		DieHand hand = new DieHand();
		MockDie die1 = new MockDie(6, 2);
		MockDie die2 = new MockDie(6, 3);
		MockDie die3 = new MockDie(6, 4);
		hand.addDie(die1);
		hand.addDie(die2);
		hand.addDie(die3);
		hand.setSelected(1, true);
		hand.roll();
		assertTrue(die1.rollCalls == 0);
		assertTrue(die2.rollCalls >= 1);
		assertTrue(die3.rollCalls == 0);
	}

	public void testRollSelectedAll() {
		DieHand hand = new DieHand();
		MockDie die1 = new MockDie(6, 2);
		MockDie die2 = new MockDie(6, 3);
		MockDie die3 = new MockDie(6, 4);
		hand.addDie(die1);
		hand.addDie(die2);
		hand.addDie(die3);
		hand.setSelected(0, true);
		hand.setSelected(1, true);
		hand.setSelected(2, true);
		hand.roll();
		assertTrue(die1.rollCalls >= 1);
		assertTrue(die2.rollCalls >= 1);
		assertTrue(die3.rollCalls >= 1);
	}

	public void testEqualsEmpty() {
		DieHand h1 = new DieHand();
		DieHand h2 = new DieHand();
		assertTrue(h1.equals(h2));
		assertTrue(h2.equals(h1));
	}

	public void testUnequalsEmpty() {
		DieHand h1 = new DieHand();
		h1.addDie(new Die(6, 2));
		DieHand h2 = new DieHand();
		assertFalse(h1.equals(h2));
		assertFalse(h2.equals(h1));
	}

	public void testEquals() {
		DieHand h1 = new DieHand();
		h1.addDie(new Die(6, 2));
		DieHand h2 = new DieHand();
		h2.addDie(new Die(6, 2));
		assertTrue(h1.equals(h2));
		assertTrue(h2.equals(h1));
	}

	public void testUnequalsSameNumber() {
		DieHand h1 = new DieHand();
		h1.addDie(new Die(6, 2));
		DieHand h2 = new DieHand();
		h2.addDie(new Die(6, 3));
		assertFalse(h1.equals(h2));
		assertFalse(h2.equals(h1));
	}

	public void testUnequalsMore() {
		DieHand h1 = new DieHand();
		h1.addDie(new Die(6, 2));
		DieHand h2 = new DieHand();
		h2.addDie(new Die(6, 2));
		h2.addDie(new Die(6, 3));
		assertFalse(h1.equals(h2));
		assertFalse(h2.equals(h1));
	}

	public void testUnequalsDisordered() {
		DieHand h1 = new DieHand();
		h1.addDie(new Die(6, 3));
		h1.addDie(new Die(6, 2));
		DieHand h2 = new DieHand();
		h2.addDie(new Die(6, 2));
		h2.addDie(new Die(6, 3));
		assertFalse(h1.equals(h2));
		assertFalse(h2.equals(h1));
	}
}
