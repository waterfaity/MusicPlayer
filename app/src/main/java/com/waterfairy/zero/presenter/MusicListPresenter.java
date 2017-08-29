package com.waterfairy.zero.presenter;

import com.waterfairy.utils.FileUtils;
import com.waterfairy.zero.application.MyApp;
import com.waterfairy.zero.database.MediaInfoDB;
import com.waterfairy.zero.model.MusicListModel;
import com.waterfairy.zero.view.MusicListView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by shui on 2017/4/16.
 */

public class MusicListPresenter implements IMusicListPresenter {
    private MusicListModel mModel;
    private MusicListView mView;

    public MusicListPresenter(MusicListView view) {
        this.mView = view;
        this.mModel = new MusicListModel(this);
    }

    public void getMusicList() {
        {
            String databasesPath = FileUtils.getAPPPath(MyApp.getInstance().getApplicationContext(), "databases");
            try {
                File daFile = new File(databasesPath);
                String[] list = daFile.list();
                FileUtils.copyFile(databasesPath + "/music_list.db", "/sdcard/database_test/music_list.db");
                FileUtils.copyFile(databasesPath + "/music_list.db-journal", "/sdcard/database_test/music_list.db-journal");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<MediaInfoDB> mediaInfoDBs = null;
        if ((mediaInfoDBs = mModel.getMusicDBList()) == null || mediaInfoDBs.size() == 0) {
            mModel.searchMusic();
        }
    }
}
