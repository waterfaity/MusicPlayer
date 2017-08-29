package com.waterfairy.zero.model;


import com.waterfairy.zero.database.MediaInfoDB;
import com.waterfairy.zero.manager.MediaPlayManger;
import com.waterfairy.zero.manager.OnMediaPlayListener;
import com.waterfairy.zero.presenter.IMainPresenterListener;
import com.waterfairy.zero.utils.ConstantUtils;
import com.waterfairy.zero.utils.ShareUtils;

import java.util.List;

/**
 * Created by shui on 2017/4/15.
 */

public class MainModel {
    private IMainPresenterListener mPresenter;
    private MediaPlayManger mediaPlayManger;
    private MediaInfoDB mediaInfoDB;

    public MainModel(IMainPresenterListener presenter) {
        this.mPresenter = presenter;
        mediaPlayManger = MediaPlayManger.getInstance();
    }

    public void initMusicFirst() {
        mediaPlayManger.initMusicList();
    }

    public void onResume() {
        setPlayListener();
        showInfo();
    }

    private void showInfo() {
        mediaInfoDB = mediaPlayManger.getPlayInfo();
        if (mediaInfoDB != null) {
            String musicName = mediaInfoDB.getMusicName();
            mPresenter.onGetMusicInfoSin(musicName,
                    mediaPlayManger.getTotalNum(),
                    mediaPlayManger.getCurrentPos());
        }
    }

    private void setPlayListener() {
        mediaPlayManger.setOnMediaPlayListener(new OnMediaPlayListener() {
            @Override
            public void onNoMusic() {

            }

            @Override
            public void onAction(int action) {

            }

            @Override
            public void onFirstWorm() {

            }

            @Override
            public void onEndWorm() {

            }

            @Override
            public void onPlay(float radio, int duration, int currentPosition) {
                mPresenter.onGetMusicInfoTime(radio, duration, currentPosition);
            }

            @Override
            public void onPlay(int currentPos) {
                ShareUtils.getSetting().edit().
                        putInt(ConstantUtils.SHARE_PLAY_POS, currentPos).
                        apply();
                showInfo();
            }

            @Override
            public void onGetMusicList(List<MediaInfoDB> mediaInfoDBs) {
                //获取到列表 初始化时
                int playPos = ShareUtils.getSetting().getInt(ConstantUtils.SHARE_PLAY_POS, 0);
                mediaPlayManger.setPos(playPos);
                int currentPos = mediaPlayManger.getCurrentPos();
                MediaInfoDB mediaInfoDB = mediaInfoDBs.get(currentPos);

            }

            @Override
            public void onError() {

            }
        });
    }

    public void play() {
        mediaPlayManger.play();
    }

    public void pause() {
        mediaPlayManger.pause();
    }

    public void next() {
        mediaPlayManger.next();
    }

    public void previous() {
        mediaPlayManger.previous();
    }

    public void onSeek(float rate) {
        mediaPlayManger.seek(rate);
    }
}
