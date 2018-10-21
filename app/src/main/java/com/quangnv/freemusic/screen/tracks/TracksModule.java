package com.quangnv.freemusic.screen.tracks;

import android.content.Context;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.OnViewTrackDetailListener;
import com.quangnv.freemusic.screen.tracks.adapter.TrackAdapter;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quangnv on 21/10/2018
 */

@Module
public class TracksModule {

    private BaseRecyclerViewAdapter.ItemRecyclerViewListener<Track> mItemTrackListener;
    private OnViewTrackDetailListener mOnViewTrackDetailListener;


    public TracksModule(BaseRecyclerViewAdapter.ItemRecyclerViewListener<Track> itemRecyclerViewListener,
                        OnViewTrackDetailListener onViewTrackDetailListener) {
        mItemTrackListener = itemRecyclerViewListener;
        mOnViewTrackDetailListener = onViewTrackDetailListener;
    }

    @FragmentScope
    @Provides
    public TrackAdapter provideTrackAdapter(@ApplicationContext Context context) {
        return new TrackAdapter(context, mItemTrackListener, mOnViewTrackDetailListener);
    }
}
