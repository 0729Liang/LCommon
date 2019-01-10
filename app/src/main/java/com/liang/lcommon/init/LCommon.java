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
    public static Exports exports = new Exports();

    public static Context app()
    {
        return exports.app;
    }

    public static LAppBuildConfig appConfig()
    {
        return exports.appConfig;
    }

    public static void init(Application paramApplication, Class paramClass)
    {
        exports.init = true;
        exports.app = paramApplication;
        LSizeX.init();
    }

    public Context getContext()
    {
        return exports.app;
    }
}
