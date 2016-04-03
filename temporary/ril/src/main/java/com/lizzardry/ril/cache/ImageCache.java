package com.lizzardry.ril.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.lizzardry.ril.exceptions.KeyNotFoundExcption;
import com.lizzardry.ril.cache.layer.ILru;
import com.lizzardry.ril.cache.layer.Level1CacheLayer;
import com.lizzardry.ril.cache.layer.Level2CacheLayer;
import com.lizzardry.ril.cache.layer.Level3CacheLayer;

public class ImageCache {
    private static final String TAG = "ImageCache";

    private static ImageCache instance;
    public static synchronized ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    private SparseArray<ILru<String, Bitmap>> layers;
    public ImageCache() {
        ILru<String, Bitmap> memoryCache = new Level1CacheLayer();
        ILru<String, Bitmap> diskCache = new Level2CacheLayer();
        ILru<String, Bitmap> networkCache = new Level3CacheLayer();
        layers = new SparseArray<>();
        layers.append(0, memoryCache);
        layers.append(1, diskCache);
        layers.append(2, networkCache);
    }

    public Bitmap get(String key) throws KeyNotFoundExcption {
        Bitmap bitmap = null;
        int currentLayer = 0;
        for (int i = 0; i < layers.size(); i++) {
            ILru<String, Bitmap> cache  = layers.get(i);
            if (cache.isEnabled()) {
                bitmap = cache.get(key);
                if (bitmap != null) {
                    currentLayer = i;
                    break;
                }
            }
        }
        if (bitmap != null) {
            for (int i=0; i<currentLayer; i++) {
                layers.get(i).put(key, bitmap);
            }
            return bitmap;
        }
        throw new KeyNotFoundExcption(key + " is not found at all;");
    }
}
