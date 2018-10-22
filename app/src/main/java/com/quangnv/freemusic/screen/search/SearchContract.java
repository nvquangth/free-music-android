package com.quangnv.freemusic.screen.search;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Track;

import java.util.List;

/**
 * Created by quangnv on 21/10/2018
 */

public interface SearchContract {

    interface View {

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showHotKey(List<String> hotKeys);

        void showSearchHistory(List<String> history);

        void showTracks(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {

        void getHotKey();

        void getHistory();

        void searchTracks(String q, int offset);
    }
}
