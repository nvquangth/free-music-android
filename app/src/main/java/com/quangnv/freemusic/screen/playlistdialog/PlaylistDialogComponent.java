package com.quangnv.freemusic.screen.playlistdialog;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by quangnv on 13/11/2018
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = PlaylistDialogModule.class)
public interface PlaylistDialogComponent {

    void inject(PlaylistDialogFragment dialogFragment);
}
