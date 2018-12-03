package com.quangnv.freemusic.screen.search.option;

import android.content.Context;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.repository.SearchHistoryRepository;
import com.quangnv.freemusic.data.source.local.SearchHistoryLocalDataSource;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quangnv on 22/10/2018
 */

@Module
public class SearchOptionModule {

    private OnItemHotKeyListener mHotKeyListener;
    private OnItemHistoryListener mHistoryListener;

    public SearchOptionModule(OnItemHotKeyListener hotKeyListener,
                              OnItemHistoryListener historyListener) {
        mHotKeyListener = hotKeyListener;
        mHistoryListener = historyListener;
    }

    @FragmentScope
    @Provides
    public HotKeyAdapter provideHotKeyAdapter(@ApplicationContext Context context) {
        return new HotKeyAdapter(context, mHotKeyListener);
    }

    @FragmentScope
    @Provides
    public HistoryAdapter provideHistoryAdapter(@ApplicationContext Context context) {
        return new HistoryAdapter(context, mHistoryListener);
    }
}
