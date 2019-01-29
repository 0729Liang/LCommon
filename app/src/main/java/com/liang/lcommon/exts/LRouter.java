package com.liang.lcommon.exts;

import android.app.Activity;
import android.content.Intent;

import com.liang.lcommon.activity.demo.ActivityStartAnimDemo;
import com.liang.lcommon.activity.demo.BarUiDemo;
import com.liang.lcommon.activity.demo.CircleSeekBarDemo;
import com.liang.lcommon.activity.demo.LKVMgrDemo;
import com.liang.lcommon.activity.demo.LLogDemo;
import com.liang.lcommon.activity.demo.RockerActivityDemo;
import com.liang.lcommon.activity.demo.SettingViewDemo;

/**
 * @author : Amarao
 * CreateAt : 13:17 2019/1/18
 * Describe : 跳转的封装，为了更好的页面解耦
 */
public class LRouter {

    public static void startActivityAnimDemo(Activity activity, ActivityStartAnimDemo.Anim type) {
        ActivityStartAnimDemo.Companion.startActivity(activity, type);
    }

    public static void startActivityAnimDemo(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityStartAnimDemo.class));
    }

    public static void startCircleSeekBarDemo(Activity activity) {
        CircleSeekBarDemo.startActivity(activity);
    }

    public static void startRockerActivity(Activity activity) {
        RockerActivityDemo.startActivity(activity);
    }

    public static void startSettingViewActivity(Activity activity) {
        SettingViewDemo.startActivity(activity);
    }

    public static void startLKVMgrDemoActivity(Activity activity) {
        LKVMgrDemo.startActivity(activity);
    }

    public static void startLLogDemoAcvtvity(Activity activity) {
        LLogDemo.startActivity(activity);
    }

    public static void startBarUiDemo(Activity activity){
        BarUiDemo.startActivity(activity);
    }
}
