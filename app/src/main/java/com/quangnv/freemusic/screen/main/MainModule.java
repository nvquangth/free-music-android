package com.quangnv.freemusic.screen.main;

import com.quangnv.freemusic.util.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quangnv on 13/10/2018
 */

@Module
public class MainModule {

    @ActivityScope
    @Provides
    public MainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }
}
