package net.pejici.easydice.adapter;
import static net.pejici.androidutil.LayoutUtil.dpToPx;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import net.pejici.easydice.view.DieView;


public class DieViewDieHandAdapter extends DieHandAdapter {

	public DieViewDieHandAdapter(Context context, DieHand hand) {
		super(context, hand);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Die die = getDie(position);
		boolean selected = getSelected(position);
		if (null == die) {
			return null;
		}
		DieView dieView = (DieView) convertView;
		if (null == dieView) {
			dieView = new DieView(getContext());
			int px = dpToPx(dieView, 66);
			dieView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, px));
		}
		dieView.setDie(die);
		dieView.setSelected(selected);
		return dieView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}
}
