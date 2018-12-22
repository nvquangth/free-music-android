package com.quangnv.freemusic.screen.more;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseFragment;
import com.quangnv.freemusic.screen.search.SearchFragment;
import com.quangnv.freemusic.screen.search.SearchType;
import com.quangnv.freemusic.util.navigator.NavigateAnim;
import com.quangnv.freemusic.util.navigator.Navigator;

/**
 * Created by quangnv on 22/12/2018
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener {

    private View mViewSearch;
    private ImageButton mButtonVoiceSearch;
    private ImageButton mButtonReport;
    private TextView mTextReport;
    private ImageButton mButtonRateMe;
    private TextView mTextRateMe;

    private Navigator mNavigator;
    private OnActionReportListener mOnActionReportListener;
    private OnActionRateListener mOnActionRateListener;

    public MoreFragment() {
    }

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();

        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnActionReportListener) {
            mOnActionReportListener = (OnActionReportListener) getActivity();
        }
        if (getActivity() instanceof OnActionRateListener) {
            mOnActionRateListener = (OnActionRateListener) getActivity();
        }
    }

    @Override
    public void onDestroy() {
        if (mOnActionReportListener != null) {
            mOnActionReportListener = null;
        }
        if (mOnActionRateListener != null) {
            mOnActionRateListener = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initComponentsOnCreate(@Nullable Bundle savedInstanceState) {
        mNavigator = new Navigator(this);
    }

    @Override
    protected void initComponentsOnCreateView(View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        registerListener();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_more;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_mail:
            case R.id.text_title_report:
                mOnActionReportListener.onActionReportClick();
                break;
            case R.id.button_rate:
            case R.id.text_rate_me:
                mOnActionRateListener.onActionRateClick();
                break;
            case R.id.view_search:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        SearchFragment.newInstance(SearchType.NONE),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
            case R.id.button_search_voice:
                mNavigator.addFragmentToBackStack(R.id.frame_container,
                        SearchFragment.newInstance(SearchType.VOICE),
                        true, NavigateAnim.RIGHT_LEFT, null);
                break;
        }
    }

    private void initViews(View view) {
        mViewSearch = view.findViewById(R.id.view_search);
        mButtonVoiceSearch = view.findViewById(R.id.button_search_voice);
        mButtonReport = view.findViewById(R.id.button_mail);
        mTextReport = view.findViewById(R.id.text_title_report);
        mButtonRateMe = view.findViewById(R.id.button_rate);
        mTextRateMe = view.findViewById(R.id.text_rate_me);
    }

    private void registerListener() {
        mViewSearch.setOnClickListener(this);
        mButtonVoiceSearch.setOnClickListener(this);
        mButtonReport.setOnClickListener(this);
        mTextReport.setOnClickListener(this);
        mButtonRateMe.setOnClickListener(this);
        mTextRateMe.setOnClickListener(this);
    }

    public interface OnActionReportListener {

        void onActionReportClick();
    }

    public interface OnActionRateListener {

        void onActionRateClick();
    }
}
