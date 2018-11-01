package com.quangnv.freemusic.screen.detail;

import com.quangnv.freemusic.base.BasePresenter;
import com.quangnv.freemusic.data.model.Track;

/**
 * Created by quangnv on 01/11/2018
 */

public interface DetailContract {
    interface View {

        void showHandleFavoriteError();

        void showTrackAddedToFavorite();

        void showTrackRemovedFromFavorite();
    }

    interface Presenter extends BasePresenter<View> {
        void addOrRemoveFavorite(Track track);

        void checkFavoriteTrack(Track track);
    }
}
