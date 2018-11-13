package com.quangnv.freemusic.screen.mymusic;

import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.local.PlaylistLocalDataSource;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
import com.quangnv.freemusic.util.dagger.FragmentScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 30/10/2018
 */

@Module
public class MyMusicModule {

    @FragmentScope
    @Provides
    CompositeDisposable provideCompositeDiaposable() {
        return new CompositeDisposable();
    }

    @FragmentScope
    @Provides
    TrackRepository provideTrackRepository(TrackRemoteDataSource remote,
                                           TrackLocalDataSource local) {
        return new TrackRepository(remote, local);
    }

    @FragmentScope
    @Provides
    PlaylistRepository providePlaylistRepository(PlaylistLocalDataSource local) {
        return new PlaylistRepository(local);
    }

    @FragmentScope
    @Provides
    MyMusicContract.Presenter provideMyMusicPresenter(BaseSchedulerProvider scheduler,
                                                      CompositeDisposable compositeDisposable,
                                                      TrackRepository trackRepository,
                                                      PlaylistRepository playlistRepository) {
        return new MyMusicPresenter(scheduler, compositeDisposable, trackRepository,
                playlistRepository);
    }
}
