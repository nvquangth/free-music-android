package com.quangnv.freemusic.screen.main;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by quangnv on 13/10/2018
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
