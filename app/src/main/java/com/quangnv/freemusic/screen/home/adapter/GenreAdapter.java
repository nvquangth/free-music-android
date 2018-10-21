package com.quangnv.freemusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Genre;
import com.quangnv.freemusic.util.DrawableUtils;

/**
 * Created by quangnv on 15/10/2018
 */

public class GenreAdapter extends BaseRecyclerViewAdapter<Genre, GenreAdapter.GenreViewHolder> {

    public GenreAdapter(Context context,
                        ItemRecyclerViewListener<Genre> itemRecyclerViewListener) {
        super(context);
        mItemRecyclerViewListener = itemRecyclerViewListener;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_genre, viewGroup, false);
        return new GenreViewHolder(view, mItemRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemRecyclerViewListener<Genre> mItemRecyclerViewListener;
        private Genre mGenre;

        private ImageView mImageGenre;
        private TextView mTextGenreTitle;

        public GenreViewHolder(@NonNull View itemView,
                               ItemRecyclerViewListener<Genre> itemRecyclerViewListener) {
            super(itemView);
            mItemRecyclerViewListener = itemRecyclerViewListener;
            initView();
            registerListener();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_genre:
                    mItemRecyclerViewListener.onItemRecyclerViewClick(mGenre, getAdapterPosition());
                    break;
            }
        }

        private void initView() {
            mImageGenre = itemView.findViewById(R.id.image_genre);
            mTextGenreTitle = itemView.findViewById(R.id.text_genre_name);
        }

        private void registerListener() {
            itemView.setOnClickListener(this);
        }

        private void bindData(Genre genre) {
            mGenre = genre;
            Glide.with(mImageGenre.getContext())
                    .load(DrawableUtils
                            .getResourceId(mImageGenre.getContext(), genre.getImageUrl()))
                    .into(mImageGenre);
            mTextGenreTitle.setText(genre.getName());
        }
    }
}
