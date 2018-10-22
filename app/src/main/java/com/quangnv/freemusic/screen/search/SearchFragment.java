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
import com.quangnv.freemusic.base.BaseRecyclerViewAdapter;
import com.quangnv.freemusic.data.model.Track;
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
        BaseRecyclerViewAdapter.ItemRecyclerViewListener<String>,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    @Inject
    SearchContract.Presenter mPresenter;

    private int offset;
    private Navigator mNavigator;
    private SearchOptionFragment mSearchOptionFragment;
    private TracksFragment mTracksFragment;

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
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBarLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBarLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showHotKey(List<String> hotKeys) {
        mSearchOptionFragment.setHotKeys(hotKeys);
    }

    @Override
    public void showSearchHistory(List<String> history) {

    }

    @Override
    public void showTracks(List<Track> tracks) {
        mTracksFragment.setTracks(tracks);
        mNavigator.hideChildFragment(mSearchOptionFragment, NavigateAnim.NONE);
        mNavigator.showChildFragment(mTracksFragment, NavigateAnim.FADED);
    }

    @Override
    public void onItemRecyclerViewClick(String s, int position) {
        mTextSearch.setText(s);
        mPresenter.searchTracks(s, offset);
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

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            String q = textView.getText().toString();
            if (!StringUtils.isEmpty(q)) {
                mPresenter.searchTracks(q, offset);
                return true;
            }
        }
        return false;
    }
}
