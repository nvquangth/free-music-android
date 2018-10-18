package com.quangnv.freemusic.screen.toptrack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.DrawableUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by quangnv on 15/10/2018
 */

public class TopTrackFragment extends BaseFragment {

    private static final int RADIUS_IMAGE = 15;

    private Track mTrack;

    private ImageView mBigImageTrack;
    private ImageView mSmallImageTrack;
    private TextView mTextTrackTitle;
    private TextView mTextTrackArtist;

    public static TopTrackFragment newInstance(Track track) {
        TopTrackFragment fragment = new TopTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ARGUMENT_TRACK, track);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TopTrackFragment() {
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) return;
        mTrack = getArguments().getParcelable(Constants.ARGUMENT_TRACK);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        bindData(mTrack);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_top_track;
    }

    private void initView(View view) {
        mBigImageTrack = view.findViewById(R.id.image_big_track);
        mSmallImageTrack = view.findViewById(R.id.image_small_track);
        mTextTrackTitle = view.findViewById(R.id.text_track_title);
        mTextTrackArtist = view.findViewById(R.id.text_track_artist);
    }

    private void bindData(Track track) {
        Glide.with(mBigImageTrack.getContext())
                .load(DrawableUtils
                        .getResourceId(mBigImageTrack.getContext(), track.getArtWorkUrl()))
                .into(mBigImageTrack);
        Glide.with(mSmallImageTrack.getContext())
                .load(DrawableUtils
                        .getResourceId(mBigImageTrack.getContext(), track.getArtWorkUrl()))
                .apply(RequestOptions
                        .bitmapTransform(new RoundedCornersTransformation(RADIUS_IMAGE, 0)))
                .into(mSmallImageTrack);
        mTextTrackTitle.setText(track.getTitle());
        mTextTrackArtist.setText(track.getPublisher().getArtist());
    }

}
