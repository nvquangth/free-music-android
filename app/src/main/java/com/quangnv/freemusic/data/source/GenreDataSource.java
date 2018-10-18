package com.quangnv.freemusic.data.source;

import com.quangnv.freemusic.data.model.Genre;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by quangnv on 15/10/2018
 */

public interface GenreDataSource {
    interface Local extends GenreDataSource {
        Observable<List<Genre>> getGenres();
    }
}
