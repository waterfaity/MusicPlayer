package com.waterfairy.zero.view;

import com.waterfairy.zero.database.MediaInfoDB;

import java.util.List;

/**
 * Created by shui on 2017/4/16.
 */

public interface MusicListView {
    void show(List<MediaInfoDB> mediaInfoDBs);
}
