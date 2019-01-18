package com.liang.lcommon.init;

import android.app.Application;

import com.liang.lcommon.adapter.LJsonAdapter;
import com.liang.lcommon.config.LAppBuildConfig;
import com.liang.lcommon.mgrs.LKVMgr;

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
