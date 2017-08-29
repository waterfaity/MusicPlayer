package com.waterfairy.zero.presenter;

/**
 * Created by water_fairy on 2017/8/29.
 * 995637517@qq.com
 */

public interface IMainPresenterListener {
    void onGetMusicInfoSin(String musicName, int totalNum, int currentPos);

    void onGetMusicInfoTime(float radio, int duration, int currentPosition);
}
