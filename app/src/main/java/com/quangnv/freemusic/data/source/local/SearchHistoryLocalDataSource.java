package com.quangnv.freemusic.data.source.local;

import com.quangnv.freemusic.data.model.SearchHistory;
import com.quangnv.freemusic.data.source.SearchHistoryDataSource;
import com.quangnv.freemusic.data.source.local.sqlite.DbHelper;
import com.quangnv.freemusic.data.source.local.sqlite.SearchHistoryDao;
import com.quangnv.freemusic.data.source.local.sqlite.SearchHistoryDaoImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 26/11/2018
 */

public class SearchHistoryLocalDataSource implements SearchHistoryDataSource.Local {

    private SearchHistoryDao mSearchHistoryDao;

    @Inject
    public SearchHistoryLocalDataSource(SearchHistoryDaoImpl searchHistoryDao) {
        mSearchHistoryDao = searchHistoryDao;
    }

    @Override
    public Observable<List<SearchHistory>> getSearchHistories() {
        return mSearchHistoryDao.getSearchHistories();
    }

    @Override
    public Observable<SearchHistory> getSearchHistory(int id) {
        return mSearchHistoryDao.getSearchHistory(id);
    }

    @Override
    public Completable insert(SearchHistory searchHistory) {
        return mSearchHistoryDao.insert(searchHistory);
    }

    @Override
    public Completable update(SearchHistory searchHistory) {
        return mSearchHistoryDao.update(searchHistory);
    }

    @Override
    public Completable delete(SearchHistory searchHistory) {
        return mSearchHistoryDao.delete(searchHistory);
    }

    @Override
    public Completable deleteAll() {
        return mSearchHistoryDao.deleteAll();
    }
}
