package com.quangnv.freemusic.data.source.local.sqlite;

import com.quangnv.freemusic.data.model.Track;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 30/10/2018
 */

public interface TrackDao {

    Observable<List<Track>> getTracks();

    Observable<Track> getTrack(long trackId);

    Observable<List<Track>> searchTrackByGenre(String genre);

    Completable insertTrack(Track track);

    Completable deleteTrack(Track track);

    Completable deleteAllTracks();

    Completable updateTrack(Track track);

    Observable<List<Track>> searchTrackByTitle(String trackTitle);
}
