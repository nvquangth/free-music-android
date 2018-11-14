package com.quangnv.freemusic.screen.playlistdialog;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Playlist;

import java.util.List;

/**
 * Created by quangnv on 13/11/2018
 */

public interface PlaylistDialogContract {

    interface View {

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showPlaylist(List<Playlist> playlists);
    }

    interface Presenter extends BasePresenter<View> {

        void getPlaylist();
    }
}
