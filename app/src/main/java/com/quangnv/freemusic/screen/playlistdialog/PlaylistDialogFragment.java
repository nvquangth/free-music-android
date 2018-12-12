package com.quangnv.freemusic.screen.playlistdialog;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseDialogFragment;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.playlistdialog.adapter.PlaylistDialogAdapter;
import com.quangnv.freemusic.util.Constants;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 13/11/2018
 */

public class PlaylistDialogFragment extends BaseDialogFragment implements
        BaseRecyclerViewAdapter.ItemRecyclerViewListener<Playlist>, PlaylistDialogContract.View {

    @Inject
    PlaylistDialogContract.Presenter mPresenter;
    @Inject
    PlaylistDialogAdapter mPlaylistDialogAdapter;

    private Track mTrack;

    private RecyclerView mRecyclerPlaylist;

    public static PlaylistDialogFragment newInstance(Track track) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.ARGUMENT_TRACK, track);

        PlaylistDialogFragment fragment = new PlaylistDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dialog_playlist;
    }

    @Override
    protected void initComponentsOnCreate(Bundle savedInstanceState) {
        DaggerPlaylistDialogComponent.builder()
                .appComponent((((MainApplication) getActivity().getApplication()).getAppComponent()))
                .playlistDialogModule(new PlaylistDialogModule(this))
                .build()
                .inject(this);
        mPresenter.setView(this);

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo);
    }

    @Override
    protected void initComponentsOnCreateView(View view, Bundle savedInstanceState) {
        if (getArguments() == null) return;
        mTrack = getArguments().getParcelable(Constants.ARGUMENT_TRACK);

        initView(view);

        mRecyclerPlaylist.setAdapter(mPlaylistDialogAdapter);
        mPresenter.getPlaylist();
    }

    @Override
    public void onItemRecyclerViewClick(Playlist playlist, int position) {
        mPresenter.addTrackToPlaylist(mTrack, playlist);
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showPlaylist(List<Playlist> playlists) {
        mPlaylistDialogAdapter.setData(playlists);
    }

    @Override
    public void showAddTrackToPlaylistSuccessful() {
        Toast.makeText(getContext(), "Track added to playlist!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showAddTrackToPlaylistFailed() {
        Toast.makeText(getContext(), "Failed add track to playlist!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    private void initView(View view) {
        mRecyclerPlaylist = view.findViewById(R.id.recycler_playlist);
    }
}
