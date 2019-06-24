package com.liang.liangutils.libs.utils;

import android.app.Activity;
import android.view.animation.AnimationSet;

import com.liang.liangutils.R;

/**
 * @author : Amarao
 * CreateAt : 19:07 2019/1/12
 * Describe : activity 启动动画类
 * <p>
 * Activity切换动画效果的常用方法
 * 1.使用overridePendingTransition
 * 2.使用Style定义动画
 * <p>
 * 启动动画方法，用在startActivity后
 * fadeInAnim  淡入
 * scaleInAnim 放大进
 * translateLeftIntAnim 左进
 * translateRightInAnim 右进
 * translateTopInAnim 上进
 * translateBottomInAnim 低进
 * rotatePositiveInAnim 顺时针进
 * rotateReverseInAnim 逆时针进
 * <p>
 * 退出动画方法，用在finished方法里面
 * fadeOutAnim 淡出
 * scaleOutAnim 缩小出
 * translateLeftOutAnim 左出
 * translateRightOutAnim 右出
 * translateTopOutAnim 上出
 * translateBottomOutAnim 低出
 * rotatePositiveOutAnim 顺时针出
 * rotateReverseOutAnim 逆时针出
 */
public class LActivityAnimX {

    public static class ActivityAnimConfig {
        private static int ACT_ANIM_NULL                = R.anim.act_no_anim;
        private static int ACT_ANIM_FADE_IN             = R.anim.act_fade_in;
        private static int ACT_ANIM_FADE_OUT            = R.anim.act_fade_out;
        private static int ACT_ANIM_LEFT_IN             = R.anim.act_translate_left_in;
        private static int ACT_ANIM_LEFT_OUT            = R.anim.act_translate_left_out;
        private static int ACT_ANIM_RIGHT_IN            = R.anim.act_translate_right_in;
        private static int ACT_ANIM_RIGHT_OUT           = R.anim.act_translate_right_out;
        private static int ACT_ANIM_TOP_IN              = R.anim.act_translate_top_in;
        private static int ACT_ANIM_TOP_OUT             = R.anim.act_translate_top_out;
        private static int ACT_ANIM_BOTTOM_IN           = R.anim.act_translate_bottom_in;
        private static int ACT_ANIM_BOTTOM_OUT          = R.anim.act_translate_bottom_out;
        private static int ACT_ANIM_SCALE_IN            = R.anim.act_scale_in;
        private static int ACT_ANIM_SCALE_OUT           = R.anim.act_scale_out;
        private static int ACT_ANIM_ROTATE_POSITIVE_IN  = R.anim.act_positive_rotate_in;
        private static int ACT_ANIM_ROTATE_POSITIVE_OUT = R.anim.act_positive_rotate_out;
        private static int ACT_ANIM_ROTATE_REVERSE_IN   = R.anim.act_reverse_rotate_in;
        private static int ACT_ANIM_ROTATE_REVERSE_OUT  = R.anim.act_reverse_rotate_out;
    }

    public static void fadeInFadeOutActivity(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_FADE_IN, ActivityAnimConfig.ACT_ANIM_FADE_OUT);
    }

    public static void fadeInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_FADE_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }

    public static void fadeOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_FADE_OUT);
    }

    public static void translateLeftIntAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_LEFT_IN, ActivityAnimConfig.ACT_ANIM_NULL);

    }

    public static void translateLeftOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_LEFT_OUT);
    }

    public static void translateRightInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_RIGHT_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }


    public static void translateRightOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_RIGHT_OUT);
    }


    public static void translateTopInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_TOP_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }

    public static void translateTopOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_TOP_OUT);
    }

    public static void translateBottomInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_BOTTOM_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }

    public static void translateBottomOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_BOTTOM_OUT);
    }

    public static void scaleInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_SCALE_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }

    public static void scaleOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_SCALE_OUT);
    }

    public static void rotatePositiveInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_ROTATE_POSITIVE_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }

    public static void rotatePositiveOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_ROTATE_POSITIVE_OUT);
    }

    public static void rotateReverseInAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_ROTATE_REVERSE_IN, ActivityAnimConfig.ACT_ANIM_NULL);
    }

    public static void rotateReverseOutAnim(Activity activity) {
        activity.overridePendingTransition(ActivityAnimConfig.ACT_ANIM_NULL, ActivityAnimConfig.ACT_ANIM_ROTATE_REVERSE_OUT);
    }
}
