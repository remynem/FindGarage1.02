package com.example.user.findgarage10.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.findgarage10.model.Offer;
import com.example.user.findgarage10.model.User;

/**
 * Created by student on 05-07-17.
 */

public class OfferDAO {

    //region db fields
    public static final String TABLE_OFFER = "offers";
    public static final String COLUMN_NUM_OFFER = "num_offer";
    public static final String COLUMN_NUM_USER = "num_user";
    public static final String COLUMN_NUM_GARAGE = "num_garage";
    public static final String COLUMN_DATE_OFFER = "date_offer";
    public static final String COLUMN_DESCRIPTION_OFFER = "description_offer";
    public static final String COLUMN_CONFIRMED_OFFER = "confirmed_offer";
    //endregion

    //region create and delete requests
    public static final String CREATE_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_OFFER + " (  "
            + COLUMN_NUM_OFFER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NUM_USER + " INTEGER NOT NULL, "
            + COLUMN_NUM_GARAGE + " INTEGER NOT NULL, "
            + COLUMN_DATE_OFFER + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION_OFFER + " TEXT NOT NULL, "
            + COLUMN_CONFIRMED_OFFER + " TEXT NOT NULL " +
            " );";

    public static final String CREATE = "create table offer if not exists (num INTEGER);";

    public static final String DELETE_REQUEST = "DROP TABLE IF EXISTS " + TABLE_OFFER + " ; ";
    public static final String PENDING_OFFER = "pending";
    //endregion

    //region fields class
    private ConnexionDB dbHelper;
    private Context context;
    private SQLiteDatabase db;
    //endregion

    public OfferDAO(Context context){
        this.context = context;

    }

    public OfferDAO openReadable(){
        dbHelper = new ConnexionDB(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public OfferDAO openWritable(){
        dbHelper = new ConnexionDB(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    private ContentValues offerToContentValues(Offer offer) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NUM_USER, offer.getNum_user());
        cv.put(COLUMN_NUM_GARAGE, offer.getNum_garage());
        cv.put(COLUMN_DATE_OFFER, offer.getDate_offer());
        cv.put(COLUMN_DESCRIPTION_OFFER, offer.getDescription_offer());
        cv.put(COLUMN_CONFIRMED_OFFER, offer.getConfirmed_offer());

        return cv;
    }

    public Offer cursorToOffer(Cursor cursor){
        int numOffer = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_OFFER));
        int numUser = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_USER));
        int numGarage = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_GARAGE));
        String dateOffer = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OFFER));
        String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_OFFER));
        String confirmedOffer = cursor.getString(cursor.getColumnIndex(COLUMN_CONFIRMED_OFFER));
        Offer offer = new Offer(numOffer, numUser, numGarage, dateOffer, description, confirmedOffer);

        return offer;
    }

    //region insert update and delete
    public Offer insertOffer(Offer offer){
        offer.setConfirmed_offer(PENDING_OFFER);
        ContentValues cv = offerToContentValues(offer);
        long id = db.insert(TABLE_OFFER, null, cv);
        if(id != -1){
            offer.setNum_offer((int)id);
        }
        return offer;
    }

    public Offer confirmOffer(Offer offer){
        offer.setConfirmed_offer("confirmed");
        String whereClause = COLUMN_NUM_OFFER + " = " + offer.getNum_offer() + " ; ";
        ContentValues cv = offerToContentValues(offer);
        db.update(TABLE_OFFER, cv, whereClause, null);
        return offer;
    }
    //endregion


    //region select
    public Offer[] getNotConfirmedOffers(){

        db.execSQL(CREATE_REQUEST);
        String whereClause = COLUMN_CONFIRMED_OFFER + "='" + PENDING_OFFER + "' ; ";
        Cursor cursor = db.query(TABLE_OFFER, null, whereClause, null, null, null, null);
        int count = cursor.getCount();
        Offer[] offers = new Offer[0];
        if(count > 0){
            offers = new Offer[count];
            for (int i = 0; i < count; i++){
                cursor.moveToPosition(i);
                offers[i] = cursorToOffer(cursor);
            }
        }
        close();
        return offers;
    }

    public void initOfferDb(){
        Offer of1 = new Offer(1,1,"12/05/12","entretien 15000km");
        insertOffer(of1);
    }
}
