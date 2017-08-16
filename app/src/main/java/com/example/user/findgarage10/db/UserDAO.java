package com.example.user.findgarage10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.user.findgarage10.model.User;

/**
 * Created by student on 03-07-17.
 */

public class UserDAO {

    //region fields db
    public static final String TABLE_USER = "user";
    public static final String COLUMN_NUM_USER = "num_user";
    public static final String COLUMN_FIRST_NAME_USER = "firstName_user";
    public static final String COLUMN_LAST_NAME_USER = "lastName_user";
    public static final String COLUMN_EMAIL_USER = "email_user";
    public static final String COLUMN_PASSWORD_USER = "pwd_user";
    public static final String COLUMN_TEL_USER = "tel_user";
    public static final String COLUMN_ADRESS_USER = "adress_user";
    //endregion

    //region request
    public static final String CREATE_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (   "
            + COLUMN_NUM_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FIRST_NAME_USER + " TEXT NOT NULL, "
            + COLUMN_LAST_NAME_USER + " TEXT NOT NULL, "
            + COLUMN_EMAIL_USER + " TEXT NOT NULL, "
            + COLUMN_PASSWORD_USER + " TEXT NOT NULL, "
            + COLUMN_TEL_USER + " TEXT NOT NULL, "
            + COLUMN_ADRESS_USER + " TEXT NOT NULL" +
            " );";
    public static final String DELETE_REQUEST = "DROP TABLE IF EXISTS " + TABLE_USER + " ; ";
    private static boolean IS_LOGGED_IN = false;
    //region fields class
    private ConnexionDB dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        this.context = context;
    }

    public UserDAO openReadable() {
        dbHelper = new ConnexionDB(context);
        db = dbHelper.getReadableDatabase();
        db.execSQL(CREATE_REQUEST);
        return this;
    }
    //endregion

    public UserDAO openWritable() {
        dbHelper = new ConnexionDB(context);
        db = dbHelper.getWritableDatabase();
        db.execSQL(CREATE_REQUEST);
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues userToContentValues(User user) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME_USER, user.getFirstName_user());
        cv.put(COLUMN_LAST_NAME_USER, user.getLastName_user());
        cv.put(COLUMN_EMAIL_USER, user.getEmailUser());
        cv.put(COLUMN_PASSWORD_USER, user.getPwdUser());
        cv.put(COLUMN_TEL_USER, user.getTelUser());
        cv.put(COLUMN_ADRESS_USER, user.getAdresseUser());

        return cv;
    }

    public User insertUser(User user) {
        ContentValues cv = userToContentValues(user);
        long id = db.insert(TABLE_USER, null, cv);
        if (id != -1) {
            user.setNum_user((int) id);
            return user;
        }
        return user;
    }

    public User cursorToUser(Cursor cursor) {
        int numUser = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_USER));
        String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME_USER));
        String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME_USER));
        String emailUser = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USER));
        String pwdUser = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_USER));
        String telUser = cursor.getString(cursor.getColumnIndex(COLUMN_TEL_USER));
        String adressUser = cursor.getString(cursor.getColumnIndex(COLUMN_ADRESS_USER));
        User user = new User(numUser, firstName, lastName, emailUser, pwdUser, telUser, adressUser);
        return user;
    }

    public User getUserByNumUser(int numUser) {
        String whereClause = COLUMN_NUM_USER + " = " + numUser;
        Cursor cursor = db.query(TABLE_USER, null, whereClause, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            return cursorToUser(cursor);
        }
        return null;
    }

    public User getUserByLogin(String login, String password) {
        String whereClause = COLUMN_FIRST_NAME_USER + "='" + login + "' AND " + COLUMN_PASSWORD_USER + " LIKE '" + password + "';";
        Log.i("test pwd", whereClause);
        Cursor cursor = db.query(TABLE_USER, null, whereClause, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            return cursorToUser(cursor);
        }
        return null;
    }


    public void initTableUser() {
        /*User user = new User("paul", "rem", "bt@email.be", "123", "04526", "adresse");
        User user2 = new User("kevin", "test", "bt@email.be", "123","04526", "adresse");
        insertUser(user);
        insertUser(user2);*/
    }

    public User[] getAllUsers() {
        User[] toReturn;
        Cursor cursor = db.query(TABLE_USER, null, null, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            toReturn = new User[count];
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                toReturn[i] = cursorToUser(cursor);
            }
            return toReturn;
        }
        return null;
    }
    //endregion


}
