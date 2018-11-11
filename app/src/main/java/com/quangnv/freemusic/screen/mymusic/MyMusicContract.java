package com.quangnv.freemusic.screen.mymusic;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;

import java.util.List;

/**
 * Created by quangnv on 01/11/2018
 */

public interface MyMusicContract {

    interface View {

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showPlayList(List<Playlist> playlists);

        void showFavorite(List<Track> tracks);

        void showDownload(List<Track> tracks);

        void showLocal(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {

        void getPlayList();

        void getFavorite();

        void getDownload();

        void getLocal();
    }
}
