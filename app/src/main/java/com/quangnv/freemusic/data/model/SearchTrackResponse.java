package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by quangnv on 14/10/2018
 */

public class SearchTrackResponse extends BaseModel implements Parcelable {

    @SerializedName("collection")
    @Expose
    private List<Track> mTracks;
    @SerializedName("total_results")
    @Expose
    private long mTotalResults;
    @SerializedName("next_href")
    @Expose
    private String mNextHref;
    @SerializedName("query_urn")
    @Expose
    private String mQueryUrn;

    private SearchTrackResponse(Builder builder) {
        mTracks = builder.mTracks;
        mTotalResults = builder.mTotalResults;
        mNextHref = builder.mNextHref;
        mQueryUrn = builder.mQueryUrn;
    }

    protected SearchTrackResponse(Parcel in) {
        mTracks = in.createTypedArrayList(Track.CREATOR);
        mTotalResults = in.readLong();
        mNextHref = in.readString();
        mQueryUrn = in.readString();
    }

    public static final Creator<SearchTrackResponse> CREATOR = new Creator<SearchTrackResponse>() {
        @Override
        public SearchTrackResponse createFromParcel(Parcel in) {
            return new SearchTrackResponse(in);
        }

        @Override
        public SearchTrackResponse[] newArray(int size) {
            return new SearchTrackResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mTracks);
        parcel.writeLong(mTotalResults);
        parcel.writeString(mNextHref);
        parcel.writeString(mQueryUrn);
    }

    public static class Builder {

        private List<Track> mTracks;
        private long mTotalResults;
        private String mNextHref;
        private String mQueryUrn;

        public Builder() {
        }

        public SearchTrackResponse build() {
            return new SearchTrackResponse(this);
        }

        public Builder setTracks(List<Track> tracks) {
            mTracks = tracks;
            return this;
        }

        public Builder setTotalResults(long totalResults) {
            mTotalResults = totalResults;
            return this;
        }

        public Builder setNextHref(String nextHref) {
            mNextHref = nextHref;
            return this;
        }

        public Builder setQueryUrn(String queryUrn) {
            mQueryUrn = queryUrn;
            return this;
        }
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public long getTotalResults() {
        return mTotalResults;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public String getQueryUrn() {
        return mQueryUrn;
    }
}
