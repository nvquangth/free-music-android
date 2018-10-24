package com.quangnv.freemusic.screen.play;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.util.Constants;

/**
 * Created by quangnv on 23/10/2018
 */

public class PlayFragment extends BaseFragment implements View.OnClickListener {

    private Track mTrack;

    private ImageView mImageTrack;
    private ImageButton mButtonFavorite;
    private ImageButton mButtonDownload;
    private ImageButton mButtonMore;

    public static PlayFragment newInstance(Track track) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.ARGUMENT_TRACK, track);

        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_play;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_favorite:
                break;
            case R.id.button_download:
                break;
            case R.id.button_more:
                break;
        }
    }

    private void initView(View view) {
        mImageTrack = view.findViewById(R.id.image_track);
        mButtonFavorite = view.findViewById(R.id.button_favorite);
        mButtonDownload = view.findViewById(R.id.button_download);
        mButtonMore = view.findViewById(R.id.button_more);
    }

    private void registerListener() {
        mButtonFavorite.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonMore.setOnClickListener(this);
    }
}
