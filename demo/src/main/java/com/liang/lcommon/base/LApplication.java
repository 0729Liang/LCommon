package com.liang.lcommon.base;

import android.app.Application;
import android.content.Context;

import com.liang.lcommon.BuildConfig;
import com.liang.liangutils.init.LCommon;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author : Amarao
 * CreateAt : 15:02 2019/1/10
 * Describe :
 */
public class LApplication extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        LCommon.init(this, BuildConfig.class);

        initLeakCanary(); // 初始化LeakCanary

    }

    /**
     * 描述：初始化LeakCanary
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    /**
     * 描述：得到LeakCanary对象，用于检测fragment
     */
    public static RefWatcher getRefWatcher(Context context) {
        LApplication application = (LApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }


}
