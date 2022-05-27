package com.zhlw.module.base.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class ResourcesUtil {

    /**
     * 获取dimen
     */
    public static int getDimens(Context context, int resId) {
        return context.getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取integer
     */
    public static int getInteger(Context context, int resId) {
        return context.getResources().getInteger(resId);
    }

    /**
     * 获取文字
     */
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(Context context, int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context, resId);
    }

    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }


    /**
     * pxz转换dip
     */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
