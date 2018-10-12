package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by quangnv on 12/10/2018
 */

public class Collection extends BaseModel implements Parcelable {

    @SerializedName("track")
    @Expose
    private Track mTrack;
    @SerializedName("score")
    @Expose
    private long mScore;

    private Collection(Builder builder) {
        mTrack = builder.mTrack;
        mScore = builder.mScore;
    }

    protected Collection(Parcel in) {
        mTrack = in.readParcelable(Track.class.getClassLoader());
        mScore = in.readLong();
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mTrack, i);
        parcel.writeLong(mScore);
    }

    public static class Builder {

        private Track mTrack;
        private long mScore;

        public Builder() {
        }

        public Collection build() {
            return new Collection(this);
        }

        public Builder setTrack(Track track) {
            mTrack = track;
            return this;
        }

        public Builder setScore(long score) {
            mScore = score;
            return this;
        }
    }

    public long getScore() {
        return mScore;
    }

    public void setScore(long score) {
        mScore = score;
    }
}
