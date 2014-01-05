package net.pejici.androidutil;

import android.util.DisplayMetrics;
import android.view.View;

public class LayoutUtil {

	static private DisplayMetrics getDisplayMetrics(View view) {
		return view.getContext().getResources().getDisplayMetrics();
	}
	static public int dpToPx(View view, int dp) {
	    DisplayMetrics displayMetrics = getDisplayMetrics(view);
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	static public int pxToDp(View view, int px) {
	    DisplayMetrics displayMetrics = getDisplayMetrics(view);
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
}
