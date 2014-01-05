package net.pejici.easydice.view;

import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class DieView extends View {

	private int size;
	private int number;
	private Bitmap bitmap;

	public DieView(Context context, int size, int number) {
		super(context);
		init(size, number);
	}

	public DieView(Context context, AttributeSet attrs) {
		super (context, attrs);
		init(6, 4);
	}

	private void init(int size, int number) {
		this.size = size;
		this.number = number;
		String filename = fileNameForDie(size, number);
		int resId = getResources().getIdentifier(filename, "drawable", "net.pejici.easydice");
		bitmap = BitmapFactory.decodeResource(getResources(), resId); 
		Log.i(getClass().toString(), "Die : " + filename);
	}

	private String fileNameForDie(int size, int number) {
		return String.format(Locale.US, "d%d_%04d",
				Integer.valueOf(size),
				Integer.valueOf(number));
	}

	private int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}

	private int pxToDp(int px) {
	    DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, getMatrix(), null);
		}
		else {
			Paint p = new Paint();
			p.setTextSize(this.getHeight()/4);
			p.setColor(0xffff0000);
			canvas.drawText(size + ", " + number,
					this.getWidth()/6, this.getHeight()/2, p);
			Paint p2 = new Paint();
			p2.setStyle(Style.STROKE);
			p2.setStrokeWidth(dpToPx(1));
			p2.setColor(0x60ffffff);
			canvas.drawRect(1, 1, this.getWidth()-1, this.getHeight()-1, p2);
		}
	}

}
