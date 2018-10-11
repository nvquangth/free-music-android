package com.quangnv.freemusic.data.source.local.sharedprf;

/**
 * Created by quangnv on 11/10/2018
 */

public interface SharedPrefsApi {

    <T> T get(String key, Class<T> clazz);

    <T> void put(String key, T data);

    void delete(String key);

    void clear();
}
