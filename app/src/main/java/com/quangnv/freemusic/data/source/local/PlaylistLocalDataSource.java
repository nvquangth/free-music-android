package com.quangnv.freemusic.data.source.local;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.source.PlaylistDataSource;
import com.quangnv.freemusic.data.source.local.sqlite.PlaylistDao;
import com.quangnv.freemusic.data.source.local.sqlite.PlaylistDaoImpl;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by quangnv on 11/11/2018
 */

public class PlaylistLocalDataSource implements PlaylistDataSource.Local {

    private PlaylistDao mPlaylistDao;

    public PlaylistLocalDataSource(PlaylistDaoImpl playlistDao) {
        mPlaylistDao = playlistDao;
    }

    @Override
    public Observable<Playlist> getPlaylist(int playlistId) {
        return mPlaylistDao.getPlaylist(playlistId);
    }

    @Override
    public Observable<List<Playlist>> getPlaylists() {
        return mPlaylistDao.getPlaylists();
    }

    @Override
    public Completable insert(Playlist playlist) {
        return mPlaylistDao.insert(playlist);
    }

    @Override
    public Completable delete(Playlist playlist) {
        return mPlaylistDao.delete(playlist);
    }

    @Override
    public Completable update(Playlist playlist) {
        return mPlaylistDao.update(playlist);
    }
}
