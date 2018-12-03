package com.quangnv.freemusic.screen.search.option;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;

/**
 * Created by quangnv on 22/10/2018
 */

public class HotKeyAdapter extends BaseRecyclerViewAdapter<String, HotKeyAdapter.HotKeyViewHolder> {

    private OnItemHotKeyListener mHotKeyListener;

    public HotKeyAdapter(Context context, OnItemHotKeyListener hotKeyListener) {
        super(context);
        mHotKeyListener = hotKeyListener;
    }

    @NonNull
    @Override
    public HotKeyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_hot_key, viewGroup, false);
        return new HotKeyViewHolder(view, mHotKeyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HotKeyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class HotKeyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemHotKeyListener mHotKeyListener;
        private String mHotKey;

        private TextView mTextHotKey;

        public HotKeyViewHolder(@NonNull View itemView, OnItemHotKeyListener hotKeyListener) {
            super(itemView);
            mHotKeyListener = hotKeyListener;

            mTextHotKey = itemView.findViewById(R.id.text_hot_key);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mHotKeyListener.onItemHotKeyClick(mHotKey, getAdapterPosition());
        }

        private void bindData(String hotKey) {
            mHotKey = hotKey;
            mTextHotKey.setText(mHotKey);
        }
    }
}
