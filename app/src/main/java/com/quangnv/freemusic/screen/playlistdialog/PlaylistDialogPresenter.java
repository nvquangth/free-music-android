package com.quangnv.freemusic.screen.playlistdialog;

import android.util.Log;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by quangnv on 13/11/2018
 */

public class PlaylistDialogPresenter implements PlaylistDialogContract.Presenter {

    private PlaylistDialogContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mScheduler;
    private PlaylistRepository mPlaylistRepository;
    private TrackRepository mTrackRepository;

    public PlaylistDialogPresenter(CompositeDisposable compositeDisposable,
                                   BaseSchedulerProvider scheduler,
                                   PlaylistRepository playlistRepository,
                                   TrackRepository trackRepository) {
        mCompositeDisposable = compositeDisposable;
        mScheduler = scheduler;
        mPlaylistRepository = playlistRepository;
        mTrackRepository = trackRepository;
    }

    @Override
    public void getPlaylist() {
        Disposable disposable = mPlaylistRepository.getPlaylists()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        mView.showLoadingIndicator();
                    }
                })
                .subscribe(new Consumer<List<Playlist>>() {
                    @Override
                    public void accept(List<Playlist> playlists) {
                        mView.hideLoadingIndicator();
                        mView.showPlaylist(playlists);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.hideLoadingIndicator();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void addTrackToPlaylist(final Track track, final Playlist playlist) {
        Disposable disposable = mTrackRepository.getTrack(track.getId())
                .map(new Function<Track, Boolean>() {
                    @Override
                    public Boolean apply(Track track) {
                        if (track.getIsAddedPlaylist() == 1 && (track.getIsAddedFavorite() == 1 || track.getIsDownloaded() == 1)) {
                            return true;
                        }
                        return false;
                    }
                })
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {

                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            track.setIsAddedPlaylist(0);
                        } else {
                            track.setIsAddedPlaylist(1);
                        }
                        update(track, playlist);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        track.setIsAddedPlaylist(1);
                        add(track, playlist);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(PlaylistDialogContract.View view) {
        mView = view;
    }

    private void add(Track track, Playlist playlist) {
        Disposable disposable = mTrackRepository.addTrackToPlaylist(track, playlist)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.d("f***", "add track to playlist successful");
                        mView.showAddTrackToPlaylistSuccessful();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("f***", "add track to playlist failed");
                        mView.showAddTrackToPlaylistFailed();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void remove(Track track, Playlist playlist) {
        Disposable disposable = mTrackRepository.deleteTrack(track)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.d("f***", "track removed successful");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("f***", "track removed failed");
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void update(Track track, Playlist playlist) {
        Disposable disposable = mTrackRepository.updateTrack(track)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.d("f***", "track updated successful");
                        mView.showAddTrackToPlaylistSuccessful();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("f***", "track updated failed");
                        mView.showAddTrackToPlaylistFailed();
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
