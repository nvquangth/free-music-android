package com.quangnv.freemusic.mediaplayer;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.mediaplayer.MediaPlayerLoopType.ALL;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerLoopType.NONE;
import static com.quangnv.freemusic.mediaplayer.MediaPlayerLoopType.ONE;

/**
 * Created by quangnv on 20/10/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({NONE, ONE, ALL})
public @interface MediaPlayerLoopType {
    int NONE = 0;
    int ONE = 1;
    int ALL = 2;
}
