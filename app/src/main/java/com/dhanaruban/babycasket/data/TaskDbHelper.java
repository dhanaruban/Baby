package com.dhanaruban.babycasket.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thenu on 21-02-2018.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "tasksDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                TaskContract.TaskEntry.COLUMN_RELATIONSHIP + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_IMAGE    + " STRING NOT NULL, " +
                TaskContract.TaskEntry.TASK_LOCAL_PATH    + " STRING NOT NULL, " +
                TaskContract.TaskEntry.UPLOAD_STATUS   + " STRING NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}

