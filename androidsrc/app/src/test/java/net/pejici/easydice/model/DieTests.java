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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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

	private void serializeAndEquals(Die d1) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new PrintWriter(baos);
		d1.serialize(new JsonWriter(out));
		out.flush();
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		InputStreamReader rd = new InputStreamReader(bais);
		Die d2 = new Die(new JsonReader(rd));
		assertEquals(d1, d2);
	}

	public void testJson() throws IOException {
		serializeAndEquals(new Die(6, 2));
	}

	public void testJsonMultiplier() throws IOException {
		serializeAndEquals(new Die(6, 2, 10, -10));
	}
}
