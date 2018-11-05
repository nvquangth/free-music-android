package com.quangnv.freemusic.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.util.StringUtils;

/**
 * Created by quangnv on 03/11/2018
 */

public final class TrackDownloadManager {

    private static TrackDownloadManager sInstance;
    private Context mContext;
    private DownloadManager mDownloadManager;

    private TrackDownloadManager(Context context, DownloadManager downloadManager) {
        mContext = context;
        mDownloadManager = downloadManager;
    }

    public static TrackDownloadManager getInstance(Context context, DownloadManager downloadManager) {
        if (sInstance == null) {
            sInstance = new TrackDownloadManager(context, downloadManager);
        }
        return sInstance;
    }

    public void downloadTrack(Track track) {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).mkdirs();
        Uri uri = Uri.parse(track.getDownloadUrl());
        Log.d("download_url", track.getDownloadUrl());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(track.getTitle());
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                StringUtils.formatTrackFile(track.getTitle()));
        mDownloadManager.enqueue(request);
    }
}
