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

import java.util.Observable;
import java.util.Observer;

import junit.framework.TestCase;

public class DieHandListTests extends TestCase implements Observer {

	private boolean updateHappened = false;
	DieHandList list = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		list = new DieHandList();
	}

	public void testInitWithHand() {
		DieHand hand = new DieHand();
		hand.addDie(new Die(6, 2));
		list = new DieHandList(hand);
		assertEquals(1, list.size());
		assertEquals(hand, list.get(0));
	}

	public void testMake() {
		startObserving();
		list.make(0);
		assertObserved();
		assertEquals(1, list.size());
	}

	public void testMakeBeyondEnd() {
		try {
			list.make(1);
			fail("No exception");
		}
		catch (IndexOutOfBoundsException e) {
			// GOOD
		}
	}

	public void testMakeNegative() {
		try {
			list.make(-1);
			fail("No exception");
		}
		catch (IndexOutOfBoundsException e) {
			// GOOD
		}
	}

	public void testGet() {
		list.make(0);
		startObserving();
		DieHand hand0b = list.get(0);
		assertNotNull(hand0b);
		assertNotObserved();
	}

	public void testGetMultiple() {
		list.make(0);
		list.make(1);
		startObserving();
		DieHand hand0 = list.get(0);
		DieHand hand1 = list.get(1);
		if (hand0 == hand1) {
			fail("Same instance returned.");
		}
		assertNotObserved();
	}

	public void testGetNegative() {
		try {
			list.get(-1);
			fail("No exception");
		}
		catch (IndexOutOfBoundsException e) {
			// GOOD
		}
	}

	public void testGetBeyondEnd() {
		try {
			list.make(0);
			list.get(1);
			fail("No exception");
		}
		catch (IndexOutOfBoundsException e) {
			// GOOD
		}
	}

	public void testRemove() {
		list.make(0);
		list.make(1);
		DieHand hand1 = list.get(1);
		startObserving();
		list.remove(0);
		assertEquals(1, list.size());
		assertObserved();
		DieHand hand1b = list.get(0);
		if (hand1 != hand1b) {
			fail("Removed the wrong object.");
		}
	}

	public void testIndexOf() {
		list.make(0);
		list.make(1);
		DieHand hand1 = list.get(1);
		int index = list.indexOfIdentical(hand1);
		assertEquals(1, index);
	}

	public void testRemoveNegative() {
		try {
			list.remove(-1);
			fail("No exception");
		}
		catch (IndexOutOfBoundsException e) {
			// GOOD
		}
	}

	public void testRemoveBeyondEnd() {
		try {
			list.make(0);
			list.remove(1);
			fail("No exception");
		}
		catch (IndexOutOfBoundsException e) {
			// GOOD
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		updateHappened = true;
	}

	private void startObserving() {
		list.addObserver(this);
	}

	private void assertObserved() {
		if (!updateHappened) {
			fail("Didn't observe the change.");
		}
	}

	private void assertNotObserved() {
		if (updateHappened) {
			fail("No update should have triggered.");
		}
	}

}
