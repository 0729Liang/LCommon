package com.liang.liangutils.init;

import android.app.Application;

import com.liang.liangutils.adapter.LJsonAdapter;
import com.liang.liangutils.config.LAppBuildConfig;
import com.liang.liangutils.mgrs.LKVMgr;

/**
 * @author : Amarao
 * CreateAt : 15:12 2019/1/10
 * Describe :
 */
public class Exports {
    public Application     app;
    public LAppBuildConfig appConfig;
    public LJsonAdapter    jsonParser;
    public boolean         init;

    public LKVMgr.LKVMgrParams kvStrategy;
}
