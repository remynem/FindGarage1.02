package com.example.user.findgarage10.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by student on 03-07-17.
 */

public class Reservation implements Parcelable{
    //region POJO


    //region fields
    private int num_res;
    private String date_begin_res;
    private String date_end_res;
    private double price_res;
    private String comment_res;
    private String payed_res;
    private String closed_res;
    //endregion
    //region constructors

    public Reservation(String date_begin_res, String date_end_res, double price_res, String comment_res, String payed_res, String closed_res) {
        this.date_begin_res = date_begin_res;
        this.date_end_res = date_end_res;
        this.price_res = price_res;
        this.comment_res = comment_res;
        this.payed_res = payed_res;
        this.closed_res = closed_res;
    }

    public Reservation(int num_res, String date_begin_res, String date_end_res, double price_res, String comment_res, String payed_res, String closed_res) {
        this(date_begin_res, date_end_res, price_res, comment_res, payed_res, closed_res);
        this.num_res = num_res;
    }

    //endregion
    //region getters and setters
    public int getNum_res() {
        return num_res;
    }

    public void setNum_res(int num_res) {
        this.num_res = num_res;
    }

    public String getDate_begin_res() {
        return date_begin_res;
    }

    public void setDate_begin_res(String date_begin_res) {
        this.date_begin_res = date_begin_res;
    }

    public String getDate_end_res() {
        return date_end_res;
    }

    public void setDate_end_res(String date_end_res) {
        this.date_end_res = date_end_res;
    }

    public double getPrice_res() {
        return price_res;
    }

    public void setPrice_res(double price_res) {
        this.price_res = price_res;
    }

    public String getComment_res() {
        return comment_res;
    }

    public void setComment_res(String comment_res) {
        this.comment_res = comment_res;
    }

    public String isPayed_res() {
        return payed_res;
    }

    public void setPayed_res(String payed_res) {
        this.payed_res = payed_res;
    }

    public String getClosed_res() {
        return closed_res;
    }

    public void setClosed_res(String closed_res) {
        this.closed_res = closed_res;
    }

    //endregion

    @Override
    public String toString() {
        return "Reservation : " +
                "num_res=" + num_res +
                ", date_begin_res='" + date_begin_res + '\'' +
                ", date_end_res='" + date_end_res + '\'' +
                ", price_res=" + price_res +
                ", comment_res='" + comment_res + '\'' +
                ", payed_res=" + payed_res +
                ", closed_res='" + closed_res + '\'' +
                '}';
    }
    //endregion
    //region parcelable
    protected Reservation(Parcel in) {
        num_res = in.readInt();
        date_begin_res = in.readString();
        date_end_res = in.readString();
        price_res = in.readDouble();
        comment_res = in.readString();
        payed_res = in.readString();
        closed_res = in.readString();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(num_res);
        parcel.writeString(date_begin_res);
        parcel.writeString(date_end_res);
        parcel.writeDouble(price_res);
        parcel.writeString(comment_res);
        parcel.writeString(payed_res);
        parcel.writeString(closed_res);
    }
    //endregion

}
