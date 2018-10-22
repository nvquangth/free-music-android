package com.quangnv.freemusic.screen.search.option;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 22/10/2018
 */

public class SearchOptionFragment extends BaseFragment implements
        BaseRecyclerViewAdapter.ItemRecyclerViewListener<String> {

    @Inject
    HotKeyAdapter mHotKeyAdapter;

    private List<String> mHotKeys;
    private List<String> mHistories;
    private BaseRecyclerViewAdapter.ItemRecyclerViewListener<String> mItemRecyclerViewListener;

    private RecyclerView mHotKeyRecycler;
    private RecyclerView mHistoryRecycler;

    public SearchOptionFragment() {
    }

    public static SearchOptionFragment newInstance() {
        SearchOptionFragment fragment = new SearchOptionFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof BaseRecyclerViewAdapter.ItemRecyclerViewListener) {
            mItemRecyclerViewListener = (BaseRecyclerViewAdapter.ItemRecyclerViewListener<String>) getParentFragment();
        }
    }

    @Override
    public void onDestroy() {
        if (mItemRecyclerViewListener != null) {
            mItemRecyclerViewListener = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerSearchOptionComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .searchOptionModule(new SearchOptionModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_option_search;
    }

    @Override
    public void onItemRecyclerViewClick(String s, int position) {
        mItemRecyclerViewListener.onItemRecyclerViewClick(s, position);
    }

    public void setHotKeys(List<String> hotKeys) {
        mHotKeys = hotKeys;
        mHotKeyAdapter.setData(hotKeys);
    }

    private void initView(View view) {
        mHotKeyRecycler = view.findViewById(R.id.recycler_hot_key);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mHotKeyRecycler.setLayoutManager(layoutManager);
        mHotKeyRecycler.setAdapter(mHotKeyAdapter);
    }
}
