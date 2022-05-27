package com.zhlw.module.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class SystemBarUtils {

    private static final String TAG = "SystemBarUtils";
    /**
     * 低api版本无法获取到该字段,故取系统值后自定义字段
     */
    private static final int CUSTOM_SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 0x00002000;
    private static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;

    /**
     * 设置状态栏、导航栏文字的颜色,true为白色，false为灰色
     */
    public static void setSystemBarWhite(Activity activity, boolean on) {
        if (activity != null) {
            View view = activity.getWindow().getDecorView();
            int systemUIVis = view.getSystemUiVisibility();
            if (on) {
                systemUIVis &= ~CUSTOM_SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                //Android11前机型系统有对样式做处理，但之后需要应用自己处理。故移除该配置以实现导航栏显示白色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    systemUIVis &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            } else {
                systemUIVis |= CUSTOM_SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                //Android11前机型系统有对样式做处理，但之后需要应用自己处理。故加上该配置以实现导航栏显示灰色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    systemUIVis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            }
            view.setSystemUiVisibility(systemUIVis);
        }
    }

    /**
     * 设置导航栏背景颜色
     */
    public static void setNavigationBarBgColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }

    public static void switchTransStatusBar(Activity activity, boolean on) {
        View decorView = activity.getWindow().getDecorView();
        Window window = activity.getWindow();
        if (decorView == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | decorView.getSystemUiVisibility());
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            return;
        }

        int systemUIVis = decorView.getSystemUiVisibility();
        if (on) {
            systemUIVis |= FLAG_TRANSLUCENT_STATUS;
        } else {
            systemUIVis &= ~FLAG_TRANSLUCENT_STATUS;
        }
        decorView.setSystemUiVisibility(systemUIVis);

        Field drawsSysBackgroundsField;
        try {
            drawsSysBackgroundsField = WindowManager.LayoutParams.class.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
            window.addFlags(drawsSysBackgroundsField.getInt(null));
        } catch (Exception e) {
            Log.e(TAG,"switchTransSystemUI error"+e.getMessage());
        }
    }

    /**
     * 隐藏导航栏、状态栏，且不影响布局位置
     */
    public static void hideSystemBar(Context context) {
        View decorView = ((Activity) context).getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 将布局拓展到状态栏、导航栏。不随着状态栏、导航栏的隐藏而影响布局
     */
    public static void setLayoutFullScreen(Context context) {
        View decorView = ((Activity) context).getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(uiOptions);
    }
}
