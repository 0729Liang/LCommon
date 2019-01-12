package com.liang.lcommon.utils;

import android.text.TextUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : 判空工具类
 */
public class LEmptyX {

    public static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static boolean isAnyEmpty(CharSequence... charSequences) {
        for (CharSequence charSequence : charSequences) {
            if (isEmpty(charSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(File file) {
        return file == null || LFileX.isNotExist(file);
    }

    public static boolean isEmpty(Integer integer) {
        return integer == null || integer == 0;
    }

    public static boolean isEmpty(Long l) {
        return l == null || l == 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || isEmpty(map.keySet());
    }

}
