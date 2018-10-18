package com.quangnv.freemusic.screen.toptrack;

import com.quangnv.freemusic.data.model.Track;

/**
 * Created by quangnv on 15/10/2018
 */

public interface TopTrackContract {
    interface View {
        void showTrack(Track track);
    }
}
