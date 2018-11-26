package com.quangnv.freemusic.data.repository;

import com.quangnv.freemusic.data.model.SearchHistory;
import com.quangnv.freemusic.data.source.SearchHistoryDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 26/11/2018
 */

public class SearchHistoryRepository implements SearchHistoryDataSource.Local {

    private SearchHistoryDataSource.Local mLocal;

    public SearchHistoryRepository(SearchHistoryDataSource.Local local) {
        mLocal = local;
    }

    @Override
    public Observable<List<SearchHistory>> getSearchHistories() {
        return mLocal.getSearchHistories();
    }

    @Override
    public Observable<SearchHistory> getSearchHistory(int id) {
        return mLocal.getSearchHistory(id);
    }

    @Override
    public Completable insert(SearchHistory searchHistory) {
        return mLocal.insert(searchHistory);
    }

    @Override
    public Completable update(SearchHistory searchHistory) {
        return mLocal.update(searchHistory);
    }

    @Override
    public Completable delete(SearchHistory searchHistory) {
        return mLocal.delete(searchHistory);
    }
}
