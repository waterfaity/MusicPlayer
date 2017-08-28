package com.waterfairy.zero.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by water_fairy on 2017/8/28.
 * 995637517@qq.com
 */
@Entity
public class MediaInfoDB {
    @Id(autoincrement = true)
    private long id;

    @Generated(hash = 1729383791)
    public MediaInfoDB(long id) {
        this.id = id;
    }

    @Generated(hash = 1683354870)
    public MediaInfoDB() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
