package com.quangnv.freemusic.screen.tracks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.OnItemTrackListener;
import com.quangnv.freemusic.screen.OnViewTrackDetailListener;
import com.quangnv.freemusic.screen.tracks.adapter.TrackAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 21/10/2018
 */

public class TracksFragment extends BaseFragment implements
        OnViewTrackDetailListener,
        BaseRecyclerViewAdapter.ItemRecyclerViewListener<Track> {

    @Inject
    TrackAdapter mTrackAdapter;

    private List<Track> mTracks;
    private OnItemTrackListener mOnItemTrackListener;

    private RecyclerView mTrackRecycler;

    public TracksFragment() {
    }

    public static TracksFragment newInstance() {
        TracksFragment fragment = new TracksFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnItemTrackListener) {
            mOnItemTrackListener = (OnItemTrackListener) getActivity();
        }
    }

    @Override
    public void onDestroy() {
        if (mOnItemTrackListener != null) {
            mOnItemTrackListener = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerTracksComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .tracksModule(new TracksModule(this, this))
                .build()
                .inject(this);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener(view);
        initData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tracks;
    }

    @Override
    public void onViewDetail(Track track) {

    }

    @Override
    public void onItemRecyclerViewClick(Track track, int position) {
        mOnItemTrackListener.onItemTrackClick(mTracks, position);
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
        mTrackAdapter.setData(tracks);
    }

    public void addTracks(List<Track> tracks) {
        for (Track track : tracks) {
            mTracks.add(track);
            mTrackAdapter.addData(track);
        }
    }

    private void initView(View view) {
        mTrackRecycler = view.findViewById(R.id.recycler_track);
    }

    private void registerListener(View view) {

    }

    private void initData() {
        mTrackRecycler.setAdapter(mTrackAdapter);
    }
}
