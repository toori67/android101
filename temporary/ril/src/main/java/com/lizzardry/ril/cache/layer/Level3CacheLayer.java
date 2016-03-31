package com.lizzardry.ril.cache.layer;

import android.graphics.Bitmap;

import okhttp3.OkHttpClient;

/**
 * Lev3 Network cache??
 */
public class Level3CacheLayer implements ILru<String, Bitmap> {

    @Override
    public Bitmap get(String key) {
        OkHttpClient okHttpClient = new OkHttpClient();
        /*
        blah blah blah
         */
        return null;
    }

    @Override
    public void put(String key, Bitmap value) {
        // nothing
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean has(String key) {
        // network 에는 항상 있다고 가정
        // !(200 - ok) 떨어지는건 나중에 생각하자
        return true;
    }
}
