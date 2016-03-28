package com.lizzardry.ril;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * the very entrance of remember Image Loader
 */
public class RememberImageLoader {
    public static RememberImageLoader instnace;
    public static synchronized RememberImageLoader getInstnace() {
        if (instnace == null) {
            instnace = new RememberImageLoader();
        }
        return instnace;
    }

    private RememberImageLoaderConfiguration configuration;

    public void init(RememberImageLoaderConfiguration config) {
        this.configuration = config;
    }

}
