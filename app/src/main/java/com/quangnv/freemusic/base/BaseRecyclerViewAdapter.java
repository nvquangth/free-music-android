package com.quangnv.freemusic.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quangnv on 11/10/2018
 */

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private Context mContext;
    protected ItemRecyclerViewListener<T> mItemRecyclerViewListener;
    protected LayoutInflater mInflater;
    protected List<T> mData;

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
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

    public void addData(T data) {
        mData.add(data);
        notifyItemInserted(mData.size() - 1);
    }

    public void removeElement(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface ItemRecyclerViewListener<T> {
        void onItemRecyclerViewClick(T t, int position);
    }
}
