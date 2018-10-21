package com.quangnv.freemusic.screen.genre;

import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
import com.quangnv.freemusic.util.dagger.FragmentScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 21/10/2018
 */

@Module
public class GenreModule {

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
    public GenreContract.Presenter provideGenrePresenter(BaseSchedulerProvider scheduler,
                                                         CompositeDisposable compositeDisposable,
                                                         TrackRepository trackRepository) {
        return new GenrePresenter(scheduler, compositeDisposable, trackRepository);
    }
}
