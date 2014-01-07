package net.pejici.easydice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DieHand extends Observable {

	private static class SelectableDie {
		public boolean selected;
		public Die die;
		public SelectableDie(Die die) {
			this.die = die;
			this.selected = false;
		}
		public SelectableDie roll() {
			Die newDie = die.roll();
			SelectableDie sDie = new SelectableDie(newDie);
			sDie.selected = this.selected;
			return sDie;
		}
	}

	private List<SelectableDie> dice = new ArrayList<SelectableDie>();

	public List<Die> getDice() {
		ArrayList<Die> newDice = new ArrayList<Die>();
		for (SelectableDie sDie : dice){
			newDice.add(sDie.die);
		}
		return newDice;
	}

	public void addDie(Die die) {
		dice.add(new SelectableDie(die));
		setChanged();
		notifyObservers();
	}

	public void clear() {
		dice.clear();
		setChanged();
		notifyObservers();
	}

	public void roll() {
		boolean hasSelected = false;
		for (SelectableDie die : dice) {
			if (die.selected) {
				hasSelected = true;
				break;
			}
		}
		for (int i = 0; i < dice.size(); i++) {
			SelectableDie die = dice.get(i);
			if (!hasSelected || die.selected) {
				dice.set(i, die.roll());
			}
		}
		setChanged();
		notifyObservers();
	}

	public void setSelected(int position, boolean selected) {
		dice.get(position).selected = selected;
		setChanged();
		notifyObservers();
	}

	public boolean isSelected(int position) {
		return dice.get(position).selected;
	}

	public int sum() {
		int sum = 0;
		for (SelectableDie die : dice) {
			sum += die.die.value();
		}
		return sum;
	}
}
