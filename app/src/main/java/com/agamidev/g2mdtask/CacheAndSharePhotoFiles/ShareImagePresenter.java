package com.agamidev.g2mdtask.CacheAndSharePhotoFiles;

import android.graphics.Bitmap;
import android.net.Uri;

import com.agamidev.g2mdtask.Interfaces.ShareImageContract;

public class ShareImagePresenter implements ShareImageContract.ShareImagePresenterInterface, ShareImageContract.ShareImageInteractorInterface.CacheResult {

    ShareImageContract.ShareImageViewInterface shareImageView;
    ShareImageContract.ShareImageInteractorInterface shareImageInteractor;

    public ShareImagePresenter(ShareImageContract.ShareImageViewInterface shareImageView, ShareImageContract.ShareImageInteractorInterface shareImageInteractor){
        this.shareImageView = shareImageView;
        this.shareImageInteractor = shareImageInteractor;
    }

    @Override
    public void cacheAndSetImage(Uri uri) {
        shareImageInteractor.cacheImage(uri, this);
    }

    @Override
    public void shareImageToFacebook(Uri uri, Bitmap bm) {
        shareImageInteractor.shareImageToFacebook(uri, bm);
    }

    @Override
    public void setBitmap(Bitmap bm) {
        shareImageView.showImage(bm);
    }
}
