package com.quangnv.freemusic.screen.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseActivity;
import com.quangnv.freemusic.data.model.Playlist;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.mediaplayer.MediaPlayerListener;
import com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType;
import com.quangnv.freemusic.screen.OnItemTrackListener;
import com.quangnv.freemusic.screen.detail.DetailActivity;
import com.quangnv.freemusic.screen.home.HomeFragment;
import com.quangnv.freemusic.screen.mymusic.MyMusicFragment;
import com.quangnv.freemusic.service.ServiceManager;
import com.quangnv.freemusic.service.TrackService;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener,
        View.OnClickListener,
        ServiceConnection,
        OnItemTrackListener,
        MediaPlayerListener.OnTrackListener,
        MediaPlayerListener.OnPlayingListener,
        MyMusicFragment.OnPlaylistListener {

    private static final int TIME_DELAY_OPEN_TRACK_DETAIL = 1000;

    @Inject
    MainContract.Presenter mPresenter;

    private Navigator mNavigator;

    private TrackService mTrackService;
    private ServiceManager mServiceManager;
    private ServiceConnection mConnection;
    private Intent mIntent;
    private boolean mBound;

    private BottomNavigationView mBottomNavigationView;
    private View mViewMiniPlayer;
    private AppCompatImageView mImageTrackArtist;
    private AppCompatTextView mTextTrackTitle;
    private AppCompatTextView mTextTrackArtist;
    private AppCompatImageButton mButtonPrev;
    private AppCompatImageButton mButtonPlayPause;
    private AppCompatImageButton mButtonNext;
    private ProgressBar mProgressBarLoadingPlayer;

    @Override
    protected void onStart() {
        mServiceManager.bindService();
        mServiceManager.startService();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mTrackService.removeTrackListener(this);
        mTrackService.removePlayingListener(this);
        mServiceManager.unbindService();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mTrackService.getPlayMediaPlayer() != MediaPlayerPlayType.PLAY) {
            mServiceManager.stopService();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        initView();
        registerListener();
        initData();

        mNavigator.addFragmentToBackStack(R.id.frame_container, HomeFragment.newInstance(),
                false, NavigateAnim.FADED, null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                mNavigator.replaceFragment(R.id.frame_container, HomeFragment.newInstance(),
                        false, NavigateAnim.FADED, null);
                return true;
            case R.id.nav_my_music:
                mNavigator.replaceFragment(R.id.frame_container, MyMusicFragment.newInstance(),
                        false, NavigateAnim.FADED, Constants.FLAG_MY_MUSIC_FRAGMENT);
                return true;
            case R.id.nav_more:
                return true;
        }
        return false;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_my_music:
                break;
            case R.id.nav_more:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mini_player:
                mNavigator.startActivity(DetailActivity.class);
                break;
            case R.id.button_prev:
                mTrackService.previous();
                break;
            case R.id.button_play_pause:
                mTrackService.changePlayPauseStatus();
                break;
            case R.id.button_next:
                mTrackService.next();
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mTrackService = ((TrackService.TrackBinder) iBinder).getService();
        mTrackService.addTrackListener(this);
        mTrackService.addPlayingListener(this);
        mBound = true;
        if (mTrackService.getPlayMediaPlayer() != MediaPlayerPlayType.WAIT) {
            mViewMiniPlayer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }

    @Override
    public void onItemTrackClick(List<Track> tracks, int position) {
        mTrackService.setTracks(tracks);
        mTrackService.play(position);
        if (mViewMiniPlayer.getVisibility() == View.GONE) {
            mViewMiniPlayer.setVisibility(View.VISIBLE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNavigator.startActivity(DetailActivity.class);
            }
        }, TIME_DELAY_OPEN_TRACK_DETAIL);
    }

    @Override
    public void onTrackChanged(Track track) {
        Glide.with(mImageTrackArtist.getContext())
                .load(track.getArtWorkUrl())
                .apply(RequestOptions
                        .bitmapTransform(new CircleCrop())
                        .error(R.drawable.image_default_circle))
                .into(mImageTrackArtist);
        if (track.getPublisher() != null) {
            mTextTrackArtist.setText(track.getPublisher().getArtist());
        }
        mTextTrackTitle.setText(track.getTitle());
    }

    @Override
    public void onPlayChanged(@MediaPlayerPlayType int playType) {
        switch (playType) {
            case MediaPlayerPlayType.PLAY:
                mProgressBarLoadingPlayer.setVisibility(View.INVISIBLE);
                mButtonPlayPause.setVisibility(View.VISIBLE);
                mButtonPlayPause.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                break;
            case MediaPlayerPlayType.PAUSE:
                mProgressBarLoadingPlayer.setVisibility(View.INVISIBLE);
                mButtonPlayPause.setVisibility(View.VISIBLE);
                mButtonPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_36dp);
                break;
            case MediaPlayerPlayType.WAIT:
                mProgressBarLoadingPlayer.setVisibility(View.VISIBLE);
                mButtonPlayPause.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.navigation_bottom_view);
        mViewMiniPlayer = findViewById(R.id.mini_player);
        mImageTrackArtist = findViewById(R.id.image_track_artist);
        mTextTrackTitle = findViewById(R.id.text_track_title);
        mTextTrackArtist = findViewById(R.id.text_track_artist);
        mButtonPrev = findViewById(R.id.button_prev);
        mButtonPlayPause = findViewById(R.id.button_play_pause);
        mButtonNext = findViewById(R.id.button_next);
        mProgressBarLoadingPlayer = findViewById(R.id.progress_bar_loading_player);
    }

    private void registerListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemReselectedListener(this);
        mViewMiniPlayer.setOnClickListener(this);
        mButtonPrev.setOnClickListener(this);
        mButtonPlayPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
    }

    private void initData() {
        mNavigator = new Navigator(this);
        mConnection = this;
        mIntent = new Intent(this, TrackService.class);
        mServiceManager = new ServiceManager(getApplicationContext(), mIntent, mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPlaylistAdded(Playlist playlist) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyMusicFragment myMusicFragment =
                (MyMusicFragment) fragmentManager.findFragmentByTag(Constants.FLAG_MY_MUSIC_FRAGMENT);
        if (myMusicFragment != null) {
            myMusicFragment.addPlaylist(playlist);
        }
    }
}
