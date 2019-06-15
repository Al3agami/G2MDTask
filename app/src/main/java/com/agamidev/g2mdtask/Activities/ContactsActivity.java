package com.agamidev.g2mdtask.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.MatrixCursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.agamidev.g2mdtask.ContactsListFiles.ContactsInteractorImp;
import com.agamidev.g2mdtask.ContactsListFiles.ContactsPresenter;
import com.agamidev.g2mdtask.Interfaces.ContactsContract;
import com.agamidev.g2mdtask.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity implements ContactsContract.ContactsView {

    ContactsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        presenter = new ContactsPresenter(this, new ContactsInteractorImp(this));

        checkPermission();

    }



    int READ_CONTACT_CODE = 1000;
    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            Log.e("tracer","1");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                Log.e("tracer","2");
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},READ_CONTACT_CODE);
            }else {
                getContacts();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == READ_CONTACT_CODE) {
            Log.e("tracer", "3");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Now You can Load Contacts", Toast.LENGTH_LONG).show();
                getContacts();
                Log.e("tracer", "4");

            }else {
                Toast.makeText(this, "Contacts Cannot be loaded", Toast.LENGTH_LONG).show();

                Log.e("tracer", "5");
            }
        }else {
            Log.e("tracer","6");
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @BindView(R.id.lst_contacts)
    ListView lstContacts;

    private void getContacts(){
        presenter.loadContacts();
    }


    @Override
    public void contactsLoadedSuccessfully(SimpleCursorAdapter simpleCursorAdapter) {
        lstContacts.setAdapter(simpleCursorAdapter);
    }
}
