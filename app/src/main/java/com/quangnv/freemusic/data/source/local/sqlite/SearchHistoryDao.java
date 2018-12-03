package com.quangnv.freemusic.data.source.local.sqlite;

import com.quangnv.freemusic.data.model.SearchHistory;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 26/11/2018
 */

public interface SearchHistoryDao {

    Observable<List<SearchHistory>> getSearchHistories();

    Observable<SearchHistory> getSearchHistory(int id);

    Completable insert(SearchHistory searchHistory);

    Completable update(SearchHistory searchHistory);

    Completable delete(SearchHistory searchHistory);

    Completable deleteAll();
}
