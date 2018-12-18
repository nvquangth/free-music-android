package com.quangnv.freemusic.screen.splash;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.screen.main.MainActivity;
import com.quangnv.freemusic.util.Constants;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMain();
            }
        }, Constants.TIME_DELAY_OPEN_HOME_SCREEN);
    }

    private void gotoMain() {
        startActivity(MainActivity.getIntent(this));
        finish();
    }
}
