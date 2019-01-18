package com.liang.lcommon.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;


/**
 * @author : Amarao
 * CreateAt : 18:32 2019/1/12
 * Describe : 得到颜色和图片
 * <p>
 * createRoundRectDrawable 创建圆角矩形
 * getDrawable 获取drawable 资源
 * getColor 得到颜色
 * parseColor 解析颜色
 */
public class LResourceX {

    /**
     * 创建圆角矩形
     *
     * @param color      颜色
     * @param radiusInDp 半径
     * @return drawable
     */
    public static Drawable createRoundRectDrawable(int color, int radiusInDp) {
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(color);
        gd.setCornerRadius(LSizeX.dp2px(radiusInDp));
        return gd;
    }

    /**
     * @param drawableRes drawable 资源
     * @return 获取 drawable
     */
    public static Drawable getDrawable(Context context, int drawableRes) {
        return ContextCompat.getDrawable(context, drawableRes);
    }

    public static Drawable getDrawable(Activity activity, int drawableRes) {
        return ContextCompat.getDrawable(activity, drawableRes);
    }

    /**
     * @param colorRes 得到颜色
     * @return color
     */
    public static int getColor(Context context, int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }

    public static int getColor(Activity activity, int colorRes) {
        return ContextCompat.getColor(activity, colorRes);
    }

    /**
     * 解析颜色
     *
     * @param color '#345678'
     * @param def   失败后默认颜色
     * @return color
     */
    public static int parseColor(String color, int def) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 解析颜色
     *
     * @param color '#345678'
     * @return color
     */
    public static int parseColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            return Color.WHITE;
        }

    }
}
