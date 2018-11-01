package com.quangnv.freemusic;

import android.content.Context;

import com.quangnv.freemusic.data.source.local.asset.AssetsHelper;
import com.quangnv.freemusic.data.source.local.sdcard.SdCardHelper;
import com.quangnv.freemusic.data.source.local.sqlite.DbHelper;
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

    @AppScope
    @Provides
    public AssetsHelper provideAssetsHelper() {
        return new AssetsHelper(mContext);
    }

    @AppScope
    @Provides
    public SdCardHelper provideSdCardHelper() {
        return new SdCardHelper(mContext);
    }

    @AppScope
    @Provides
    public DbHelper providerDbHelper() {
        return new DbHelper(mContext);
    }
}
