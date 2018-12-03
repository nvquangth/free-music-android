package com.quangnv.freemusic.screen.search;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.SearchHistory;
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

        void showSearchHistory(List<SearchHistory> histories);

        void showTracks(List<Track> tracks);
    }

    interface Presenter extends BasePresenter<View> {

        void getHotKey();

        void addHistory(SearchHistory history);

        void clearHistory(SearchHistory history);

        void clearAllHistories();

        void getHistories();

        void searchTracks(String q, int offset);
    }
}
