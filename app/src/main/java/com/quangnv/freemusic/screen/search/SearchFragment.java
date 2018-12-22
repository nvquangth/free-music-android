package com.quangnv.freemusic.screen.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quangnv.freemusic.MainApplication;
import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.data.model.SearchHistory;
import com.quangnv.freemusic.data.model.Track;
import com.quangnv.freemusic.screen.search.option.OnActionClearHistoryListener;
import com.quangnv.freemusic.screen.search.option.OnItemHistoryListener;
import com.quangnv.freemusic.screen.search.option.OnItemHotKeyListener;
import com.quangnv.freemusic.screen.search.option.SearchOptionFragment;
import com.quangnv.freemusic.screen.tracks.TracksFragment;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.StringUtils;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by quangnv on 21/10/2018
 */

public class SearchFragment extends BaseFragment implements SearchContract.View,
        View.OnClickListener,
        TextView.OnEditorActionListener,
        OnItemHotKeyListener,
        OnItemHistoryListener,
        OnActionClearHistoryListener,
        TracksFragment.OnLoadMoreTrackListener {

    @Inject
    SearchContract.Presenter mPresenter;

    private int mOffset;
    private boolean mIsLoadMore;
    private Navigator mNavigator;
    private SearchOptionFragment mSearchOptionFragment;
    private TracksFragment mTracksFragment;
    private String mKeyWord;

    private ProgressBar mProgressBarLoadingIndicator;
    private ImageButton mButtonBack;
    private ImageButton mButtonSearchVoice;
    private EditText mTextSearch;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(@SearchType int searchType) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGUMENT_SEARCH, searchType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        DaggerSearchComponent.builder()
                .appComponent(((MainApplication) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);
        mPresenter.setView(this);
        mSearchOptionFragment = SearchOptionFragment.newInstance();
        mTracksFragment = TracksFragment.newInstance();
        mNavigator = new Navigator(this);
        mNavigator.goNextChildFragment(R.id.frame_container, mTracksFragment,
                false, NavigateAnim.NONE, null);
        mNavigator.hideChildFragment(mTracksFragment, NavigateAnim.NONE);
        mNavigator.goNextChildFragment(R.id.frame_container, mSearchOptionFragment,
                false, NavigateAnim.NONE, null);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        registerListener();
        // if crash, call within handler
        mPresenter.getHotKey();
        mPresenter.getHistories();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBarLoadingIndicator.setVisibility(View.VISIBLE);
        mIsLoadMore = true;
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBarLoadingIndicator.setVisibility(View.GONE);
        mIsLoadMore = false;
    }

    @Override
    public void showHotKey(List<String> hotKeys) {
        mSearchOptionFragment.setHotKeys(hotKeys);
    }

    @Override
    public void showSearchHistory(List<SearchHistory> histories) {
        mSearchOptionFragment.setHistories(histories);
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mTracksFragment.addTracks(tracks);
        mNavigator.hideChildFragment(mSearchOptionFragment, NavigateAnim.NONE);
        mNavigator.showChildFragment(mTracksFragment, NavigateAnim.FADED);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                mNavigator.goBackFragment();
                break;
            case R.id.button_search_voice:

                break;
        }
    }

    @Override
    public void onItemHotKeyClick(String s, int position) {
        mKeyWord = s;
        mPresenter.searchTracks(s, mOffset);
        mTextSearch.setText(s);
    }

    @Override
    public void onItemHistoryClick(SearchHistory history, int position, boolean isClearHistory) {
        if (!isClearHistory) {
            mKeyWord = history.getTitle();
            mPresenter.searchTracks(history.getTitle(), mOffset);
            mTextSearch.setText(history.getTitle());
        } else {
            mPresenter.clearHistory(history);
            mSearchOptionFragment.removeHistory(history, position);
        }
    }

    @Override
    public void onClearAllClick() {
        mPresenter.clearAllHistories();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            String q = textView.getText().toString();
            if (!StringUtils.isEmpty(q)) {
                SearchHistory history = createHistory(q);
                mKeyWord = q;
                mTracksFragment.clearTracks();
                mOffset = 0;
                mPresenter.searchTracks(q, mOffset);
                mPresenter.addHistory(history);
//                mSearchOptionFragment.addHistory(history);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadMore) {
            mOffset = mOffset + Constants.LOAD_OFFSET;
            mPresenter.searchTracks(mKeyWord, mOffset);
        }
    }

    private SearchHistory createHistory(String title) {
        SearchHistory history = new SearchHistory();
        history.setTitle(title);
        return history;
    }

    private void initView(View view) {
        mProgressBarLoadingIndicator = view.findViewById(R.id.progress_bar_loading);
        mButtonBack = view.findViewById(R.id.button_back);
        mButtonSearchVoice = view.findViewById(R.id.button_search_voice);
        mTextSearch = view.findViewById(R.id.text_search);
    }

    private void registerListener() {
        mButtonBack.setOnClickListener(this);
        mButtonSearchVoice.setOnClickListener(this);
        mTextSearch.setOnEditorActionListener(this);
    }
}
