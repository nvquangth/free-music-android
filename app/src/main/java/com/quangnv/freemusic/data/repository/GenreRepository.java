package com.quangnv.freemusic.data.repository;

import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.source.GenreDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by quangnv on 15/10/2018
 */

public class GenreRepository implements GenreDataSource.Local {

    private GenreDataSource.Local mLocal;

    public GenreRepository(GenreDataSource.Local local) {
        mLocal = local;
    }

    @Override
    public Observable<List<Genre>> getGenres() {
        return mLocal.getGenres();
    }
}
