package com.quangnv.freemusic.data.source.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.model.TrackLocalType;
import com.quangnv.freemusic.data.source.TrackDataSource;
import com.quangnv.freemusic.data.source.local.asset.AssetsHelper;
import com.quangnv.freemusic.data.source.local.sdcard.SdCardHelper;
import com.quangnv.freemusic.data.source.local.sqlite.TrackDao;
import com.quangnv.freemusic.data.source.local.sqlite.TrackDaoImpl;
import com.quangnv.freemusic.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by quangnv on 17/10/2018
 */

public class TrackLocalDataSource implements TrackDataSource.Local {

    private AssetsHelper mAssetsHelper;
    private SdCardHelper mSdCardHelper;
    private TrackDao mTrackDao;

    @Inject
    public TrackLocalDataSource(AssetsHelper assetsHelper,
                                SdCardHelper sdCardHelper,
                                TrackDaoImpl trackDaoImpl) {
        mAssetsHelper = assetsHelper;
        mSdCardHelper = sdCardHelper;
        mTrackDao = trackDaoImpl;
    }

    @Override
    public Observable<List<Track>> searchTracks(String q) {
        return mTrackDao.searchTrackByTitle(q);
    }

    @Override
    public Observable<List<Track>> getTracks(@TrackLocalType int type) {
        return mTrackDao.getTracks(type);
    }

    @Override
    public Observable<List<Track>> getTopTracks() {
        return Observable.just(getTopTrackFromAssets(Constants.TOP_TRACK_ASSETS_PATH));
    }

    @Override
    public Observable<Track> getTrack(long id) {
        return mTrackDao.getTrack(id);
    }

    @Override
    public Observable<List<Track>> getTrackFromLocalMemory() {
        return mSdCardHelper.getTrackFromLocal();
    }

    @Override
    public Completable insertTrack(Track track, @TrackLocalType int type) {
        return mTrackDao.insertTrack(track, type);
    }

    @Override
    public Completable updateTrack(Track track) {
        return mTrackDao.updateTrack(track);
    }

    @Override
    public Completable deleteTrack(Track track) {
        return mTrackDao.deleteTrack(track);
    }

    @Override
    public Observable<Boolean> isExistTrack(Track track) {
        return mTrackDao.getTrack(track.getId())
                .flatMap(new Function<Track, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Track track) throws Exception {
                        if (track == null) {
                            return Observable.just(false);
                        }
                        return Observable.just(true);
                    }
                });
    }

    @Override
    public Completable addTrackToPlaylist(Track track, Playlist playlist) {
        return mTrackDao.addTrackToPlaylist(track, playlist);
    }

    private List<Track> getTopTrackFromAssets(String path) {
        String json = mAssetsHelper.read(path);
        return new Gson().fromJson(json, new TypeToken<List<Track>>(){}.getType());
    }
}
