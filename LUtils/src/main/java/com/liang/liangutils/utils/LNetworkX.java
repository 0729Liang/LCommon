package com.liang.liangutils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

import com.liang.liangutils.init.LCommon;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 检测网络环境
 */
public class LNetworkX {

    /**
     * 开启 wifi 设置页面
     */
    public static void openWirelessSettings() {
        LCommon.getApp().startActivity(
                new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }


    /**
     * 获取当前网络状态
     *
     * @return NetworkInfo
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        Context context = LCommon.getApp();
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivityManager = getConnectivityManager(context.getApplicationContext());
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * @return 网络是否连接，包括流量和 wifi
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * @return 网络是否可用，包括流量和 wifi
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    /**
     * @return 当前连接网络的名字
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) LCommon.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : "";
    }

    String[] mStrings = {ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE};

    /**
     * wifi网络是否连接
     *
     * @return 是否连接
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE})
    public static boolean isWifiConnected() {
        ConnectivityManager cm = getConnectivityManager(LCommon.getApp());
        if (cm != null) {
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }


    private static ConnectivityManager getConnectivityManager(Context app) {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
