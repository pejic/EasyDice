package net.pejici.easydice.adapter;

import java.util.Observable;
import java.util.Observer;

import net.pejici.easydice.model.Die;
import net.pejici.easydice.model.DieHand;
import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.widget.ListAdapter;

public abstract class DieHandAdapter extends DataSetObservable
		implements Observer, ListAdapter
{

	private DieHand hand;
	private Context context;

	public DieHandAdapter(Context context, DieHand hand) {
		super();
		this.context = context;
		this.hand = hand;
		this.hand.addObserver(this);
	}

	public Context getContext() {
		return context;
	}

	@Override
	public int getCount() {
		return hand.getDice().size();
	}

	@Override
	public Object getItem(int position) {
		return getDie(position);
	}

	public Die getDie(int position) {
		return hand.getDice().get(position);
	}

	public boolean getSelected(int position) {
		return hand.isSelected(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return hand.getDice().isEmpty();
	}

	@Override
	public void update(Observable observable, Object data) {
		notifyChanged();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		super.unregisterObserver(observer);
	}

}
