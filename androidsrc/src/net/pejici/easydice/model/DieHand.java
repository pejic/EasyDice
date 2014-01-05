package net.pejici.easydice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DieHand extends Observable {

	private List<Die> dice = new ArrayList<Die>();

	public List<Die> getDice() {
		return dice;
	}

	public void addDie(Die die) {
		dice.add(die);
		setChanged();
		notifyObservers();
	}

	public void clear() {
		dice.clear();
		setChanged();
		notifyObservers();
	}

	public void roll() {
		for (int i = 0; i < dice.size(); i++) {
			Die oldDie = dice.get(i);
			dice.set(i, oldDie.roll());
		}
		setChanged();
		notifyObservers();
	}

	public Number sum() {
		return 0;
	}
}
