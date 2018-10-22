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

    public HotKeyAdapter(Context context,
                         ItemRecyclerViewListener<String> itemRecyclerViewListener) {
        super(context);
        mItemRecyclerViewListener = itemRecyclerViewListener;
    }

    @NonNull
    @Override
    public HotKeyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_hot_key, viewGroup, false);
        return new HotKeyViewHolder(view, mItemRecyclerViewListener);
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

        private ItemRecyclerViewListener<String> mItemRecyclerViewListener;
        private String mHotKey;

        private TextView mTextHotKey;

        public HotKeyViewHolder(@NonNull View itemView,
                                ItemRecyclerViewListener<String> itemRecyclerViewListener) {
            super(itemView);
            mItemRecyclerViewListener = itemRecyclerViewListener;

            mTextHotKey = itemView.findViewById(R.id.text_hot_key);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemRecyclerViewListener.onItemRecyclerViewClick(mHotKey, getAdapterPosition());
        }

        private void bindData(String hotKey) {
            mHotKey = hotKey;
            mTextHotKey.setText(mHotKey);
        }
    }
}
