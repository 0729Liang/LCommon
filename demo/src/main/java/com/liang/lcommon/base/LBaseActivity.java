package com.liang.lcommon.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.liang.liangutils.libs.utils.ant.TestLogProcress;
import com.liang.liangutils.init.LCommon;
import com.liang.liangutils.msg.Exts;

import java.lang.ref.WeakReference;

/**
 * @author : Amarao
 * CreateAt : 10:44 2019/1/22
 * Describe : 基类
 */
public class LBaseActivity extends AppCompatActivity {

    public Context                     mContext;
    public WeakReference<LBaseActivity> mActivity;

    public LBaseActivity() {
        TestLogProcress.classLog(this);
        Exts.registerEvent(this);
        mContext = LCommon.getApp();
        mActivity = new WeakReference<>(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Exts.unRegisterEvent(this);
    }
}
