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
		this.hand = hand;
		hand.addObserver(this);
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
