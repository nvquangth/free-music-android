package com.quangnv.freemusic.screen.playlist;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Playlist;

/**
 * Created by quangnv on 11/11/2018
 */

public interface PlaylistContract {

    interface View {

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showCreatePlaylistSuccessful(Playlist playlist);

        void showCreatePlaylistFailed();
    }

    interface Presenter extends BasePresenter<View> {

        void savePlaylist(Playlist playlist);
    }
}
