package com.agamidev.g2mdtask.CountriesListFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;


public class DatabaseManager {
    private String dbName = "MyCountries";
    private String dbTableName = "Countries";
    private String countryID = "ID";
    private String countryName = "Name";
    private String countryBrief = "Brief";

    private int dbVersion = 1;

    private String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTableName + " (" + countryID + " INTEGER PRIMARY KEY, "
            + countryName + " TEXT, " + countryBrief + " TEXT);";
    private SQLiteDatabase sqlDB = null;

    public DatabaseManager(Context context){
        DatabaseHelperCountries db = new DatabaseHelperCountries(context);
        sqlDB = db.getWritableDatabase();

    }

    class DatabaseHelperCountries extends SQLiteOpenHelper {
        Context context;
        //@androidx.annotation.Nullable
        public DatabaseHelperCountries(Context context) {
            super(context, dbName, null , dbVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sqlCreateTable);
            Toast.makeText(context,"Database Created Successfully", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table IF EXISTS "+dbTableName);

        }
    }

    public Long insert(ContentValues contentValues){
        return sqlDB.insert(dbTableName,"",contentValues);
    }

    public Cursor query(String[] projection, String selection , String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(dbTableName);
        Cursor cursor = mQueryBuilder.query(sqlDB,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }
}
