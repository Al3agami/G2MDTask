package com.agamidev.g2mdtask.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.agamidev.g2mdtask.MyPreferences;
import com.agamidev.g2mdtask.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacebookLoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.cb_remember_me)
    CheckBox cbRememberMe;
    AccessToken accessToken;
    public static Boolean isRemembered;
    boolean isLoggedIn;
    MyPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();

        myPreferences = new MyPreferences(this);

        isRemembered = false;

        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (myPreferences.getLoginStatus()){
            isRemembered = true;
            Intent i = new Intent(FacebookLoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }else {
            goRegister();
        }

    }

    private void goRegister() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        if (cbRememberMe.isChecked()){
                            isRemembered = true;
                            myPreferences.storeLoginStatus(true);
                        }else {
                            isRemembered = false;
                            myPreferences.storeLoginStatus(false);
                        }
                        Toast.makeText(getApplicationContext(),"successfully logged",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(FacebookLoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(getApplicationContext(),"Cancel logged",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(),"Error logged",Toast.LENGTH_LONG).show();
                        Log.e("Logging",exception.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
