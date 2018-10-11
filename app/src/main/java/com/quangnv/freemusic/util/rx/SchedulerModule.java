package com.quangnv.freemusic.util.rx;

import com.quangnv.freemusic.util.dagger.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quangnv on 11/10/2018
 */

@Module
public class SchedulerModule {
    @Provides
    @AppScope
    public BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
