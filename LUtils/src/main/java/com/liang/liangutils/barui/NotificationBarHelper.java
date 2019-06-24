package com.liang.liangutils.barui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import com.liang.liangutils.init.LCommon;

import java.lang.reflect.Method;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

/**
 * CreateAt : 2018/9/28
 * Describe :
 *
 * @author chendong
 */
public class NotificationBarHelper {


    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void toggleNotificationBar(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }
        invokePanels(methodName);
    }

    private static void invokePanels(final String methodName) {
        try {
            @SuppressLint("WrongConstant")
            Object service = LCommon.getApp().getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
