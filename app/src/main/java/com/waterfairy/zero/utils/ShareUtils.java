package com.waterfairy.zero.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.waterfairy.zero.application.MyApp;

/**
 * Created by water_fairy on 2017/8/29.
 * 995637517@qq.com
 */

public class ShareUtils {

    public static SharedPreferences getSetting() {
        return MyApp.getInstance().getSharedPreferences("setting", Context.MODE_PRIVATE);
    }
}
