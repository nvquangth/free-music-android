package com.quangnv.freemusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Created by quangnv on 11/10/2018
 */

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponentsOnCreate(savedInstanceState);
    }

    protected abstract void initComponentsOnCreate(Bundle savedInstanceState);
}
