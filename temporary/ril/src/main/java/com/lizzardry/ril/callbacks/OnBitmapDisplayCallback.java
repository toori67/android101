package com.lizzardry.ril.callbacks;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public abstract class OnBitmapDisplayCallback implements ImageLoaderCallbackInterface {
    private WeakReference<ImageView> imageViewWeakReference;

    public OnBitmapDisplayCallback(ImageView imageView) {
        imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    public void after(Bitmap bitmap) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView != null) {
            onSetBitmapToImageView(bitmap, imageView);
        }
    }

    /**
     * BitmapDrawable 로 바꾸려면 context 가 필요한데 그건 RetroImageLoader 에 위임 시켜버리자
     * @param bitmap
     * @param imageView
     */
    public abstract void onSetBitmapToImageView(Bitmap bitmap, ImageView imageView);
}
