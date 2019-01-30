package com.liang.lcommon;

import android.app.Application;

import com.liang.liangutils.init.LCommon;
import com.march.common.Common;

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
        Common.init(this, BuildConfig.class);
    }
}
