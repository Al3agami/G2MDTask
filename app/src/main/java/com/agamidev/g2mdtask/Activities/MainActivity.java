package com.agamidev.g2mdtask.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.agamidev.g2mdtask.MapFiles.LocationServiceForMaps;
import com.agamidev.g2mdtask.MyPreferences;
import com.agamidev.g2mdtask.R;
import com.facebook.login.LoginManager;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    MyPreferences myPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myPreferences = new MyPreferences(this);
    }

    @Override
    protected void onDestroy() {
        if (!FacebookLoginActivity.isRemembered) {
            LoginManager.getInstance().logOut();
            myPreferences.LogOut();
        }
        super.onDestroy();
    }


    public void goCountries(View view) {
        Intent i = new Intent(MainActivity.this, CountriesListActivity.class);
        startActivity(i);
    }

    public void goContacts(View view) {
        Intent i = new Intent(MainActivity.this, ContactsActivity.class);
        startActivity(i);
    }

    public void goMap(View view) {
        new LocationServiceForMaps(this).displayLocationSettingsRequest();
    }

    public void goSharePhoto(View view) {
        Intent i = new Intent(MainActivity.this, SharePhotoActivity.class);
        startActivity(i);
    }


}
