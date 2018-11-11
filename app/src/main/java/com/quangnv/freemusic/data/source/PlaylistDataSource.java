package com.quangnv.freemusic.data.source;

import com.quangnv.freemusic.data.model.Playlist;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 11/11/2018
 */

public interface PlaylistDataSource {

    interface Local extends PlaylistDataSource {

        Observable<Playlist> getPlaylist(int playlistId);

        Observable<List<Playlist>> getPlaylists();

        Completable insert(Playlist playlist);

        Completable delete(Playlist playlist);

        Completable update(Playlist playlist);
    }
}
