package com.waterfairy.zero.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by water_fairy on 2017/8/29.
 * 995637517@qq.com
 */

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat;

    public static String getTimeMin(int time) {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("mm:ss");
        }
        return simpleDateFormat.format(new Date(time));
    }

}
