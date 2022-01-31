package com.example.lab9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MapEntryDbHelper extends SQLiteOpenHelper {

    /* Inner class that defines the table contents */
    public static class MapEntry implements BaseColumns {
        public static final String TABLE_NAME = "coordinates";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_COORDINATE_LAT = "Lat";
        public static final String COLUMN_COORDINATE_LNG = "Lng";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MapEntry.TABLE_NAME + " (" +
                    MapEntry._ID + " INTEGER PRIMARY KEY," +
                    MapEntry.COLUMN_NAME + " TEXT," +
                    MapEntry.COLUMN_COORDINATE_LAT + " TEST," +
                    MapEntry.COLUMN_COORDINATE_LNG + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MapEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Map.db";

    public MapEntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addData(String info) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] duom = info.split(" ");

        ContentValues values = new ContentValues();
        values.put(MapEntry.COLUMN_NAME, duom[0]);
        values.put(MapEntry.COLUMN_COORDINATE_LAT, duom[1]);
        values.put(MapEntry.COLUMN_COORDINATE_LNG, duom[2]);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MapEntry.TABLE_NAME, null, values);

        if(newRowId < 0) {
            System.out.println("FAIL");
        }
        else {
            System.out.println("SUCCESS");
        }

    }

    public Cursor readDuom() {
        SQLiteDatabase db = this.getReadableDatabase();

//            String[] projection = {
//                    BaseColumns._ID,
//                    MapEntry.COLUMN_NAME,
//                    MapEntry.COLUMN_COORDINATE_LAT,
//                    MapEntry.COLUMN_COORDINATE_LNG
//            };
//
//            String selection = MapEntry.COLUMN_NAME + " = ?";
//            String[] selectionArgs = { "My Title" };
//
//            String sortOrder = MapEntry.COLUMN_NAME + " DESC";
//
//            Cursor cursor = db.query(
//                    MapEntry.TABLE_NAME,   // The table to query
//                    projection,             // The array of columns to return (pass null to get all)
//                    selection,              // The columns for the WHERE clause
//                    selectionArgs,          // The values for the WHERE clause
//                    null,                   // don't group the rows
//                    null,                   // don't filter by row groups
//                    sortOrder               // The sort order
//            );

        String query = "SELECT * FROM " + MapEntry.TABLE_NAME;

        Cursor cursor = null;

        if(db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void wipeData() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MapEntry.TABLE_NAME, null, null);
    }
}

