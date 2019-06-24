package com.liang.lcommon.activity.main2;

import android.widget.Toast;

import com.liang.liangutils.init.LCommon;

/**
 * Auther: amarao
 * CreateAt: 2019-06-24
 * Describer: ToastUtils
 */
public class ToastUtils {

    public static void showShort(String s) {
        Toast.makeText(LCommon.getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
