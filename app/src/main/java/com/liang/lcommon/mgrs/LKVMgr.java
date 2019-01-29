package com.liang.lcommon.mgrs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import com.liang.lcommon.init.LCommon;
import com.liang.lcommon.utils.LJsonX;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Amarao & chendong
 * CreateAt : 13:26 2019/1/17
 * Describe : key-value 管理工具
 * 参考：
 * mmkv git：https://github.com/Tencent/MMKV
 * mmkv 文档：https://github.com/Tencent/MMKV/wiki/android_tutorial_cn
 * SparseArray：https://www.cnblogs.com/RGogoing/p/5095168.html
 * <p>
 * // int
 * int getInt(String key);
 * int getInt(String key, int defaultValue);
 * IKV putInt(String key, int value);
 * <p>
 * //float
 * float getFloat(String key);
 * float getFloat(String key, float defaultValue);
 * IKV putFloat(String key, float value);
 * <p>
 * //long
 * long getLong(String key);
 * long getLong(String key, long defaultValue);
 * IKV putLong(String key, long value);
 * <p>
 * //boolean
 * boolean getBool(String key);
 * boolean getBool(String key, boolean defaultValue);
 * IKV putBool(String key, boolean value);
 * <p>
 * //String
 * String getString(String key);
 * String getString(String key, String defaultValue);
 * IKV putString(String key, String value);
 * <p>
 * //obj list map set
 * <T> T getObj(String key, Class<T> clazz);
 * IKV putObj(String key, Object value);
 * // list
 * IKV putList(String key, Object object);
 * <T> List<T> getList(String key, Class<T> clazz);
 * // map
 * IKV putMap(String key, Object object);
 * <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz);
 * <V> IKV putStringKeyMap(String key, Map<String, V> map, Class<V> vClass);
 * <V> Map<String, V> getStingKeyMap(String key, Class<V> vClazz);
 * <p>
 * //String sets
 * Set<String> getStringSets(String key);
 * IKV putStringSets(String key, Set<String> value);
 */
public class LKVMgr implements LIMgr {

    private static SparseArray<IKV> sIKVSparseArray = new SparseArray<>();
    private static String           MAP_KEY         = "_MAP_KEY";
    private static String           MAP_VALUE       = "_MAP_VALUE";

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

        IKV putObj(String key, Object value);

        // list
        IKV putList(String key, Object object);

        <T> List<T> getList(String key, Class<T> clazz);

        // map
        <V> IKV putStringKeyMap(String key, Map<String, V> map, Class<V> vClass);

        <V> Map<String, V> getStingKeyMap(String key, Class<V> vClazz);

        IKV putMap(String key, Object object);

