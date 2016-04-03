package com.lizzardry.ril;

import android.widget.ImageView;

import com.lizzardry.ril.tasks.BitmapDrawableTask;

/**
 * the very entrance of Retro Image Loader
 */
public class RetroImageLoader {
    public static RetroImageLoader instance;
    public static synchronized RetroImageLoader getInstance() {
        if (instance == null) {
            instance = new RetroImageLoader();
        }
        return instance;
    }

    private boolean hasConfigured = false;
    private RetroLoaderConfiguration configuration;

    public RetroImageLoader() {
    }

    public void init(RetroLoaderConfiguration config) {
        this.configuration = config;
        hasConfigured = true;
    }

    public RetroLoaderConfiguration getConfiguration() {
        if (!hasConfigured) {
            throw new IllegalStateException("RIL not configured");
        }
        return configuration;
    }

    public void displayImage(final String url, final ImageView imageView) {
        BitmapDrawableTask bitmapDrawableTask = new BitmapDrawableTask(imageView);
        bitmapDrawableTask.execute(url);
    }
}
