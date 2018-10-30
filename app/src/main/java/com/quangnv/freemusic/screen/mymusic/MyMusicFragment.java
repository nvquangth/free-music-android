package com.quangnv.freemusic.screen.mymusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;

/**
 * Created by quangnv on 29/10/2018
 */

public class MyMusicFragment extends BaseFragment {
    
    public MyMusicFragment() {
    }

    public static MyMusicFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MyMusicFragment fragment = new MyMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_music;
    }
}
