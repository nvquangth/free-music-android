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

    /**
     * Argument
     */
    public static final String ARGUMENT_GENRE = "ARGUMENT_GENRE";
    public static final String ARGUMENT_TRACK = "ARGUMENT_TRACK";
}
