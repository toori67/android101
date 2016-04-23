package com.lizzardry.ril.cache.layer;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.lizzardry.ril.RetroImageLoader;
import com.lizzardry.ril.cache.object.CacheableBitmap;
import com.lizzardry.ril.helpers.Utils;

/**
 * Lev1 Memory cache
 */
public class Level1CacheLayer implements ILru<String, Bitmap> {
    public static final String TAG = "Level1CacheLayer";
    private LruCache<String, CacheableBitmap> bitmapLruCache;

    public Level1CacheLayer() {
        int maxSize = RetroImageLoader.getInstance().getConfiguration().getMaximumMemoryCacheSize();
        bitmapLruCache = new LruCache<String, CacheableBitmap>(maxSize) {
                protected void entryRemoved(
                        boolean evicted, String key,
                        CacheableBitmap oldValue, CacheableBitmap newValue) {
                    oldValue.setCached(false);
                }

                @Override
                protected int sizeOf(String key, CacheableBitmap value) {
                    final int bitmapSize = value.getBitmapSize() / 1024;
                    return bitmapSize == 0 ? 1 : bitmapSize;
                }
        };
    }

    @Override
    public boolean isEnabled() {
        // always true
        return true;
//        return false;
    }

    @Override
    public Bitmap get(String key) {
        CacheableBitmap cacheableBitmap = bitmapLruCache.get(Utils.hashKey(key));
        if (cacheableBitmap != null
                && cacheableBitmap.hasValidBitmap()) {
            Log.d(TAG, "MemoryCache hit : " + key);
            return cacheableBitmap.getBitmap();
        }
        return null;
    }

    @Override
    public void put(String key, Bitmap value) {
        if (value == null) {
            return;
        }
        CacheableBitmap cacheableBitmap = new CacheableBitmap(value);
        cacheableBitmap.setCached(true);
        bitmapLruCache.put(Utils.hashKey(key), cacheableBitmap);
    }

    @Override
    public boolean has(String key) {
        return get(key) != null;
    }
}
