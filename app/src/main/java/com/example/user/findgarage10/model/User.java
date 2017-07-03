package com.example.user.findgarage10.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 3/07/17.
 */

public class User implements Parcelable{

    //region POJO

    //region Fields
    private int num_user;
    private String firstName_user;
    private String lastName_user;
    private String emailUser;
    private String telUser;
    private String adresseUser;
    //endregion

    //region constructors

    public User(String firstName_user, String lastName_user, String emailUser, String telUser, String adresseUser) {
        this.num_user = -1;
        this.firstName_user = firstName_user;
        this.lastName_user = lastName_user;
        this.emailUser = emailUser;
        this.telUser = telUser;
        this.adresseUser = adresseUser;
    }

    public User(int num_user, String firstName_user, String lastName_user, String emailUser, String telUser, String adresseUser) {
        this(firstName_user, lastName_user, emailUser, telUser, adresseUser);
        setNum_user(num_user);
    }

    //endregion

    //region getters and setters
    public int getNum_user() {
        return num_user;
    }

    public void setNum_user(int num_user) {
        this.num_user = num_user;
    }

    public String getFirstName_user() {
        return firstName_user;
    }

    public void setFirstName_user(String firstName_user) {
        this.firstName_user = firstName_user;
    }

    public String getLastName_user() {
        return lastName_user;
    }

    public void setLastName_user(String lastName_user) {
        this.lastName_user = lastName_user;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getTelUser() {
        return telUser;
    }

    public void setTelUser(String telUser) {
        this.telUser = telUser;
    }

    public String getAdresseUser() {
        return adresseUser;
    }

    public void setAdresseUser(String adresseUser) {
        this.adresseUser = adresseUser;
    }
    //endregion

    @Override
    public String toString() {
        return "User : " + firstName_user + " " + lastName_user;
    }
    //endregion

    //region Parcalable

    protected User(Parcel in) {
        num_user = in.readInt();
        firstName_user = in.readString();
        lastName_user = in.readString();
        emailUser = in.readString();
        telUser = in.readString();
        adresseUser = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(num_user);
        parcel.writeString(firstName_user);
        parcel.writeString(lastName_user);
        parcel.writeString(emailUser);
        parcel.writeString(telUser);
        parcel.writeString(adresseUser);
    }

    //endregion
}
