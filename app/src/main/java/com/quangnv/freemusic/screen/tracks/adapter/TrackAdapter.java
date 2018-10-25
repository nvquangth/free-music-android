package com.quangnv.freemusic.screen.tracks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.OnViewTrackDetailListener;
import com.quangnv.freemusic.util.Constants;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by quangnv on 21/10/2018
 */

public class TrackAdapter extends BaseRecyclerViewAdapter<Track, TrackAdapter.TrackViewHolder> {

    private OnViewTrackDetailListener mOnViewTrackDetailListener;

    public TrackAdapter(Context context,
                        ItemRecyclerViewListener<Track> itemRecyclerViewListener,
                        OnViewTrackDetailListener onViewTrackDetailListener) {
        super(context);
        mItemRecyclerViewListener = itemRecyclerViewListener;
        mOnViewTrackDetailListener = onViewTrackDetailListener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_track, viewGroup, false);
        return new TrackViewHolder(view, mItemRecyclerViewListener, mOnViewTrackDetailListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        holder.binData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Track mTrack;
        private ItemRecyclerViewListener<Track> mItemTrackListener;
        private OnViewTrackDetailListener mOnViewTrackDetailListener;

        private ImageView mImageTrack;
        private TextView mTextTrackTitle;
        private TextView mTextTrackArtist;
        private ImageButton mButtonTrackDetail;

        public TrackViewHolder(@NonNull View itemView,
                               ItemRecyclerViewListener<Track> itemTrackListener,
                               OnViewTrackDetailListener onViewTrackDetailListener) {
            super(itemView);
            mItemTrackListener = itemTrackListener;
            mOnViewTrackDetailListener = onViewTrackDetailListener;

            initView();
            registerListener();
        }

        private void initView() {
            mImageTrack = itemView.findViewById(R.id.image_track);
            mTextTrackTitle = itemView.findViewById(R.id.text_track_title);
            mTextTrackArtist = itemView.findViewById(R.id.text_track_artist);
            mButtonTrackDetail = itemView.findViewById(R.id.button_track_detail);
        }

        private void registerListener() {
            mButtonTrackDetail.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void binData(Track track) {
            mTrack = track;
            Glide.with(mImageTrack.getContext())
                    .load(track.getArtWorkUrl())
                    .apply(new RequestOptions().error(R.drawable.image_default_border))
                    .apply(
                            RequestOptions.bitmapTransform(
                                    new RoundedCornersTransformation(Constants.ROUND_CORNER, 0)))
                    .into(mImageTrack);
            mTextTrackTitle.setText(track.getTitle());
            if (track.getPublisher() != null) {
                mTextTrackArtist.setText(track.getPublisher().getArtist());
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_track_detail:
                    mOnViewTrackDetailListener.onViewDetail(mTrack);
                    break;
                case R.id.item_track:
                    mItemTrackListener.onItemRecyclerViewClick(mTrack, getAdapterPosition());
                    break;
            }
        }
    }
}
