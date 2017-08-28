package com.waterfairy.zero.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.waterfairy.media.Mp3Player;

/**
 * Created by water_fairy on 2017/8/28.
 * 995637517@qq.com
 */

public class MediaPlayerService extends Service {
    private Mp3Player mMp3Player;
    private String mMp3Url;
    private static final String MP3_URL = "mp3Url";
    private static final String SEEK_tIME = "seekTime";
    private static final String ACTION_PLAY = "com.waterfairy.zero.MediaPlayerService_play";
    private static final String ACTION_STOP = "com.waterfairy.zero.MediaPlayerService_stop";
    private static final String ACTION_PAUSE = "com.waterfairy.zero.MediaPlayerService_pause";
    private static final String ACTION_SEEK = "com.waterfairy.zero.MediaPlayerService_seek";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMp3Player == null) {
            mMp3Player = new Mp3Player();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_PLAY:
                String mp3Url = intent.getStringExtra(MP3_URL);
                if (!TextUtils.isEmpty(mp3Url)) {
                    this.mMp3Url = mp3Url;
                }
                mMp3Player.play(this.mMp3Url);
                break;
            case ACTION_PAUSE:
                mMp3Player.pause();
                break;
            case ACTION_SEEK:
                mMp3Player.seekTo(intent.getIntExtra(SEEK_tIME, 0));
                break;
            case ACTION_STOP:
                mMp3Player.stop();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 播放
     *
     * @param context
     */
    public static void play(Context context) {
        play(context, null);
    }

    /**
     * 播放
     *
     * @param context
     * @param mp3Url
     */
    public static void play(Context context, String mp3Url) {
        Intent intent = new Intent();
        intent.putExtra(MP3_URL, mp3Url);
        intent.setAction(ACTION_PLAY);
        context.startService(intent);
    }

    public static void pause(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_PAUSE);
        context.startService(intent);
    }

    public static void seek(Context context, int seekTime) {
        Intent intent = new Intent();
        intent.setAction(ACTION_SEEK);
        intent.putExtra(SEEK_tIME, seekTime);
        context.startService(intent);
    }

    public static void stop(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }
}
