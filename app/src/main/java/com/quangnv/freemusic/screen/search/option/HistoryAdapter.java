package com.quangnv.freemusic.screen.search.option;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.SearchHistory;

/**
 * Created by quangnv on 26/11/2018
 */

public class HistoryAdapter extends BaseRecyclerViewAdapter<SearchHistory, HistoryAdapter.HistoryViewHolder> {

    private OnItemHistoryListener mHistoryListener;

    public HistoryAdapter(Context context, OnItemHistoryListener historyListener) {
        super(context);
        mHistoryListener = historyListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_search_history, viewGroup, false);
        return new HistoryViewHolder(view, mHistoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int i) {
        holder.bindData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemHistoryListener mHistoryListener;
        private SearchHistory mSearchHistory;

        private TextView mTitleText;
        private ImageButton mClearButton;

        public HistoryViewHolder(@NonNull View itemView, OnItemHistoryListener historyListener) {
            super(itemView);
            mHistoryListener = historyListener;

            mTitleText = itemView.findViewById(R.id.text_title);
            mClearButton = itemView.findViewById(R.id.button_clear);
            itemView.setOnClickListener(this);
            mClearButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_recycler:
                    mHistoryListener.onItemHistoryClick(mSearchHistory, getAdapterPosition(), false);
                    break;
                case R.id.button_clear:
                    mHistoryListener.onItemHistoryClick(mSearchHistory, getAdapterPosition(), true);
                    break;
            }
        }

        private void bindData(SearchHistory searchHistory) {
            mSearchHistory = searchHistory;
            mTitleText.setText(searchHistory.getTitle());
        }
    }
}
