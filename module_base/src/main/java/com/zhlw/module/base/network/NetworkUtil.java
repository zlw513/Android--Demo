package com.zhlw.module.base.network;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.provider.Settings;
import android.util.Log;

import com.zhlw.module.base.utils.SoftReferenceUtils;


public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    private static ConnectivityManager mConnectivityManager;

    private NetworkUtil(){}

    /**
     * 判断是否有网络连接
     * @return true 有网络链接; 否则 false
     */
    public static boolean isNetConnected(Context context){
        return NetUtils.isConnected(context);
    }


    /**
     * 判断当前网络是否真正可用
     * @param context
     * @return true 有真实可用的网络链接; 否则 false
     */
    public static boolean isNetWorkAvailable(Context context){
        if (NetUtils.isConnected(context)){
            if (mConnectivityManager == null){
                mConnectivityManager = SoftReferenceUtils.getSystemService(context.getApplicationContext(), Context.CONNECTIVITY_SERVICE);
            }
            @SuppressLint("MissingPermission") NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());

            Log.i(TAG,"NetworkCapalbilities:"+networkCapabilities.toString());
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            return false;
        }
    }

    /**
     * 跳转到网络设置界面
     */
    public static void startWifiSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Log.e("NoNetworkView", "startWifiSetting ACTION_WIFI_SETTINGS error = ActivityNotFoundException");
            intent = new Intent(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
            } catch (Exception e2) {
                Log.e("NoNetworkView", "startWifiSetting ACTION_SETTINGS error = " + e2.getMessage());
            }
        } catch (Exception e) {
            Log.e("HttpUtil", "startWifiSetting ACTION_WIFI_SETTINGS error = " + e.getMessage());
        }
    }

}
