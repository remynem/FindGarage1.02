package com.example.user.findgarage10.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 3/07/17.
 */

public class Garage implements Parcelable{

    //region POJO

    //region fields
    private int num_garage;
    private String name_garage;
    private String domain_garage;
    private String email_garage;
    private String tel_garage;
    private String adress_garage;
    //endregion
    //region constructors

    public Garage(String name_garage, String domain_garage, String email_garage, String tel_garage, String adress_garage) {
        this.num_garage = -1;
        this.name_garage = name_garage;
        this.domain_garage = domain_garage;
        this.email_garage = email_garage;
        this.tel_garage = tel_garage;
        this.adress_garage = adress_garage;
    }

    public Garage(int num_garage, String name_garage, String domain_garage, String email_garage, String tel_garage, String adress_garage) {
        this(name_garage, domain_garage, email_garage, tel_garage, adress_garage);
        this.num_garage = num_garage;
    }


    //endregion
    //region getters and setters

    public int getNum_garage() {
        return num_garage;
    }

    public void setNum_garage(int num_garage) {
        this.num_garage = num_garage;
    }

    public String getName_garage() {
        return name_garage;
    }

    public void setName_garage(String name_garage) {
        this.name_garage = name_garage;
    }

    public String getDomain_garage() {
        return domain_garage;
    }

    public void setDomain_garage(String domain_garage) {
        this.domain_garage = domain_garage;
    }

    public String getEmail_garage() {
        return email_garage;
    }

    public void setEmail_garage(String email_garage) {
        this.email_garage = email_garage;
    }

    public String getTel_garage() {
        return tel_garage;
    }

    public void setTel_garage(String tel_garage) {
        this.tel_garage = tel_garage;
    }

    public String getAdress_garage() {
        return adress_garage;
    }

    public void setAdress_garage(String adress_garage) {
        this.adress_garage = adress_garage;
    }

    //endregion

    @Override
    public String toString() {
        return "Garage : " +
                "name_garage='" + name_garage + '\'' +
                ", domain_garage='" + domain_garage + '\'' +
                ", email_garage='" + email_garage + '\'' +
                ", tel_garage='" + tel_garage + '\'' +
                ", adress_garage='" + adress_garage + '\'' +
                '}';
    }

    //endregion

    //region parcelable

    protected Garage(Parcel in) {
        num_garage = in.readInt();
        name_garage = in.readString();
        domain_garage = in.readString();
        email_garage = in.readString();
        tel_garage = in.readString();
        adress_garage = in.readString();
    }

    public static final Creator<Garage> CREATOR = new Creator<Garage>() {
        @Override
        public Garage createFromParcel(Parcel in) {
            return new Garage(in);
        }

        @Override
        public Garage[] newArray(int size) {
            return new Garage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(num_garage);
        parcel.writeString(name_garage);
        parcel.writeString(domain_garage);
        parcel.writeString(email_garage);
        parcel.writeString(tel_garage);
        parcel.writeString(adress_garage);
    }
    //endregion
}
