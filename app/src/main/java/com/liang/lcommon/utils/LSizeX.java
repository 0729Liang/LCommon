package com.liang.lcommon.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.liang.lcommon.init.LCommon;

/**
 * @author : Amarao
 * CreateAt : 15:32 2019/1/10
 * Describe : dp sp px 之间的转化
 * <p>
 * dp2px：dp转px
 * px2dp：px转dp
 * sp2px：sp转px
 * px2sp：px2sp
 */
public class LSizeX {

    public static  int   WIDTH          = getDisplayMetrics().widthPixels;
    public static  int   HEIGHT         = getDisplayMetrics().heightPixels;
    private static float DENSITY        = getDisplayMetrics().density;
    private static float SCALED_DENSITY = getDisplayMetrics().scaledDensity;

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

    /**
     * sp转px
     *
     * @param spVal sp 值
     * @return px 值
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getDisplayMetrics());
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

}
