package com.quangnv.freemusic.screen.search;

import com.quangnv.freemusic.AppComponent;
import com.quangnv.freemusic.util.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by quangnv on 22/10/2018
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = SearchModule.class)
public interface SearchComponent {
    void inject(SearchFragment fragment);
}
