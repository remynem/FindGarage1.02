package com.example.user.findgarage10.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by student on 03-07-17.
 */

public class ConnexionDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "com.example.user.findgarage10.localdatabase.db";
    private static final int DB_VERSION = 1;

    public ConnexionDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserDAO.CREATE_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(UserDAO.DELETE_REQUEST);
        onCreate(sqLiteDatabase);
    }
}
