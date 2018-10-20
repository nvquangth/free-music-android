package com.quangnv.freemusic.mediaplayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.mediaplayer.MediaPlayerShuffleType.OFF;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerShuffleType.ON;

/**
 * Created by quangnv on 20/10/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({OFF, ON})
public @interface MediaPlayerShuffleType {
    int OFF = 0;
    int ON = 1;
}
