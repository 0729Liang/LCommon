package com.liang.lcommon.activity.demo.utils.ant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Amarao
 * CreateAt : 14:37 2019/2/14
 * Describe : 字段注解
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldLog {
}
