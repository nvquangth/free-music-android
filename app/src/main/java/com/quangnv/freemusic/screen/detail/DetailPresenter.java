package com.quangnv.freemusic.screen.detail;

import android.os.Environment;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.model.TrackLocalType;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.download.TrackDownloadManager;
import com.quangnv.freemusic.util.StringUtils;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 01/11/2018
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private TrackRepository mTrackRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mScheduler;
    private TrackDownloadManager mTrackDownloadManager;

    public DetailPresenter(BaseSchedulerProvider scheduler,
                           CompositeDisposable compositeDisposable,
                           TrackRepository trackRepository) {
        mScheduler = scheduler;
        mCompositeDisposable = compositeDisposable;
        mTrackRepository = trackRepository;
    }

    @Override
    public void addOrRemoveFavorite(final Track track) {
        Disposable disposable = mTrackRepository.getTrack(track.getId())
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Consumer<Track>() {
                    @Override
                    public void accept(Track track) {
                        if (track.getIsDownloaded() == 0 && track.getIsAddedPlaylist() == 0) {
                            if (track.getIsAddedFavorite() == 0) {
                                track.setIsAddedFavorite(1);
                                updateTrack(track, 1);
                            } else {
                                deleteTrack(track);
                            }
                        } else {
                            if (track.getIsAddedFavorite() == 0) {
                                track.setIsAddedFavorite(1);
                                updateTrack(track, 1);
                            } else {
                                track.setIsAddedFavorite(0);
                                updateTrack(track, 0);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        track.setIsAddedFavorite(1);
                        insertTrack(track);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void checkFavoriteTrack(Track track) {
        Disposable disposable = mTrackRepository.getTrack(track.getId())
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Consumer<Track>() {
                    @Override
                    public void accept(Track track) {
                        if (track.getDownloadStatus() == TrackLocalType.FAVORITE) {
                            mView.showTrackAddedToFavorite();
                        } else {
                            mView.showTrackRemovedFromFavorite();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.showTrackRemovedFromFavorite();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void saveTrack(Track track) {
        mTrackDownloadManager.downloadTrack(track);
        String url = StringUtils.formatFilePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath(),
                track.getTitle());
        track.setStreamUrl(url);
        Disposable disposable = mTrackRepository.insertTrack(track, TrackLocalType.DOWNLOAD)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        mView.showTrackDownloaded();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.showTrackDownloadError();
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
    public void setView(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void setTrackDownloadManager(TrackDownloadManager trackDownloadManager) {
        mTrackDownloadManager = trackDownloadManager;
    }

    private void deleteTrack(Track track) {
        Disposable disposable = mTrackRepository.deleteTrack(track)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        mView.showTrackRemovedFromFavorite();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.showHandleFavoriteError();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void updateTrack(Track track, final int isAdded) {
        Disposable disposable = mTrackRepository.updateTrack(track)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        if (isAdded == 0) {
                            mView.showTrackRemovedFromFavorite();
                        } else {
                            mView.showTrackAddedToFavorite();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.showHandleFavoriteError();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void insertTrack(Track track) {
        Disposable disposable = mTrackRepository.insertTrack(track,
                TrackLocalType.FAVORITE)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        mView.showTrackAddedToFavorite();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.showHandleFavoriteError();
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
