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
    public static final String EXTENSION_MP3 = "mp3";
    @Id(autoincrement = true)
    private Long id;
    private String musicPath;
    private String musicName;
    private String extension;
    @Generated(hash = 2008569032)
    public MediaInfoDB(Long id, String musicPath, String musicName,
            String extension) {
        this.id = id;
        this.musicPath = musicPath;
        this.musicName = musicName;
        this.extension = extension;
    }
    @Generated(hash = 1683354870)
    public MediaInfoDB() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMusicPath() {
        return this.musicPath;
    }
    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }
    public String getMusicName() {
        return this.musicName;
    }
    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
    public String getExtension() {
        return this.extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }



}
