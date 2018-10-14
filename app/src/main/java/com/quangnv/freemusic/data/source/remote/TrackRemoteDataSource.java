package com.quangnv.freemusic.data.source.remote;

import com.quangnv.freemusic.data.model.Collection;
import com.quangnv.freemusic.data.model.SearchTrackResponse;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.data.model.GetTrackResponse;
import com.quangnv.freemusic.data.source.TrackDataSource;
import com.quangnv.freemusic.data.source.remote.service.Api;
import com.quangnv.freemusic.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by quangnv on 12/10/2018
 */

public class TrackRemoteDataSource implements TrackDataSource.Remote {

    private Api mApi;

    @Inject
    public TrackRemoteDataSource(Api api) {
        mApi = api;
    }

    @Override
    public Observable<List<Track>> searchTracks(String q, int offset) {
        return mApi.searchTracks(Constants.CLIENT_ID, q, Constants.LOAD_LIMIT, offset)
                .flatMap(new Function<SearchTrackResponse, ObservableSource<List<Track>>>() {
                    @Override
                    public ObservableSource<List<Track>> apply(SearchTrackResponse searchTrackResponse) throws Exception {
                        return Observable.just(searchTrackResponse.getTracks());
                    }
                });
    }

    @Override
    public Observable<List<Track>> getTracksByGenre(String genre, int offset) {
        return mApi.getTracksByGenre(Constants.CLIENT_ID, Constants.KIND, genre,
                Constants.LOAD_LIMIT, offset)
                .flatMap(new Function<GetTrackResponse, ObservableSource<List<Track>>>() {
                    @Override
                    public ObservableSource<List<Track>> apply(GetTrackResponse getTrackResponse) throws Exception {
                        List<Track> tracks = new ArrayList<>();
                        for (Collection collection: getTrackResponse.getCollections()) {
                            tracks.add(collection.getTrack());
                        }
                        return Observable.just(tracks);
                    }
                });
    }
}
