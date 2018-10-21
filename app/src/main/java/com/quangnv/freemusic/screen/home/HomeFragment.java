package com.quangnv.freemusic.screen.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.OnItemTrackListener;
import com.quangnv.freemusic.screen.genre.GenreFragment;
import com.quangnv.freemusic.screen.home.adapter.GenreAdapter;
import com.quangnv.freemusic.screen.home.adapter.TrackPagerAdapter;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 15/10/2018
 */

public class HomeFragment extends BaseFragment implements HomeContract.View,
        View.OnClickListener,
        OnItemTrackListener,
        BaseRecyclerViewAdapter.ItemRecyclerViewListener<Genre> {

    private static final int SPAN_COUNT = 2;
    private static final int SPLIT_NUM = 3;
    private static final int DOUBLE_SPAN_SIZE = 2;
    private static final int SINGLE_SPAN_SIZE = 1;

    @Inject
    HomeContract.Presenter mPresenter;
    @Inject
    TrackPagerAdapter mTrackPagerAdapter;
    @Inject
    GenreAdapter mGenreAdapter;

    private Navigator mNavigator;

    private List<Track> mTracks;
    private OnItemTrackListener mOnItemTrackListener;

    private View mViewSearch;
    private ViewPager mTrackPager;
    private RecyclerView mGenreRecycler;
    private ProgressBar mProgressBarLoadingTrack;
    private ProgressBar mProgressBarLoadingGenre;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
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
        DaggerHomeComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .homeModule(new HomeModule(getChildFragmentManager(), this))
                .build()
                .inject(this);
        mPresenter.setView(this);
        mNavigator = new Navigator(this);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener();
        setupGenreRecycler();
        mPresenter.getTopTracks();
        mPresenter.getGenres();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void showLoadingTopTrackIndicator() {
        mProgressBarLoadingTrack.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingTopTrackIndicator() {
        mProgressBarLoadingTrack.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingGenreIndicator() {
        mProgressBarLoadingGenre.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingGenreIndicator() {
        mProgressBarLoadingGenre.setVisibility(View.GONE);
    }

    @Override
    public void showTopTrack(List<Track> tracks) {
        mTracks = tracks;
        mTrackPagerAdapter.setTracks(tracks);
        mTrackPager.setAdapter(mTrackPagerAdapter);
    }

    @Override
    public void showGenres(List<Genre> genres) {
        mGenreAdapter.setData(genres);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_search:
                break;
        }
    }

    @Override
    public void onItemTrackClick(List<Track> tracks, int position) {
        mOnItemTrackListener.onItemTrackClick(mTracks, mTrackPager.getCurrentItem());
    }

    @Override
    public void onItemRecyclerViewClick(Genre genre, int position) {
        mNavigator.addFragmentToBackStack(R.id.frame_container, GenreFragment.newInstance(genre),
                true, NavigateAnim.RIGHT_LEFT, null);
    }

    private void initView(View view) {
        mViewSearch = view.findViewById(R.id.view_search);
        mTrackPager = view.findViewById(R.id.view_pager);
        mGenreRecycler = view.findViewById(R.id.recycler_genre);
        mProgressBarLoadingTrack = view.findViewById(R.id.progress_bar_loading_track);
        mProgressBarLoadingGenre = view.findViewById(R.id.progress_bar_loading_genre);
    }

    private void registerListener() {
        mViewSearch.setOnClickListener(this);
    }

    private void setupGenreRecycler() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (i % SPLIT_NUM == 0) {
                    return DOUBLE_SPAN_SIZE;
                }
                return SINGLE_SPAN_SIZE;
            }
        });
        mGenreRecycler.setLayoutManager(layoutManager);
        mGenreRecycler.setAdapter(mGenreAdapter);
    }
}
