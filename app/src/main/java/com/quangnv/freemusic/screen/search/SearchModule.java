package com.quangnv.freemusic.screen.search;

import com.quangnv.freemusic.data.repository.SearchHistoryRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.local.SearchHistoryLocalDataSource;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
import com.quangnv.freemusic.util.dagger.FragmentScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 22/10/2018
 */

@Module
public class SearchModule {

    @FragmentScope
    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @FragmentScope
    @Provides
    public TrackRepository provideTrackRepository(TrackRemoteDataSource remote,
                                                  TrackLocalDataSource local) {
        return new TrackRepository(remote, local);
    }

    @FragmentScope
    @Provides
    public SearchHistoryRepository provideSearchHistoryRepository(SearchHistoryLocalDataSource local) {
        return new SearchHistoryRepository(local);
    }

    @FragmentScope
    @Provides
    public SearchContract.Presenter provideSearchPresenter(BaseSchedulerProvider scheduler,
                                                           CompositeDisposable compositeDisposable,
                                                           TrackRepository trackRepository,
                                                           SearchHistoryRepository historyRepository) {
        return new SearchPresenter(scheduler, compositeDisposable, trackRepository, historyRepository);
    }
}
