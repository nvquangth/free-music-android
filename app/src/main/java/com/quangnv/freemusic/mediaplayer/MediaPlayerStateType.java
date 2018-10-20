package com.quangnv.freemusic.mediaplayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.END;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.ERROR;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.IDLE;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.INITIALIZED;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.PAUSED;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.PLAYBACK_COMPLETE;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.PREPARED;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.PREPARING;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.STARTED;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerStateType.STOPPED;

/**
 * Created by quangnv on 20/10/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({IDLE, INITIALIZED, PREPARING, PREPARED, STARTED, PAUSED, STOPPED, PLAYBACK_COMPLETE, END,
        ERROR})
public @interface MediaPlayerStateType {
    int IDLE = 0;
    int INITIALIZED = 1;
    int PREPARING = 2;
    int PREPARED = 3;
    int STARTED = 4;
    int PAUSED = 5;
    int STOPPED = 6;
    int PLAYBACK_COMPLETE = 7;
    int END = 8;
    int ERROR = 9;
}
