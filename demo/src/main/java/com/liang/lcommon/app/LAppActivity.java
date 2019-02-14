package com.liang.lcommon.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liang.lcommon.R;
import com.liang.lcommon.ant.TestLogProcress;
import com.liang.liangutils.init.LCommon;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
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
        WeakReference<LAppActivity> reference = new WeakReference<>(this);
        mContext = LCommon.getApp();
        mActivity = reference.get();
        TestLogProcress.classLog(this);
    }
}
