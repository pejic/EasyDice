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
package net.pejici.easydice.view;

import java.util.Observable;
import java.util.Observer;

import net.pejici.easydice.R;
import net.pejici.easydice.model.DieHand;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class DieSumTextView extends TextView implements Observer {

	private DieHand hand;

	public DieSumTextView(Context context) {
		super(context);
	}

	public DieSumTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DieSumTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setDieHand(DieHand hand) {
		if (null != this.hand){
			this.hand.deleteObserver(this);
		}
		this.hand = hand;
		if (null != hand) {
			this.hand.addObserver(this);
		}
		updateLabels();
	}

	private void updateLabels() {
		if (null != hand && !hand.getDice().isEmpty()) {
			int sum = hand.sum();
			String format = getResources().getString(R.string.hand_sum_format_text);
			String value = String.format(format, String.valueOf(sum));
			setText(value);
		}
		else if (null != hand) {
			setText(getResources().getString(R.string.hand_sum_no_dice_text));
		}
		else {
			setText("");
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		updateLabels();
	}

}
