package com.quangnv.freemusic.util;

/**
 * Created by quangnv on 11/10/2018
 */

public final class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
