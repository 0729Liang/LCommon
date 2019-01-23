package com.liang.lcommon.app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.liang.lcommon.init.LCommon;

import java.lang.ref.WeakReference;

/**
 * @author : Amarao
 * CreateAt : 10:44 2019/1/22
 * Describe : 基类
 */
public class LAppActivity extends AppCompatActivity {

    public Context  mContext;
    public Activity mActivity;

    public LAppActivity() {
        WeakReference reference = new WeakReference(this);
        mContext = LCommon.getApp();
        mActivity = (Activity) reference.get();
    }
}
