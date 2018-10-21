package com.quangnv.freemusic.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.quangnv.freemusic.data.model.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangnv on 20/10/2018
 */

public final class MediaPlayerManager extends MediaPlayerSetting implements BaseMediaPlayer,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener {

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private List<Track> mTracks;
    private int mCurrentTrack;
    @MediaPlayerStateType
    private int mState;
    private List<MediaPlayerListener.OnLoopingListener> mLoopingListeners;
    private List<MediaPlayerListener.OnShufflingListener> mShufflingListeners;
    private List<MediaPlayerListener.OnPlayingListener> mPlayingListeners;
    private List<MediaPlayerListener.OnCurrentTimeListener> mCurrentTimeListeners;
    private List<MediaPlayerListener.OnTotalTimeListener> mTotalTimeListeners;
    private List<MediaPlayerListener.OnTrackListener> mTrackListeners;
    private List<MediaPlayerListener.OnErrorListener> mErrorListeners;

    public MediaPlayerManager(Context context) {
        mContext = context;
        mLoopingListeners = new ArrayList<>();
        mShufflingListeners = new ArrayList<>();
        mPlayingListeners = new ArrayList<>();
        mCurrentTimeListeners = new ArrayList<>();
        mTotalTimeListeners = new ArrayList<>();
        mTrackListeners = new ArrayList<>();
        mErrorListeners = new ArrayList<>();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        setState(MediaPlayerStateType.IDLE);
    }

