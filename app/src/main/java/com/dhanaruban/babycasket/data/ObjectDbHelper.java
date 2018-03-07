package com.dhanaruban.babycasket.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thenu on 21-02-2018.
 */

public class ObjectDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "objectDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 2;


    // Constructor
    ObjectDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + ObjectContract.TaskEntry.TABLE_NAME + " (" +
                ObjectContract.TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                ObjectContract.TaskEntry.COLUMN_OBJECT_NAME + " TEXT NOT NULL, " +
                ObjectContract.TaskEntry.COLUMN_OBJECT_IMAGE    + " STRING NOT NULL, " +
                ObjectContract.TaskEntry.UPLOAD_OBJECT_STATUS   + " STRING NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ObjectContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}

