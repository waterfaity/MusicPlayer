package com.waterfairy.zero.model;

import android.os.Environment;
import android.util.Log;

import com.waterfairy.utils.FileUtils;
import com.waterfairy.zero.application.MyApp;
import com.waterfairy.zero.database.MediaInfoDB;
import com.waterfairy.zero.presenter.IMusicListPresenter;
import com.waterfairy.zero.thread.MediaSearchASync;
import com.waterfairy.zero.thread.OnSearchListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by shui on 2017/4/16.
 */

public class MusicListModel {
    private static final String TAG = "MusicListModel";
    private IMusicListPresenter mPresenter;

    public MusicListModel(IMusicListPresenter musicListPresenter) {
        this.mPresenter = musicListPresenter;
    }

    public List<MediaInfoDB> getMusicDBList() {
        return MyApp.getInstance().getDaoSession().queryBuilder(MediaInfoDB.class).build().list();
    }

    public void searchMusic() {
        MediaSearchASync mediaSearchASync = new MediaSearchASync(new OnSearchListener() {
            @Override
            public void onSearchFile(String file) {
                Log.i(TAG, "onSearchFile: " + file);
            }

            @Override
            public void onSearchPath(String path) {
                Log.i(TAG, "onSearchPath: " + path);
            }

            @Override
            public void onSearchFinish(List<MediaInfoDB> mediaInfoDBs) {
                Log.i(TAG, "onSearchFinish: " + mediaInfoDBs.size());

            }
        });
        mediaSearchASync.execute(Environment.getExternalStorageDirectory().getAbsolutePath());
    }
}
