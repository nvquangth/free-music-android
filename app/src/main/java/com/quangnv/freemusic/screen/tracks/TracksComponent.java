package com.quangnv.freemusic.screen.tracks;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by quangnv on 21/10/2018
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = TracksModule.class)
public interface TracksComponent {
    void inject(TracksFragment fragment);
}
