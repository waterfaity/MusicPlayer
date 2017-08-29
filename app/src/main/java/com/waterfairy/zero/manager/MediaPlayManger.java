package com.waterfairy.zero.manager;

import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.waterfairy.media.Mp3Player;
import com.waterfairy.zero.application.MyApp;
import com.waterfairy.zero.database.MediaInfoDB;
import com.waterfairy.zero.thread.MediaSearchASync;
import com.waterfairy.zero.thread.OnSearchListener;

import java.util.List;

/**
 * Created by water_fairy on 2017/8/28.
 * 995637517@qq.com
 */

public class MediaPlayManger {
    private static final String TAG = "MediaPlayManger";
    public static final MediaPlayManger MEDIA_PLAY_MANGER = new MediaPlayManger();
    private OnMediaPlayListener onMediaPlayListener;
    private List<MediaInfoDB> mediaInfoDBs;
    private int mCurrentPos;//播放的当前位置
    private int mCurrentTime;//当前时间
    private int totalNum;
    private Mp3Player mp3Player;
    private MediaInfoDB mediaInfoDB;

    private MediaPlayManger() {
        mp3Player = new Mp3Player();
        mp3Player.setOnMp3PlayListener(getMp3PlayListener());
    }

    private Mp3Player.onMp3PlayListener getMp3PlayListener() {
        return new Mp3Player.onMp3PlayListener() {
            @Override
            public void onMp3PlayError(int state, String message) {
                Log.i(TAG, "onMp3PlayError: " + state + "--" + message);
            }

            @Override
            public void onPlayStateChanged(int state, String message) {
                Log.i(TAG, "onPlayStateChanged: " + state + "--" + message);
            }
        };
    }

    public static MediaPlayManger getInstance() {
        return MEDIA_PLAY_MANGER;
    }

    public void setOnMediaPlayListener(OnMediaPlayListener onMediaPlayListener) {
        this.onMediaPlayListener = onMediaPlayListener;
    }

    public void setMusicList(List<MediaInfoDB> musicList) {
        mediaInfoDBs = musicList;
        if (mediaInfoDBs == null || mediaInfoDBs.size() == 0) {
            totalNum = 0;
            searchMusic();
        } else {
            totalNum = mediaInfoDBs.size();
            if (onMediaPlayListener != null)
                onMediaPlayListener.onGetMusicList(mediaInfoDBs);
        }
    }

    private void searchMusic() {
        MediaSearchASync mediaSearchASync = new MediaSearchASync(new OnSearchListener() {
            @Override
            public void onSearchFile(String file) {

            }

            @Override
            public void onSearchPath(String path) {

            }

            @Override
            public void onSearchFinish(List<MediaInfoDB> mediaInfoDBs) {
                if (onMediaPlayListener != null) onMediaPlayListener.onGetMusicList(mediaInfoDBs);
            }
        });
        mediaSearchASync.execute(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public void initMusicList() {
        List<MediaInfoDB> list = MyApp.getInstance().getDaoSession()
                .queryBuilder(MediaInfoDB.class).build()
                .list();
        setMusicList(list);
    }

    /**
     * 上一首
     */
    public void previous() {
        if (mCurrentPos > 0) {
            mCurrentPos--;
            if (mp3Player.getPlayState() == Mp3Player.PLAYING) {
                play(mCurrentPos);
            } else {
                prepareMp3(mCurrentPos);
            }
            if (onMediaPlayListener != null) {
                onMediaPlayListener.onPlay(mCurrentPos);
            }
        } else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onFirstWorm();
        }
    }

    private MediaInfoDB prepareMp3(int pos) {
        mCurrentPos = pos;
        if (mediaInfoDBs != null && mediaInfoDBs.size() > pos && mp3Player != null) {
            mediaInfoDB = mediaInfoDBs.get(mCurrentPos);
            String musicPath = mediaInfoDB.getMusicPath();
            if (mp3Player.getMediaState() != Mp3Player.NOT_INIT) {
                mp3Player.release();
            }
            mp3Player.prepare(musicPath);
            handler.sendEmptyMessage(0);
            if (onMediaPlayListener != null) {
                onMediaPlayListener.onPlay(mCurrentPos);
            }
            return mediaInfoDB;
        } else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onError();
            return null;
        }
    }

    /**
     * 下一首
     */
    public void next() {
        if (mCurrentPos + 1 < totalNum) {
            mCurrentPos++;
            if (mp3Player.getPlayState() == Mp3Player.PLAYING) {
                play(mCurrentPos);
            } else {
                prepareMp3(mCurrentPos);
            }
            if (onMediaPlayListener != null) {
                onMediaPlayListener.onPlay(mCurrentPos);
            }
        } else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onEndWorm();
        }
    }

    /**
     * 播放
     */
    public MediaInfoDB play() {
        if (onMediaPlayListener != null) {
            onMediaPlayListener.onPlay(mCurrentPos);
        }
        if (mp3Player.getPlayState() == Mp3Player.PAUSE) {
            return replay();
        } else {
            return play(mCurrentPos);
        }


    }

    /**
     * 播放
     */
    public MediaInfoDB play(int pos) {
        MediaInfoDB mediaInfoDB = prepareMp3(pos);
        if (mediaInfoDB != null) {
            mp3Player.play(mediaInfoDB.getMusicPath());
        }
        return mediaInfoDB;

    }

    /**
     * 暂停
     */
    public void pause() {
        if (mp3Player != null) mp3Player.pause();
        else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onError();
        }
    }

    /**
     * 暂停->播放
     */
    public MediaInfoDB replay() {
        if (mp3Player != null) {
            mp3Player.resume();
            handler.sendEmptyMessage(0);
            return mediaInfoDB;
        } else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onError();
            return null;
        }
    }

    /**
     * 停止
     */
    public void stop() {
        if (mp3Player != null) mp3Player.stop();
        else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onError();
        }
    }

    /**
     * 指定位置播放
     */
    public void seek(int time) {
        if (mp3Player != null) mp3Player.seekTo(time);
        else {
            if (onMediaPlayListener != null)
                onMediaPlayListener.onError();
        }
    }

    public int getTotalNum() {
        return totalNum;
    }

    public MediaInfoDB getPlayInfo() {
        if (mediaInfoDBs != null && mCurrentPos < mediaInfoDBs.size()) {
            return mediaInfoDBs.get(mCurrentPos);
        } else {
            return null;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mp3Player != null
                    && mp3Player.getPlayState() == Mp3Player.PLAYING
                    && onMediaPlayListener != null) {
                MediaPlayer mediaPlayer = mp3Player.getMediaPlayer();
                if (mediaPlayer != null) {
                    int duration = mediaPlayer.getDuration();
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    float radio = 0;
                    if (duration == 0) radio = 0;
                    else radio = (float) currentPosition / duration;

                    onMediaPlayListener.onPlay(radio, duration, currentPosition);
                }

            }
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    public void setPos(int pos) {
        this.mCurrentPos = pos;
    }

    public int getCurrentPos() {
        return mCurrentPos;
    }

    public void seek(float rate) {
        if (mp3Player != null) {
            MediaPlayer mediaPlayer = mp3Player.getMediaPlayer();
            if (mediaPlayer != null) {
                int duration = mediaPlayer.getDuration();
                mp3Player.seekTo((int) (duration * rate));
            }
        }
    }
}
