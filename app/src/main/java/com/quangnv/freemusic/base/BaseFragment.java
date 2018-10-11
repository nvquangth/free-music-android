package com.quangnv.freemusic.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by quangnv on 11/10/2018
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponentsOnCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        initComponentsOnCreateView(view, savedInstanceState);
        return view;
    }

    protected abstract void initComponentsOnCreate(@Nullable Bundle savedInstanceState);

    protected abstract void initComponentsOnCreateView(View view,
                                                       @Nullable Bundle savedInstanceState);

    @LayoutRes
    protected abstract int getLayoutResource();
}
