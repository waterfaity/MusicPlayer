package com.waterfairy.zero.thread;

import com.waterfairy.zero.database.MediaInfoDB;

import java.util.List;

/**
 * Created by water_fairy on 2017/8/29.
 * 995637517@qq.com
 */

public interface OnSearchListener {
    void onSearchFile(String file);

    void onSearchPath(String path);

    void onSearchFinish(List<MediaInfoDB> mediaInfoDBs);
}
