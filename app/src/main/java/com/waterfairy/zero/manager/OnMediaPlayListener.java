package com.waterfairy.zero.manager;

import com.waterfairy.zero.database.MediaInfoDB;

import java.util.List;

/**
 * Created by water_fairy on 2017/8/29.
 * 995637517@qq.com
 */

public interface OnMediaPlayListener {
    void onNoMusic();

    void onAction(int action);

    void onFirstWorm();

    void onEndWorm();

    void onPlay(float radio, int duration, int currentPosition);

    void onPlay(int currentPos);

    void onGetMusicList(List<MediaInfoDB> mediaInfoDBs);

    void onError();
}
