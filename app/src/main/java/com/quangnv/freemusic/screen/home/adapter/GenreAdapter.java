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

    public GenreAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_genre, viewGroup, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageGenre;
        private TextView mTextGenreTitle;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageGenre = itemView.findViewById(R.id.image_genre);
            mTextGenreTitle = itemView.findViewById(R.id.text_genre_name);
        }

        private void bindData(Genre genre) {
            Glide.with(mImageGenre.getContext())
                    .load(DrawableUtils
                            .getResourceId(mImageGenre.getContext(), genre.getImageUrl()))
                    .into(mImageGenre);
            mTextGenreTitle.setText(genre.getName());
        }
    }
}
