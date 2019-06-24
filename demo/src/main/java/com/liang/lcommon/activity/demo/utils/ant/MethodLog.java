package com.liang.lcommon.activity.demo.utils.ant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Amarao
 * CreateAt : 14:47 2019/2/14
 * Describe : 方法注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {
    String name() default "method";

    int age() default 13;
}
