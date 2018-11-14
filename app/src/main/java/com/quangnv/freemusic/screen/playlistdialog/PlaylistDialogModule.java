package com.quangnv.freemusic.screen.playlistdialog;

import android.content.Context;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.data.source.local.PlaylistLocalDataSource;
import com.quangnv.freemusic.screen.playlistdialog.adapter.PlaylistDialogAdapter;
import com.quangnv.freemusic.util.dagger.ApplicationContext;
import com.quangnv.freemusic.util.dagger.FragmentScope;
import com.quangnv.freemusic.util.rx.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by quangnv on 13/11/2018
 */

@Module
public class PlaylistDialogModule {

    private BaseRecyclerViewAdapter.ItemRecyclerViewListener<Playlist> mItemPlaylistListener;

    public PlaylistDialogModule(BaseRecyclerViewAdapter.ItemRecyclerViewListener<Playlist>
                                        itemPlaylistListener) {
        mItemPlaylistListener = itemPlaylistListener;
    }

    @FragmentScope
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @FragmentScope
    @Provides
    PlaylistRepository providePlaylistRepository(PlaylistLocalDataSource local) {
        return new PlaylistRepository(local);
    }

    @FragmentScope
    @Provides
    PlaylistDialogContract.Presenter providePlaylistDialogPresenter(CompositeDisposable compositeDisposable,
                                                                    BaseSchedulerProvider scheduler,
                                                                    PlaylistRepository playlistRepository) {
        return new PlaylistDialogPresenter(compositeDisposable, scheduler, playlistRepository);
    }

    @FragmentScope
    @Provides
    PlaylistDialogAdapter providePlaylistDialogAdapter(@ApplicationContext Context context) {
        return new PlaylistDialogAdapter(context, mItemPlaylistListener);
    }
}
