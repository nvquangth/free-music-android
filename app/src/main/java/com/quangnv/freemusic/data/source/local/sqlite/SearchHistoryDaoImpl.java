package com.quangnv.freemusic.data.source.local.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quangnv.freemusic.data.model.SearchHistory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by quangnv on 26/11/2018
 */

public class SearchHistoryDaoImpl implements SearchHistoryDao {

    private DbHelper mDbHelper;

    @Inject
    public SearchHistoryDaoImpl(DbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<List<SearchHistory>> getSearchHistories() {
        List<SearchHistory> histories = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbHelper.SearchHistoryEntry.TABLE_NAME,
                getProjections(),
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            histories.add(getSearchHistory(cursor));
        }
        cursor.close();
        db.close();
        return Observable.just(histories);
    }

    @Override
    public Observable<SearchHistory> getSearchHistory(final int id) {
        return Observable.create(new ObservableOnSubscribe<SearchHistory>() {
            @Override
            public void subscribe(ObservableEmitter<SearchHistory> emitter) throws Exception {
                SearchHistory searchHistory = null;
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                final String selection = DbHelper.SearchHistoryEntry.COLUMN_NAME_ID + " = ? ";
                final String selectionArgs[] = {String.valueOf(id)};
                Cursor cursor = db.query(
                        DbHelper.SearchHistoryEntry.TABLE_NAME,
                        getProjections(),
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                while (cursor.moveToNext()) {
                    searchHistory = getSearchHistory(cursor);
                }
                cursor.close();
                db.close();
                if (searchHistory != null) {
                    emitter.onNext(searchHistory);
                    emitter.onComplete();
                } else {
                    emitter.onError(new Exception("Element is not exists"));
                }
            }
        });
    }

    @Override
    public Completable insert(final SearchHistory searchHistory) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DbHelper.SearchHistoryEntry.COLUMN_NAME_TITLE, searchHistory.getTitle());
                long result = db.insert(DbHelper.SearchHistoryEntry.TABLE_NAME, null, values);
                db.close();
                if (result != -1) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Exception("An error occurred"));
                }
            }
        });
    }

    @Override
    public Completable update(final SearchHistory searchHistory) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DbHelper.SearchHistoryEntry.COLUMN_NAME_TITLE, searchHistory.getTitle());
                int result = db.update(
                        DbHelper.SearchHistoryEntry.TABLE_NAME,
                        values,
                        DbHelper.SearchHistoryEntry.COLUMN_NAME_ID + " = " + searchHistory.getId(),
                        null);
                db.close();
                if (result > 0) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Exception("Element don't update"));
                }
            }
        });
    }

    @Override
    public Completable delete(final SearchHistory searchHistory) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                final String selection = DbHelper.SearchHistoryEntry.COLUMN_NAME_ID + " = ? ";
                final String selectionArgs[] = {String.valueOf(searchHistory.getId())};

                long n = db.delete(DbHelper.SearchHistoryEntry.TABLE_NAME, selection, selectionArgs);
                db.close();
                if (n > 0) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Exception("Element is not exists"));
                }
            }
        });
    }

    private SearchHistory getSearchHistory(Cursor cursor) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setId(cursor.getInt(
                cursor.getColumnIndex(DbHelper.SearchHistoryEntry.COLUMN_NAME_ID)));
        searchHistory.setTitle(cursor.getString(
                cursor.getColumnIndex(DbHelper.SearchHistoryEntry.COLUMN_NAME_TITLE)));
        return searchHistory;
    }

    private String[] getProjections() {
        return new String[]{
                DbHelper.SearchHistoryEntry.COLUMN_NAME_ID,
                DbHelper.SearchHistoryEntry.COLUMN_NAME_TITLE
        };
    }
}
