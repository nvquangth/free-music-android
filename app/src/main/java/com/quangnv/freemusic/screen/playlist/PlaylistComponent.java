package com.quangnv.freemusic.screen.playlist;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by quangnv on 11/11/2018
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = PlaylistModule.class)
public interface PlaylistComponent {

    void inject(PlaylistFragment fragment);
}
