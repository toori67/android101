package com.lizzardry.temporary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ScreenUtil {
    private Resources resources;
    private DisplayMetrics displayMetrics;
    public ScreenUtil(Context context) {
        this.resources = context.getResources();
        this.displayMetrics = resources.getDisplayMetrics();
    }

    public float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static float dpToPx(DisplayMetrics displayMetrics, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }
}
