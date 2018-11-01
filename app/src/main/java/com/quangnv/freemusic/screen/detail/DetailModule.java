package com.quangnv.freemusic.screen.detail;

import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
import com.quangnv.freemusic.util.dagger.ActivityScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 01/11/2018
 */

@Module
public class DetailModule {

    @ActivityScope
    @Provides
    TrackRepository provideTrackRepository(TrackRemoteDataSource remote,
                                           TrackLocalDataSource local) {
        return new TrackRepository(remote, local);
    }

    @ActivityScope
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ActivityScope
    @Provides
    DetailContract.Presenter provideDetailPresenter(BaseSchedulerProvider scheduler,
                                                    CompositeDisposable compositeDisposable,
                                                    TrackRepository trackRepository) {
        return new DetailPresenter(scheduler, compositeDisposable, trackRepository);
    }
}
