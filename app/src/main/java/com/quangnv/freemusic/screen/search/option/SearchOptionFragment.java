package com.quangnv.freemusic.screen.search.option;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.SearchHistory;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 22/10/2018
 */

public class SearchOptionFragment extends BaseFragment implements OnItemHotKeyListener,
        OnItemHistoryListener,
        View.OnClickListener {

    @Inject
    HotKeyAdapter mHotKeyAdapter;
    @Inject
    HistoryAdapter mHistoryAdapter;

    private List<String> mHotKeys;
    private List<SearchHistory> mHistories;
    private OnItemHotKeyListener mHotKeyListener;
    private OnItemHistoryListener mHistoryListener;
    private OnActionClearHistoryListener mActionClearHistoryListener;

    private RecyclerView mHotKeyRecycler;
    private RecyclerView mHistoryRecycler;
    private TextView mClearAllHistoryText;

    public SearchOptionFragment() {
    }

    public static SearchOptionFragment newInstance() {
        SearchOptionFragment fragment = new SearchOptionFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof OnItemHotKeyListener) {
            mHotKeyListener = (OnItemHotKeyListener) getParentFragment();
        }
        if (getParentFragment() instanceof OnItemHistoryListener) {
            mHistoryListener = (OnItemHistoryListener) getParentFragment();
        }
        if (getParentFragment() instanceof OnActionClearHistoryListener) {
            mActionClearHistoryListener = (OnActionClearHistoryListener) getParentFragment();
        }
    }

    @Override
    public void onDestroy() {
        if (mHotKeyListener != null) {
            mHotKeyListener = null;
        }
        if (mHistoryListener != null) {
            mHistoryListener = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerSearchOptionComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .searchOptionModule(new SearchOptionModule(this, this))
                .build()
                .inject(this);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_option_search;
    }

    @Override
    public void onItemHotKeyClick(String s, int position) {
        mHotKeyListener.onItemHotKeyClick(s, position);
    }

    @Override
    public void onItemHistoryClick(SearchHistory history, int position, boolean isClearHistory) {
        mHistoryListener.onItemHistoryClick(history, position, isClearHistory);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_title_clear:
                mActionClearHistoryListener.onClearAllClick();
                mHistoryAdapter.clearData();
            break;
        }
    }

    public void setHotKeys(List<String> hotKeys) {
        mHotKeys = hotKeys;
        mHotKeyAdapter.setData(hotKeys);
    }

    public void setHistories(List<SearchHistory> histories) {
        mHistories = histories;
        mHistoryAdapter.setData(histories);
    }

    public void addHistory(SearchHistory history) {
        mHistories.add(history);
        mHistoryAdapter.addData(history);
    }

    public void removeHistory(SearchHistory history, int position) {
        mHistories.remove(position);
        mHistoryAdapter.removeElement(position);
    }

    private void initView(View view) {
        mHotKeyRecycler = view.findViewById(R.id.recycler_hot_key);
        mHistoryRecycler = view.findViewById(R.id.recycler_history_search);
        mClearAllHistoryText = view.findViewById(R.id.text_title_clear);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mHotKeyRecycler.setLayoutManager(layoutManager);
        mHotKeyRecycler.setAdapter(mHotKeyAdapter);
        mHistoryRecycler.setAdapter(mHistoryAdapter);
    }

    private void registerListener() {
        mClearAllHistoryText.setOnClickListener(this);
    }
}
