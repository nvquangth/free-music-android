package com.quangnv.freemusic.data.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.data.model.TrackLocalType.DOWNLOAD;
import static com.quangnv.freemusic.data.model.TrackLocalType.FAVORITE;

/**
 * Created by quangnv on 03/11/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({FAVORITE, DOWNLOAD})
public @interface TrackLocalType {
    int FAVORITE = 0;
    int DOWNLOAD = 1;
}
