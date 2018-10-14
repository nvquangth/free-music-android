package com.quangnv.freemusic.screen.main;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 13/10/2018
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mScheduler;
    private TrackRepository mRepository;

    public MainPresenter(CompositeDisposable compositeDisposable,
                         BaseSchedulerProvider scheduler,
                         TrackRepository repository) {
        mCompositeDisposable = compositeDisposable;
        mScheduler = scheduler;
        mRepository = repository;
    }

    @Override
    public void getTrack(String genre, int offset) {
        Disposable disposable = mRepository.getTracksByGenre(genre, offset)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Consumer<List<Track>>() {
                    @Override
                    public void accept(List<Track> tracks) throws Exception {
                        mView.showTracks(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                });
        mCompositeDisposable.add(disposable);

        Disposable disposable1 = mRepository.searchTracks("faded", 0)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Consumer<List<Track>>() {
                    @Override
                    public void accept(List<Track> tracks) throws Exception {
                        mView.showTracks(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                });
        mCompositeDisposable.add(disposable1);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }
}
