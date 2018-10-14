package com.quangnv.freemusic.data.repository;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.source.TrackDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by quangnv on 13/10/2018
 */

public class TrackRepository implements TrackDataSource.Remote {

    private TrackDataSource.Remote mRemote;

    public TrackRepository(TrackRemoteDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public Observable<List<Track>> searchTracks(String q, int offset) {
        return mRemote.searchTracks(q, offset);
    }

    @Override
    public Observable<List<Track>> getTracksByGenre(String genre, int offset) {
        return mRemote.getTracksByGenre(genre, offset);
    }
}
