package com.zhlw.module.base.utils;

import android.util.Log;

public class EventUtil {
    private static int DEFAULT_TIME = 300;
    private static long lastTime = 0;

    public static boolean isNotFastDoubleEvent() {
        return isNotFastDoubleEvent(DEFAULT_TIME);

    }
    public static boolean isNotFastDoubleEvent(int ingervalTime) {
        boolean ret = false;
        long time = System.currentTimeMillis();
        long interval = time - lastTime;
        if (interval < 0 || interval > ingervalTime) {
            ret = true;
            lastTime = time;
        }
        Log.d("EventUtil","interval = " + interval);
        return ret;

    }
    public static boolean isFastDoubleEvent() {
        return !isNotFastDoubleEvent();

    }
    public static boolean isFastDoubleEvent(int ingervalTime) {
        return !isNotFastDoubleEvent(ingervalTime);

    }
    public static void setCurrentTime() {
        lastTime = System.currentTimeMillis();

    }
}
