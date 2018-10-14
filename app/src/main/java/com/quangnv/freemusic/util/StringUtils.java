package com.quangnv.freemusic.util;

import com.quangnv.freemusic.BuildConfig;

/**
 * Created by quangnv on 11/10/2018
 */

public final class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String generateLinkStreamTrack(long trackId) {
        return Constants.BASE_URL_TRACK +
                "/" +
                trackId +
                "/" +
                "stream" +
                "?client_id=" +
                BuildConfig.API_KEY;
    }

    public static String generateLinkDownloadTrack(long trackId) {
        return Constants.BASE_URL_TRACK +
                "/" +
                trackId +
                "/" +
                "download" +
                "?client_id=" +
                BuildConfig.API_KEY;
    }
}
