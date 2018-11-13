package com.quangnv.freemusic.data.source.local.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quangnv.freemusic.data.model.Playlist;

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
 * Created by quangnv on 11/11/2018
 */

public class PlaylistDaoImpl implements PlaylistDao {

    private DbHelper mDbHelper;

    @Inject
    public PlaylistDaoImpl(DbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<Playlist> getPlaylist(final int playlistId) {
        return Observable.create(new ObservableOnSubscribe<Playlist>() {
            @Override
            public void subscribe(ObservableEmitter<Playlist> emitter) throws Exception {
                Playlist playlist = null;
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                try {
                    final String selection = DbHelper.PlaylistEntry.COLUMN_NAME_ID + " = ?";
                    final String[] selectionArgs = {String.valueOf(playlistId)};
                    Cursor cursor = db.query(
                            DbHelper.PlaylistEntry.TABLE_NAME,
                            genProjection(),
                            selection,
                            selectionArgs,
                            null,
                            null,
                            null
                    );
                    if (cursor != null) {
                        cursor.moveToFirst();
                        playlist = genPlaylist(cursor);
                    }

                    if (playlist != null) {
                        emitter.onNext(playlist);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NullPointerException());
                    }

                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                } finally {
                    db.close();
                }
            }
        });
    }

    @Override
    public Observable<List<Playlist>> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.query(
                    DbHelper.PlaylistEntry.TABLE_NAME,
                    genProjection(),
                    null,
                    null,
                    null,
                    null,
                    null
            );
            playlists = genPlaylists(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return Observable.just(playlists);
    }

    @Override
    public Completable insert(final Playlist playlist) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                try {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.PlaylistEntry.COLUMN_NAME_TITLE, playlist.getName());
                    db.insert(DbHelper.PlaylistEntry.TABLE_NAME, null, values);
                    db.close();
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    db.close();
                    emitter.onError(e);
                } finally {
                    db.close();
                }
            }
        });
    }

    @Override
    public Completable delete(final Playlist playlist) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                try {
                    final String selection = DbHelper.PlaylistEntry.COLUMN_NAME_ID + " = ? ";
                    final String[] selectionArgs = {String.valueOf(playlist.getId())};
                    db.delete(DbHelper.PlaylistEntry.TABLE_NAME, selection, selectionArgs);
                    db.close();
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    db.close();
                    emitter.onError(e);
                }
            }
        });
    }

    @Override
    public Completable update(Playlist playlist) {
        return null;
    }

    private List<Playlist> genPlaylists(Cursor cursor) {
        List<Playlist> playlists = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                playlists.add(genPlaylist(cursor));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return playlists;
    }

    private Playlist genPlaylist(Cursor cursor) {
        Playlist playlist = new Playlist();
        playlist.setId(cursor.getInt(cursor.getColumnIndex(DbHelper.PlaylistEntry.COLUMN_NAME_ID)));
        playlist.setName(cursor.getString(cursor.getColumnIndex(DbHelper.PlaylistEntry.COLUMN_NAME_TITLE)));
        playlist.setImageUrl(cursor.getString(cursor.getColumnIndex(DbHelper.PlaylistEntry.COLUMN_NAME_IMAGE_URL)));
        return playlist;
    }

    private String[] genProjection() {
        return new String[] {
                DbHelper.PlaylistEntry.COLUMN_NAME_ID,
                DbHelper.PlaylistEntry.COLUMN_NAME_TITLE,
                DbHelper.PlaylistEntry.COLUMN_NAME_IMAGE_URL
        };
    }
}
