package com.waterfairy.zero.presenter;

import com.waterfairy.zero.model.MainModel;
import com.waterfairy.zero.utils.DateUtils;
import com.waterfairy.zero.view.MainView;

/**
 * Created by shui on 2017/4/15.
 */

public class MainPresenter implements IMainPresenterListener {
    private MainView mView;
    private MainModel mModel;

    public MainPresenter(MainView view) {
        mView = view;
        mModel = new MainModel(this);
    }

    public void initMusic() {
        mModel.initMusicFirst();
    }

    public void onResume() {
        mModel.onResume();
    }

    @Override
    public void onGetMusicInfoSin(String musicName, int totalNum, int currentPos) {
        mView.setMusicInfoSin(musicName, totalNum, currentPos);
    }

    @Override
    public void onGetMusicInfoTime(float radio, int duration, int currentPosition) {
        String durationMin = DateUtils.getTimeMin(duration);
        String currentPositionMin = DateUtils.getTimeMin(currentPosition);
        mView.setMusicInfoTime(radio, durationMin, currentPositionMin);
    }

    public void play() {
        mModel.play();
    }

    public void pause() {
        mModel.pause();
    }
    public void next() {
        mModel.next();
    }

    public void previous() {
        mModel.previous();
    }
    public void onSeek(float rate) {
        mModel.onSeek(rate);
    }
}
