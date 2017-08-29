package com.waterfairy.zero.view;

/**
 * Created by shui on 2017/4/15.
 */

public interface MainView {

    void setMusicInfoSin(String musicName, int totalNum, int currentPos);

    void setMusicInfoTime(float radio, String duration, String currentPosition);
}
