package com.agamidev.g2mdtask.ContactsListFiles;

import android.app.Activity;
import android.database.MatrixCursor;
import android.widget.SimpleCursorAdapter;

import com.agamidev.g2mdtask.Interfaces.ContactsContract;
import com.agamidev.g2mdtask.R;

public class ContactsInteractorImp implements ContactsContract.ContactsInteractorInterface {

    Activity activity;
    private MatrixCursor mMatrixCursor;
    private SimpleCursorAdapter mAdapter;

    public ContactsInteractorImp(Activity activity){
        this.activity = activity;
    }


    @Override
    public void loadContacts(OnLoadedContactsFinished onLoadedContactsFinished) {
        mMatrixCursor = new MatrixCursor(new String[] { "_id", "name", "photo",
                "details" });

        // Adapter to set data in the listview
        mAdapter = new SimpleCursorAdapter(activity.getBaseContext(),
                R.layout.card_contact_item, null, new String[] { "name", "photo",
                "details" }, new int[] { R.id.tv_name, R.id.iv_photo,
                R.id.tv_details }, 0);


        // Creating an AsyncTask object to retrieve and load listview with
        // contacts
        ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader(activity,mMatrixCursor,mAdapter);

        // Starting the AsyncTask process to retrieve and load listview with
        // contacts
        listViewContactsLoader.execute();
        onLoadedContactsFinished.loadFinished(mAdapter);
    }
}
