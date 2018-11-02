package com.quangnv.freemusic.screen.mymusic;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by quangnv on 01/11/2018
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = MyMusicModule.class)
public interface MyMusicComponent {
    void inject(MyMusicFragment fragment);
}
