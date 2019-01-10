package com.liang.lcommon.config;

import com.liang.lcommon.init.LCommon;

import java.lang.reflect.Field;

/**
 * @author : Amarao
 * CreateAt : 15:11 2019/1/10
 * Describe :
 */

public class LAppBuildConfig {
    public static final String DEV_CHANNEL = "dev";

    public boolean DEBUG;
    public String  APPLICATION_ID;
    public String  BUILD_TYPE;
    public String  FLAVOR;
    public int     VERSION_CODE;
    public String  VERSION_NAME;
    public String  CHANNEL;

    public LAppBuildConfig(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                switch (declaredField.getName()) {
                    case "DEBUG":
                        DEBUG = (boolean) declaredField.get(clazz);
                        break;
                    case "APPLICATION_ID":
                        APPLICATION_ID = (String) declaredField.get(clazz);
                        break;
                    case "BUILD_TYPE":
                        BUILD_TYPE = (String) declaredField.get(clazz);
                        break;
                    case "FLAVOR":
                        FLAVOR = (String) declaredField.get(clazz);
                        break;
                    case "VERSION_CODE":
                        VERSION_CODE = (int) declaredField.get(clazz);
                        break;
                    case "VERSION_NAME":
                        VERSION_NAME = (String) declaredField.get(clazz);
                        break;
                }
            } catch (IllegalAccessException e) {
                //LogX.e(e);
                e.printStackTrace();
            }
        }
    }

    public boolean isDev() {
        return LCommon.appConfig().DEBUG || DEV_CHANNEL.equals(LCommon.appConfig().CHANNEL);
    }

    public boolean isDebug() {
        return LCommon.appConfig().DEBUG;
    }
}