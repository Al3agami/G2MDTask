package com.agamidev.g2mdtask.CacheAndSharePhotoFiles;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

public class ImageLoader implements ComponentCallbacks2 {
    private MyLruCache cache;

    public ImageLoader(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8; // 1/8th of total ram
        cache = new MyLruCache(limitKb);
    }

    public Bitmap display(String url, Bitmap bm) {

        Bitmap image = cache.get(url);
        if (image != null) {
            Log.e("displayPhoto", "not null");
            return image;
        }
        else {
            Log.e("displayPhoto", "null");
            cache.put(url, bm);
            return bm;
        }
    }

    public Bitmap getCached(String uri){
        Bitmap bitmap = cache.get(uri);
        return bitmap;
    }

    private class MyLruCache extends LruCache<String, Bitmap> {

        public MyLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount() / 1024;

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll();
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2);
        }
    }
}
