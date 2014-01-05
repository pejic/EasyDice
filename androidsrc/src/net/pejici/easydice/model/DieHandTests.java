package net.pejici.easydice.model;

import java.util.List;

import junit.framework.TestCase;

public class DieHandTests extends TestCase {

	public void testAddDie() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 20));
		List<Die> dice = hand.getDice();
		assertEquals(1, dice.size());
		assertEquals(new Die(6, 20), dice.get(0));
	}

	public void testClear() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 20));
		hand.addDie(new Die(6, 20));
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
}
