package com.lizzardry.temporary;

import android.app.Application;
import android.graphics.Bitmap;

import com.lizzardry.ril.RetroImageLoader;
import com.lizzardry.ril.RetroLoaderConfiguration;

public class Apps extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }
    private void initImageLoader() {
        RetroLoaderConfiguration configuration = new RetroLoaderConfiguration.Builder()
                .setDecodeConfig(Bitmap.Config.ARGB_8888)
                .setDiskCachePath(getFilesDir() + "/images_cache5")
                .setMaximumDiskCacheSize(1024 * 1024 * 20) // 20MB
                .setMaximumMemoryCacheSize(Math.round(0.25f * Runtime.getRuntime().maxMemory() / 1024)) // 가용 메모리의 25%
                .setCompressQuality(80)
                .build();
        RetroImageLoader.getInstance().init(configuration);
    }
}
