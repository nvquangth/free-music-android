package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by quangnv on 12/10/2018
 */

public class Genre extends BaseModel implements Parcelable {

    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("image_url")
    @Expose
    private String mImageUrl;
    @SerializedName("meta_data")
    @Expose
    private String mMetaData;

    private Genre(Builder builder) {
        mName = builder.mName;
        mImageUrl = builder.mImageUrl;
        mMetaData = builder.mMetaData;
    }

    protected Genre(Parcel in) {
        mName = in.readString();
        mImageUrl = in.readString();
        mMetaData = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mImageUrl);
        parcel.writeString(mMetaData);
    }

    public static class Builder {

        private String mName;
        private String mImageUrl;
        private String mMetaData;

        public Builder() {
        }

        public Genre build() {
            return new Genre(this);
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
            return this;
        }

        public Builder setMetaData(String metaData) {
            mMetaData = metaData;
            return this;
        }
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getMetaData() {
        return mMetaData;
    }
}
