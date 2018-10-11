package com.quangnv.freemusic.base;

/**
 * Created by quangnv on 11/10/2018
 */

public interface BasePresenter<T> {

    void onStart();

    void onStop();

    void setView(T view);
}
