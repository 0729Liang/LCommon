package com.liang.lcommon.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.liang.lcommon.utils.LLogX;
import com.liang.lcommon.utils.LMapX;
import com.march.common.exts.LogX;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CreateAt : 2018/4/3
 * Describe :
 *
 * @author chendong
 */
public class LJsonAdapter {

    private enum Singleton {
        /**
         * 枚举实现单例模式
         */
        INSTANCE;

        private LJsonAdapter mAdapter;

        Singleton() {
            mAdapter = new LJsonAdapter();
        }

        public LJsonAdapter getAdapter() {
            return mAdapter;
        }
    }

    public static LJsonAdapter getAdapter() {
        return Singleton.INSTANCE.getAdapter();
    }

    private LJsonAdapter() {
    }

    private Gson sGson = new Gson();

    public String toJson(Object object) {
        return sGson.toJson(object);
    }

    public <T> T toObj(String json, Class<T> cls) {
        return sGson.fromJson(json, cls);
    }

    public <T> List<T> toList(String json, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(new Gson().fromJson(elem, clazz));
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public <K, V> Map<K, V> toMap(String kJson, String vJson, Class<K> kClazz, Class<V> vClazz) {
        Map<K, V> map = new HashMap<>();
        try {
            List<K> kList = toList(kJson, kClazz);
            List<V> vList = toList(vJson, vClazz);
            for (int i = 0; i < kList.size(); i++) {
                K k = kList.get(i);
                V v = vList.get(i);
                map.put(k, v);
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

    public <V> Map<String, V> toStringKeyMap(String json, Class<V> vClazz) {
        Map<String, V> map = new HashMap<>();
        try {
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject value = (JsonObject) entry.getValue();
                V value1 = sGson.fromJson(value, vClazz);
                map.put(entryKey, value1);
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

    public <K, V> String mapKeyToJson(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        List<K> keyList = LMapX.getMapKeyList(map, kClazz, vClazz);
        return toJson(keyList);
    }

    public <K, V> String mapValueToJson(Map<K, V> map, Class<K> kClazz, Class<V> vClazz) {
        List<V> valueList = LMapX.getMapValueList(map, kClazz, vClazz);
        return toJson(valueList);
    }

    public <K, V> String mapValueToJson(Map<K, V> map, List<K> kList, Class<K> kClazz, Class<V> vClazz) {
        //List<V> valueList = LMapX.getMapValueList(map, kClazz, vClazz);
        List<V> vList = LMapX.getMapValueListByKey(map, kList, kClazz, vClazz);
        return toJson(vList);
    }

    public <V> Map<String, V> parseMap(String json, Class<V> vClass) {
        Map<String, V> map = new HashMap<>();
        try {
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject value = (JsonObject) entry.getValue();
                V value1 = sGson.fromJson(value, vClass);
                map.put(entryKey, value1);
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

    // TODO: 2019/1/24   map 类型，泛型被擦出，待处理
    // TODO: 2019/1/24   LOG：com.google.gson.internal.LinkedTreeMap cannot be cast to com.liang.lcommon.activity.demo.LKVMgrDemo$Personal
    public <K, V> Map<K, V> toMap2(String json, Class<K> kClazz, Class<V> vClazz) {
        return sGson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());
    }
}
