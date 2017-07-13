package com.example.user.findgarage10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.findgarage10.model.Garage;

/**
 * Created by student on 10-07-17.
 */

public class GarageDAO {
    //region fields db
    public static final String TABLE_GARAGE = "garage";
    public static final String COLUMN_NUM_GARAGE = "num_garage";
    public static final String COLUMN_NAME_GARAGE = "name_garage";
    public static final String COLUMN_DOMAINE_GARAGE = "domaine_garage";
    public static final String COLUMN_PASSWORD_GARAGE = "pwd_garage";
    public static final String COLUMN_EMAIL_GARAGE = "email_garage";
    public static final String COLUMN_TEL_GARAGE = "tel_garage";
    public static final String COLUMN_ADRESS_GARAGE = "adress_garage";
    //endregion

    public static final String CREATE_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_GARAGE + " (   "
            + COLUMN_NUM_GARAGE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_GARAGE + " TEXT NOT NULL, "
            + COLUMN_DOMAINE_GARAGE + " TEXT NOT NULL, "
            + COLUMN_PASSWORD_GARAGE + " TEXT NOT NULL, "
            + COLUMN_EMAIL_GARAGE + " TEXT NOT NULL, "
            + COLUMN_TEL_GARAGE + " TEXT NOT NULL, "
            + COLUMN_ADRESS_GARAGE + " TEXT NOT NULL" +
            " );";
    public static final String DELETE_REQUEST = "DROP TABLE IF EXISTS " + TABLE_GARAGE + " ; ";

    //region fields class
    private ConnexionDB dbHelper;
    private Context context;
    private SQLiteDatabase db;

    //endregion
    public GarageDAO(Context context) {
        this.context = context;
    }

    public GarageDAO openReadable() {
        dbHelper = new ConnexionDB(context);
        db = dbHelper.getReadableDatabase();
        db.execSQL(CREATE_REQUEST);
        return this;
    }


    public GarageDAO openWritable() {
        dbHelper = new ConnexionDB(context);
        db = dbHelper.getWritableDatabase();
        db.execSQL(CREATE_REQUEST);
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    private ContentValues garageToContentValues(Garage garage) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_GARAGE, garage.getName_garage());
        cv.put(COLUMN_DOMAINE_GARAGE, garage.getDomain_garage());
        cv.put(COLUMN_EMAIL_GARAGE, garage.getEmail_garage());
        cv.put(COLUMN_PASSWORD_GARAGE, garage.getPwd_garage());
        cv.put(COLUMN_TEL_GARAGE, garage.getTel_garage());
        cv.put(COLUMN_ADRESS_GARAGE, garage.getAdress_garage());

        return cv;
    }

    public Garage cursorToGarage(Cursor cursor) {
        int numGarage = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_GARAGE));
        String nameGarage = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_GARAGE));
        String domainGarage = cursor.getString(cursor.getColumnIndex(COLUMN_DOMAINE_GARAGE));
        String pwdGarage = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_GARAGE));
        String emailGarage = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_GARAGE));
        String telGarage = cursor.getString(cursor.getColumnIndex(COLUMN_TEL_GARAGE));
        String adressGarage = cursor.getString(cursor.getColumnIndex(COLUMN_ADRESS_GARAGE));
        Garage garage = new Garage(numGarage, nameGarage, domainGarage, emailGarage, pwdGarage, telGarage, adressGarage);
        return garage;
    }

    public Garage insertGarage(Garage garage) {
        ContentValues cv = garageToContentValues(garage);
        long id = db.insert(TABLE_GARAGE, null, cv);
        if (id != -1) {
            garage.setNum_garage((int) id);
            return garage;
        }
        return garage;
    }

    public void initTableGarage() {
        /*Garage garage_ixelles = new Garage("Toyota ixelles", "Mecanique", "toyota-ixelles@gmail.com", "027553595", "Rue Leon Cuissez 29, 1050 Bruxelles");
        Garage garage_mail = new Garage("Dieteren Mail", "Carrosserie", "dieteren-mail@gmail.com", "027553595", "Rue du Mail 50, 1050 Ixelles");*/
        /*insertGarage(garage_ixelles);
        insertGarage(garage_mail);*/
    }

    public Garage[] getAllGarages() {
        Garage[] toReturn;
        Cursor cursor = db.query(TABLE_GARAGE, null, null, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            toReturn = new Garage[count];
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                toReturn[i] = cursorToGarage(cursor);
            }
            return toReturn;
        }
        return null;
    }

    public Garage getGarageByName(String nameGarage) {
        String whereClause = COLUMN_NAME_GARAGE + "='" + nameGarage + "';";
        Cursor cursor = db.query(TABLE_GARAGE, null, whereClause, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            return cursorToGarage(cursor);
        }
        return null;
    }

    public Garage getGarageByLogin(String nameGarage, String password) {
        String whereClause = COLUMN_NAME_GARAGE + "='" + nameGarage + "' AND " + COLUMN_PASSWORD_GARAGE + " = '" + password + "';";

        Cursor cursor = db.query(TABLE_GARAGE, null, whereClause, null, null, null, null);
        int count = cursor.getCount();
        if (count > 0) {
            cursor.moveToFirst();
            return cursorToGarage(cursor);
        }
        return null;
    }
}
