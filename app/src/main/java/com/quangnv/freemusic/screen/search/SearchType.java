package com.quangnv.freemusic.screen.search;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.quangnv.freemusic.screen.search.SearchType.NONE;
import static com.quangnv.freemusic.screen.search.SearchType.VOICE;

/**
 * Created by quangnv on 21/10/2018
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({NONE, VOICE})
public @interface SearchType {
    int NONE = 0;
    int VOICE = 1;
}
