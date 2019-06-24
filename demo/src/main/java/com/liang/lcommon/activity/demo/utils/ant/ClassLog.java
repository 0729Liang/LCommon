package com.liang.lcommon.activity.demo.utils.ant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Amarao
 * CreateAt : 15:18 2019/2/14
 * Describe : 类注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassLog {
    String name() default "class";

    int age() default 31;
}
