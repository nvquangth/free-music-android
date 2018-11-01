package com.quangnv.freemusic.screen.detail;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by quangnv on 01/11/2018
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailActivity activity);
}
