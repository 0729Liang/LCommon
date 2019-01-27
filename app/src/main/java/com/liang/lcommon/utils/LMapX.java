package com.liang.lcommon.utils;

import com.liang.lcommon.activity.demo.RockerActivityDemo;
import com.march.common.exts.LogX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Amarao
 * CreateAt : 15:27 2019/1/27
 * Describe : map的相关操作
 * <p>
 * getMapKeyList 得到由Map的 k 组成的 list
 * getMapValueList 得到由Map的 v 组成的list
 * printMap 打印Map的 k v
 */
public class LMapX {

    public static <K, V> List<K> getMapKeyList(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        List<K> keyList = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            keyList.add(entry.getKey());
        }
        return keyList;
    }

    public static <K, V> List<V> getMapValueList(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        List<V> valueList = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            valueList.add(entry.getValue());
        }
        return valueList;
    }

    private <K, V> void printMap(Map<K, V> map) {
        if (map == null) {
            return;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            K entryKey = entry.getKey();
            V value = entry.getValue();
            LogX.e(" key = " + entryKey + " value = " + value);
        }
    }
}
