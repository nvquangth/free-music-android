package com.quangnv.freemusic.data.repository;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.source.PlaylistDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 11/11/2018
 */

public class PlaylistRepository implements PlaylistDataSource.Local {

    private PlaylistDataSource.Local mLocal;

    public PlaylistRepository(PlaylistDataSource.Local local) {
        mLocal = local;
    }

    @Override
    public Observable<Playlist> getPlaylist(int playlistId) {
        return mLocal.getPlaylist(playlistId);
    }

    @Override
    public Observable<List<Playlist>> getPlaylists() {
        return mLocal.getPlaylists();
    }

    @Override
    public Completable insert(Playlist playlist) {
        return mLocal.insert(playlist);
    }

    @Override
    public Completable delete(Playlist playlist) {
        return mLocal.delete(playlist);
    }

    @Override
    public Completable update(Playlist playlist) {
        return mLocal.update(playlist);
    }
}
