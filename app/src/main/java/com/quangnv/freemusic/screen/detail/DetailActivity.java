package com.quangnv.freemusic.screen.detail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseActivity;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.mediaplayer.MediaPlayerListener;
import com.quangnv.freemusic.mediaplayer.MediaPlayerLoopType;
import com.quangnv.freemusic.mediaplayer.MediaPlayerPlayType;
import com.quangnv.freemusic.mediaplayer.MediaPlayerShuffleType;
import com.quangnv.freemusic.service.ServiceManager;
import com.quangnv.freemusic.service.TrackService;
import com.quangnv.freemusic.util.TimeUtils;
import com.quangnv.freemusic.util.navigator.Navigator;

public class DetailActivity extends BaseActivity implements View.OnClickListener,
        ServiceConnection,
        MediaPlayerListener.OnTrackListener,
        MediaPlayerListener.OnPlayingListener,
        MediaPlayerListener.OnCurrentTimeListener,
        MediaPlayerListener.OnTotalTimeListener,
        MediaPlayerListener.OnLoopingListener,
        MediaPlayerListener.OnShufflingListener, SeekBar.OnSeekBarChangeListener {

    /**
     * The time to update track's current time
     */
    private static final int TIME_DELAY = 500;

    private Navigator mNavigator;

    private TrackService mTrackService;
    private ServiceManager mServiceManager;
    private ServiceConnection mConnection;
    private Intent mIntent;
    private boolean mBound;
    private boolean mSeekbarTracking;
    private Handler mHandler;
    private Runnable mRunnableTimer;

    private Toolbar mToolbar;
    private ImageView mImageTrack;
    private ImageButton mButtonShuffle;
    private ImageButton mButtonLoop;
    private ImageButton mButtonPrev;
    private ImageButton mButtonPlayPause;
    private ImageButton mButtonNext;
    private TextView mTextCurrentTime;
    private TextView mTextTotalTime;
    private SeekBar mSeekBarTime;
    private ProgressBar mProgressBarLoadingPlayer;

    @Override
    protected void onStart() {
        mServiceManager.bindService();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mTrackService.removeTrackListener(this);
        mTrackService.removePlayingListener(this);
        mTrackService.removeCurrentTimeListener(this);
        mTrackService.removeTotalTimeListener(this);
        mTrackService.removeShufflingListener(this);
        mTrackService.removeLoopingListener(this);
        mServiceManager.unbindService();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        if (mTrackService.getPlayMediaPlayer() != MediaPlayerPlayType.PLAY) {
//            mServiceManager.stopService();
//        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        initView();
        registerListener();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_shuffle:
                mTrackService.changeShuffle();
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
            case R.id.button_loop:
                mTrackService.changeLoop();
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mTrackService = ((TrackService.TrackBinder) iBinder).getService();
        mTrackService.addTrackListener(this);
        mTrackService.addPlayingListener(this);
        mTrackService.addCurrentTimeListener(this);
        mTrackService.addTotalTimeListener(this);
        mTrackService.addLoopingListener(this);
        mTrackService.addShufflingListener(this);
        updateTime();
        mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mSeekbarTracking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mSeekbarTracking = false;
        mTrackService.seekTo(seekBar.getProgress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbar.inflateMenu(R.menu.menu_timer);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavigator.finishActivity();
                break;
            case R.id.nav_timer:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrackChanged(Track track) {
        mToolbar.setTitle(track.getTitle());
        mToolbar.setSubtitle(track.getPublisher().getArtist());
        Glide.with(this)
                .load(track.getArtWorkUrl())
                .apply(new RequestOptions().error(R.drawable.image_default_border))
                .apply(RequestOptions.circleCropTransform())
                .into(mImageTrack);
    }

    @Override
    public void onPlayChanged(int playType) {
        switch (playType){
            case MediaPlayerPlayType.WAIT:
                mProgressBarLoadingPlayer.setVisibility(View.VISIBLE);
                mButtonPlayPause.setVisibility(View.INVISIBLE);
                break;
            case MediaPlayerPlayType.PLAY:
                mProgressBarLoadingPlayer.setVisibility(View.INVISIBLE);
                mButtonPlayPause.setVisibility(View.VISIBLE);
                setImageResourceButtonPlayPause(R.drawable.ic_pause_circle_outline_black_36dp);
                break;
            case MediaPlayerPlayType.PAUSE:
                mProgressBarLoadingPlayer.setVisibility(View.INVISIBLE);
                mButtonPlayPause.setVisibility(View.VISIBLE);
                setImageResourceButtonPlayPause(R.drawable.ic_play_circle_outline_black_36dp);
                break;
        }
    }

    @Override
    public void onCurrentTimeChanged(int currentTime) {
        mTextCurrentTime.setText(TimeUtils.convertMilisecondToFormatTime(currentTime));
        if (!mSeekbarTracking) {
            mSeekBarTime.setProgress(currentTime);
        }
    }

    @Override
    public void onTotalTimeChanged(int totalTime) {
        mTextTotalTime.setText(TimeUtils.convertMilisecondToFormatTime(totalTime));
        mSeekBarTime.setMax(totalTime);
    }

    @Override
    public void onLoopChanged(int loopType) {
        switch (loopType) {
            case MediaPlayerLoopType.NONE:
                setImageResourceButtonLoop(R.drawable.ic_repeat_gray_24dp);
                break;
            case MediaPlayerLoopType.ONE:
                setImageResourceButtonLoop(R.drawable.ic_repeat_one_black_24dp);
                break;
            case MediaPlayerLoopType.ALL:
                setImageResourceButtonLoop(R.drawable.ic_repeat_black_24dp);
                break;
        }
    }

    @Override
    public void onShuffleChanged(int shuffleType) {
        switch (shuffleType) {
            case MediaPlayerShuffleType.OFF:
                setImageResourceButtonShuffle(R.drawable.ic_shuffle_gray_24dp);
                break;
            case MediaPlayerShuffleType.ON:
                setImageResourceButtonShuffle(R.drawable.ic_shuffle_black_24dp);
                break;
        }
    }

    public static Intent getDetailActivityIntent(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        return intent;
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_down_white_24dp);
        mImageTrack = findViewById(R.id.image_track);
        mButtonShuffle = findViewById(R.id.button_shuffle);
        mButtonPrev = findViewById(R.id.button_prev);
        mButtonPlayPause = findViewById(R.id.button_play_pause);
        mButtonNext = findViewById(R.id.button_next);
        mButtonLoop = findViewById(R.id.button_loop);
        mTextCurrentTime = findViewById(R.id.text_current_time);
        mTextTotalTime = findViewById(R.id.text_total_time);
        mSeekBarTime = findViewById(R.id.seek_bar_time);
        mProgressBarLoadingPlayer = findViewById(R.id.progress_bar_loading_player);
    }

    private void registerListener() {
        mButtonShuffle.setOnClickListener(this);
        mButtonPrev.setOnClickListener(this);
        mButtonPlayPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonLoop.setOnClickListener(this);
        mSeekBarTime.setOnSeekBarChangeListener(this);
    }

    private void initData() {
        mNavigator = new Navigator(this);
        mConnection = this;
        mIntent = new Intent(this, TrackService.class);
        mServiceManager = new ServiceManager(getApplicationContext(), mIntent, mConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void updateTime() {
        mHandler = new Handler();
        mRunnableTimer = new Runnable() {
            @Override
            public void run() {
                mTrackService.updateTime();
                mHandler.postDelayed(this, TIME_DELAY);
            }
        };
        mHandler.post(mRunnableTimer);
    }

    private void setImageResourceButtonShuffle(int resId) {
        mButtonShuffle.setImageResource(resId);
    }

    private void setImageResourceButtonLoop(int resId) {
        mButtonLoop.setImageResource(resId);
    }

    private void setImageResourceButtonPlayPause(int resId) {
        mButtonPlayPause.setImageResource(resId);
    }
}
