package com.quangnv.freemusic.screen.playlist;

import android.content.Context;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.data.source.local.PlaylistLocalDataSource;
import com.quangnv.freemusic.screen.playlist.adapter.PlaylistAdapter;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.dagger.FragmentScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 11/11/2018
 */

@Module
public class PlaylistModule {

    private BaseRecyclerViewAdapter.ItemRecyclerViewListener<Playlist> mItemPlaylistListener;

    public PlaylistModule(BaseRecyclerViewAdapter.ItemRecyclerViewListener<Playlist> itemPlaylistListener) {
        mItemPlaylistListener = itemPlaylistListener;
    }

    @Provides
    @FragmentScope
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @FragmentScope
    PlaylistRepository providePlaylistRepository(PlaylistLocalDataSource local) {
        return new PlaylistRepository(local);
    }

    @Provides
    @FragmentScope
    PlaylistContract.Presenter providePlaylistPresenter(CompositeDisposable compositeDisposable,
                                                        BaseSchedulerProvider scheduler,
                                                        PlaylistRepository repository) {
        return new PlaylistPresenter(compositeDisposable, scheduler, repository);
    }

    @Provides
    @FragmentScope
    PlaylistAdapter providePlaylistAdapter(@ApplicationContext Context context) {
        return new PlaylistAdapter(context, mItemPlaylistListener);
    }
}
