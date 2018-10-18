package com.quangnv.freemusic.screen.main;

/**
 * Created by quangnv on 15/10/2018
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }
}
