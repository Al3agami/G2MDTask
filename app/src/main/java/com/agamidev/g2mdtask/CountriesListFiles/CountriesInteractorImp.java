package com.agamidev.g2mdtask.CountriesListFiles;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.agamidev.g2mdtask.Interfaces.CountriesContract;
import com.agamidev.g2mdtask.Models.CountryModel;

import java.util.ArrayList;

public class CountriesInteractorImp implements CountriesContract.CountriesImp {


    DatabaseManager mDatabaseManager;

    public CountriesInteractorImp(DatabaseManager mDatabaseManager){
        this.mDatabaseManager = mDatabaseManager;
    }


    @Override
    public void addToDatabase(String name, String brief, OnDataAddedSuccessfully onDataAddedSuccessfully) {
        ContentValues values = new ContentValues();
        values.put("Name",name);
        values.put("Brief",brief);
        Long result = mDatabaseManager.insert(values);

        if (result>0){
            Log.e("CountriesInteractorImp","Country Added Successfully!");
            onDataAddedSuccessfully.added("Country Added Successfully!");
//            Toast.makeText(getApplicationContext(),"Country Added Successfully!",Toast.LENGTH_LONG).show();
//            et_country_name.setText("");
//            et_country_brief.setText("");
//            loadCountries("%");
        }else {
            Log.e("CountriesInteractorImp","Error Adding Country!");
            onDataAddedSuccessfully.errorAdding("Error Adding Country!");
//            Toast.makeText(getApplicationContext(),"Error Adding Country!",Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<CountryModel> countriesArrayList = new ArrayList<>();

    @Override
    public void getDataFromDatabase(OnDataReadyListener onDataReadyListener, String row) {

        String[] projectionArgs = {"ID","Name", "Brief"};
        String[] selectionArgs ={row};
        Cursor cursor = mDatabaseManager.query(projectionArgs,"Name like ?",selectionArgs,"Name");
        countriesArrayList.clear();
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("Name"));
                String brief = cursor.getString(cursor.getColumnIndex("Brief"));
                countriesArrayList.add(new CountryModel(id, name, brief));

            }while (cursor.moveToNext());
            if (countriesArrayList.size() != 0) {
                Log.e("CountriesInteractorImp","Countries Loaded Successfully!");
                onDataReadyListener.loaded(countriesArrayList);
            }else {
                Log.e("CountriesInteractorImp","Error Loading Countries!");
                onDataReadyListener.errorLoading("Error Loading Countries!");
            }
        }

    }
}
