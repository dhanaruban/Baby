package com.dhanaruban.babycasket.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thenu on 08-03-2018.
 */

public class UserSettingDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "usersetting.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    UserSettingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + UserSettingContract.TaskEntry.TABLE_NAME + " (" +
                UserSettingContract.TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                UserSettingContract.TaskEntry.COLUMN_OPTION   + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserSettingContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}


