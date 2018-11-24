package com.quangnv.freemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.quangnv.freemusic.util.StringUtils;

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
    @SerializedName("stream_url")
    @Expose
    private String mStreamUrl;
    @SerializedName("download_url")
    @Expose
    private String mDownloadUrl;

    private int mDownloadStatus;
    private int mIsAddedPlaylist;
    private int mIsAddedFavorite;
    private int mIsDownloaded;

    public Track() {
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
        mDownloadStatus = in.readInt();
        mIsAddedPlaylist = in.readInt();
        mIsAddedFavorite = in.readInt();
        mIsDownloaded = in.readInt();
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
        parcel.writeInt(mDownloadStatus);
        parcel.writeInt(mIsAddedPlaylist);
        parcel.writeInt(mIsAddedFavorite);
        parcel.writeInt(mIsDownloaded);
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public String getArtWorkUrl() {
        if (!StringUtils.isEmpty(mArtWorkUrl)) {
            mArtWorkUrl = mArtWorkUrl.replace("large", "t500x500");
        }
        return mArtWorkUrl;
    }

    public void setArtWorkUrl(String artWorkUrl) {
        mArtWorkUrl = artWorkUrl;
    }

    public boolean isStreamable() {
        return mStreamable;
    }

    public void setStreamable(boolean streamable) {
        mStreamable = streamable;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public long getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(long playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Publisher getPublisher() {
        return mPublisher;
    }

    public void setPublisher(Publisher publisher) {
        mPublisher = publisher;
    }

    public String getStreamUrl() {
        if (mStreamable) {
            return StringUtils.generateLinkStreamTrack(mId);
        }
        return mStreamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public String getDownloadUrl() {
        if (mDownloadable) {
            return StringUtils.generateLinkDownloadTrack(mId);
        }
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getDownloadStatus() {
        return mDownloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        mDownloadStatus = downloadStatus;
    }

    public int getIsAddedPlaylist() {
        return mIsAddedPlaylist;
    }

    public void setIsAddedPlaylist(int isAddedPlaylist) {
        mIsAddedPlaylist = isAddedPlaylist;
    }

    public int getIsAddedFavorite() {
        return mIsAddedFavorite;
    }

    public void setIsAddedFavorite(int isAddedFavorite) {
        mIsAddedFavorite = isAddedFavorite;
    }

    public int getIsDownloaded() {
        return mIsDownloaded;
    }

    public void setIsDownloaded(int isDownloaded) {
        mIsDownloaded = isDownloaded;
    }
}
