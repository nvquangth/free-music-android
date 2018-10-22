package com.quangnv.freemusic.screen.search.option;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by quangnv on 22/10/2018
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = SearchOptionModule.class)
public interface SearchOptionComponent {
    void inject(SearchOptionFragment fragment);
}
