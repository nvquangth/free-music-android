package com.quangnv.freemusic.data.source.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by quangnv on 30/10/2018
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "SoundCloud.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMA_SEP = ",";

    private static final String SQL_CRREATE_TRACK_ENTRIES = "CREATE TABLE "
            + TrackEntry.TABLE_NAME
            + " ("
            + TrackEntry.COLUMN_NAME_ID
            + INTEGER_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_TITLE
            + TEXT_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_DURATION
            + INTEGER_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_ARTWORK_URL
            + TEXT_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_STREAMABLE
            + INTEGER_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_STREAM_URL
            + TEXT_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_DOWNLOADABLE
            + INTEGER_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_DOWNLOAD_URL
            + TEXT_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_GENRE
            + TEXT_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_PLAYBACK_COUNT
            + INTEGER_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_DESCRIPTION
            + TEXT_TYPE
            + COMA_SEP
            + TrackEntry.COLUMN_NAME_ARTIST
            + INTEGER_TYPE
            +COMA_SEP
            + TrackEntry.COLUMN_NAME_DOWNLOAD_STATUS
            + INTEGER_TYPE
            + " )";
    private static final String SQL_DELETE_TRACK_ENTRIES = "DROP TABLE IF EXISTS "
            + TrackEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CRREATE_TRACK_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TRACK_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static final class TrackEntry implements BaseColumns {

        public static final String TABLE_NAME = "track";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_ARTWORK_URL = "artwork_url";
        public static final String COLUMN_NAME_STREAMABLE = "streamable";
        public static final String COLUMN_NAME_STREAM_URL = "stream_url";
        public static final String COLUMN_NAME_DOWNLOADABLE = "downloadable";
        public static final String COLUMN_NAME_DOWNLOAD_URL = "download_url";
        public static final String COLUMN_NAME_GENRE = "genre";
        public static final String COLUMN_NAME_PLAYBACK_COUNT = "playback_count";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_ARTIST = "artist";
        public static final String COLUMN_NAME_DOWNLOAD_STATUS = "download_status";
    }
}