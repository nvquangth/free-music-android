package com.quangnv.freemusic.data.source;

import com.quangnv.freemusic.data.model.Track;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 12/10/2018
 */

public interface TrackDataSource {

    interface Local extends TrackDataSource {
        Observable<List<Track>> searchTracks(String q);

        Observable<List<Track>> getTracks();

        Observable<List<Track>> getTopTracks();

        Observable<Track> getTrack(long id);

        Observable<List<Track>> getTrackFromLocalMemory();

        Completable insertTrack(Track track);

        Completable updateTrack(Track track);

        Completable deleteTrack(Track track);

        Observable<Boolean> isExistTrack(Track track);
    }

    interface Remote extends TrackDataSource {
        Observable<List<Track>> searchTracks(String q, int offset);

        Observable<List<Track>> getTracksByGenre(String genre, int offset);
    }
}
