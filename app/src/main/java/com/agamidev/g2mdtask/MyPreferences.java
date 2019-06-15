package com.agamidev.g2mdtask;

import android.content.Context;
import android.content.SharedPreferences;

import com.agamidev.g2mdtask.Activities.FacebookLoginActivity;

/*
created by mahmoud 10/4/2017
 */

public class MyPreferences {
    final static String PREFS_NAME = "MyPrefs";
    final static String SETTINGS_LOGIN = "login";
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor PrefsEditor;


    public MyPreferences(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        PrefsEditor = prefs.edit();
        PrefsEditor.commit();
    }


    public void LogOut() {
        if (FacebookLoginActivity.isRemembered){
            PrefsEditor.clear();
            PrefsEditor.commit();
        }
    }

    public Boolean getLoginStatus() {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Boolean value = prefs.getBoolean(SETTINGS_LOGIN, false);
        return value;
    }

    public void storeLoginStatus(Boolean status) {
        final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SETTINGS_LOGIN, status);
        editor.commit();
    }


}

