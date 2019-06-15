package com.agamidev.g2mdtask.Interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

public interface ShareImageContract {

    interface ShareImagePresenterInterface{
        void cacheAndSetImage(Uri uri);
        void shareImageToFacebook(Uri uri, Bitmap bm);
    }

    interface ShareImageViewInterface{
        void showImage(Bitmap bm);
    }

    interface ShareImageInteractorInterface{
        interface CacheResult{
            void setBitmap(Bitmap bm);
        }
        void cacheImage(Uri uri, CacheResult cacheResult);
        void shareImageToFacebook(Uri uri, Bitmap bm);
    }
}
