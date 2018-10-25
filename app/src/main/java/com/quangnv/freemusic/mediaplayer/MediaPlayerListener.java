package com.quangnv.freemusic.mediaplayer;

import com.quangnv.freemusic.data.model.Track;

/**
 * Created by quangnv on 20/10/2018
 */

public interface MediaPlayerListener {

    /**
     * setting
     */
    interface OnLoopingListener {
        void onLoopChanged(@MediaPlayerLoopType int loopType);
    }

    interface OnShufflingListener {
        void onShuffleChanged(@MediaPlayerShuffleType int shuffleType);
    }

    /**
     * control
     */
    interface OnPlayingListener {
        void onPlayChanged(@MediaPlayerPlayType int playType);
    }

    /**
     * timer
     */
    interface OnCurrentTimeListener {
        void onCurrentTimeChanged(int currentTime);
    }

    interface OnTotalTimeListener {
        void onTotalTimeChanged(int totalTime);
    }

    /**
     * description
     */
    interface OnTrackListener {
        void onTrackChanged(Track track);
    }

    /**
     * error
     */
    interface OnErrorListener {
        void onTrackError(Track track);
    }
}
