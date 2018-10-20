package com.quangnv.freemusic.screen;

import com.quangnv.freemusic.data.model.Track;

import java.util.List;

/**
 * Created by quangnv on 20/10/2018
 */

public interface OnItemTrackListener {
    void onItemTrackClick(List<Track> tracks, int position);
}
