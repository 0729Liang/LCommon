package com.liang.lcommon.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.liang.lcommon.init.LCommon;

/**
 * @author : Amarao
 * CreateAt : 15:32 2019/1/10
 * Describe : dp 与 px的转化
 */
public class LSizeX {

    public static int   WIDTH          = 0;
    public static int   HEIGHT         = 0;
    public static float DENSITY        = 0;
    public static float SCALED_DENSITY = 0;

    public static void init() {
        DisplayMetrics metrics = getDisplayMetrics();
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
        DENSITY = metrics.density;
        SCALED_DENSITY = metrics.scaledDensity;
    }

    private static DisplayMetrics getDisplayMetrics() {
        return LCommon.getApp().getResources().getDisplayMetrics();
    }

    /**
     * dp转px
     *
     * @param dpVal dp值
     * @return px值
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getDisplayMetrics());
    }

    public static int dp2px(float dpVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal px 值
     * @return dp值
     */
    public static float px2dp(float pxVal) {
        return pxVal / DENSITY;
    }

    public static float px2dp(float pxVal, Context context) {
        return pxVal / context.getResources().getDisplayMetrics().density;
    }

    /**
     * sp转px
     *
     * @param spVal sp 值
     * @return px 值
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getDisplayMetrics());
    }

    public static int sp2px(float spVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxVal px值
     * @return sp值
     */
    public static float px2sp(float pxVal) {
        return pxVal / SCALED_DENSITY;
    }

    public static float px2sp(float pxVal, Context context) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
