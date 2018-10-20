package com.quangnv.freemusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.mediaplayer.MediaPlayerListener;
import com.quangnv.freemusic.mediaplayer.MediaPlayerManager;
import com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType;
import com.quangnv.freemusic.screen.detail.DetailActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 20/10/2018
 */

public class TrackService extends Service implements MediaPlayerListener.OnTrackListener, MediaPlayerListener.OnPlayingListener {

    private static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    private static final String ACTION_PLAY = "ACTION_PLAY";
    private static final String ACTION_PAUSE = "ACTION_PAUSE";
    private static final String ACTION_NEXT = "ACTION_NEXT";
    private static final String ACTION_CLOSE = "ACTION_CLOSE";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_CODE = 1000;
    private final IBinder mBinder = new TrackBinder();

    @Inject
    MediaPlayerManager mMediaPlayerManager;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private NotificationTarget mNotificationTarget;
    private RemoteViews mRemoteViews;
    private Intent mIntentNotification;
    private Intent mIntentRemoteView;
    private PendingIntent mPendingIntentNotification;

    public class TrackBinder extends Binder {
        public TrackService getService() {
            return TrackService.this;
        }
    }

    @Override
    public void onCreate() {
        mMediaPlayerManager = new MediaPlayerManager(this);
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

    }

    @Override
    public void onPlayChanged(int playType) {

    }

    public void setTracks(List<Track> tracks) {
        mMediaPlayerManager.setTracks(tracks);
    }

    public void play(int position) {
        mMediaPlayerManager.setCurrentTrack(position);
        mMediaPlayerManager.init();
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

    private void handleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        switch (intent.getAction()) {
            case ACTION_PREVIOUS:
                previous();
                break;
            case ACTION_PLAY:
                changePlayPauseStatus();
                break;
            case ACTION_PAUSE:
                changePlayPauseStatus();
                break;
            case ACTION_NEXT:
                next();
                break;
            case ACTION_CLOSE:
                closeNotification();
                break;
        }
    }

    private void initNotification() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mIntentRemoteView = new Intent(this, TrackService.class);
        mIntentNotification = new Intent(this, DetailActivity.class);
        mIntentNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntentNotification = PendingIntent.getActivity(this, REQUEST_CODE,
                mIntentNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
    }

    private void createNotification() {
        setPreviousRemoteView(mIntentRemoteView);
        setPauseRemoteView(mIntentRemoteView);
        setNextRemoteView(mIntentRemoteView);
        setCloseRemoteView(mIntentRemoteView);
        mNotification = buildNotification();
        mNotificationTarget = buildNotificationTarget();
        updateDescriptionNotification(
                mMediaPlayerManager.getTracks().get(mMediaPlayerManager.getCurrentTrack()));
    }

    private void setPreviousRemoteView(Intent intent) {
        intent.setAction(ACTION_PREVIOUS);
        PendingIntent previousPendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_previous, previousPendingIntent);
    }

    private void setPauseRemoteView(Intent intent) {
        intent.setAction(ACTION_PAUSE);
        PendingIntent pausePendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_play_pause, pausePendingIntent);
        mRemoteViews.setImageViewResource(R.id.button_play_pause, R.drawable.ic_pause_white_48dp);
    }

    private void setNextRemoteView(Intent intent) {
        intent.setAction(ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);
    }

    private void setCloseRemoteView(Intent intent) {
        intent.setAction(ACTION_CLOSE);
        PendingIntent closePendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_close, closePendingIntent);
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(mPendingIntentNotification)
                .setSmallIcon(R.drawable.image_default)
                .setContent(mRemoteViews)
                .build();
    }

    private NotificationTarget buildNotificationTarget() {
        return new NotificationTarget(this, R.id.image_track, mRemoteViews, mNotification,
                NOTIFICATION_ID);
    }

    private void updatePauseNotification() {
        mIntentRemoteView.setAction(ACTION_PAUSE);
        PendingIntent pendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                mIntentRemoteView, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setImageViewResource(R.id.button_play_pause, R.drawable.ic_pause_white_48dp);
        mRemoteViews.setOnClickPendingIntent(R.id.button_play_pause, pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        startForeground(NOTIFICATION_ID, mNotification);
    }

    private void updatePlayNotification() {
        mIntentRemoteView.setAction(ACTION_PLAY);
        PendingIntent pendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                mIntentRemoteView, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setImageViewResource(R.id.button_play_pause,
                R.drawable.ic_play_arrow_white_48dp);
        mRemoteViews.setOnClickPendingIntent(R.id.button_play_pause, pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        startForeground(NOTIFICATION_ID, mNotification);
    }

    private void updateDescriptionNotification(Track track) {
        mRemoteViews.setTextViewText(R.id.text_track_title, track.getTitle());
        mRemoteViews.setTextViewText(R.id.text_track_artist, track.getPublisher().getArtist());
        Glide.with(this)
                .asBitmap()
                .load(track.getArtWorkUrl())
                .into(mNotificationTarget);
        startForeground(NOTIFICATION_ID, mNotification);
    }

    private void closeNotification() {
        if (mMediaPlayerManager.getPlay() == MediaPlayerPlayType.PLAY) {
            pause();
        }
        stopForeground(true);
    }
}
