package com.lizzardry.ril.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;

/**
 * Lev1 Memory cache
 */
public class Level1CacheStore {
    // keep singleton
    private static Level1CacheStore instance;
    public static synchronized Level1CacheStore getInstance() {
        if (instance == null) {
            instance = new Level1CacheStore();
        }
        return instance;
    }

    LruCache<String, CacheableBitmapDrawable> bitmapLruCache;
    public Level1CacheStore() {
        bitmapLruCache = new LruCache<String, CacheableBitmapDrawable>(24) {
            @Override
            protected void entryRemoved(boolean evicted, String key, CacheableBitmapDrawable oldValue, CacheableBitmapDrawable newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
    }
}
