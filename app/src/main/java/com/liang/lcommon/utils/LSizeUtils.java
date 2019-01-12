package com.liang.lcommon.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author : Amarao
 * CreateAt : 22:23 2019/1/12
 * Describe : dp sp px 之间的转化
 * <p>
 * dp2px：dp转px
 * px2dp：px转dp
 * sp2px：sp转px
 * px2sp：px2sp
 */
public class LSizeUtils {
    /**
     * dp转px
     *
     * @param dpVal dp值
     * @return px值
     */
    public static int dp2px(float dpVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal px 值
     * @return dp值
     */
    public static float px2dp(float pxVal, Context context) {
        return pxVal / context.getResources().getDisplayMetrics().density;
    }

    /**
     * sp转px
     *
     * @param spVal sp 值
     * @return px 值
     */
    public static int sp2px(float spVal, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxVal px值
     * @return sp值
     */
    public static float px2sp(float pxVal, Context context) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
