package com.agamidev.g2mdtask.ContactsListFiles;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.agamidev.g2mdtask.R;

import java.io.File;
import java.io.FileOutputStream;

public class ListViewContactsLoader extends AsyncTask<Void, Void, Cursor> {

    private Activity activity;
    private MatrixCursor mMatrixCursor;
    private SimpleCursorAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView textView;

    public ListViewContactsLoader(Activity activity, MatrixCursor mMatrixCursor, SimpleCursorAdapter mAdapter){
        this.activity = activity;
        this.mMatrixCursor = mMatrixCursor;
        this.mAdapter = mAdapter;
        initProgressBar();
    }
    private void initProgressBar() {
        progressBar = new ProgressBar(activity, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        textView = new TextView(activity,null,android.R.attr.textViewStyle);
        textView.setText("Please wait it may takes a minute...");

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textView);
        linearLayout.addView(progressBar);

        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setGravity(Gravity.CENTER);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        activity.addContentView(linearLayout, params);
    }

    @Override
    protected Cursor doInBackground(Void... params) {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

        // Querying the table ContactsContract.Contacts to retrieve all the
        // contacts
        Cursor contactsCursor = activity.getContentResolver().query(contactsUri,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        if (contactsCursor.moveToFirst()) {
            do {
                Log.e("contactsss","1");
                long contactId = contactsCursor.getLong(contactsCursor
                        .getColumnIndex("_ID"));

                Uri dataUri = ContactsContract.Data.CONTENT_URI;

                // Querying the table ContactsContract.Data to retrieve
                // individual items like
                // home phone, mobile phone, work email etc corresponding to
                // each contact
                Cursor dataCursor = activity.getContentResolver().query(dataUri,
                        null,
                        ContactsContract.Data.CONTACT_ID + "=" + contactId,
                        null, null);

                String displayName = "";
                String nickName = "";
                String homePhone = "";
                String mobilePhone = "";
                String workPhone = "";
                String photoPath = "" + R.mipmap.ic_launcher_round;
                byte[] photoByte = null;
                String homeEmail = "";
                String workEmail = "";
                String companyName = "";
                String title = "";

                if (dataCursor.moveToFirst()) {
                    // Getting Display Name
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                    do {

                        // Getting NickName
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
                            nickName = dataCursor.getString(dataCursor.getColumnIndex("data1"));

                        // Getting Phone numbers
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {

                            switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    workPhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    break;
                            }
                        }

                        // Getting EMails
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {

                            switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    workEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    break;
                            }
                        }

                        // Getting Organization details
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {

                            companyName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                            title = dataCursor.getString(dataCursor.getColumnIndex("data4"));
                        }

                        // Getting Photo
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                            photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));

                            if (photoByte != null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

                                // Getting Caching directory
                                File cacheDirectory = activity.getBaseContext()
                                        .getCacheDir();

                                // Temporary file to store the contact image
                                File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + contactId + ".png");

                                // The FileOutputStream to the temporary
                                // file
                                try {
                                    FileOutputStream fOutStream = new FileOutputStream(tmpFile);

                                    // Writing the bitmap to the temporary
                                    // file as png file
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);

                                    // Flush the FileOutputStream
                                    fOutStream.flush();

                                    // Close the FileOutputStream
                                    fOutStream.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                photoPath = tmpFile.getPath();
                            }

                        }

                    } while (dataCursor.moveToNext());

                    String details = "";

                    // Concatenating various information to single string
                    if (homePhone != null && !homePhone.equals(""))
                        details = "HomePhone : " + homePhone + "\n";
                    if (mobilePhone != null && !mobilePhone.equals(""))
                        details += "MobilePhone : " + mobilePhone + "\n";
                    if (workPhone != null && !workPhone.equals(""))
                        details += "WorkPhone : " + workPhone + "\n";
                    if (nickName != null && !nickName.equals(""))
                        details += "NickName : " + nickName + "\n";
                    if (homeEmail != null && !homeEmail.equals(""))
                        details += "HomeEmail : " + homeEmail + "\n";
                    if (workEmail != null && !workEmail.equals(""))
                        details += "WorkEmail : " + workEmail + "\n";
                    if (companyName != null && !companyName.equals(""))
                        details += "CompanyName : " + companyName + "\n";
                    if (title != null && !title.equals(""))
                        details += "Title : " + title + "\n";

                    // Adding id, display name, path to photo and other
                    // details to cursor
                    mMatrixCursor.addRow(new Object[] {
                            Long.toString(contactId), displayName,
                            photoPath, details });
                }

            } while (contactsCursor.moveToNext());
        }
        return mMatrixCursor;
    }

    @Override
    protected void onPostExecute(Cursor result) {
        // Setting the cursor containing contacts to listview
        mAdapter.swapCursor(result);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

    }
}