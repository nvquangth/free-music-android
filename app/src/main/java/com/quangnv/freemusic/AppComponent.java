package com.quangnv.freemusic;

import android.content.Context;

import com.quangnv.freemusic.data.source.local.asset.AssetsHelper;
import com.quangnv.freemusic.data.source.local.sdcard.SdCardHelper;
import com.quangnv.freemusic.data.source.remote.service.Api;
import com.quangnv.freemusic.data.source.remote.service.NetworkModule;
import com.quangnv.freemusic.util.dagger.AppScope;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;
import com.quangnv.freemusic.util.rx.SchedulerModule;

import dagger.Component;

/**
 * Created by quangnv on 26/09/2018
 */

@AppScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, SchedulerModule.class})
public interface AppComponent {

    Api api();

    AssetsHelper assetsHelper();

    SdCardHelper sdCardHelper();

    @ApplicationContext
    Context applicationContext();

    BaseSchedulerProvider baseSchedulerProvider();
}
