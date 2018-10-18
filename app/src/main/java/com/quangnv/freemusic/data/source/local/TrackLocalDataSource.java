package com.quangnv.freemusic.data.source.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.source.TrackDataSource;
import com.quangnv.freemusic.data.source.local.asset.AssetsHelper;
import com.quangnv.freemusic.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 17/10/2018
 */

public class TrackLocalDataSource implements TrackDataSource.Local {

    private AssetsHelper mAssetsHelper;

    @Inject
    public TrackLocalDataSource(AssetsHelper assetsHelper) {
        mAssetsHelper = assetsHelper;
    }

    @Override
    public Observable<List<Track>> searchTracks(String q) {
        return null;
    }

    @Override
    public Observable<List<Track>> getTracks() {
        return null;
    }

    @Override
    public Observable<List<Track>> getTopTracks() {
        return Observable.just(getTopTrackFromAssets(Constants.TOP_TRACK_ASSETS_PATH));
    }

    @Override
    public Observable<Track> getTrack() {
        return null;
    }

    @Override
    public Completable insertTrack(Track track) {
        return null;
    }

    @Override
    public Completable updateTrack(Track track) {
        return null;
    }

    @Override
    public Completable deleteTrack(Track track) {
        return null;
    }

    private List<Track> getTopTrackFromAssets(String path) {
        String json = mAssetsHelper.read(path);
        return new Gson().fromJson(json, new TypeToken<List<Track>>(){}.getType());
    }
}
