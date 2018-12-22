package com.quangnv.freemusic.screen.mymusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.favorite.FavoriteFragment;
import com.quangnv.freemusic.screen.local.LocalFragment;
import com.quangnv.freemusic.screen.playlist.PlaylistFragment;
import com.quangnv.freemusic.screen.search.SearchFragment;
import com.quangnv.freemusic.screen.search.SearchType;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 29/10/2018
 */

public class MyMusicFragment extends BaseFragment implements MyMusicContract.View, View.OnClickListener {

    @Inject
    MyMusicContract.Presenter mPresenter;

    private Navigator mNavigator;
    private ArrayList<Playlist> mPlaylists;
    private ArrayList<Track> mFavoriteTracks;
    private ArrayList<Track> mLocalTracks;

    private View mViewSearch;
    private ImageButton mButtonVoiceSearch;
    private TextView mTextNumberPlaylist;
    private TextView mTextNumberFavorite;
    private TextView mTextNumberDownload;
    private TextView mTextNumberLocal;
    private ImageButton mButtonPlaylist;
    private ImageButton mButtonFavorite;
    private ImageButton mButtonDownload;
    private ImageButton mButtonLocal;
    private ProgressBar mProgressBarLoading;

    public MyMusicFragment() {
    }

    public static MyMusicFragment newInstance() {

        Bundle args = new Bundle();

        MyMusicFragment fragment = new MyMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerMyMusicComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);
        mPresenter.setView(this);
        mNavigator = new Navigator(this);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener();
        mPresenter.getPlayList();
        mPresenter.getFavorite();
        mPresenter.getLocal();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_music;
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void showPlayList(List<Playlist> playlists) {
        mPlaylists = (ArrayList<Playlist>) playlists;
        mTextNumberPlaylist.setText(playlists.size() + "");
    }

    @Override
    public void showFavorite(List<Track> tracks) {
        mFavoriteTracks = (ArrayList<Track>) tracks;
        mTextNumberFavorite.setText(tracks.size() + "");
    }

    @Override
    public void showDownload(List<Track> tracks) {

    }

    @Override
    public void showLocal(List<Track> tracks) {
        mLocalTracks = (ArrayList<Track>) tracks;
        mTextNumberLocal.setText(tracks.size() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_playlist:
            case R.id.text_title_playlist:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        PlaylistFragment.newInstance(mPlaylists),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
            case R.id.button_favorite:
            case R.id.text_title_favorite:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        FavoriteFragment.newInstance(mFavoriteTracks),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
            case R.id.button_download:
            case R.id.text_title_download:
                break;
            case R.id.button_folder:
            case R.id.text_title_folder:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        LocalFragment.newInstance(mLocalTracks),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
            case R.id.view_search:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        SearchFragment.newInstance(SearchType.NONE),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
            case R.id.button_search_voice:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        SearchFragment.newInstance(SearchType.VOICE),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
        }
    }

    public void addPlaylist(Playlist playlist) {
        mPlaylists.add(playlist);
        showPlayList(mPlaylists);
    }

    private void initView(View view) {
        mTextNumberPlaylist = view.findViewById(R.id.text_number_playlist);
        mTextNumberFavorite = view.findViewById(R.id.text_number_favorite);
        mTextNumberDownload = view.findViewById(R.id.text_number_download);
        mTextNumberLocal = view.findViewById(R.id.text_number_folder);
        mButtonPlaylist = view.findViewById(R.id.button_playlist);
        mButtonFavorite = view.findViewById(R.id.button_favorite);
        mButtonDownload = view.findViewById(R.id.button_download);
        mButtonLocal = view.findViewById(R.id.button_folder);
        mProgressBarLoading = view.findViewById(R.id.progress_bar_loading);
        mViewSearch = view.findViewById(R.id.view_search);
        mButtonVoiceSearch = view.findViewById(R.id.button_search_voice);
    }

    private void registerListener() {
        mButtonPlaylist.setOnClickListener(this);
        mButtonFavorite.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonLocal.setOnClickListener(this);
        mViewSearch.setOnClickListener(this);
        mButtonVoiceSearch.setOnClickListener(this);
    }

    public interface OnPlaylistListener {

        void onPlaylistAdded(Playlist playlist);
    }

    public interface OnReloadFavoriteListener {

        void onReloadFavorite();
    }
}
