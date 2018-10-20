package com.quangnv.freemusic.mediaplayer;

/**
 * Created by quangnv on 20/10/2018
 */

public interface BaseMediaPlayer {

    /**
     * Init media player
     */
    void init();

    /**
     * start media player
     */
    void start();

    /**
     * pause media player
     */
    void pause();

    /**
     * reset media player
     */
    void reset();

    /**
     * release media player
     */
    void release();

    /**
     * stop media player
     */
    void stop();

    /**
     * play next track
     */
    void next();

    /**
     * play prev track
     */
    void previous();

    /**
     * seek to timer of track
     * @param msec
     */
    void seekTo(int msec);

    /**
     * get total time of track
     * @return
     */
    int getDuration();

    /**
     * get current time of track
     * @return
     */
    int getCurrentPosition();
}
