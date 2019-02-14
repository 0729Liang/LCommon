package com.liang.lcommon.ant;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.liang.liangutils.utils.LLogX;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author : Amarao
 * CreateAt : 14:39 2019/2/14
 * Describe :
 * 参考：https://blog.csdn.net/wuyuxing24/article/details/81139846
 */
public class TestLogProcress {

    public static void fieldBind(final Activity activity) {
        Class parentClass = activity.getClass();
        Field[] fields = parentClass.getDeclaredFields();
        for (final Field field : fields) {
            FieldLog fieldLog = field.getAnnotation(FieldLog.class);
            if (fieldLog != null) {
                LLogX.e("field " + field.getName());
            } else {
//                LLogX.e("field " + field.getName() + " = NULL");
            }
        }
    }

    public static void methodBind(final Activity activity) {
        Class clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (final Method method : methods) {
            MethodLog methodLog = method.getAnnotation(MethodLog.class);
            if (methodLog != null) {
                LLogX.e(method.getName() + " name = " + methodLog.name() + " age = " + methodLog.age());
            } else {
                //LLogX.e(method.getName() + " method = NULL");
            }
        }
    }

    public static void classLog(final Activity activity) {
        ClassLog classLog = activity.getClass().getAnnotation(ClassLog.class);
        if (classLog != null) {
            LLogX.e(" name = " + classLog.name() + " age = " + classLog.age());
        } else {
            //LLogX.e(method.getName() + " method = NULL");
        }
    }
}

