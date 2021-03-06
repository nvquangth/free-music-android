package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by quangnv on 12/10/2018
 */

public class GetTrackResponse extends BaseModel implements Parcelable {

    @SerializedName("genre")
    @Expose
    private String mGenre;
    @SerializedName("kind")
    @Expose
    private String mKind;
    @SerializedName("last_updated")
    @Expose
    private String mLastUpdated;
    @SerializedName("collection")
    @Expose
    private List<Collection> mCollections;

    private GetTrackResponse(Builder builder) {
        mGenre = builder.mGenre;
        mKind = builder.mKind;
        mLastUpdated = builder.mLastUpdated;
        mCollections = builder.mCollections;
    }

    protected GetTrackResponse(Parcel in) {
        mGenre = in.readString();
        mKind = in.readString();
        mLastUpdated = in.readString();
        mCollections = in.createTypedArrayList(Collection.CREATOR);
    }

    public static final Creator<GetTrackResponse> CREATOR = new Creator<GetTrackResponse>() {
        @Override
        public GetTrackResponse createFromParcel(Parcel in) {
            return new GetTrackResponse(in);
        }

        @Override
        public GetTrackResponse[] newArray(int size) {
            return new GetTrackResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mGenre);
        parcel.writeString(mKind);
        parcel.writeString(mLastUpdated);
        parcel.writeTypedList(mCollections);
    }

    public static class Builder {

        private String mGenre;
        private String mKind;
        private String mLastUpdated;
        private List<Collection> mCollections;

        public Builder() {
        }

        public GetTrackResponse build() {
            return new GetTrackResponse(this);
        }

        public Builder setGenre(String genre) {
            mGenre = genre;
            return this;
        }

        public Builder setKind(String kind) {
            mKind = kind;
            return this;
        }

        public Builder setLastUpdated(String lastUpdated) {
            mLastUpdated = lastUpdated;
            return this;
        }

        public Builder setCollection(List<Collection> collections) {
            mCollections = collections;
            return this;
        }
    }

    public String getGenre() {
        return mGenre;
    }

    public String getKind() {
        return mKind;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public List<Collection> getCollections() {
        return mCollections;
    }
}
