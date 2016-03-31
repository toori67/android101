package com.lizzardry.ril.cache.object;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import com.lizzardry.ril.helper.Versions;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheableBitmap {
    private static final String TAG = "CacheableBitmapDrawable";

    private Bitmap bitmap;
    private AtomicInteger cacheRefCount = new AtomicInteger(0);
    private AtomicInteger displayRefCount = new AtomicInteger(0);
    private AtomicBoolean hasDisplayed = new AtomicBoolean(false);

    public CacheableBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int getBitmapSize() {
        if (bitmap == null) {
            return 0;
        }
        // From KitKat onward use getAllocationByteCount() as allocated bytes can potentially be
        // larger than bitmap byte count.
        if (Versions.hasKitKat()) {
            return bitmap.getAllocationByteCount();
        }

        if (Versions.hasHoneycombMR1()) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    private void checkState() {
        if (cacheRefCount.get() <= 0 && displayRefCount.get() <=0
                && hasDisplayed.get() && hasValidBitmap()) {
            Log.d(TAG, "Bitmap not used, now recycle");
            bitmap.recycle();
        }
    }

    public synchronized boolean hasValidBitmap() {
        return bitmap != null && !bitmap.isRecycled();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
