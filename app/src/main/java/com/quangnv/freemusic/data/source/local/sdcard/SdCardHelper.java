package com.quangnv.freemusic.data.source.local.sdcard;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.quangnv.freemusic.data.model.Publisher;
import com.quangnv.freemusic.data.model.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by quangnv on 30/10/2018
 */

public class SdCardHelper {

    private Context mContext;

    public SdCardHelper(Context context) {
        mContext = context;
    }

    public Observable<List<Track>> getTrackFromLocal() {
        ContentResolver resolver = mContext.getContentResolver();

        String[] projections = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media._ID
        };

        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projections,
                null,
                null,
                null);

        List<Track> tracks = new ArrayList<>();

        if (cursor == null) return Observable.just(tracks);

        while (cursor.moveToNext()) {
            Track track = new Track();
            track.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            track.setTitle(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            track.setDuration(
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            track.setArtWorkUrl(
                    getAlbumArt(
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));
            track.setStreamUrl(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            Publisher.Builder pBuilder = new Publisher.Builder();
            pBuilder.setArtist(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            track.setPublisher(pBuilder.build());
            tracks.add(track);
        }

        cursor.close();
        return Observable.just(tracks);
    }

    private String getAlbumArt(int albumId) {
        String[] projections = {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART};
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = {String.valueOf(albumId)};
        Cursor cursorAlbum = mContext.getContentResolver()
                .query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        projections, selection,
                        selectionArgs,
                        null);
        if (cursorAlbum == null) return null;
        String artwork = null;
        if (cursorAlbum.moveToFirst()) {
            artwork = cursorAlbum.getString(
                    cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        }
        cursorAlbum.close();
        return artwork;
    }
}
