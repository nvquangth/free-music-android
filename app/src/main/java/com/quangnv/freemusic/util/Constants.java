package com.quangnv.freemusic.util;

import com.quangnv.freemusic.BuildConfig;

/**
 * Created by quangnv on 11/10/2018
 */

public final class Constants {

    public static String END_POINT = "https://api-v2.soundcloud.com";

    public static String BASE_URL_TRACK = "http://api.soundcloud.com/tracks";

    public static String GENRE_ASSETS_PATH = "data/genres.json";

    public static String TOP_TRACK_ASSETS_PATH = "data/top_track.json";

    public static String CLIENT_ID = BuildConfig.API_KEY;

    public static String KIND = "top";

    public static String ARTIST_UNKNOWN = "Unknown";

    public static int LOAD_LIMIT = 20;

    public static int LOAD_OFFSET = 20;

    public static int ROUND_CORNER = 15;

    public static int TIME_DELAY_OPEN_HOME_SCREEN = 1000;

    /**
     * Argument
     */
    public static final String ARGUMENT_GENRE = "ARGUMENT_GENRE";
    public static final String ARGUMENT_TRACK = "ARGUMENT_TRACK";
    public static final String ARGUMENT_SEARCH = "ARGUMENT_SEARCH";
    public static final String ARGUMENT_PLAYLIST = "ARGUMENT_PLAYLIST";

    /**
     * Extra
     */
    public static final String EXTRA_TRACK = "com.quangnv.freemusic.EXTRA_TRACK";

    /**
     * Flag
     */
    public static final String FLAG_MY_MUSIC_FRAGMENT = "FLAG_MY_MUSIC_FRAGMENT";

    public static final String ARGUMENT_TIMER = "ARGUMENT_TIMER";
}
