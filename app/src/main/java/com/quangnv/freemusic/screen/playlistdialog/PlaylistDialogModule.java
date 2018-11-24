package com.quangnv.freemusic.screen.playlistdialog;

import android.content.Context;

import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.repository.PlaylistRepository;
import com.quangnv.freemusic.data.repository.TrackRepository;
import com.quangnv.freemusic.data.source.local.PlaylistLocalDataSource;
import com.quangnv.freemusic.data.source.local.TrackLocalDataSource;
import com.quangnv.freemusic.data.source.remote.TrackRemoteDataSource;
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
    TrackRepository provideTrackRepository(TrackRemoteDataSource remote,
                                           TrackLocalDataSource local) {
        return new TrackRepository(remote, local);
    }

    @FragmentScope
    @Provides
    PlaylistDialogContract.Presenter providePlaylistDialogPresenter(CompositeDisposable compositeDisposable,
                                                                    BaseSchedulerProvider scheduler,
                                                                    PlaylistRepository playlistRepository,
                                                                    TrackRepository trackRepository) {
        return new PlaylistDialogPresenter(compositeDisposable, scheduler, playlistRepository,
                trackRepository);
    }

    @FragmentScope
    @Provides
    PlaylistDialogAdapter providePlaylistDialogAdapter(@ApplicationContext Context context) {
        return new PlaylistDialogAdapter(context, mItemPlaylistListener);
    }
}
