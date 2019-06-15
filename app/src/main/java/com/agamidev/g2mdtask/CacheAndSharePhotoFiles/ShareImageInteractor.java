package com.agamidev.g2mdtask.CacheAndSharePhotoFiles;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.agamidev.g2mdtask.Interfaces.ShareImageContract;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class ShareImageInteractor implements ShareImageContract.ShareImageInteractorInterface {
    private Activity activity;
    private Bitmap image;
    private Bitmap sharedImage;
    private ImageLoader mImageLoader;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    public ShareImageInteractor(Activity activity){
        this.activity = activity;
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(activity);
        mImageLoader = new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public void cacheImage(Uri uri,CacheResult cacheResult) {
        try {
            image = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Cache Image before setting
        image = mImageLoader.display(uri.toString(), image);
        cacheResult.setBitmap(image);
    }

    @Override
    public void shareImageToFacebook(Uri uri, Bitmap bm) {

        if (bm != null) {

            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Toast.makeText(activity,"Photo shared successfully",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(activity,"Photo share canceled",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(activity,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
            sharedImage = mImageLoader.getCached(uri.toString());
            SharePhoto sharePhoto;
            if (sharedImage != null){
                sharePhoto = new SharePhoto.Builder()
                        .setBitmap(sharedImage)
                        .build();
                Log.e("sharePhoto", "not null");
            }else {
                sharePhoto = new SharePhoto.Builder()
                        .setBitmap(bm)
                        .build();
                Log.e("sharePhoto", "null");
            }



            if (ShareDialog.canShow(SharePhotoContent.class)){
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);
                Log.e("sharePhoto", "ended");
            }


        }else{
            Toast.makeText(activity,"Select photo you want to share!",Toast.LENGTH_LONG).show();
        }
    }
}
