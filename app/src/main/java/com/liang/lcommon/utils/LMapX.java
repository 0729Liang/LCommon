package com.liang.lcommon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Amarao
 * CreateAt : 15:27 2019/1/27
 * Describe :
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


}
