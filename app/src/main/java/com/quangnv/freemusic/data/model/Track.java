package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by quangnv on 12/10/2018
 */

public class Track extends BaseModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private long mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("duration")
    @Expose
    private long mDuration;
    @SerializedName("artwork_url")
    @Expose
    private String mArtWorkUrl;
    @SerializedName("streamable")
    @Expose
    private boolean mStreamable;
    @SerializedName("downloadable")
    @Expose
    private boolean mDownloadable;
    @SerializedName("genre")
    @Expose
    private String mGenre;
    @SerializedName("playback_count")
    @Expose
    private long mPlaybackCount;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("publisher_metadata")
    @Expose
    private Publisher mPublisher;
    private String mStreamUrl;
    private String mDownloadUrl;

    private Track(Builder builder) {
        mId = builder.mId;
        mTitle = builder.mTitle;
        mDuration = builder.mDuration;
        mArtWorkUrl = builder.mArtWorkUrl;
        mStreamable = builder.mStreamable;
        mDownloadable = builder.mDownloadable;
        mGenre = builder.mGenre;
        mPlaybackCount = builder.mPlaybackCount;
        mDescription = builder.mDescription;
        mPublisher = builder.mPublisher;
        mStreamUrl = builder.mStreamUrl;
        mDownloadUrl = builder.mDownloadUrl;
    }

    protected Track(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mDuration = in.readLong();
        mArtWorkUrl = in.readString();
        mStreamable = in.readByte() != 0;
        mDownloadable = in.readByte() != 0;
        mGenre = in.readString();
        mPlaybackCount = in.readLong();
        mDescription = in.readString();
        mPublisher = in.readParcelable(Publisher.class.getClassLoader());
        mStreamUrl = in.readString();
        mDownloadUrl = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mTitle);
        parcel.writeLong(mDuration);
        parcel.writeString(mArtWorkUrl);
        parcel.writeByte((byte) (mStreamable ? 1 : 0));
        parcel.writeByte((byte) (mDownloadable ? 1 : 0));
        parcel.writeString(mGenre);
        parcel.writeLong(mPlaybackCount);
        parcel.writeString(mDescription);
        parcel.writeParcelable(mPublisher, i);
        parcel.writeString(mStreamUrl);
        parcel.writeString(mDownloadUrl);
    }

    public static class Builder {

        private long mId;
        private String mTitle;
        private long mDuration;
        private String mArtWorkUrl;
        private boolean mStreamable;
        private boolean mDownloadable;
        private String mGenre;
        private long mPlaybackCount;
        private String mDescription;
        private Publisher mPublisher;
        private String mStreamUrl;
        private String mDownloadUrl;

        public Builder() {

        }

        public Track build() {
            return new Track(this);
        }

        public Builder setId(long id) {
            mId = id;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setDuration(long duration) {
            mDuration = duration;
            return this;
        }

        public Builder setArtWorkUrl(String artWorkUrl) {
            mArtWorkUrl = artWorkUrl;
            return this;
        }

        public Builder setStreamable(boolean streamable) {
            mStreamable = streamable;
            return this;
        }

        public Builder setDownloadable(boolean downloadable) {
            mDownloadable = downloadable;
            return this;
        }

        public Builder setGenre(String genre) {
            mGenre = genre;
            return this;
        }

        public Builder setPlaybackCount(long playbackCount) {
            mPlaybackCount = playbackCount;
            return this;
        }

        public Builder setDescription(String description) {
            mDescription = description;
            return this;
        }

        public Builder setPublisher(Publisher publisher) {
            mPublisher = publisher;
            return this;
        }

        public Builder setStreamUrl(String streamUrl) {
            mStreamUrl = streamUrl;
            return this;
        }

        public Builder setDownloadUrl(String downloadUrl) {
            mDownloadUrl = downloadUrl;
            return this;
        }
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public long getDuration() {
        return mDuration;
    }

    public String getArtWorkUrl() {
        return mArtWorkUrl;
    }

    public boolean isStreamable() {
        return mStreamable;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public String getGenre() {
        return mGenre;
    }

    public long getPlaybackCount() {
        return mPlaybackCount;
    }

    public String getDescription() {
        return mDescription;
    }

    public Publisher getPublisher() {
        return mPublisher;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }
}
