package com.quangnv.freemusic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType;
import com.quangnv.freemusic.screen.detail.DetailActivity;
import com.quangnv.freemusic.service.TrackService;

/**
 * Created by quangnv on 21/10/2018
 */

public final class TrackNotificationManager {

    public static final String ACTION_PREVIOUS = "com.quangnv.freemusic.ACTION_PREVIOUS";
    public static final String ACTION_PLAY = "com.quangnv.freemusic.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.quangnv.freemusic.ACTION_PAUSE";
    public static final String ACTION_NEXT = "com.quangnv.freemusic.ACTION_NEXT";
    public static final String ACTION_CLOSE = "com.quangnv.freemusic.ACTION_CLOSE";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_CODE = 1000;

    private TrackService mTrackService;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private NotificationTarget mNotificationTarget;
    private RemoteViews mRemoteViews;
    private Intent mIntentNotification;
    private Intent mIntentRemoteView;
    private PendingIntent mPendingIntentNotification;

    public TrackNotificationManager(TrackService service) {
        mTrackService = service;
        mNotificationManager =
                (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        mIntentRemoteView = new Intent(service, TrackService.class);
        mIntentNotification = new Intent(service, DetailActivity.class);
        mIntentNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntentNotification = PendingIntent.getActivity(service, REQUEST_CODE,
                mIntentNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews = new RemoteViews(service.getPackageName(), R.layout.custom_notification);
    }

    public void createNotification() {
        setPreviousRemoteView(mIntentRemoteView);
        setPauseRemoteView(mIntentRemoteView);
        setNextRemoteView(mIntentRemoteView);
        setCloseRemoteView(mIntentRemoteView);
        mNotification = buildNotification();
        mNotificationTarget = buildNotificationTarget();
        updateDescriptionNotification(mTrackService.getCurrentTrack());
    }

    public void updatePauseNotification() {
        if (mNotification == null) return;
        mIntentRemoteView.setAction(ACTION_PAUSE);
        PendingIntent pendingIntent = PendingIntent.getService(mTrackService, REQUEST_CODE,
                mIntentRemoteView, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setImageViewResource(R.id.button_play_pause, R.drawable.ic_pause_white_48dp);
        mRemoteViews.setOnClickPendingIntent(R.id.button_play_pause, pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        mTrackService.startForeground(NOTIFICATION_ID, mNotification);
    }

    public void updatePlayNotification() {
        if (mNotification == null) return;
        mIntentRemoteView.setAction(ACTION_PLAY);
        PendingIntent pendingIntent = PendingIntent.getService(mTrackService, REQUEST_CODE,
                mIntentRemoteView, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setImageViewResource(R.id.button_play_pause,
                R.drawable.ic_play_arrow_white_48dp);
        mRemoteViews.setOnClickPendingIntent(R.id.button_play_pause, pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        mTrackService.startForeground(NOTIFICATION_ID, mNotification);
    }

    public void updateDescriptionNotification(Track track) {
        if (mNotificationTarget == null) return;
        if (mNotification == null) return;
        mRemoteViews.setTextViewText(R.id.text_track_title, track.getTitle());
        if (track.getPublisher() != null) {
            mRemoteViews.setTextViewText(R.id.text_track_artist, track.getPublisher().getArtist());
        }
        Glide.with(mTrackService)
                .asBitmap()
                .load(track.getArtWorkUrl())
                .into(mNotificationTarget);
        mTrackService.startForeground(NOTIFICATION_ID, mNotification);
    }

    public void closeNotification() {
        if (mTrackService.getPlayMediaPlayer() == MediaPlayerPlayType.PLAY) {
            mTrackService.pause();
        }
        mTrackService.stopForeground(true);
    }

    private void setPreviousRemoteView(Intent intent) {
        intent.setAction(ACTION_PREVIOUS);
        PendingIntent previousPendingIntent = PendingIntent.getService(mTrackService, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_previous, previousPendingIntent);
    }

    private void setPauseRemoteView(Intent intent) {
        intent.setAction(ACTION_PAUSE);
        PendingIntent pausePendingIntent = PendingIntent.getService(mTrackService, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_play_pause, pausePendingIntent);
        mRemoteViews.setImageViewResource(R.id.button_play_pause, R.drawable.ic_pause_white_48dp);
    }

    private void setNextRemoteView(Intent intent) {
        intent.setAction(ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getService(mTrackService, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);
    }

    private void setCloseRemoteView(Intent intent) {
        intent.setAction(ACTION_CLOSE);
        PendingIntent closePendingIntent = PendingIntent.getService(mTrackService, REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.button_close, closePendingIntent);
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(mTrackService, CHANNEL_ID)
                .setContentIntent(mPendingIntentNotification)
                .setSmallIcon(R.drawable.ic_music_note_white_24dp)
                .setContent(mRemoteViews)
                .build();
    }

    private NotificationTarget buildNotificationTarget() {
        return new NotificationTarget(mTrackService, R.id.image_track, mRemoteViews, mNotification,
                NOTIFICATION_ID);
    }
}
