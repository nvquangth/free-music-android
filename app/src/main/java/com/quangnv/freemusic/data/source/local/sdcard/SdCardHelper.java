package com.quangnv.freemusic.data.source.local.sdcard;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

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
                MediaStore.Audio.Media.DATA
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
            Track.Builder builder = new Track.Builder();
            builder.setTitle(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            builder.setDuration(
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            builder.setStreamUrl(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            Publisher.Builder pBuilder = new Publisher.Builder();
            pBuilder.setArtist(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            builder.setPublisher(pBuilder.build());
            tracks.add(builder.build());
        }

        cursor.close();
        return Observable.just(tracks);
    }
}
