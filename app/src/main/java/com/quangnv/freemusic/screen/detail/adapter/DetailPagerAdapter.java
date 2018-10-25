package com.quangnv.freemusic.screen.detail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by quangnv on 22/10/2018
 */

public class DetailPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 2;
    private Fragment mNowPlayingFragment;
    private Fragment mPlayFragment;

    public DetailPagerAdapter(FragmentManager fm, Fragment nowPlayingFragment,
                              Fragment playFragment) {
        super(fm);
        mNowPlayingFragment = nowPlayingFragment;
        mPlayFragment = playFragment;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            default:
            case TabType.NOW_PLAYING:
                return mNowPlayingFragment;
            case TabType.PLAYING:
                return mPlayFragment;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
