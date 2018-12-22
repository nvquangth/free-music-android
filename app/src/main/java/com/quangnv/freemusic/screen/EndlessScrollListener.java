package com.quangnv.freemusic.screen;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by quangnv on 22/12/2018
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;

    protected EndlessScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int pastItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int visibleItem = mLinearLayoutManager.getChildCount();
        int totalItem = mLinearLayoutManager.getItemCount();
        if (dy > 0) {
            if (pastItem + visibleItem == totalItem)
                onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
