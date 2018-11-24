package com.quangnv.freemusic.data.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.data.model.TrackLocalType.DEFAULT;
import static com.quangnv.freemusic.data.model.TrackLocalType.DOWNLOAD;
import static com.quangnv.freemusic.data.model.TrackLocalType.FAVORITE;
import static com.quangnv.freemusic.data.model.TrackLocalType.PLAYLIST;

/**
 * Created by quangnv on 03/11/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({DEFAULT, FAVORITE, DOWNLOAD, PLAYLIST})
public @interface TrackLocalType {
    int DEFAULT = -1;
    int FAVORITE = 0;
    int DOWNLOAD = 1;
    int PLAYLIST = 2;
}
