package com.quangnv.freemusic.mediaplayer;

/**
 * Created by quangnv on 20/10/2018
 */

public class MediaPlayerSetting {
    private int mLoop;
    private int mShuffle;
    private int mPlay;

    @MediaPlayerLoopType
    public int getLoop() {
        return mLoop;
    }

    public void setLoop(@MediaPlayerLoopType int loop) {
        mLoop = loop;
    }

    @MediaPlayerShuffleType
    public int getShuffle() {
        return mShuffle;
    }

    public void setShuffle(@MediaPlayerShuffleType int shuffle) {
        mShuffle = shuffle;
    }

    @MediaPlayerPlayType
    public int getPlay() {
        return mPlay;
    }

    public void setPlay(@MediaPlayerPlayType int play) {
        mPlay = play;
    }
}
