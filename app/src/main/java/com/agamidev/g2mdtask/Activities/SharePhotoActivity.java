package com.agamidev.g2mdtask.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.agamidev.g2mdtask.CacheAndSharePhotoFiles.ImageLoader;
import com.agamidev.g2mdtask.R;
import com.agamidev.g2mdtask.Interfaces.ShareImageContract;
import com.agamidev.g2mdtask.CacheAndSharePhotoFiles.ShareImageInteractor;
import com.agamidev.g2mdtask.CacheAndSharePhotoFiles.ShareImagePresenter;
import com.facebook.FacebookSdk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharePhotoActivity extends AppCompatActivity implements ShareImageContract.ShareImageViewInterface {

    Uri returnedImage;
    Bitmap bitmap = null;
    @BindView(R.id.iv_share)
    ImageView iv_share;

    ShareImagePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_share_photo);
        ButterKnife.bind(this);
        presenter = new ShareImagePresenter(this, new ShareImageInteractor(this));

    }

    int intentCode = 123;
    private void loadImageFromStorage(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, intentCode);
    }

    ImageLoader mImageLoader;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == intentCode && resultCode == Activity.RESULT_OK && data != null){

            returnedImage = data.getData();
            iv_share.setImageResource(R.mipmap.ic_launcher);
            presenter.cacheAndSetImage(returnedImage);
        }
    }

    public void selectPhoto(View view) {
        loadImageFromStorage();
    }

    public void sharePhoto(View view) {
        presenter.shareImageToFacebook(returnedImage, bitmap);
    }

    @Override
    public void showImage(Bitmap bm) {
        this.bitmap = bm;
        iv_share.setImageBitmap(bm);
    }
}
