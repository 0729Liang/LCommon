package com.liang.lcommon.exts;

import android.app.Activity;
import android.content.Intent;
import com.liang.lcommon.demo.blog.eventbus.EventBusDemo;
import com.liang.lcommon.demo.blog.fragment.FragmentActivity;
import com.liang.lcommon.demo.blog.lineChart.LineChartDemo;
import com.liang.lcommon.demo.blog.popupwindow.PopupWindowDemo;
import com.liang.lcommon.demo.utils.ActivityStartAnimDemo;
import com.liang.lcommon.demo.utils.AnnotationDemo;
import com.liang.lcommon.demo.utils.BarUiDemo;
import com.liang.lcommon.demo.utils.LKVMgrDemo;
import com.liang.lcommon.demo.utils.LogDemo;
import com.liang.lcommon.demo.view.CircleSeekBarDemo;
import com.liang.lcommon.demo.view.RockerActivityDemo;
import com.liang.lcommon.demo.view.SettingViewDemo;

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

    public static void startRockerDemo(Activity activity) {
        RockerActivityDemo.startActivity(activity);
    }

    public static void startSettingViewDemo(Activity activity) {
        SettingViewDemo.startActivity(activity);
    }

    public static void startLKVMgrDemoDemo(Activity activity) {
        LKVMgrDemo.startActivity(activity);
    }

    public static void startLLogDemoDemo(Activity activity) {
        LogDemo.startActivity(activity);
    }

    public static void startBarUiDemo(Activity activity) {
        BarUiDemo.startActivity(activity);
    }

    public static void startAnnotationDemo(Activity activity) {
        AnnotationDemo.startActivity(activity);
    }

    public static void startEventBusDemo(Activity activity) {
        EventBusDemo.startActivity(activity);
    }

    public static void startFragmentDemo(Activity activity) {
        FragmentActivity.startActivity(activity);
    }

    public static void startLineChartDemo(Activity activity) {
        LineChartDemo.startActivity(activity);
    }

    public static void startPopupWindowDemo(Activity activity) {
        PopupWindowDemo.startActivity(activity);
    }
}
