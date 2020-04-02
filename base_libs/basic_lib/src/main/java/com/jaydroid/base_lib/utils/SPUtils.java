package com.jaydroid.base_lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 对SharedPreference的使用做了封装，对外公布出put，get，remove，clear等方法
 *
 * @author zhanghao
 * @version 1.0
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名，默认文件名称（app登录账号）
     */
    public static final String FILE_NAME = "boss_app_account_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param key      键key
     * @param object   值
     */
    @SuppressWarnings("unchecked")
    public static void put(Context context, String fileName, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        putApply(editor, key, object);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context 上下文
     * @param key     键key
     * @param object  值
     */
    @SuppressWarnings("unchecked")
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        putApply(editor, key, object);
    }

    private static void putApply(SharedPreferences.Editor editor, String key, Object object) {

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           键key
     * @param defaultObject 默认值
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public static Object get(Context context, String fileName, String key, Object defaultObject) {

        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return getSpValue(sp, key, defaultObject);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           键key
     * @param defaultObject 默认值
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public static Object get(Context context, String key, Object defaultObject) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        return getSpValue(sp, key, defaultObject);
    }

    private static Object getSpValue(SharedPreferences sp, String key, Object defaultObject) {

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个文件下key值已经对应的值
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @param key      键key
     */
    public static void remove(Context context, String fileName, String key) {

        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context 上下文
     * @param key     键key
     */
    public static void remove(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context 上下文
     * @param keys    键key数组
     */
    public static void remove(Context context, String... keys) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context 上下文
     */
    public static void clear(Context context, String fileName) {

        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context 上下文
     */
    public static void clear(Context context) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context 上下文
     * @param key     键key
     * @return 是否存在
     */
    public static boolean contains(Context context, String fileName, String key) {

        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context 上下文
     * @param key     键key
     * @return 是否存在
     */
    public static boolean contains(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @return 获取所有键值对
     */
    public static Map<String, ?> getAll(Context context, String fileName) {

        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 返回所有的键值对
     *
     * @param context 上下文
     * @return 获取所有键值对
     */
    public static Map<String, ?> getAll(Context context) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * <p/>
     * 里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，
     * 目的是尽可能的使用apply代替commit，首先说下为什么，因为commit方法是同步的，
     * 并且我们很多时候commit操作都是UI线程中，毕竟是IO操作，尽可能异步；所以我们使用
     * apply进行替代，apply异步的进行写入；但是apply相当于commit来说是new API呢，
     * 为了更好的兼容，我们做了适配；SharedPreferencesCompat也可以给大家创建兼容类提供一定的参考
     *
     * @author zhanghao
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return 返回方法名称为apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {

            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor Editor
         */
        public static void apply(SharedPreferences.Editor editor) {

            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                }
            } catch (IllegalArgumentException | IllegalAccessException |
                    InvocationTargetException e) {
                editor.commit();
            }

        }
    }
}
