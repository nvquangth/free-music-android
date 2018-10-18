package com.quangnv.freemusic.screen.home;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.model.Track;

import java.util.List;

/**
 * Created by quangnv on 15/10/2018
 */

public interface HomeContract {
    interface View {
        void showLoadingTopTrackIndicator();

        void hideLoadingTopTrackIndicator();

        void showLoadingGenreIndicator();

        void hideLoadingGenreIndicator();

        void showTopTrack(List<Track> tracks);

        void showGenres(List<Genre> genres);
    }

    interface Presenter extends BasePresenter<View> {
        void getTopTracks();

        void getGenres();
    }
}
