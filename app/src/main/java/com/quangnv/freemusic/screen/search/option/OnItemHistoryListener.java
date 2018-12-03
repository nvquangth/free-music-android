package com.quangnv.freemusic.screen.search.option;

import com.quangnv.freemusic.data.model.SearchHistory;

/**
 * Created by quangnv on 29/11/2018
 */

public interface OnItemHistoryListener {

    void onItemHistoryClick(SearchHistory history, int position, boolean isClearHistory);
}
