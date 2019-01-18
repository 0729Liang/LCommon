package com.liang.lcommon.exts;

import android.app.Activity;

import com.liang.lcommon.activity.ActivityStartAnimDemo;
import com.liang.lcommon.activity.RockerActivity;
import com.liang.lcommon.activity.SettingViewActivity;

/**
 * @author : Amarao
 * CreateAt : 13:17 2019/1/18
 * Describe : 跳转的封装，为了更好的页面解耦
 */
public class LRouter {

    public static void startActivityAnimDemo(Activity activity, int type) {
        ActivityStartAnimDemo.startTextActivity(activity, type);
    }

    public static void startRockerActivity(Activity activity) {
        RockerActivity.startActivity(activity);
    }

    public static void startSettingViewActivity(Activity activity){
        SettingViewActivity.startActivity(activity);
    }
}
