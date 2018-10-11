package com.quangnv.freemusic.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangnv on 11/10/2018
 */

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private Context mContext;
    private ItemRecyclerViewListener<T> mItemRecyclerViewListener;
    protected List<T> mData = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setItemRecyclerViewListener(ItemRecyclerViewListener<T> itemRecyclerViewListener) {
        mItemRecyclerViewListener = itemRecyclerViewListener;
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    interface ItemRecyclerViewListener<T> {
        void onItemRecyclerViewClick(T t, int position);
    }
}
