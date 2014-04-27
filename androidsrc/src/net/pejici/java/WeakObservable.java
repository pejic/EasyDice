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
package net.pejici.java;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * A version of Observable using weak references.  Note that the weak refernce
 * objects get removed on the notifyObservers following the deallocation of an
 * observer, not right away.  The observers do not need to change, but the
 * Observable need to change to WeakObservable.
 *
 * @author slobo
 *
 */
public class WeakObservable extends Observable {

	private Vector<WeakObserver> weakObservers = new Vector<WeakObserver>();

	public WeakObservable() {
	}

	@Override
	public void addObserver(Observer observer) {
		WeakObserver weak = new WeakObserver(observer);
		weakObservers.add(weak);
		super.addObserver(weak);
	}

	@Override
	public synchronized void deleteObserver(Observer observer) {
		WeakObserver weak = null;
		Vector<WeakObserver> nulls = new Vector<WeakObserver>();
		for (WeakObserver someWeak : weakObservers) {
			Observer someObs = someWeak.get();
			if (null == someObs) {
				nulls.add(someWeak);
			}
			else if (someWeak.get() == observer) {
				weak = someWeak;
			}
		}
		for (WeakObserver someNull : nulls) {
			weakObservers.remove(someNull);
			super.deleteObserver(someNull);
		}
		if (null != weak) {
			weakObservers.remove(weak);
			super.deleteObserver(observer);
		}
	}

	@Override
	public synchronized void deleteObservers() {
		weakObservers.removeAllElements();
		super.deleteObservers();
	}

	public void notifyObservers() {
		Vector<WeakObserver> nulls = new Vector<WeakObserver>();
		for (WeakObserver someWeak : weakObservers) {
			Observer someObs = someWeak.get();
			if (null == someObs) {
				nulls.add(someWeak);
			}
		}
		for (WeakObserver someNull : nulls) {
			weakObservers.remove(someNull);
			super.deleteObserver(someNull);
		}
		super.notifyObservers();
	}

}
