package com.quangnv.freemusic.data.source.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.data.source.GenreDataSource;
import com.quangnv.freemusic.data.source.local.asset.AssetsHelper;
import com.quangnv.freemusic.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by quangnv on 15/10/2018
 */

public class GenreLocalDataSource implements GenreDataSource.Local {

    private AssetsHelper mAssetsHelper;

    @Inject
    public GenreLocalDataSource(AssetsHelper assetsHelper) {
        mAssetsHelper = assetsHelper;
    }

    @Override
    public Observable<List<Genre>> getGenres() {
        return Observable.just(getGenreFromAsset(Constants.GENRE_ASSETS_PATH));
    }

    private List<Genre> getGenreFromAsset(String path) {
        String json = mAssetsHelper.read(path);
        return new Gson().fromJson(json, new TypeToken<List<Genre>>(){}.getType());
    }
}
