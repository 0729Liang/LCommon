package com.liang.liangutils.utils;


import com.liang.liangutils.libs.utils.LLogX;

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
 * getMapValueListByKey 通过指定顺序的keyList得到valueList
 * printMap 打印Map的 k v
 */
public class LMapX {

    /**
     * @param map map对象
     * @param <K> k类型
     * @param <V> v类型
     * @return keyList
     */
    public static <K, V> List<K> getMapKeyList(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        List<K> keyList = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            keyList.add(entry.getKey());
        }
        return keyList;
    }

    /**
     * @param map map对象
     * @param <K> k类型
     * @param <V> v类型
     * @return valueList
     */
    public static <K, V> List<V> getMapValueList(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        List<V> valueList = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            valueList.add(entry.getValue());
        }
        return valueList;
    }

    /**
     * @param map     map对象
     * @param keyList 由map的key组成的keyList
     * @param <K>     k类型
     * @param <V>     v类型
     * @return valueList
     */
    public static <K, V> List<V> getMapValueListByKey(Map<K, V> map, List<K> keyList, Class<K> kClazz, Class<V> vClazz) {
        List<V> valueList = new ArrayList<>();

        for (K k : keyList) {
            V v = map.get(k);
            valueList.add(v);
        }

        return valueList;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        if (map == null) {
            return;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            K entryKey = entry.getKey();
            V value = entry.getValue();
            LLogX.e(" key = " + entryKey + " value = " + value);
        }
    }
}
