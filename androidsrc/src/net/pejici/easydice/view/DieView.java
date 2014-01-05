package net.pejici.easydice.view;

import java.util.Locale;

import net.pejici.easydice.model.Die;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import static net.pejici.androidutil.LayoutUtil.*;

public class DieView extends View {

	private Die die;
	private Bitmap bitmap;

	public DieView(Context context, Die die) {
		super(context);
		init(die);
	}

	public DieView(Context context, AttributeSet attrs) {
		super (context, attrs);
		init(new Die(6, 4));
	}

	private void init(Die die) {
		this.die = die;
		String filename = fileNameForDie(die);
		int resId = getResources().getIdentifier(filename, "drawable", "net.pejici.easydice");
		bitmap = BitmapFactory.decodeResource(getResources(), resId);
	}

	private String fileNameForDie(Die die) {
		if (die.multiplier == 1) {
			return String.format(Locale.US, "d%d_%04d",
					Integer.valueOf(die.size),
					Integer.valueOf(die.number));
		}
		else {
			return String.format(Locale.US, "d%dx%d_%04d",
					Integer.valueOf(die.size),
					Integer.valueOf(die.multiplier),
					Integer.valueOf(die.number));
		}
	}

	public void setDie(Die die) {
		init(die);
		invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		if (bitmap != null) {
			float w = getWidth();
			float h = getHeight();
			float bw = bitmap.getWidth();
			float bh = bitmap.getHeight();
			canvas.translate(Math.round((w-bw)/2), Math.round((h-bh)/2));
			canvas.drawBitmap(bitmap, getMatrix(), null);
		}
		else {
			Paint p = new Paint();
			p.setTextSize(this.getHeight()/4);
			p.setColor(0xffff0000);
			canvas.drawText(die.toString(),
					this.getWidth()/6, this.getHeight()/2, p);
			Paint p2 = new Paint();
			p2.setStyle(Style.STROKE);
			p2.setStrokeWidth(dpToPx(this, 1));
			p2.setColor(0x60ffffff);
			canvas.drawRect(1, 1, this.getWidth()-1, this.getHeight()-1, p2);
		}
	}

}
