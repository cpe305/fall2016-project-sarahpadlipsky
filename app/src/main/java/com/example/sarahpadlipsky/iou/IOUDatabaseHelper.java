package com.example.sarahpadlipsky.iou;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to handle database operations.
 * @author sarahpadlipsky
 * @version October 13, 2016
 */
public class IOUDatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "iouDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_USERS = "users";

    //Groups Table Columns
    private static final String KEY_GROUPS_NAME = "name";
    private static final String KEY_GROUPS_DESCRIPTION = "description";
    private static final String KEY_GROUPS_TOTALMONEYSPENT = "totalMoneySpent";
    private static final String KEY_GROUPS_USERS = "listOfUsers";

    //Users Table Columns
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_MONEYSPENT = "moneySpent";
    private static final String KEY_USER_MONEYOWED = "moneyOwed";

    private static IOUDatabaseHelper sInstance;

    public static synchronized IOUDatabaseHelper getInstance(Context context) {
        // Uses application context to ensure that Activity's context is not leaked.
        if (sInstance == null) {
            sInstance = new IOUDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private IOUDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {

    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROUPS_TABLE = "CREATE TABLE";
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
