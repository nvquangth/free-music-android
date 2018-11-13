package com.quangnv.freemusic.screen.playlist;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 11/11/2018
 */

public class PlaylistPresenter implements PlaylistContract.Presenter {

    private PlaylistContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mScheduler;
    private PlaylistRepository mPlaylistRepository;

    public PlaylistPresenter(CompositeDisposable compositeDisposable,
                             BaseSchedulerProvider scheduler,
                             PlaylistRepository playlistRepository) {
        mCompositeDisposable = compositeDisposable;
        mScheduler = scheduler;
        mPlaylistRepository = playlistRepository;
    }

    @Override
    public void savePlaylist(Playlist playlist) {
        Disposable disposable = mPlaylistRepository.insert(playlist)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        mView.showLoadingIndicator();
                    }
                })
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        mView.hideLoadingIndicator();
                        mView.showCreatePlaylistSuccessful();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.hideLoadingIndicator();
                        mView.showCreatePlaylistFailed();
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
    public void setView(PlaylistContract.View view) {
        mView = view;
    }
}