    @Override
    public void init() {
        if (mTracks.isEmpty()) return;

        // sure media player's state is idle
        if (mState != MediaPlayerStateType.IDLE) reset();

        Uri uri = Uri.parse(mTracks.get(mCurrentTrack).getStreamUrl());
        try {
            mMediaPlayer.setDataSource(mContext, uri);
            setState(MediaPlayerStateType.INITIALIZED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            next();
        } catch (IOException e) {
            e.printStackTrace();
            next();
        }

        if (mState == MediaPlayerStateType.INITIALIZED) {
            mMediaPlayer.prepareAsync();
            setState(MediaPlayerStateType.PREPARING);
        }
    }

    @Override
    public void start() {
        if (mState != MediaPlayerStateType.IDLE
                && mState != MediaPlayerStateType.INITIALIZED
                && mState != MediaPlayerStateType.STOPPED
                && mState != MediaPlayerStateType.ERROR) {
            mMediaPlayer.start();
            setState(MediaPlayerStateType.STARTED);
        }
    }

    @Override
    public void pause() {
        if (mState != MediaPlayerStateType.IDLE
                && mState != MediaPlayerStateType.INITIALIZED
                && mState != MediaPlayerStateType.PREPARED
                && mState != MediaPlayerStateType.STOPPED
                && mState != MediaPlayerStateType.ERROR) {
            mMediaPlayer.pause();
            setState(MediaPlayerStateType.PAUSED);
        }
    }

    @Override
    public void reset() {
        if (mState != MediaPlayerStateType.PREPARING) {
            mMediaPlayer.reset();
            setState(MediaPlayerStateType.IDLE);
        }
    }

    @Override
    public void release() {
        mMediaPlayer.release();
        setState(MediaPlayerStateType.END);
    }

    @Override
    public void stop() {
        if (mState != MediaPlayerStateType.IDLE
                && mState != MediaPlayerStateType.INITIALIZED
                && mState != MediaPlayerStateType.ERROR) {
            mMediaPlayer.stop();
            setState(MediaPlayerStateType.STOPPED);
        }
    }

    @Override
    public void next() {
        if (mCurrentTrack < mTracks.size() - 1) {
            mCurrentTrack++;
            init();
        }
    }

    @Override
    public void previous() {
        if (mCurrentTrack > 0) {
            mCurrentTrack--;
            init();
        }
    }

    @Override
    public void seekTo(int msec) {
        if (mState != MediaPlayerStateType.IDLE
                && mState != MediaPlayerStateType.INITIALIZED
                && mState != MediaPlayerStateType.STOPPED
                && mState != MediaPlayerStateType.ERROR) {
            mMediaPlayer.seekTo(msec);
        }
    }

    @Override
    public int getDuration() {
        if (mState != MediaPlayerStateType.IDLE
                && mState != MediaPlayerStateType.INITIALIZED
                && mState != MediaPlayerStateType.ERROR) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (mState != MediaPlayerStateType.ERROR) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        setState(MediaPlayerStateType.STARTED);
        start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        setState(MediaPlayerStateType.PLAYBACK_COMPLETE);
        switch (getLoop()) {
            case MediaPlayerLoopType.NONE:
                next();
                break;
            case MediaPlayerLoopType.ONE:
                break;
            case MediaPlayerLoopType.ALL:
                if (mTracks.size() - 1 == mCurrentTrack) {
                    mCurrentTrack = 0;
                }
                next();
                break;
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        setState(MediaPlayerStateType.ERROR);
        reset();
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        notifyCurrentTimeChanged();
    }

    @Override
    public void setPlay(@MediaPlayerPlayType int play) {
        super.setPlay(play);
        notifyPlayChanged();
    }

    @Override
    public void setLoop(@MediaPlayerLoopType int loop) {
        super.setLoop(loop);
        switch (loop) {
            case MediaPlayerLoopType.NONE:
                mMediaPlayer.setLooping(false);
                break;
            case MediaPlayerLoopType.ONE:
                mMediaPlayer.setLooping(true);
                break;
            case MediaPlayerLoopType.ALL:
                break;
            default:
                mMediaPlayer.setLooping(true);
                break;
        }
        notifyLoopChanged();
    }

    @Override
    public void setShuffle(@MediaPlayerShuffleType int shuffle) {
        super.setShuffle(shuffle);
        notifyShuffleChanged();
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public int getCurrentTrack() {
        return mCurrentTrack;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public void setCurrentTrack(int currentTrack) {
        mCurrentTrack = currentTrack;
    }

    public int getState() {
        return mState;
    }

    public void setState(@MediaPlayerStateType int state) {
        mState = state;
        switch (state) {
            case MediaPlayerStateType.IDLE:
                setPlay(MediaPlayerPlayType.WAIT);
                break;
            case MediaPlayerStateType.INITIALIZED:
                setPlay(MediaPlayerPlayType.WAIT);
                notifyTrackChanged();
                break;
            case MediaPlayerStateType.PREPARING:
                setPlay(MediaPlayerPlayType.WAIT);
                break;
            case MediaPlayerStateType.PREPARED:
                setPlay(MediaPlayerPlayType.PAUSE);
                notifyTotalTimeChanged();
                break;
            case MediaPlayerStateType.STARTED:
                setPlay(MediaPlayerPlayType.PLAY);
                break;
            case MediaPlayerStateType.PAUSED:
                setPlay(MediaPlayerPlayType.PAUSE);
                break;
            case MediaPlayerStateType.STOPPED:
                setPlay(MediaPlayerPlayType.PAUSE);
                break;
            case MediaPlayerStateType.PLAYBACK_COMPLETE:
                setPlay(MediaPlayerPlayType.PAUSE);
                break;
            case MediaPlayerStateType.END:
                setPlay(MediaPlayerPlayType.PAUSE);
                break;
            case MediaPlayerStateType.ERROR:
                setPlay(MediaPlayerPlayType.WAIT);
                break;
            default:
                break;
        }
        notifyPlayChanged();
    }

    public void addLoopingListener(MediaPlayerListener.OnLoopingListener listener) {
        mLoopingListeners.add(listener);
        if (mTracks != null && !mTracks.isEmpty()) notifyLoopChanged();
    }

    public void addShufflingListener(MediaPlayerListener.OnShufflingListener listener) {
        mShufflingListeners.add(listener);
        if (mTracks != null && !mTracks.isEmpty()) notifyShuffleChanged();
    }

    public void addPlayingListener(MediaPlayerListener.OnPlayingListener listener) {
        mPlayingListeners.add(listener);
        if (mTracks != null && !mTracks.isEmpty()) notifyPlayChanged();
    }

    public void addCurrentTimeListener(MediaPlayerListener.OnCurrentTimeListener listener) {
        mCurrentTimeListeners.add(listener);
        if (mTracks != null && !mTracks.isEmpty()) notifyCurrentTimeChanged();
    }

    public void addTotalTimeListener(MediaPlayerListener.OnTotalTimeListener listener) {
        mTotalTimeListeners.add(listener);
        if (mTracks != null && !mTracks.isEmpty()) notifyTotalTimeChanged();
    }

    public void addTrackListener(MediaPlayerListener.OnTrackListener listener) {
        mTrackListeners.add(listener);
        if (mTracks != null && !mTracks.isEmpty()) notifyTrackChanged();
    }

    public void addTrackErrorListener(MediaPlayerListener.OnErrorListener listener) {
        mErrorListeners.add(listener);
    }

    public void removeLoopingListener(MediaPlayerListener.OnLoopingListener listener) {
        mLoopingListeners.remove(listener);
    }

    public void removeShufflingListener(MediaPlayerListener.OnShufflingListener listener) {
        mShufflingListeners.remove(listener);
    }

    public void removePlayingListener(MediaPlayerListener.OnPlayingListener listener) {
        mPlayingListeners.remove(listener);
    }

    public void removeCurrentTimeListener(MediaPlayerListener.OnCurrentTimeListener listener) {
        mCurrentTimeListeners.remove(listener);
    }

    public void removeTotalTimeListener(MediaPlayerListener.OnTotalTimeListener listener) {
        mTotalTimeListeners.remove(listener);
    }

    public void removeTrackListener(MediaPlayerListener.OnTrackListener listener) {
        mTrackListeners.remove(listener);
    }

    public void removeTrackErrorListener(MediaPlayerListener.OnErrorListener listener) {
        mErrorListeners.remove(listener);
    }

    private void notifyPlayChanged() {
        for (MediaPlayerListener.OnPlayingListener listener : mPlayingListeners) {
            listener.onPlayChanged(getPlay());
        }
    }

    private void notifyLoopChanged() {
        for (MediaPlayerListener.OnLoopingListener listener : mLoopingListeners) {
            listener.onLoopChanged(getLoop());
        }
    }

    private void notifyShuffleChanged() {
        for (MediaPlayerListener.OnShufflingListener listener : mShufflingListeners) {
            listener.onShuffleChanged(getShuffle());
        }
    }

    private void notifyCurrentTimeChanged() {
        for (MediaPlayerListener.OnCurrentTimeListener listener : mCurrentTimeListeners) {
            listener.onCurrentTimeChanged(getCurrentPosition());
        }
    }

    private void notifyTotalTimeChanged() {
        for (MediaPlayerListener.OnTotalTimeListener listener : mTotalTimeListeners) {
            listener.onTotalTimeChanged(getDuration());
        }
    }

    private void notifyTrackChanged() {
        for (MediaPlayerListener.OnTrackListener listener : mTrackListeners) {
            listener.onTrackChanged(getTracks().get(getCurrentTrack()));
        }
    }

    private void notifyAllChanged() {
        notifyLoopChanged();
        notifyShuffleChanged();
        notifyPlayChanged();
        notifyCurrentTimeChanged();
        notifyTotalTimeChanged();
        notifyTrackChanged();
    }
}
