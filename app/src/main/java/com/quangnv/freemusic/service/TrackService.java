package com.quangnv.freemusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.mediaplayer.MediaPlayerListener;
import com.quangnv.freemusic.mediaplayer.MediaPlayerManager;
import com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType;
import com.quangnv.freemusic.notification.TrackNotificationManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 20/10/2018
 */

public class TrackService extends Service implements MediaPlayerListener.OnTrackListener,
        MediaPlayerListener.OnPlayingListener {

    private final IBinder mBinder = new TrackBinder();
    private TrackNotificationManager mTrackNotificationManager;

    @Inject
    MediaPlayerManager mMediaPlayerManager;

    public class TrackBinder extends Binder {
        public TrackService getService() {
            return TrackService.this;
        }
    }

    @Override
    public void onCreate() {
        mMediaPlayerManager = new MediaPlayerManager(this);
        mTrackNotificationManager = new TrackNotificationManager(this);
        addTrackListener(this);
        addPlayingListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayerManager.getPlay() == MediaPlayerPlayType.PLAY) {
            mMediaPlayerManager.stop();
        }
        mMediaPlayerManager.release();
    }

    @Override
    public void onTrackChanged(Track track) {
        mTrackNotificationManager.updateDescriptionNotification(track);
    }

    @Override
    public void onPlayChanged(int playType) {
        switch (playType) {
            case MediaPlayerPlayType.PAUSE:
                mTrackNotificationManager.updatePlayNotification();
                break;
            case MediaPlayerPlayType.PLAY:
                mTrackNotificationManager.updatePauseNotification();
                break;
            case MediaPlayerPlayType.WAIT:
                break;
        }
    }

    public void setTracks(List<Track> tracks) {
        mMediaPlayerManager.setTracks(tracks);
    }

    public void play(int position) {
        mMediaPlayerManager.setCurrentTrack(position);
        mMediaPlayerManager.init();
        mTrackNotificationManager.createNotification();
    }

    public void start() {
        mMediaPlayerManager.start();
    }

    public void pause() {
        mMediaPlayerManager.pause();
    }

    public void next() {
        mMediaPlayerManager.next();
    }

    public void previous() {
        mMediaPlayerManager.previous();
    }

    public void changePlayPauseStatus() {
        if (mMediaPlayerManager.getPlay() == MediaPlayerPlayType.PAUSE) {
            start();
        } else if (mMediaPlayerManager.getPlay() == MediaPlayerPlayType.PLAY) {
            pause();
        }
    }

    public int getPlayMediaPlayer() {
        return mMediaPlayerManager.getPlay();
    }

    public Track getCurrentTrack() {
        return mMediaPlayerManager.getTracks().get(mMediaPlayerManager.getCurrentTrack());
    }

    public void addLoopingListener(MediaPlayerListener.OnLoopingListener listener) {
        mMediaPlayerManager.addLoopingListener(listener);
    }

    public void addShufflingListener(MediaPlayerListener.OnShufflingListener listener) {
        mMediaPlayerManager.addShufflingListener(listener);
    }

    public void addPlayingListener(MediaPlayerListener.OnPlayingListener listener) {
        mMediaPlayerManager.addPlayingListener(listener);
    }

    public void addCurrentTimeListener(MediaPlayerListener.OnCurrentTimeListener listener) {
        mMediaPlayerManager.addCurrentTimeListener(listener);
    }

    public void addTotalTimeListener(MediaPlayerListener.OnTotalTimeListener listener) {
        mMediaPlayerManager.addTotalTimeListener(listener);
    }

    public void addTrackListener(MediaPlayerListener.OnTrackListener listener) {
        mMediaPlayerManager.addTrackListener(listener);
    }

    public void addTrackErrorListener(MediaPlayerListener.OnErrorListener listener) {
        mMediaPlayerManager.addTrackErrorListener(listener);
    }

    public void removeLoopingListener(MediaPlayerListener.OnLoopingListener listener) {
        mMediaPlayerManager.removeLoopingListener(listener);
    }

    public void removeShufflingListener(MediaPlayerListener.OnShufflingListener listener) {
        mMediaPlayerManager.removeShufflingListener(listener);
    }

    public void removePlayingListener(MediaPlayerListener.OnPlayingListener listener) {
        mMediaPlayerManager.removePlayingListener(listener);
    }

    public void removeCurrentTimeListener(MediaPlayerListener.OnCurrentTimeListener listener) {
        mMediaPlayerManager.removeCurrentTimeListener(listener);
    }

    public void removeTotalTimeListener(MediaPlayerListener.OnTotalTimeListener listener) {
        mMediaPlayerManager.removeTotalTimeListener(listener);
    }

    public void removeTrackListener(MediaPlayerListener.OnTrackListener listener) {
        mMediaPlayerManager.removeTrackListener(listener);
    }

    public void removeTrackErrorListener(MediaPlayerListener.OnErrorListener listener) {
        mMediaPlayerManager.removeTrackErrorListener(listener);
    }

    public void updateMiniPlayer() {

    }

    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        switch (intent.getAction()) {
            case TrackNotificationManager.ACTION_PREVIOUS:
                previous();
                break;
            case TrackNotificationManager.ACTION_PLAY:
                changePlayPauseStatus();
                break;
            case TrackNotificationManager.ACTION_PAUSE:
                changePlayPauseStatus();
                break;
            case TrackNotificationManager.ACTION_NEXT:
                next();
                break;
            case TrackNotificationManager.ACTION_CLOSE:
                mTrackNotificationManager.closeNotification();
                break;
        }
    }
}
