package com.quangnv.freemusic.screen.genre;

import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 21/10/2018
 */

public class GenrePresenter implements GenreContract.Presenter {

    private GenreContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mScheduler;
    private TrackRepository mTrackRepository;

    public GenrePresenter(BaseSchedulerProvider scheduler,
                          CompositeDisposable compositeDisposable,
                          TrackRepository trackRepository) {
        mScheduler = scheduler;
        mCompositeDisposable = compositeDisposable;
        mTrackRepository = trackRepository;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void getTracks(Genre genre, int offset) {
        Disposable disposable = mTrackRepository
                .getTracksByGenre(genre.getMetaData(), offset)
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
                        mView.showTracks(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoadingIndicator();
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
