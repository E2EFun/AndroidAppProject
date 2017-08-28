package com.lfang.invoiceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lfang on 8/22/17.
 */

public class Invoice implements Parcelable{
    private UUID mId;
    private String mName;
    private String mDate;
    private String mEmail;
    private String mTotal;
    private List<Item> mItemList;

    public Invoice(String name, String email, String date, String total) {
        mName = name;
        mEmail = email;
        mDate = date;
        mTotal = total;
    }

    public Invoice(UUID uuid, String name, String email, String date, String total) {
        mId = uuid;
        mName = name;
        mEmail = email;
        mDate = date;
        mTotal = total;
    }

    public Invoice(UUID uuid, String name, String email, String date, String total, List<Item> itemList) {
        mId = uuid;
        mName = name;
        mEmail = email;
        mDate = date;
        mTotal = total;
        mItemList = itemList;
    }

    private Invoice(Parcel source) {
        readFromParcel(source);
    }

    private void readFromParcel(Parcel source) {
        mName = source.readString();
        mEmail = source.readString();
        mDate = source.readString();
        mTotal = source.readString();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(Item item) {
        mItemList.add(item);
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        this.mTotal = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mDate);
        dest.writeString(mTotal);
    }

    public static final Parcelable.Creator<Invoice> CREATOR = new Parcelable.Creator<Invoice>() {

        @Override
        public Invoice createFromParcel(Parcel source) {
            return new Invoice( source );
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };

}
