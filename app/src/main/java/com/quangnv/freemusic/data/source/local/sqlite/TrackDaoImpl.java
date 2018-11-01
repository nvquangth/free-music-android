package com.quangnv.freemusic.data.source.local.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quangnv.freemusic.data.model.Publisher;
import com.quangnv.freemusic.data.model.Track;

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
 * Created by quangnv on 30/10/2018
 */

public class TrackDaoImpl implements TrackDao {

    private DbHelper mDbHelper;

    @Inject
    public TrackDaoImpl(DbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    @Override
    public Observable<List<Track>> getTracks() {
        List<Track> tracks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.query(
                    DbHelper.TrackEntry.TABLE_NAME,
                    genProjection(),
                    null,
                    null,
                    null,
                    null,
                    null
            );
            tracks = genTracks(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return Observable.just(tracks);
    }

    @Override
    public Observable<Track> getTrack(final long trackId) {
        return Observable.create(new ObservableOnSubscribe<Track>() {
            @Override
            public void subscribe(ObservableEmitter<Track> emitter) throws Exception {
                Track track = null;
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                try {
                    final String selection = DbHelper.TrackEntry.COLUMN_NAME_ID + " = ?";
                    final String[] selectionArgs = {String.valueOf(trackId)};
                    Cursor cursor = db.query(
                            DbHelper.TrackEntry.TABLE_NAME,
                            genProjection(),
                            selection,
                            selectionArgs,
                            null,
                            null,
                            null
                    );
                    if (cursor != null) {
                        cursor.moveToFirst();
                        track = genTrack(cursor);
                    }

                    if (track != null) {
                        emitter.onNext(track);
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
    public Observable<List<Track>> searchTrackByGenre(String genre) {
        List<Track> tracks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            final String selection = DbHelper.TrackEntry.COLUMN_NAME_GENRE + " LIKE ?";
            final String[] selectionArgs = {"%" + genre + "%"};
            Cursor cursor = db.query(
                    DbHelper.TrackEntry.TABLE_NAME,
                    genProjection(),
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            tracks = genTracks(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return Observable.just(tracks);
    }

    @Override
    public Completable insertTrack(final Track track) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                try {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_ID, track.getId());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_TITLE, track.getTitle());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DURATION, track.getDuration());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_ARTWORK_URL, track.getArtWorkUrl());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_STREAMABLE, track.isStreamable());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_STREAM_URL, track.getStreamUrl());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DOWNLOADABLE, track.isDownloadable());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DOWNLOAD_URL, track.getDownloadUrl());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_GENRE, track.getGenre());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_PLAYBACK_COUNT, track.getPlaybackCount());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DESCRIPTION, track.getDescription());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_ARTIST, track.getPublisher().getArtist());
                    db.insert(DbHelper.TrackEntry.TABLE_NAME, null, values);
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
    public Completable deleteTrack(final Track track) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                try {
                    final String selection = DbHelper.TrackEntry.COLUMN_NAME_ID + " = ? ";
                    final String[] selectionArgs = {String.valueOf(track.getId())};
                    db.delete(DbHelper.TrackEntry.TABLE_NAME, selection, selectionArgs);
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
    public Completable deleteAllTracks() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                try {
                    db.delete(DbHelper.TrackEntry.TABLE_NAME, null, null);
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
    public Completable updateTrack(final Track track) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                try {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_ID, track.getId());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_TITLE, track.getTitle());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DURATION, track.getDuration());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_ARTWORK_URL, track.getArtWorkUrl());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_STREAMABLE, track.isStreamable());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_STREAM_URL, track.getStreamUrl());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DOWNLOADABLE, track.isDownloadable());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DOWNLOAD_URL, track.getDownloadUrl());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_GENRE, track.getGenre());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_PLAYBACK_COUNT, track.getPlaybackCount());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_DESCRIPTION, track.getDescription());
                    values.put(DbHelper.TrackEntry.COLUMN_NAME_ARTIST, track.getPublisher().getArtist());
                    db.insert(DbHelper.TrackEntry.TABLE_NAME, null, values);
                    db.update(
                            DbHelper.TrackEntry.TABLE_NAME,
                            values,
                            DbHelper.TrackEntry.COLUMN_NAME_ID + " = " + track.getId(),
                            null);
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
    public Observable<List<Track>> searchTrackByTitle(String trackTitle) {
        List<Track> tracks;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        final String selection = DbHelper.TrackEntry.COLUMN_NAME_TITLE + " LIKE ?";
        final String[] selectionArgs = {"%" + trackTitle + "%"};
        Cursor cursor = db.query(
                DbHelper.TrackEntry.TABLE_NAME,
                genProjection(),
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        tracks = genTracks(cursor);
        db.close();
        return Observable.just(tracks);
    }

    private List<Track> genTracks(Cursor cursor) {
        List<Track> tracks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            tracks = new ArrayList<>();
            do {
                tracks.add(genTrack(cursor));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return tracks;
    }

    private Track genTrack(Cursor cursor) {
        Track.Builder builder = new Track.Builder();
        Track track = builder
                .setId(cursor.getLong(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_ID)))
                .setTitle(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_TITLE)))
                .setDuration(cursor.getLong(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_DURATION)))
                .setArtWorkUrl(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_ARTWORK_URL)))
                .setStreamable(cursor.getInt(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_STREAMABLE)) == 1)
                .setStreamUrl(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_STREAM_URL)))
                .setDownloadable(cursor.getInt(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_DOWNLOADABLE))
                        == 1)
                .setDownloadUrl(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_DOWNLOAD_URL)))
                .setGenre(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_GENRE)))
                .setPlaybackCount(cursor.getLong(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_PLAYBACK_COUNT)))
                .setDescription(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_DESCRIPTION)))
                .setPublisher(new Publisher.Builder().setArtist(cursor.getString(
                        cursor.getColumnIndex(DbHelper.TrackEntry.COLUMN_NAME_ARTIST))).build())
                .build();
        return track;
    }

    private String[] genProjection() {
        return new String[]{
                DbHelper.TrackEntry.COLUMN_NAME_ID,
                DbHelper.TrackEntry.COLUMN_NAME_TITLE,
                DbHelper.TrackEntry.COLUMN_NAME_DURATION,
                DbHelper.TrackEntry.COLUMN_NAME_ARTWORK_URL,
                DbHelper.TrackEntry.COLUMN_NAME_STREAMABLE,
                DbHelper.TrackEntry.COLUMN_NAME_STREAM_URL,
                DbHelper.TrackEntry.COLUMN_NAME_DOWNLOADABLE,
                DbHelper.TrackEntry.COLUMN_NAME_DOWNLOAD_URL,
                DbHelper.TrackEntry.COLUMN_NAME_GENRE,
                DbHelper.TrackEntry.COLUMN_NAME_PLAYBACK_COUNT,
                DbHelper.TrackEntry.COLUMN_NAME_DESCRIPTION,
                DbHelper.TrackEntry.COLUMN_NAME_ARTIST
        };
    }
}
