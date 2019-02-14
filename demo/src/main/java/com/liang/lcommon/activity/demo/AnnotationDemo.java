package com.liang.lcommon.activity.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.liang.lcommon.ant.ClassLog;
import com.liang.lcommon.ant.FieldLog;
import com.liang.lcommon.ant.MethodLog;
import com.liang.lcommon.R;
import com.liang.lcommon.ant.TestLogProcress;
import com.liang.lcommon.activity.LBaseItemBean;
import com.liang.lcommon.app.LAppActivity;
import com.liang.lcommon.exts.LRouter;
import com.liang.liangutils.utils.LLogX;

@ClassLog(name = "AnnotationDemo", age = 22)
public class AnnotationDemo extends LAppActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AnnotationDemo.class));
    }

    public static LBaseItemBean getItem() {
        String intro = "自定义注解";
        int src = R.drawable.src_null;
        return new LBaseItemBean(intro, src);
    }

    public static LBaseItemBean.ClickEvent getClickEvent() {
        return LRouter::startAnnotationDemo;
    }


    @FieldLog
    private int    v1 = 1;
    @FieldLog
    public  String v2 = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_demo);


        TestLogProcress.fieldBind(this);
        TestLogProcress.methodBind(this);
    }

    //界面初始化
    @MethodLog(name = "initView", age = 11)
    private void initView() {

    }

    //摇杆初始化
    @MethodLog
    public void initMyView() {
    }
}
