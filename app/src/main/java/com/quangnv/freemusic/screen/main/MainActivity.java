package com.quangnv.freemusic.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    @Inject
    MainContract.Presenter mPresenter;

    private BottomNavigationView mBottomNavigationView;
    private View mViewSearch;
    private View mViewMiniPlayer;
    private AppCompatImageView mImageTrackArtist;
    private AppCompatTextView mTextTrackTitle;
    private AppCompatTextView mTextTrackArtist;
    private AppCompatImageButton mButtonPrev;
    private AppCompatImageButton mButtonPlayPause;
    private AppCompatImageButton mButtonNext;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        initView();
        registerListener();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                return true;
            case R.id.nav_my_music:
                return true;
            case R.id.nav_more:
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_search:
                break;
            case R.id.mini_player:
                break;
            case R.id.button_prev:
                break;
            case R.id.button_play_pause:
                break;
            case R.id.button_next:
                break;
        }
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.navigation_bottom_view);
        mViewSearch = findViewById(R.id.view_search);
        mViewMiniPlayer = findViewById(R.id.mini_player);
        mImageTrackArtist = findViewById(R.id.image_track_artist);
        mTextTrackTitle = findViewById(R.id.text_track_title);
        mTextTrackArtist = findViewById(R.id.text_track_artist);
        mButtonPrev = findViewById(R.id.button_prev);
        mButtonPlayPause = findViewById(R.id.button_play_pause);
        mButtonNext = findViewById(R.id.button_next);
    }

    private void registerListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mViewSearch.setOnClickListener(this);
        mViewMiniPlayer.setOnClickListener(this);
        mButtonPrev.setOnClickListener(this);
        mButtonPlayPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
    }
}
