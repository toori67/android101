package com.lizzardry.ril;

import android.graphics.Bitmap;

public class RememberImageLoaderConfiguration {
    private Bitmap.Config decodeConfig;


    public RememberImageLoaderConfiguration(Builder builder) {

    }

    public static class Builder {
        private Bitmap.Config decodeConing;
        private int maximumMemeoryCacheSize;
        private int maximumDiskCachSize;

        public Builder setDecodeConing(Bitmap.Config decodeConing) {
            this.decodeConing = decodeConing;
            return this;
        }

        public Builder setMaximumMemeoryCacheSize(int maximumMemeoryCacheSize) {
            this.maximumMemeoryCacheSize = maximumMemeoryCacheSize;
            return this;
        }

        public Builder setMaximumDiskCachSize(int maximumDiskCachSize) {
            this.maximumDiskCachSize = maximumDiskCachSize;
            return this;
        }
    }
}

