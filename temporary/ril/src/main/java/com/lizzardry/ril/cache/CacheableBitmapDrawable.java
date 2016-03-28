package com.lizzardry.ril.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheableBitmapDrawable extends BitmapDrawable {
    private static final String TAG = "CacheableBitmapDrawable";

    private AtomicInteger cacheRefCount = new AtomicInteger(0);
    private AtomicInteger displayRefCount = new AtomicInteger(0);
    private AtomicBoolean hasDisplayed = new AtomicBoolean(false);

    public void setDisplayed(boolean isDisplayed) {
        if (isDisplayed) {
            displayRefCount.addAndGet(1);
            hasDisplayed.set(true);
        } else {
            displayRefCount.addAndGet(-1);
        }

        checkState();
    }

    public void setCached(boolean isCached) {
        if (isCached) {
            cacheRefCount.addAndGet(1);
        } else {
            cacheRefCount.addAndGet(-1);
        }
    }

    private void checkState() {
        if (cacheRefCount.get() <= 0 && displayRefCount.get() <=0
                && hasDisplayed.get() && hasValidBitmap()) {
            Log.d(TAG, "Bitmap not used, now recycle");
            getBitmap().recycle();
        }
    }

    private synchronized boolean hasValidBitmap() {
        Bitmap bitmap = getBitmap();
        return bitmap != null && !bitmap.isRecycled();
    }
}
