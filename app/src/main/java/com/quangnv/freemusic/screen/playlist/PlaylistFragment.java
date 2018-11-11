package com.quangnv.freemusic.screen.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangnv on 11/11/2018
 */

public class PlaylistFragment extends BaseFragment {

    private Navigator mNavigator;
    private List<Playlist> mPlaylists;

    public static PlaylistFragment newInstance(ArrayList<Playlist> playlists) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.ARGUMENT_PLAYLIST, playlists);

        PlaylistFragment fragment = new PlaylistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_playlist;
    }
}
