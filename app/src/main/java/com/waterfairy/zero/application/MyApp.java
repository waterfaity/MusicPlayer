package com.waterfairy.zero.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.waterfairy.utils.ToastUtils;
import com.waterfairy.zero.database.DaoMaster;
import com.waterfairy.zero.database.DaoSession;

/**
 * Created by water_fairy on 2017/3/23.
 */

public class MyApp extends Application {
    private SQLiteDatabase sqLiteDatabase;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private DaoMaster daoMaster;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        ToastUtils.initToast(this);
        initDatabase();
    }

    /**
     * 设置greenDao
     */
    private void initDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        devOpenHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        sqLiteDatabase = devOpenHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(sqLiteDatabase);
        daoSession = daoMaster.newSession();
    }
}
