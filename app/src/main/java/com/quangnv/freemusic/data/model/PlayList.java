package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by quangnv on 12/10/2018
 */

public class PlayList extends BaseModel implements Parcelable {

    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("track")
    @Expose
    private List<Track> mTracks;

    private PlayList(Builder builder) {
        mName = builder.mName;
        mTracks = builder.mTracks;
    }

    protected PlayList(Parcel in) {
        mName = in.readString();
        mTracks = in.createTypedArrayList(Track.CREATOR);
    }

    public static final Creator<PlayList> CREATOR = new Creator<PlayList>() {
        @Override
        public PlayList createFromParcel(Parcel in) {
            return new PlayList(in);
        }

        @Override
        public PlayList[] newArray(int size) {
            return new PlayList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeTypedList(mTracks);
    }

    public static class Builder {

        private String mName;
        private List<Track> mTracks;

        public Builder() {
        }

        public PlayList build() {
            return new PlayList(this);
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        private Builder setTrack(List<Track> tracks) {
            mTracks = tracks;
            return this;
        }
    }

    public String getName() {
        return mName;
    }

    public List<Track> getTracks() {
        return mTracks;
    }
}
