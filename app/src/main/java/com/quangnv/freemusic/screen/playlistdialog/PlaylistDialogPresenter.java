package com.quangnv.freemusic.screen.playlistdialog;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 13/11/2018
 */

public class PlaylistDialogPresenter implements PlaylistDialogContract.Presenter {

    private PlaylistDialogContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mScheduler;
    private PlaylistRepository mPlaylistRepository;

    public PlaylistDialogPresenter(CompositeDisposable compositeDisposable,
                                   BaseSchedulerProvider scheduler,
                                   PlaylistRepository playlistRepository) {
        mCompositeDisposable = compositeDisposable;
        mScheduler = scheduler;
        mPlaylistRepository = playlistRepository;
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
}
