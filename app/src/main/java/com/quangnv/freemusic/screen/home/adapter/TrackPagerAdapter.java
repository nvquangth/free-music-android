package com.quangnv.freemusic.screen.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.toptrack.TopTrackFragment;

import java.util.List;

/**
 * Created by quangnv on 15/10/2018
 */

public class TrackPagerAdapter extends FragmentPagerAdapter {

    private List<Track> mTracks;

    public TrackPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return TopTrackFragment.newInstance(mTracks.get(i));
    }

    @Override
    public int getCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
        notifyDataSetChanged();
    }
}
