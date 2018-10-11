package com.quangnv.freemusic.util.rx;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by quangnv on 11/10/2018
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
