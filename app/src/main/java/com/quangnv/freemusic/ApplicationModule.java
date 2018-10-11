package com.quangnv.freemusic;

import android.content.Context;

import com.quangnv.freemusic.util.dagger.AppScope;
import com.quangnv.freemusic.util.dagger.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quangnv on 11/10/2018
 */

@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context mContext) {
        this.mContext = mContext;
    }

    @ApplicationContext
    @AppScope
    @Provides
    public Context provideApplicationContext() {
        return mContext;
    }
}
