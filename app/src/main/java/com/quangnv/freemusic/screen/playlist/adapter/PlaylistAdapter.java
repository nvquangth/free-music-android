package com.quangnv.freemusic.screen.playlist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Playlist;

/**
 * Created by quangnv on 12/11/2018
 */

public class PlaylistAdapter extends
        BaseRecyclerViewAdapter<Playlist, PlaylistAdapter.PlaylistViewHolder> {

    public PlaylistAdapter(Context context,
                           ItemRecyclerViewListener<Playlist> itemRecyclerViewListener) {
        super(context);
        mItemRecyclerViewListener = itemRecyclerViewListener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_playlist, viewGroup, false);
        return new PlaylistViewHolder(view, mItemRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Playlist mPlaylist;
        private ItemRecyclerViewListener<Playlist> mPlaylistItemRecyclerViewListener;
        private TextView mTextKey;
        private TextView mTextTitle;
        private TextView mTextNumberTrack;

        public PlaylistViewHolder(@NonNull View itemView,
                                  ItemRecyclerViewListener<Playlist> itemRecyclerViewListener) {
            super(itemView);
            mPlaylistItemRecyclerViewListener = itemRecyclerViewListener;

            mTextKey = itemView.findViewById(R.id.text_playlist_key);
            mTextTitle = itemView.findViewById(R.id.text_playlist_title);
            mTextNumberTrack = itemView.findViewById(R.id.text_number_track);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mPlaylistItemRecyclerViewListener.onItemRecyclerViewClick(mPlaylist,
                    getAdapterPosition());
        }

        private void bindData(Playlist playlist) {
            mPlaylist = playlist;
            String key;
            if (playlist.getName().length() > 1) {
                key = playlist.getName().substring(0, 1);
            } else {
                key = playlist.getName();
            }
            mTextKey.setText(key.toUpperCase());
            mTextTitle.setText(playlist.getName());
            if (playlist.getTracks() == null || playlist.getTracks().isEmpty()) {
                mTextNumberTrack.setText("0");
            } else {
                mTextNumberTrack.setText(playlist.getTracks().size());
            }
        }
    }
}
