package com.waterfairy.zero.thread;

import android.os.AsyncTask;

import com.waterfairy.zero.application.MyApp;
import com.waterfairy.zero.database.MediaInfoDB;
import com.waterfairy.zero.database.MediaInfoDBDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by water_fairy on 2017/8/29.
 * 995637517@qq.com
 */

public class MediaSearchASync extends AsyncTask<String, Object, List<MediaInfoDB>> {
    private MediaInfoDBDao mediaInfoDBDao;
    private List<MediaInfoDB> mediaInfoDBList;
    private OnSearchListener onSearchListener;

    public MediaSearchASync(OnSearchListener onSearchListener) {
        mediaInfoDBList = new ArrayList<>();
        this.onSearchListener = onSearchListener;
        initMediaInfoDao();
    }

    private void initMediaInfoDao() {
        mediaInfoDBDao = MyApp.getInstance().getDaoSession().getMediaInfoDBDao();
    }

    @Override
    protected List<MediaInfoDB> doInBackground(String... params) {

        for (String path : params) {
            search(path);
        }
        return mediaInfoDBList;
    }

    /**
     * @param path 路径(非文件)
     */
    private void search(String path) {
        File file = new File(path);
        if (file.exists() && !file.getAbsolutePath().startsWith(".")) {
            publishProgress(true, file.getAbsolutePath());//进度
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    if (childFile.isDirectory()) {
                        search(childFile.getAbsolutePath());
                    } else if (childFile.isFile()) {
                        String absolutePath = childFile.getAbsolutePath();
                        if (absolutePath.endsWith("." + MediaInfoDB.EXTENSION_MP3)) {
                            save(childFile);//保存
                            publishProgress(false, absolutePath);//进度
                        }
                    }
                }
            }
        }
    }

    /**
     * 保存
     *
     * @param childFile
     */
    private void save(File childFile) {
        String name = childFile.getName();
        MediaInfoDB mediaInfoDB = new MediaInfoDB();
        mediaInfoDB.setExtension(MediaInfoDB.EXTENSION_MP3);
        mediaInfoDB.setMusicName(name.substring(0, name.length() - 4));
        mediaInfoDB.setMusicPath(childFile.getAbsolutePath());
        long insertID = mediaInfoDBDao.insert(mediaInfoDB);
        mediaInfoDB.setId(insertID);
        mediaInfoDBList.add(mediaInfoDB);
    }

    /**
     * 搜索完成
     *
     * @param mediaInfoDBs
     */
    @Override
    protected void onPostExecute(List<MediaInfoDB> mediaInfoDBs) {
        super.onPostExecute(mediaInfoDBs);
        if (onSearchListener != null) onSearchListener.onSearchFinish(mediaInfoDBs);
    }

    /**
     * 搜索中
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        if (onSearchListener != null)
            if ((boolean) values[0]) {
                onSearchListener.onSearchPath((String) values[1]);
            } else {
                onSearchListener.onSearchFile((String) values[1]);
            }
    }
}
