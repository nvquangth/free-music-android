package com.quangnv.freemusic.screen.genre;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.model.Track;

import java.util.List;

/**
 * Created by quangnv on 21/10/2018
 */

public interface GenreContract {

    interface View {
        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showTracks(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {
        void getTracks(Genre genre, int offset);
    }
}
