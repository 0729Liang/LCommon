package com.liang.lcommon.mgrs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import com.liang.lcommon.init.LCommon;
import com.liang.lcommon.utils.LJsonX;
import com.liang.lcommon.utils.LLogX;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Amarao
 * CreateAt : 13:26 2019/1/17
 * Describe : key-value 管理工具
 * 参考：
 * mmkv git：https://github.com/Tencent/MMKV
 * mmkv 文档：https://github.com/Tencent/MMKV/wiki/android_tutorial_cn
 * SparseArray：https://www.cnblogs.com/RGogoing/p/5095168.html
 */
public class LKVMgr implements LIMgr {
    private static final int              STRATEGY_MMKV   = 0; // tencent mmvk 实现 （保存到本地）
    private static final int              STRATEGY_SP     = 1; // sharePerfence 实现 （保存到本地）
    private static final int              STRATEGY_MEMORY = 2; // hasmap实现（保存到内存）
    private static       SparseArray<IKV> sIKVSparseArray = new SparseArray<>();

    private LKVMgr() {
    }

    @Override
    public void recycle() {
        sIKVSparseArray.clear();
    }

    @Override
    public boolean isRecycled() {
        return false;
    }

    /**
     * LKVMgr  LKVMgr参数，限制LKVMgr的实例类型
     * STRATEGY_MMKV   = 0; // tencent mmvk 实现 （保存到本地）
     * STRATEGY_SP     = 1; // sharePerfence 实现 （保存到本地）
     * STRATEGY_MEMORY = 2; // hasmap实现（保存到内存）
     */
    public static enum LKVMgrParams {
        /**
         * LKVMgr  LKVMgr参数，限制LKVMgr的实例类型
         */
        STRATEGY_MMKV("MMKV", 0),
        STRATEGY_SP("Sharedpreferences", 1),
        STRATEGY_MEMORY("Memory", 2);

        private String name;
        private int    strategy;

        LKVMgrParams(String name, int strategy) {
            this.name = name;
            this.strategy = strategy;
        }

        public String getName() {
            return name;
        }

        public int getStrategy() {
            return strategy;
        }

    }

    /**
     * key-value 操作接口
     */
    public interface IKV {

        // int
        int getInt(String key);

        int getInt(String key, int defaultValue);

        IKV putInt(String key, int value);

        //float
        float getFloat(String key);

        float getFloat(String key, float defaultValue);

        IKV putFloat(String key, float value);

        //long
        long getLong(String key);

        long getLong(String key, long defaultValue);

        IKV putLong(String key, long value);

        //boolean
        boolean getBool(String key);

        boolean getBool(String key, boolean defaultValue);

        IKV putBool(String key, boolean value);

        //String
        String getString(String key);

        String getString(String key, String defaultValue);

        IKV putString(String key, String value);

        //String sets
        Set<String> getStringSets(String key);

        IKV putStringSets(String key, Set<String> value);

        //obj list map
        <T> T getObj(String key, Class<T> clazz);

        <T> List<T> getList(String key, Class<T> clazz);

        <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz);

