package com.quangnv.freemusic.screen.mymusic;

import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.model.TrackLocalType;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 01/11/2018
 */

public class MyMusicPresenter implements MyMusicContract.Presenter {

    private MyMusicContract.View mView;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mCompositeDisposable;
    private TrackRepository mTrackRepository;
    private PlaylistRepository mPlaylistRepository;

    public MyMusicPresenter(BaseSchedulerProvider scheduler,
                            CompositeDisposable compositeDisposable,
                            TrackRepository trackRepository,
                            PlaylistRepository playlistRepository) {
        mScheduler = scheduler;
        mCompositeDisposable = compositeDisposable;
        mTrackRepository = trackRepository;
        mPlaylistRepository = playlistRepository;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(MyMusicContract.View view) {
        mView = view;
    }

    @Override
    public void getPlayList() {
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
                        mView.showPlayList(playlists);
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
    public void getFavorite() {
        Disposable disposable = mTrackRepository.getTracks(TrackLocalType.FAVORITE)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoadingIndicator();
                    }
                })
                .subscribe(new Consumer<List<Track>>() {
                    @Override
                    public void accept(List<Track> tracks) throws Exception {
                        mView.hideLoadingIndicator();
                        mView.showFavorite(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoadingIndicator();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getDownload() {

    }

    @Override
    public void getLocal() {
        Disposable disposable = mTrackRepository.getTrackFromLocalMemory()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        mView.showLoadingIndicator();
                    }
                })
                .subscribe(new Consumer<List<Track>>() {
                    @Override
                    public void accept(List<Track> tracks) {
                        mView.hideLoadingIndicator();
                        mView.showLocal(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.hideLoadingIndicator();
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
