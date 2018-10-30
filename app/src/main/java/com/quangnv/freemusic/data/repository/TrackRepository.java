package com.quangnv.freemusic.data.repository;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.source.TrackDataSource;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 13/10/2018
 */

public class TrackRepository implements TrackDataSource.Remote, TrackDataSource.Local {

    private TrackDataSource.Remote mRemote;
    private TrackDataSource.Local mLocal;

    public TrackRepository(TrackDataSource.Remote remote,
                           TrackDataSource.Local local) {
        mRemote = remote;
        mLocal = local;
    }

    @Override
    public Observable<List<Track>> searchTracks(String q, int offset) {
        return mRemote.searchTracks(q, offset);
    }

    @Override
    public Observable<List<Track>> getTracksByGenre(String genre, int offset) {
        return mRemote.getTracksByGenre(genre, offset);
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
        return mLocal.getTopTracks();
    }

    @Override
    public Observable<Track> getTrack() {
        return null;
    }

    @Override
    public Observable<List<Track>> getTrackFromLocalMemory() {
        return mLocal.getTrackFromLocalMemory();
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
}
