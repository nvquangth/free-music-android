package com.quangnv.freemusic.screen.genre;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.search.SearchFragment;
import com.quangnv.freemusic.screen.search.SearchType;
import com.quangnv.freemusic.screen.tracks.TracksFragment;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 21/10/2018
 */

public class GenreFragment extends BaseFragment implements GenreContract.View {

    @Inject
    GenreContract.Presenter mPresenter;

    private Navigator mNavigator;
    private TracksFragment mTracksFragment;
    private Genre mGenre;

    private Toolbar mToolbar;
    private ProgressBar mProgressLoading;

    public GenreFragment() {
    }

    public static GenreFragment newInstance(Genre genre) {
        GenreFragment fragment = new GenreFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ARGUMENT_GENRE, genre);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerGenreComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .genreModule(new GenreModule())
                .build()
                .inject(this);
        mPresenter.setView(this);
        mTracksFragment = TracksFragment.newInstance();
        mNavigator = new Navigator(this);
        mNavigator.goNextChildFragment(R.id.frame_container, mTracksFragment,
                false, NavigateAnim.NONE, null);
        if (getArguments() != null) {
            mGenre = getArguments().getParcelable(Constants.ARGUMENT_GENRE);
        }
        setHasOptionsMenu(true);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        mToolbar.setTitle(mGenre.getName());
        mPresenter.getTracks(mGenre, 0);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_genre;
    }

    @Override
    public void showLoadingIndicator() {
        mProgressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressLoading.setVisibility(View.GONE);
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mTracksFragment.setTracks(tracks);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mToolbar.inflateMenu(R.menu.menu_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavigator.goBackFragment();
                break;
            case R.id.nav_search:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        SearchFragment.newInstance(SearchType.NONE), true, NavigateAnim.RIGHT_LEFT, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        mProgressLoading = view.findViewById(R.id.progress_bar_loading);
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }
}