        <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz);
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


    /**
     * @return 获取 mmkv key-value 操作
     */
    public static LKVMgr.IKV mmkv() {
        return getInstance(LKVMgrParams.STRATEGY_MMKV);
    }

    /**
     * @return 获取 SharePreference key-value 操作
     */
    public static LKVMgr.IKV sp() {
        return getInstance(LKVMgrParams.STRATEGY_SP);
    }

    /**
     * @return 获取内存 key-value 操作
     */
    public static LKVMgr.IKV memory() {
        return getInstance(LKVMgrParams.STRATEGY_MEMORY);
    }

    // 基于 Tencent MMKV 的实现
    static class MMKVImpl implements IKV {

        private final String mInitialize;

        MMKVImpl(Context context) {
            mInitialize = MMKV.initialize(context);
        }

        private MMKV kv() {
            //MMKV 提供一个全局的实例，可以直接使用
            return MMKV.defaultMMKV();
        }

        public String getMmkvPath() {
            return mInitialize;
        }

        @Override
        public IKV putInt(String key, int value) {
            kv().putInt(key, value);
            return this;
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public int getInt(String key, int defaultValue) {
            return kv().getInt(key, defaultValue);
        }

        @Override
        public IKV putFloat(String key, float value) {
            kv().putFloat(key, value);
            return this;
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            return kv().getFloat(key, defaultValue);
        }

        @Override
        public IKV putLong(String key, long value) {
            kv().putLong(key, value);
            return this;
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            return kv().getLong(key, defaultValue);
        }

        @Override
        public IKV putBool(String key, boolean value) {
            kv().putBoolean(key, value);
            return this;
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
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
        public IKV putString(String key, String value) {
            kv().putString(key, value);
            return this;
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public IKV putObj(String key, Object value) {
            kv().putString(key, LJsonX.toJson(value));
            return this;
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            return LJsonX.toObj(getString(key), clazz);
        }

        @Override
        public IKV putList(String key, Object object) {
            putObj(key, object);
            return this;
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            return LJsonX.toList(getString(key), clazz);
        }

        @Override
        public IKV putMap(String key, Object object) {
            putObj(key, object);
//            String mapKey = key + MAP_KEY;
//            String mapValue = key + MAP_VALUE;
//            List<K> keyList = LMapX.getMapKeyList(map, kClazz, vClazz);
//            List<V> valueList = LMapX.getMapValueListByKey(map, keyList, kClazz, vClazz);
//            kv().putString(mapKey, LJsonX.toJson(keyList));
//            kv().putString(mapValue, LJsonX.toJson(valueList));
            return this;
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
//            String mapKey = key + MAP_KEY;
//            String mapValue = key + MAP_VALUE;
//            String kJson = getString(mapKey);
//            String vJson = getString(mapValue);
//            return LJsonX.toMap(kJson, vJson, kClazz, vClazz);
            return LJsonX.toMap(getString(key), kClazz, vClazz);
        }

        @Override
        public <V> IKV putStringKeyMap(String key, Map<String, V> value, Class<V> vClass) {
            kv().putString(key, LJsonX.toJson(value));
            return this;
        }

        @Override
        public <V> Map<String, V> getStingKeyMap(String key, Class<V> vClazz) {
            return LJsonX.toStringKeyMap(getString(key), vClazz);
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            kv().putStringSet(key, value);
            return this;
        }

        @Override
        public Set<String> getStringSets(String key) {
            HashSet<String> strings = new HashSet<>();
            return kv().getStringSet(key, strings);
        }

    }

    // 基于 SharePreference 实现
    static class SpKvImpl implements IKV {

        private final SharedPreferences mSp;

        public SpKvImpl(Context context) {
            mSp = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        }

        @Override
        public IKV putInt(String key, int value) {
            mSp.edit().putInt(key, value).apply();
            return this;
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
        public IKV putFloat(String key, float value) {
            mSp.edit().putFloat(key, value).apply();
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
        public IKV putLong(String key, long value) {
            mSp.edit().putLong(key, value).apply();
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
        public IKV putBool(String key, boolean value) {
            mSp.edit().putBoolean(key, value).apply();
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
        public IKV putString(String key, String value) {
            mSp.edit().putString(key, value).apply();
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
        public IKV putObj(String key, Object value) {
            return putString(key, LJsonX.toJson(value));
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            return LJsonX.toObj(getString(key), clazz);
        }

        @Override
        public IKV putList(String key, Object object) {
            putObj(key, object);
            return this;
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            return LJsonX.toList(getString(key), clazz);
        }

        @Override
        public IKV putMap(String key, Object object) {
            putObj(key, object);
            return this;
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            return LJsonX.toMap(getString(key), kClazz, vClazz);
        }

        @Override
        public <V> IKV putStringKeyMap(String key, Map<String, V> value, Class<V> vClass) {
            putString(key, LJsonX.toJson(value));
            return this;
        }

        @Override
        public <V> Map<String, V> getStingKeyMap(String key, Class<V> vClazz) {
            return LJsonX.toStringKeyMap(getString(key), vClazz);
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            mSp.edit().putStringSet(key, value).apply();
            return this;
        }

        @Override
        public Set<String> getStringSets(String key) {
            return mSp.getStringSet(key, new HashSet<>());
        }

    }

    // 基于内存实现
    static class MemoryKvImpl implements IKV {

        private Map<String, Object> mObjectMap;

        public MemoryKvImpl() {
            mObjectMap = new HashMap<>();
        }

        @Override
        public IKV putInt(String key, int value) {
            mObjectMap.put(key, value);
            return this;
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
        public IKV putFloat(String key, float value) {
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
        public IKV putLong(String key, long value) {
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
        public IKV putBool(String key, boolean value) {
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
        public IKV putString(String key, String value) {
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
        public IKV putObj(String key, Object value) {
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
        public IKV putList(String key, Object object) {
            putObj(key, object);
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
        public IKV putMap(String key, Object object) {
            putObj(key, object);
            return this;
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Map) {
                return (Map<K, V>) o;
            }
            return null;
        }

        @Override
        public <V> IKV putStringKeyMap(String key, Map<String, V> value, Class<V> vClass) {
            putString(key, LJsonX.toJson(value));
            return this;
        }

        @Override
        public <V> Map<String, V> getStingKeyMap(String key, Class<V> vClazz) {
            return LJsonX.toStringKeyMap(getString(key), vClazz);
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
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


    }
}
