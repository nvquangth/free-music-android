package com.quangnv.freemusic.screen.home;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.repository.GenreRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.local.GenreLocalDataSource;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
import com.quangnv.freemusic.screen.home.adapter.GenreAdapter;
import com.quangnv.freemusic.screen.home.adapter.TrackPagerAdapter;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.dagger.FragmentScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 15/10/2018
 */

@Module
public class HomeModule {

    private FragmentManager mFragmentManager;
    private BaseRecyclerViewAdapter.ItemRecyclerViewListener<Genre> mGenreItemRecyclerViewListener;

    public HomeModule(FragmentManager fragmentManager,
                      BaseRecyclerViewAdapter.ItemRecyclerViewListener<Genre> itemRecyclerViewListener) {
        mFragmentManager = fragmentManager;
        mGenreItemRecyclerViewListener = itemRecyclerViewListener;
    }

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
    public GenreRepository provideGenreRepository(GenreLocalDataSource local) {
        return new GenreRepository(local);
    }

    @FragmentScope
    @Provides
    public TrackPagerAdapter provideTrackPagerAdapter() {
        return new TrackPagerAdapter(mFragmentManager);
    }

    @FragmentScope
    @Provides
    public GenreAdapter provideGenreAdapter(@ApplicationContext Context context) {
        return new GenreAdapter(context, mGenreItemRecyclerViewListener);
    }

    @FragmentScope
    @Provides
    public HomeContract.Presenter provideHomePresenter(BaseSchedulerProvider scheduler,
                                                       CompositeDisposable compositeDisposable,
                                                       TrackRepository trackRepository,
                                                       GenreRepository genreRepository) {
        return new HomePresenter(scheduler,
                compositeDisposable,
                trackRepository,
                genreRepository);
    }
}
