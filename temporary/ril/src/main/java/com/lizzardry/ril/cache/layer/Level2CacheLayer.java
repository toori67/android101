package com.lizzardry.ril.cache.layer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lizzardry.ril.RememberImageLoader;
import com.lizzardry.ril.aosp.DiskLruCache;
import com.lizzardry.ril.helper.Utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Lev2 Disk cache
 */
public class Level2CacheLayer implements ILru<String, Bitmap> {
    private static final String TAG = "Level2CacheLayer";
    private static final int DISK_CACHE_INDEX = 0;
    // keep singleton
    private static Level2CacheLayer instance;
    public static synchronized Level2CacheLayer getInstance() {
        if (instance == null) {
            instance = new Level2CacheLayer();
        }
        return instance;
    }

    private final Object lock = new Object();

    private DiskLruCache bitmapDiskLruCache;
    private boolean isCacheEnabled = false;
    private Bitmap.CompressFormat compressFormat;
    private int compressQuality;
    private Bitmap.Config config;

    private Level2CacheLayer() {
        this.isCacheEnabled = true;
        long maxSize = RememberImageLoader.getInstance().getConfiguration().getMaximumDiskCacheSize();
        File cacheFile = new File(RememberImageLoader.getInstance().getConfiguration().getDiskCachePath());
        int appVersion = RememberImageLoader.getInstance().getConfiguration().getAppVersion();
        compressFormat = Bitmap.CompressFormat.PNG;
        compressQuality = RememberImageLoader.getInstance().getConfiguration().getCompressQuality();
        config = RememberImageLoader.getInstance().getConfiguration().getDecodeConfig();

        try {
            bitmapDiskLruCache = DiskLruCache.open(cacheFile, appVersion, 1, maxSize);
        } catch (IOException e) {
            Log.e(TAG, "Open DiskLruCache Fail : " + e.toString());
            isCacheEnabled = false;
        }
    }

    @Override
    public boolean isEnabled() {
        return isCacheEnabled;
    }

    @Override
    public Bitmap get(String key) {
        if (!isEnabled()) {
            return null;
        }
        synchronized (lock) {
            if (bitmapDiskLruCache != null) {
                InputStream inputStream = null;
                try {
                    final DiskLruCache.Snapshot snapshot = bitmapDiskLruCache.get(Utils.hashKey(key));
                    if (snapshot != null) {
                        Log.d(TAG, "Disk cache hit");
                        inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
                        if (inputStream != null) {
                            return decode(((FileInputStream) inputStream).getFD());
                        }
                    }
                } catch (final IOException e) {
                    Log.e(TAG, "getBitmapFromDiskCache - " + e);
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
            lock.notifyAll();
        }
        return null;
    }

    private Bitmap decode(FileDescriptor fileDescriptor) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = config;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }


    @Override
    public void put(String key, Bitmap value) {
        if (!isEnabled() || get(key) != null) {
            return;
        }
        String hashedKey = Utils.hashKey(key);
        synchronized (lock) {
            if (bitmapDiskLruCache != null) {
                OutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot = bitmapDiskLruCache.get(hashedKey);
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = bitmapDiskLruCache.edit(hashedKey);
                        if (editor != null) {
                            out = editor.newOutputStream(DISK_CACHE_INDEX);
                            value.compress(compressFormat, compressQuality, out);
                            editor.commit();
                            out.close();
                        }
                    } else {
                        snapshot.getInputStream(DISK_CACHE_INDEX).close();
                    }
                } catch (final IOException e) {
                    Log.e(TAG, "addBitmapToCache - " + e);
                } catch (Exception e) {
                    Log.e(TAG, "addBitmapToCache - " + e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {}
                }
            }
            lock.notifyAll();
        }
    }

    @Override
    public boolean has(String key) {
        return isEnabled() && get(key) != null;
    }
}
