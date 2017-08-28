package com.lfang.invoiceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by lfang on 8/22/17.
 */

public class Item implements Parcelable{
    private String mDescription;
    private String mPrice;

    public Item(String description, String price) {
        mDescription = description;
        mPrice = price;
    }
    private Item(Parcel source) {
        readFromParcel(source);
    }

    private void readFromParcel(Parcel source) {
        mDescription = source.readString();
        mPrice = source.readString();
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDescription);
        dest.writeString(mPrice);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item( source );
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
