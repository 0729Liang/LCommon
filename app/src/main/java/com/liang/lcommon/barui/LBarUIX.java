package com.liang.lcommon.barui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import com.liang.lcommon.barui.*;
import com.liang.lcommon.init.LCommon;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 状态栏/通知栏 的相关操作
 */
public class LBarUIX {

    /**
     * 透明状态栏
     *
     * @param activity act
     * @return 是否可以透明状态栏
     */
    public static boolean canTranslucent(Activity activity) {
        return QMUIStatusBarHelper.canTranslucent(activity);
    }

    /**
     * 透明状态栏
     *
     * @param activity act
     */
    public static void translucent(Activity activity) {
        if (canTranslucent(activity)) {
            QMUIStatusBarHelper.translucent(activity);
        }
    }

    /**
     * 设置状态栏文字为黑色
     *
     * @param activity act
     */
    public static boolean setStatusBarLightMode(Activity activity) {
        return QMUIStatusBarHelper.setStatusBarLightMode(activity);
    }

    /**
     * 设置状态栏文字为白色
     *
     * @param activity act
     */
    public static void setStatusBarDarkMode(Activity activity) {
        QMUIStatusBarHelper.setStatusBarDarkMode(activity);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity act
     * @param color    颜色
     */
    public static boolean setStatusBarColor(Activity activity, int color) {
        return StatusBarColorHelper.setStatusBarColor(activity, color);
    }

    /**
     * 获取状态栏高度
     *
     * @param context ctx
     * @return 状态栏高度
     */
    public static int getStatusbarHeight(Context context) {
        return QMUIStatusBarHelper.getStatusbarHeight(context);
    }

    /**
     * 是否有底部虚拟按键
     *
     * @param context ctx
     * @return 是否有 bottom bar
     */
    public static boolean hasBottomBar(Context context) {
        return BottomBarHelper.hasNavBar(context);
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @return 虚拟按键高度
     */
    public static int getBottomBarHeight() {
        if (hasBottomBar(LCommon.getApp())) {
            return BottomBarHelper.getBottomBarHeight(LCommon.getApp());
        }
        return 0;
    }

    /**
     * 隐藏底部虚拟按键
     *
     * @param activity act
     */
    public static void hideBottomBar(Activity activity) {
        BottomBarHelper.hideBottomUI(activity);
    }

    /**
     * 设置底部虚拟按键颜色
     *
     * @param activity act
     * @param color    颜色
     */
    public static void setBottomBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }


    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void toggleNotificationBar(final boolean isVisible) {
        NotificationBarHelper.toggleNotificationBar(isVisible);
    }

}
