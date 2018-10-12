package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by quangnv on 12/10/2018
 */

public class Publisher extends BaseModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private long mId;
    @SerializedName("urn")
    @Expose
    private String mUrn;
    @SerializedName("artist")
    @Expose
    private String mArtist;
    @SerializedName("album_title")
    @Expose
    private String mAlbumTitle;

    private Publisher(Builder builder) {
        mId = builder.mId;
        mUrn = builder.mUrn;
        mArtist = builder.mArtist;
        mAlbumTitle = builder.mAlbumTitle;
    }

    protected Publisher(Parcel in) {
        mId = in.readLong();
        mUrn = in.readString();
        mArtist = in.readString();
        mAlbumTitle = in.readString();
    }

    public static final Creator<Publisher> CREATOR = new Creator<Publisher>() {
        @Override
        public Publisher createFromParcel(Parcel in) {
            return new Publisher(in);
        }

        @Override
        public Publisher[] newArray(int size) {
            return new Publisher[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mUrn);
        parcel.writeString(mArtist);
        parcel.writeString(mAlbumTitle);
    }

    public static class Builder {

        private long mId;
        private String mUrn;
        private String mArtist;
        private String mAlbumTitle;

        public Builder() {
        }

        public Publisher build() {
            return new Publisher(this);
        }

        public Builder setId(long id) {
            mId = id;
            return this;
        }

        public Builder setUrn(String urn) {
            mUrn = urn;
            return this;
        }

        public Builder setArtist(String artist) {
            mArtist = artist;
            return this;
        }

        public Builder setAlbumTitle(String albumTitle) {
            mAlbumTitle = albumTitle;
            return this;
        }
    }

    public long getId() {
        return mId;
    }

    public String getUrn() {
        return mUrn;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getAlbumTitle() {
        return mAlbumTitle;
    }
}
