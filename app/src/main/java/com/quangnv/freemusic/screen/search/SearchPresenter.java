package com.quangnv.freemusic.screen.search;

import android.util.Log;

import com.quangnv.freemusic.data.model.SearchHistory;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.repository.SearchHistoryRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by quangnv on 22/10/2018
 */

public class SearchPresenter implements SearchContract.Presenter {

    private static final String TAG = "SearchPresenterTag";

    private SearchContract.View mView;
    private BaseSchedulerProvider mScheduler;
    private CompositeDisposable mCompositeDisposable;
    private TrackRepository mTrackRepository;
    private SearchHistoryRepository mHistoryRepository;

    public SearchPresenter(BaseSchedulerProvider scheduler,
                           CompositeDisposable compositeDisposable,
                           TrackRepository trackRepository,
                           SearchHistoryRepository historyRepository) {
        mScheduler = scheduler;
        mCompositeDisposable = compositeDisposable;
        mTrackRepository = trackRepository;
        mHistoryRepository = historyRepository;
    }

    @Override
    public void getHotKey() {
        // TODO: 10/22/18 Fake data
        List<String> hotKeys = new ArrayList<>();
        hotKeys.add("Hương Tràm");
        hotKeys.add("Mưa");
        hotKeys.add("Mùa Đông");
        hotKeys.add("Lạnh");
        hotKeys.add("Mỹ Tâm");
        hotKeys.add("Người Lạ Ơi");
        hotKeys.add("Phai Dấu Cuộc Tình");
        hotKeys.add("Tết");
        hotKeys.add("Cẩm Ly");
        mView.showHotKey(hotKeys);
    }

    @Override
    public void addHistory(SearchHistory history) {
        Disposable disposable = mHistoryRepository.insert(history)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.d(TAG, " history added successful");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, " history added failed");
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearHistory(SearchHistory history) {
        Disposable disposable = mHistoryRepository.delete(history)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.d(TAG, " history deleted successful");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, " history deleted failed");
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clearAllHistories() {
        Disposable disposable = mHistoryRepository.deleteAll()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        Log.d(TAG, " history cleared successful");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, " history cleared failed");
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getHistories() {
        Disposable disposable = mHistoryRepository.getSearchHistories()
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoadingIndicator();
                    }
                })
                .subscribe(new Consumer<List<SearchHistory>>() {
                    @Override
                    public void accept(List<SearchHistory> searchHistories) throws Exception {
                        mView.hideLoadingIndicator();
                        mView.showSearchHistory(searchHistories);
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
    public void searchTracks(String q, int offset) {
        Disposable disposable = mTrackRepository.searchTracks(q, offset)
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

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(SearchContract.View view) {
        mView = view;
    }
}
