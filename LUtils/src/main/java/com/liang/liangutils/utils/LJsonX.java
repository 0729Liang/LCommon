package com.liang.liangutils.utils;

import android.text.TextUtils;

import com.google.gson.internal.LinkedTreeMap;
import com.liang.liangutils.adapter.LJsonAdapter;
import com.liang.liangutils.init.LCommon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author : Amarao & chendong
 * CreateAt : 15:27 2018/6/17
 * Describe : json相关操作
 * <p>
 * toJson 转化为json
 * toObj json转化为object
 * toList json转化为List
 * toStringKeyMap json转化为Map<String,V>
 * toMap json 转化为Map
 */
public class LJsonX {

    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return "";
        }
        return jsonAdapter.toJson(object);
    }

    public static <T> T toObj(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toObj(json, clazz);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toList(json, clazz);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz) {
        if (TextUtils.isEmpty(json) || kClazz == null || vClazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toMap(json, kClazz, vClazz);
    }

    public static <V> Map<String, V> toStringKeyMap(String json, Class<V> vClazz) {
        if (TextUtils.isEmpty(json) || vClazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toStringKeyMap(json, vClazz);
    }


    public static String toJsonString(String json, String def) {
        try {
            if (json.startsWith("[")) {
                return new JSONArray(json).toString(2).replace("\\/", "/");
            } else if (json.startsWith("{")) {
                return new JSONObject(json).toString(2).replace("\\/", "/");
            }
        } catch (JSONException e) {
            // e.printStackTrace();
            return def;
        }
        return def;
    }

    public static <K, V> String mapKeyToJson(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        if (map == null || kClazz == null || vClazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.mapKeyToJson(map, kClazz, vClazz);
    }

    public static <K, V> String mapValueToJson(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        if (map == null || kClazz == null || vClazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.mapValueToJson(map, kClazz, vClazz);
    }

    public static <K, V> String mapValueToJson(Map<K, V> map, List<K> kList, Class<K> kClazz, Class<V> vClazz) {
        if (map == null || kList == null || kClazz == null || vClazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.mapValueToJson(map, kList, kClazz, vClazz);
    }

    public static <K, V> Map<K, V> toMap(String kJson, String vJson, Class<K> kClazz, Class<V> vClazz) {
        if (TextUtils.isEmpty(kJson) || TextUtils.isEmpty(vJson)
                || kClazz == null || vClazz == null) {
            return null;
        }
        LJsonAdapter jsonAdapter = LCommon.getJsonAdapter();
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toMap(kJson, vJson, kClazz, vClazz);
    }
}
