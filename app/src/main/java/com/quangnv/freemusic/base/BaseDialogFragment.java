package com.quangnv.freemusic.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by quangnv on 11/10/2018
 */

public abstract class BaseDialogFragment extends DialogFragment {

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

    protected abstract int getLayoutResource();

    protected abstract void initComponentsOnCreate(Bundle savedInstanceState);

    protected abstract void initComponentsOnCreateView(View view, Bundle savedInstanceState);
}
