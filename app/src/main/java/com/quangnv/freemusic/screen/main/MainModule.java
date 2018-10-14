package com.quangnv.freemusic.screen.main;

import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
import com.quangnv.freemusic.util.dagger.ActivityScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 13/10/2018
 */

@Module
public class MainModule {

    @ActivityScope
    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ActivityScope
    @Provides
    public TrackRepository provideTrackRepository(TrackRemoteDataSource remoteDataSource) {
        return new TrackRepository(remoteDataSource);
    }

    @ActivityScope
    @Provides
    public MainContract.Presenter providePresenter(CompositeDisposable compositeDisposable,
                                                   BaseSchedulerProvider scheduler,
                                                   TrackRepository repository) {
        return new MainPresenter(compositeDisposable, scheduler, repository);
    }
}
