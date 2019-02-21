package com.liang.liangutils.init;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.liang.liangutils.adapter.LJsonAdapter;
import com.liang.liangutils.config.LAppBuildConfig;
import com.liang.liangutils.mgrs.LKVMgr;

/**
 * @author : Amarao
 * CreateAt : 15:12 2019/1/10
 * Describe :
 */
public class LCommon {

    private static Exports exports = new Exports();

    public static LAppBuildConfig appConfig() {
        return exports.appConfig;
    }

    public static void init(Application application, Class buildClazz) {
        exports.init = true;
        exports.app = application;
        exports.appConfig = new LAppBuildConfig(buildClazz);
        exports.kvStrategy = LKVMgr.LKVMgrParams.STRATEGY_MMKV;
        exports.jsonParser = LJsonAdapter.getAdapter();
    }

    public LCommon(Activity activity) {
        exports.init = true;
        exports.app = activity.getApplication();
        exports.kvStrategy = LKVMgr.LKVMgrParams.STRATEGY_MMKV;
        exports.jsonParser = LJsonAdapter.getAdapter();
    }

    public static Exports getExports() {
        return exports;
    }

    public static Context getContext() {
        return exports.app;
    }

    public static Context getApp() {
        return exports.app;
    }

    public static LKVMgr.LKVMgrParams getKvStrategy() {
        return exports.kvStrategy;
    }

    public static LJsonAdapter getJsonAdapter() {
        return exports.jsonParser;
    }

//    public static LAppBuildConfig getAppBuildConfig() {
//        return exports.appConfig;
//    }

}
