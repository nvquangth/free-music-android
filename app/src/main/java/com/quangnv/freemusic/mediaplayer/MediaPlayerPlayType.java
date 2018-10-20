package com.quangnv.freemusic.mediaplayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType.PAUSE;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType.PLAY;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType.WAIT;

/**
 * Created by quangnv on 20/10/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({PLAY, PAUSE, WAIT})
public @interface MediaPlayerPlayType {
    int PLAY = 0;
    int PAUSE = 1;
    int WAIT = 2;
}
