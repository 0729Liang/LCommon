package com.liang.lcommon.init;

import android.app.Application;
import android.content.Context;

import com.liang.lcommon.adapter.LJsonAdapter;
import com.liang.lcommon.config.JsonAdapterImpl;
import com.liang.lcommon.config.LAppBuildConfig;
import com.liang.lcommon.mgrs.LKVMgr;
import com.liang.lcommon.utils.LSizeUtils;
import com.liang.lcommon.utils.LSizeX;

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
        exports.jsonParser = new JsonAdapterImpl();
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

    public static LAppBuildConfig getAppBuildConfig() {
        return exports.appConfig;
    }

    public static LKVMgr.LKVMgrParams getKvStrategy() {
        return exports.kvStrategy;
    }

    public static LJsonAdapter getJsonAdapter() {
        return exports.jsonParser;
    }


}
