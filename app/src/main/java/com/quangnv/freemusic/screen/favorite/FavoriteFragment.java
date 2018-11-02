package com.quangnv.freemusic.screen.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.search.SearchFragment;
import com.quangnv.freemusic.screen.search.SearchType;
import com.quangnv.freemusic.screen.tracks.TracksFragment;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangnv on 02/11/2018
 */

public class FavoriteFragment extends BaseFragment {

    private Navigator mNavigator;
    private TracksFragment mTracksFragment;
    private List<Track> mTracks;

    private Toolbar mToolbar;

    public static FavoriteFragment newInstance(ArrayList<Track> tracks) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.ARGUMENT_TRACK, tracks);

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTracks = getArguments().getParcelableArrayList(Constants.ARGUMENT_TRACK);
        }
        mTracksFragment = TracksFragment.newInstance();
        mNavigator = new Navigator(this);
        mNavigator.goNextChildFragment(R.id.frame_container, mTracksFragment,
                false, NavigateAnim.NONE, null);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        mTracksFragment.setTracks(mTracks);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_favorite;
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
                mNavigator.addFragmentToBackStack(
                        R.id.frame_container,
                        SearchFragment.newInstance(SearchType.NONE),
                        true,
                        NavigateAnim.RIGHT_LEFT,
                        null);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setTitle(R.string.title_favorite);
    }
}
