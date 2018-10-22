package com.quangnv.freemusic.screen.search.option;

import android.content.Context;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quangnv on 22/10/2018
 */

@Module
public class SearchOptionModule {

    private BaseRecyclerViewAdapter.ItemRecyclerViewListener<String> mItemRecyclerViewListener;

    public SearchOptionModule(BaseRecyclerViewAdapter.ItemRecyclerViewListener<String>
                                      itemRecyclerViewListener) {
        mItemRecyclerViewListener = itemRecyclerViewListener;
    }

    @FragmentScope
    @Provides
    public HotKeyAdapter provideHotKeyAdapter(@ApplicationContext Context context) {
        return new HotKeyAdapter(context, mItemRecyclerViewListener);
    }
}
