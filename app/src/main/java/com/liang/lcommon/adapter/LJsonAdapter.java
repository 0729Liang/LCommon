package com.liang.lcommon.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.liang.lcommon.utils.LLogX;
import com.march.common.exts.LogX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            LLogX.e("枚举实现单例模式");
        }

        public LJsonAdapter getAdapter() {
            return mAdapter;
        }
    }

    public static LJsonAdapter getAdapter() {
        return Singleton.INSTANCE.getAdapter();
    }

    private LJsonAdapter() {
        LLogX.e("实例化");
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


    public <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz) {
        return sGson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());
    }
}
