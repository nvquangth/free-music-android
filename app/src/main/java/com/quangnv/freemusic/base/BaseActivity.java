package com.quangnv.freemusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by quangnv on 11/10/2018
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        initComponents(savedInstanceState);
    }

    protected abstract int getLayoutResource();

    protected abstract void initComponents(Bundle savedInstanceState);
}
