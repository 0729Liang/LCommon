package com.liang.lcommon.init;

import android.app.Application;
import android.content.Context;

import com.liang.lcommon.config.LAppBuildConfig;
import com.liang.lcommon.utils.LSizeX;

/**
 * @author : Amarao
 * CreateAt : 15:12 2019/1/10
 * Describe :
 */
public class LCommon {
    private static Exports exports = new Exports();

    public static Context app()
    {
        return exports.app;
    }

    public static LAppBuildConfig appConfig()
    {
        return exports.appConfig;
    }

    public static void init(Application application, Class paramClass)
    {
        exports.init = true;
        exports.app = application;
        LSizeX.init();
    }

    public Context getContext()
    {
        return exports.app;
    }
}
