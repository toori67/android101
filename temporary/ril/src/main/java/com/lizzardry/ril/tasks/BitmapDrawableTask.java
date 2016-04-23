package com.lizzardry.ril.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.lizzardry.ril.cache.ImageCache;

import java.lang.ref.WeakReference;

public class BitmapDrawableTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> imageViewWeakReference;

    public BitmapDrawableTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String key = strings[0];
        return ImageCache.getInstance().get(key);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView == null || bitmap == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

}
