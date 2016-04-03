package com.lizzardry.ril.cache.layer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lizzardry.ril.RetroImageLoader;
import com.lizzardry.ril.exceptions.KeyNotFoundExcption;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Lev3 Network cache??
 */
public class Level3CacheLayer implements ILru<String, Bitmap> {
    private static final String TAG = "Level3CacheLayer";
    private BitmapFactory.Options options;

    public Level3CacheLayer() {
        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = RetroImageLoader.getInstance().getConfiguration().getDecodeConfig();
    }

    @Override
    public Bitmap get(String key) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(key)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.code() != 200) {
                Log.d(TAG, "Response is not 200, invalid!");
                throw new KeyNotFoundExcption("HTTP response code is not 200!");
            }
            return BitmapFactory.decodeStream(response.body().byteStream(), null, options);
        } catch (IOException e) {
            Log.d(TAG, e.toString() +  " @ OkHttp Response.");
            throw new KeyNotFoundExcption(e.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            throw new KeyNotFoundExcption(e.toString());
        } finally {
            try {
                response.body().close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
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
