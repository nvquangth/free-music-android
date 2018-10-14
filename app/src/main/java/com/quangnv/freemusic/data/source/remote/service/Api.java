package com.quangnv.freemusic.data.source.remote.service;

import com.quangnv.freemusic.data.model.GetTrackResponse;
import com.quangnv.freemusic.data.model.SearchTrackResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by quangnv on 11/10/2018
 */

public interface Api {

    @GET("/charts")
    Observable<GetTrackResponse> getTracksByGenre(@Query("client_id") String clientId,
                                                  @Query("kind") String kind,
                                                  @Query("genre") String genre,
                                                  @Query("limit") int limit,
                                                  @Query("offset") int offset);

    @GET("/search/tracks")
    Observable<SearchTrackResponse> searchTracks(@Query("client_id") String clientId,
                                                 @Query("q") String q,
                                                 @Query("limit") int limit,
                                                 @Query("offset") int offset);
}
