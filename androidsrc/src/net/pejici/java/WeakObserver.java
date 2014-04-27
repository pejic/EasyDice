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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Observable;
import java.util.Observer;

/**
 * A wrapper around any observer object that makes a weak reference.  This is
 * used to avoid having the model hang on to the views.
 *
 * @author slobo
 */
public class WeakObserver extends WeakReference<Observer> implements Observer {

	/**
	 * Creates a weak reference to an observer.
	 * 
	 * @param observer The observer that is to be wrapped as a weak reference.
	 */
	public WeakObserver(Observer observer) {
		super(observer);
	}

	/**
	 * Creates a weak reference to an observer.
	 *
	 * @param observer The observer that is to be wrapped as a weak reference.
	 * @param q See the weak reference documentation.
	 */
	public WeakObserver(Observer observer, ReferenceQueue<Observer> q) {
		super(observer, q);
	}

	/**
	 * Calls the observable method on the contained observer.  If the object
	 * has been deallocated then nothing is done.
	 */
	@Override
	public void update(Observable observable, Object data) {
		Observer observer = this.get();
		if (null != observer) {
			observer.update(observable, data);
		}
	}

}
