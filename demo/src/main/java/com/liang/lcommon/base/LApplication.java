package com.liang.lcommon.base;

import android.app.Application;

import com.liang.lcommon.BuildConfig;
import com.liang.liangutils.init.LCommon;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author : Amarao
 * CreateAt : 15:02 2019/1/10
 * Describe :
 */
public class LApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LCommon.init(this, BuildConfig.class);
        //Common.init(this, BuildConfig.class);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }




}
