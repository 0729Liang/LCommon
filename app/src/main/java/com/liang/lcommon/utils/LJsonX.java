package com.liang.lcommon.utils;

import android.text.TextUtils;

import com.liang.lcommon.adapter.LJsonAdapter;
import com.liang.lcommon.init.LCommon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/6/17
 * Describe : Json 解析转换
 *
 * @author chendong
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
}
