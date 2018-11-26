package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quangnv on 26/11/2018
 */

public class SearchHistory implements Parcelable {

    private int mId;
    private String mTitle;

    public SearchHistory() {
    }

    protected SearchHistory(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
    }

    public static final Creator<SearchHistory> CREATOR = new Creator<SearchHistory>() {
        @Override
        public SearchHistory createFromParcel(Parcel in) {
            return new SearchHistory(in);
        }

        @Override
        public SearchHistory[] newArray(int size) {
            return new SearchHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
