package com.quangnv.freemusic.screen.playlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.screen.playlist.adapter.PlaylistAdapter;
import com.quangnv.freemusic.screen.search.SearchFragment;
import com.quangnv.freemusic.screen.search.SearchType;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 11/11/2018
 */

public class PlaylistFragment extends BaseFragment implements View.OnClickListener, PlaylistContract.View, BaseRecyclerViewAdapter.ItemRecyclerViewListener<Playlist> {

    @Inject
    PlaylistContract.Presenter mPresenter;
    @Inject
    PlaylistAdapter mPlaylistAdapter;

    private Navigator mNavigator;
    private List<Playlist> mPlaylists;

    private Toolbar mToolbar;
    private FloatingActionButton mFABNewPlaylist;
    private RecyclerView mRecyclerPlaylist;

    public static PlaylistFragment newInstance(ArrayList<Playlist> playlists) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.ARGUMENT_PLAYLIST, playlists);

        PlaylistFragment fragment = new PlaylistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerPlaylistComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .playlistModule(new PlaylistModule(this))
                .build()
                .inject(this);
        mNavigator = new Navigator(this);
        mPresenter.setView(this);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener();

        mRecyclerPlaylist.setAdapter(mPlaylistAdapter);

        mPlaylists = getArguments().getParcelableArrayList(Constants.ARGUMENT_PLAYLIST);
        mPlaylistAdapter.setData(mPlaylists);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_playlist;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_new_playlist:
                showDialogCreatePlaylist();
                break;
        }
    }

    @Override
    public void onItemRecyclerViewClick(Playlist playlist, int position) {

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showCreatePlaylistSuccessful() {

    }

    @Override
    public void showCreatePlaylistFailed() {

    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setTitle(R.string.title_playlist);
        mFABNewPlaylist = view.findViewById(R.id.fab_new_playlist);
        mRecyclerPlaylist = view.findViewById(R.id.recycler_playlist);
    }

    private void registerListener() {
        mFABNewPlaylist.setOnClickListener(this);
    }

    private void showDialogCreatePlaylist() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edittext = new EditText(getContext());
        alert.setMessage(R.string.title_create_playlist);

        alert.setView(edittext);

        alert.setPositiveButton(R.string.title_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String title = edittext.getText().toString();
                Playlist playlist = new Playlist();
                playlist.setName(title);
                mPresenter.savePlaylist(playlist);
                mPlaylistAdapter.addData(playlist);
                mRecyclerPlaylist.scrollToPosition(mPlaylistAdapter.getData().size() - 1);
            }
        });

        alert.setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }
}
