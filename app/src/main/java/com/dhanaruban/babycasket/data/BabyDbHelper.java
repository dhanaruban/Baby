package com.dhanaruban.babycasket.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thenu on 21-02-2018.
 */

public class BabyDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "childDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 3;


    // Constructor
    BabyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + BabyContract.TaskEntry.TABLE_NAME + " (" +
                BabyContract.TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                BabyContract.TaskEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BabyContract.TaskEntry.COLUMN__BABY_IMAGE    + " STRING NOT NULL, " +
                BabyContract.TaskEntry.UPLOAD_BABY_STATUS   +"STRING NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BabyContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}

