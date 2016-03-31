package com.lizzardry.ril;

import android.graphics.Bitmap;

public class RememberImageLoaderConfiguration {
    private Bitmap.Config decodeConfig;
    private int appVersion;
    private int maximumMemoryCacheSize;
    private long maximumDiskCacheSize;
    private String diskCachePath;
    private int compressQuality;

    public RememberImageLoaderConfiguration(Builder builder) {
        decodeConfig = builder.decodeConfig;
        appVersion = 1;
        maximumMemoryCacheSize = builder.maximumMemoryCacheSize;
        maximumDiskCacheSize = builder.maximumDiskCacheSize;
        diskCachePath = builder.diskCachePath;
        compressQuality = builder.compressQuality;
    }

    public Bitmap.Config getDecodeConfig() {
        return decodeConfig;
    }

    public int getAppVersion() {
        return 1;
    }

    public int getMaximumMemoryCacheSize() {
        return maximumMemoryCacheSize;
    }

    public long getMaximumDiskCacheSize() {
        return maximumDiskCacheSize;
    }

    public String getDiskCachePath() {
        return diskCachePath;
    }

    public int getCompressQuality() {
        return compressQuality;
    }

    public static class Builder {
        private Bitmap.Config decodeConfig;
        private int maximumMemoryCacheSize;
        private long maximumDiskCacheSize;
        private String diskCachePath;
        private int compressQuality;

        public Builder setDecodeConfig(Bitmap.Config decodeConfig) {
            this.decodeConfig = decodeConfig;
            return this;
        }

        public Builder setMaximumMemoryCacheSize(int maximumMemoryCacheSize) {
            this.maximumMemoryCacheSize = maximumMemoryCacheSize;
            return this;
        }

        public Builder setMaximumDiskCacheSize(long maximumDiskCacheSize) {
            this.maximumDiskCacheSize = maximumDiskCacheSize;
            return this;
        }

        public Builder setDiskCachePath(String diskCachePath) {
            this.diskCachePath = diskCachePath;
            return this;
        }

        public Builder setCompressQuality(int compressQuality) {
            this.compressQuality = compressQuality;
            return this;
        }

        public RememberImageLoaderConfiguration build() {
            return new RememberImageLoaderConfiguration(this);
        }
    }
}