        IKV putObj(String key, Object value);
    }

    /**
     * 获取一个 key-value 操作类
     *
     * @return IKV
     */
    public static IKV getInstance() {
        return getInstance(LCommon.getKvStrategy());
    }


    /**
     * 以指定 strategy 获取 key-value 操作类
     *
     * @param params 策略参数
     * @return IKV
     */
    public static IKV getInstance(LKVMgrParams params) {
        int strategy = params.getStrategy();
        LLogX.e(strategy);
        IKV ikv = sIKVSparseArray.get(strategy);
        if (ikv != null) {
            return ikv;
        }
        switch (params) {
            case STRATEGY_MMKV:
                ikv = new MMKVImpl(LCommon.getApp());
                break;
            case STRATEGY_SP:
                ikv = new SpKvImpl(LCommon.getApp());
                break;
            case STRATEGY_MEMORY:
                ikv = new MemoryKvImpl();
                break;
            default:
        }
        sIKVSparseArray.put(params.getStrategy(), ikv);
        return ikv;
    }

    // 基于 Tencent MMKV 的实现
    static class MMKVImpl implements IKV {

        MMKVImpl(Context context) {
            String initialize = MMKV.initialize(context);
            LLogX.e("path = " + initialize);
        }

        private MMKV kv() {
            //MMKV 提供一个全局的实例，可以直接使用
            return MMKV.defaultMMKV();
        }

        @Override
        public int getInt(String key, int defaultValue) {
            return kv().getInt(key, defaultValue);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            return kv().getFloat(key, defaultValue);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            return kv().getLong(key, defaultValue);
        }

        @Override
        public boolean getBool(String key, boolean defaultValue) {
            return kv().getBoolean(key, defaultValue);
        }

        @Override
        public String getString(String key, String defaultValue) {
            return kv().getString(key, defaultValue);
        }

        @Override
        public Set<String> getStringSets(String key) {
            HashSet<String> strings = new HashSet<>();
            return kv().getStringSet(key, strings);
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            return LJsonX.toObj(getString(key), clazz);
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            return LJsonX.toList(getString(key), clazz);
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            return LJsonX.toMap(getString(key), kClazz, vClazz);
        }

        @Override
        public IKV putInt(String key, int value) {
            kv().putInt(key, value);
            return this;
        }

        @Override
        public IKV putFloat(String key, float value) {
            kv().putFloat(key, value);
            return this;
        }

        @Override
        public IKV putLong(String key, long value) {
            kv().putLong(key, value);
            return this;
        }

        @Override
        public IKV putBool(String key, boolean value) {
            kv().putBoolean(key, value);
            return this;
        }

        @Override
        public IKV putString(String key, String value) {
            kv().putString(key, value);
            return this;
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            kv().putStringSet(key, value);
            return this;
        }

        @Override
        public IKV putObj(String key, Object value) {
            kv().putString(key, LJsonX.toJson(value));
            return this;
        }

    }

    // 基于 SharePreference 实现
    static class SpKvImpl implements IKV {

        private final SharedPreferences mSp;

        public SpKvImpl(Context context) {
            mSp = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public int getInt(String key, int defaultValue) {
            return mSp.getInt(key, defaultValue);
        }

        @Override
        public IKV putInt(String key, int value) {
            mSp.edit().putInt(key, value).apply();
            return this;
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            return mSp.getFloat(key, defaultValue);
        }

        @Override
        public IKV putFloat(String key, float value) {
            mSp.edit().putFloat(key, value).apply();
            return this;
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            return mSp.getLong(key, defaultValue);
        }

        @Override
        public IKV putLong(String key, long value) {
            mSp.edit().putLong(key, value).apply();
            return this;
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
        }

        @Override
        public boolean getBool(String key, boolean defaultValue) {
            return mSp.getBoolean(key, defaultValue);
        }

        @Override
        public IKV putBool(String key, boolean value) {
            mSp.edit().putBoolean(key, value).apply();
            return this;
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public String getString(String key, String defaultValue) {
            return mSp.getString(key, defaultValue);
        }

        @Override
        public IKV putString(String key, String value) {
            mSp.edit().putString(key, value).apply();
            return this;
        }

        @Override
        public Set<String> getStringSets(String key) {
            return mSp.getStringSet(key, new HashSet<>());
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            mSp.edit().putStringSet(key, value).apply();
            return this;
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            return LJsonX.toObj(getString(key), clazz);
        }

        @Override
        public IKV putObj(String key, Object value) {
            return putString(key, LJsonX.toJson(value));
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            return LJsonX.toList(getString(key), clazz);
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            return LJsonX.toMap(getString(key), kClazz, vClazz);
        }
    }

    // 基于内存实现
    static class MemoryKvImpl implements IKV {

        private Map<String, Object> mObjectMap;

        public MemoryKvImpl() {
            mObjectMap = new HashMap<>();
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public int getInt(String key, int defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Integer) {
                return (int) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putInt(String key, int value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Float) {
                return (float) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putFloat(String key, float value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Long) {
                return (long) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putLong(String key, long value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
        }

        @Override
        public boolean getBool(String key, boolean defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Boolean) {
                return (boolean) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putBool(String key, boolean value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public String getString(String key, String defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof String) {
                return (String) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putString(String key, String value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public Set<String> getStringSets(String key) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Set) {
                return (Set<String>) o;
            }
            return new HashSet<>();
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o.getClass().equals(clazz)) {
                return (T) o;
            }
            return null;
        }

        @Override
        public IKV putObj(String key, Object value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof List) {
                return (List<T>) o;
            }
            return null;
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Map) {
                return (Map<K, V>) o;
            }
            return null;
        }
    }
}
