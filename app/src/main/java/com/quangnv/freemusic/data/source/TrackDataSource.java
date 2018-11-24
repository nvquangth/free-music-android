package com.quangnv.freemusic.data.source;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.model.TrackLocalType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 12/10/2018
 */

public interface TrackDataSource {

    interface Local extends TrackDataSource {
        Observable<List<Track>> searchTracks(String q);

        Observable<List<Track>> getTracks(@TrackLocalType int type);

        Observable<List<Track>> getTopTracks();

        Observable<Track> getTrack(long id);

        Observable<List<Track>> getTrackFromLocalMemory();

        Completable insertTrack(Track track, @TrackLocalType int type);

        Completable updateTrack(Track track);

        Completable deleteTrack(Track track);

        Observable<Boolean> isExistTrack(Track track);

        Completable addTrackToPlaylist(Track track, Playlist playlist);
    }

    interface Remote extends TrackDataSource {
        Observable<List<Track>> searchTracks(String q, int offset);

        Observable<List<Track>> getTracksByGenre(String genre, int offset);
    }
}
