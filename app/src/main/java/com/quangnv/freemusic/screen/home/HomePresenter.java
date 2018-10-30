package com.quangnv.freemusic.screen.home;

import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.repository.GenreRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 15/10/2018
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mCompositeDisposable;
    private TrackRepository mTrackRepository;
    private GenreRepository mGenreRepository;

    public HomePresenter(BaseSchedulerProvider scheduler,
                         CompositeDisposable compositeDisposable,
                         TrackRepository trackRepository,
                         GenreRepository genreRepository) {
        mScheduler = scheduler;
        mCompositeDisposable = compositeDisposable;
        mTrackRepository = trackRepository;
        mGenreRepository = genreRepository;
    }

    @Override
    public void getTopTracks() {
        Disposable disposable = mTrackRepository.getTopTracks()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoadingTopTrackIndicator();
                    }
                })
                .subscribe(new Consumer<List<Track>>() {
                    @Override
                    public void accept(List<Track> tracks) throws Exception {
                        mView.hideLoadingTopTrackIndicator();
                        mView.showTopTrack(tracks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoadingTopTrackIndicator();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getGenres() {
        Disposable disposable = mGenreRepository.getGenres()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoadingGenreIndicator();
                    }
                })
                .subscribe(new Consumer<List<Genre>>() {
                    @Override
                    public void accept(List<Genre> genres) throws Exception {
                        mView.hideLoadingGenreIndicator();
                        mView.showGenres(genres);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoadingGenreIndicator();
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
    public void setView(HomeContract.View view) {
        mView = view;
    }
}
