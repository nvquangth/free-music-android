package com.quangnv.freemusic.screen.detail.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.screen.detail.adapter.TabType.NOW_PLAYING;
import static com.quangnv.freemusic.screen.detail.adapter.TabType.PLAYING;

/**
 * Created by quangnv on 23/10/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({NOW_PLAYING, PLAYING})
public @interface TabType {
    int NOW_PLAYING = 0;
    int PLAYING = 1;
}
