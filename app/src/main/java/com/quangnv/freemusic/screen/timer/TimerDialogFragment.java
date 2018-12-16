package com.quangnv.freemusic.screen.timer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.quangnv.freemusic.R;
import com.quangnv.freemusic.base.BaseDialogFragment;
import com.quangnv.freemusic.util.Constants;
import com.quangnv.freemusic.util.StringUtils;

/**
 * Created by quangnv on 16/12/2018
 */

public class TimerDialogFragment extends BaseDialogFragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        TextWatcher {

    private Switch mSwitchTimer;
    private EditText mEditTextTimer;
    private TextView mTextOk;
    private TextView mTextCancel;

    private OnTimerPlayerListener mOnTimerPlayerListener;
    private int mTimer;

    public static TimerDialogFragment newInstance(int timer) {

        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_TIMER, timer);

        TimerDialogFragment fragment = new TimerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnTimerPlayerListener) {
            mOnTimerPlayerListener = (OnTimerPlayerListener) getActivity();
        }
    }

    @Override
    public void onDestroy() {
        if (mOnTimerPlayerListener != null) {
            mOnTimerPlayerListener = null;
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dialog_timer;
    }

    @Override
    protected void initComponentsOnCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Dialog_Alert);
    }

    @Override
    protected void initComponentsOnCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        registerListener();

        if (getArguments() != null) {
            mTimer = getArguments().getInt(Constants.ARGUMENT_TIMER);
        }

        if (mTimer != 0) {
            mSwitchTimer.setChecked(true);
            mEditTextTimer.setText(String.valueOf(mTimer));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.text_title_ok) {
            if (getTimer() != 0 && mSwitchTimer.isChecked()) {
                mOnTimerPlayerListener.onTimer(getTimer());
                Toast.makeText(getActivity(), getTitleToast(), Toast.LENGTH_SHORT).show();
            }
        }
        dismiss();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (!isChecked) {
            mOnTimerPlayerListener.onCancel();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!mSwitchTimer.isChecked()) {
            mSwitchTimer.setChecked(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void initView(View view) {
        mSwitchTimer = view.findViewById(R.id.switch_timer);
        mEditTextTimer = view.findViewById(R.id.text_input_timer);
        mTextOk = view.findViewById(R.id.text_title_ok);
        mTextCancel = view.findViewById(R.id.text_title_cancel);
    }

    private void registerListener() {
        mTextOk.setOnClickListener(this);
        mTextCancel.setOnClickListener(this);
        mEditTextTimer.addTextChangedListener(this);
        mSwitchTimer.setOnCheckedChangeListener(this);
    }

    private int getTimer() {
        String s = mEditTextTimer.getText().toString();
        if (StringUtils.isEmpty(s)) {
            return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getTitleToast() {
        return StringUtils.format(
                getString(R.string.msg_timer_pause),
                " ",
                String.valueOf(getTimer()),
                " ",
                getString(R.string.msg_minutes));
    }
}
