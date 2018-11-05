package com.quangnv.freemusic.screen.detail;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.download.TrackDownloadManager;

/**
 * Created by quangnv on 01/11/2018
 */

public interface DetailContract {
    interface View {

        void showHandleFavoriteError();

        void showTrackAddedToFavorite();

        void showTrackRemovedFromFavorite();

        void showTrackDownloaded();

        void showTrackDownloadError();
    }

    interface Presenter extends BasePresenter<View> {
        void addOrRemoveFavorite(Track track);

        void checkFavoriteTrack(Track track);

        void saveTrack(Track track);

        void setTrackDownloadManager(TrackDownloadManager trackDownloadManager);
    }
}
