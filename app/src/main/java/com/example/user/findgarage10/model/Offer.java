package com.example.user.findgarage10.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by student on 03-07-17.
 */

public class Offer implements Parcelable{

    //region POJO

    //region fields
    private int num_offer;
    private int num_user;
    private int num_garage;
    private String date_offer;
    private String description_offer;
    private String confirmed_offer;
    //endregion
    //region constructors

    public Offer(int num_user, int num_garage, String date_offer, String description_offer) {
        this.num_offer = -1;
        this.num_user = num_user;
        this.num_garage = num_garage;
        this.date_offer = date_offer;
        this.description_offer = description_offer;
        this.confirmed_offer = "pending";
    }

    public Offer(int num_offer, int num_user, int num_garage, String date_offer, String description_offer) {
        this(num_user, num_garage, date_offer, description_offer);
        this.num_offer = num_offer;
    }

    public Offer(int num_offer, int num_user, int num_garage, String date_offer, String description_offer, String confirmed_offer) {
        this(num_offer, num_user, num_garage, date_offer, description_offer);
        this.confirmed_offer = confirmed_offer;
    }
    //endregion
    //region getters ans setters

    public int getNum_offer() {
        return num_offer;
    }

    public void setNum_offer(int num_offer) {
        this.num_offer = num_offer;
    }

    public int getNum_user() {
        return num_user;
    }

    public void setNum_user(int num_user) {
        this.num_user = num_user;
    }

    public int getNum_garage() {
        return num_garage;
    }

    public void setNum_garage(int num_garage) {
        this.num_garage = num_garage;
    }

    public String getDate_offer() {
        return date_offer;
    }

    public void setDate_offer(String date_offer) {
        this.date_offer = date_offer;
    }

    public String getDescription_offer() {
        return description_offer;
    }

    public void setDescription_offer(String description_offer) {
        this.description_offer = description_offer;
    }

    public String getConfirmed_offer() {
        return confirmed_offer;
    }

    public void setConfirmed_offer(String confirmed_offer) {
        this.confirmed_offer = confirmed_offer;
    }


    //endregion

    @Override
    public String toString() {
        return "Num : " + num_offer +
                " Client : " + num_user +
                " Garage : " + num_garage +
                " Date : " + date_offer + '\'' +
                " Description : " + description_offer + '\'' +
                " Status : " + confirmed_offer;
    }
    //endregion
    //region parcelable
    protected Offer(Parcel in) {
        num_offer = in.readInt();
        num_user = in.readInt();
        num_garage = in.readInt();
        date_offer = in.readString();
        description_offer = in.readString();
        confirmed_offer = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(num_offer);
        parcel.writeInt(num_user);
        parcel.writeInt(num_garage);
        parcel.writeString(date_offer);
        parcel.writeString(description_offer);
        parcel.writeString(confirmed_offer);
    }
    //endregion
}
