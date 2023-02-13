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

import java.util.Locale;

import net.pejici.easydice.model.Die;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.LruCache;
import android.view.View;

import static net.pejici.androidutil.LayoutUtil.*;

public class DieView extends View {

	private Die die;
	private Bitmap image;
	private Bitmap selectedImage;
	private boolean selected = false;

	static final private BitmapCache cache = new BitmapCache(12*1024*1024);

	static private class BitmapCache extends LruCache<String, Bitmap> {
		public BitmapCache(int maxSize) {
			super(maxSize);
		}
	}

	public DieView(Context context) {
		super(context);
	}

	public DieView(Context context, AttributeSet attrs) {
		super (context, attrs);
		init(new Die(6, 4));
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		invalidate();
	}

	public boolean getSelected() {
		return selected;
	}

	private void init(Die die) {
		this.die = die;
		image = makeBitmap(fileNameForDie(die));
		selectedImage = makeBitmap(selectedFileNameForDie(die));
	}

	private String fileNameForDie(Die die) {
		if (die.multiplier == 1) {
			return String.format(Locale.US, "d%d_%04d",
					Integer.valueOf(die.size),
					Integer.valueOf(die.number));
		}
		else {
			int number = die.number-1;
			if (number < 1) {
				number += die.multiplier;
			}
			return String.format(Locale.US, "d%dx%d_%04d",
					Integer.valueOf(die.size),
					Integer.valueOf(die.multiplier),
					Integer.valueOf(number));
		}
	}

	private String selectedFileNameForDie(Die die) {
		return fileNameForDie(die) + "_sel";
	}

	private Bitmap makeBitmap(String filename) {
		Bitmap bitmap = cache.get(filename);
		if (null == bitmap) {
			int resId = getResources().getIdentifier(filename, "drawable", "net.pejici.easydice");
			bitmap = BitmapFactory.decodeResource(getResources(), resId);
			cache.put(filename, bitmap);
		}
		return bitmap;
	}

	public void setDie(Die die) {
		init(die);
		invalidate();
	}

	private void drawDieImage(Canvas canvas, Bitmap image) {
		float w = getWidth();
		float h = getHeight();
		float bw = image.getWidth();
		float bh = image.getHeight();
		canvas.save();
		canvas.translate(Math.round((w-bw)/2), Math.round((h-bh)/2));
		canvas.drawBitmap(image, getMatrix(), null);
		canvas.restore();
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (image != null) {
			if (selected) {
				drawDieImage(canvas, selectedImage);
			}
			drawDieImage(canvas, image);
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
