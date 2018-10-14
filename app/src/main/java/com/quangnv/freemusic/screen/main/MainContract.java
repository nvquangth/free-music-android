package com.quangnv.freemusic.screen.main;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Track;

import java.util.List;

/**
 * Created by quangnv on 13/10/2018
 */

public interface MainContract {

    interface View {

        void showTracks(List<Track> tracks);

        void showError();
    }

    interface Presenter extends BasePresenter<View> {

        void getTrack(String genre, int offset);
    }
}
