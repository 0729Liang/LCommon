package com.liang.lcommon.activity.demo.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.liang.lcommon.activity.demo.utils.ant.ClassLog;
import com.liang.lcommon.activity.demo.utils.ant.FieldLog;
import com.liang.lcommon.activity.demo.utils.ant.MethodLog;
import com.liang.lcommon.R;
import com.liang.lcommon.activity.demo.utils.ant.TestLogProcress;
import com.liang.lcommon.activity.main.LBaseItemBean;
import com.liang.lcommon.base.LAppActivity;
import com.liang.lcommon.exts.LRouter;

@ClassLog(name = "AnnotationDemo", age = 22)
public class AnnotationDemo extends LAppActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AnnotationDemo.class));
    }

    public static LBaseItemBean newItem() {
        String title = "自定义注解";
        String intro = "自定义注解\n类注解\n方法注解\n字段注解\n解析成功会打印日志";
        int src = R.drawable.src_null;
        LBaseItemBean bean = new LBaseItemBean(title, intro, src);
        bean.setClickEvent(getClickEvent());
        bean.setType(LBaseItemBean.BeanType.UTILS);
        return bean;
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
        setContentView(R.layout.demo_activity_annotation);


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
