package com.quangnv.freemusic.screen.main;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseActivity;
import com.quangnv.freemusic.data.model.Track;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = "MainActivityTest";
    @Inject
    MainContract.Presenter mPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        DaggerMainComponent.builder()
                .appComponent(((MainApplication)getApplicationContext()).getAppComponent())
                .build()
                .inject(this);
        mPresenter.setView(this);
        mPresenter.getTrack("soundcloud:genres:pop", 0);
    }

    @Override
    public void showTracks(List<Track> tracks) {
        for(Track track: tracks) {
            if (track.isStreamable()) {
                Log.d(TAG, track.getStreamUrl());
            }
        }
    }

    @Override
    public void showError() {
        Log.d(TAG, "showError xxx");
    }
}
