package com.jay.base_lib.utils;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * GSONAPI处理json格式的字符串
 *
 * @author zhanghao
 */
public class GsonUtils {
    /**
     * json字符串回数组 example:Type listType = new
     * TypeToken<List<Order>>(){}.getType();将listType传到方法里
     */
    public static <T> List<T> fromJson(String strjson, Type tp) {

        try {
            Gson gs = new Gson();
            // Type listType = new TypeToken<List<T>>(){}.getType();
            return gs.fromJson(strjson, tp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json字符串回数组 example:Type listType = new
     * TypeToken<List<Order>>(){}.getType();将listType传到方法里
     */
    public static <T> List<T> fromJson(Reader reader, Type tp) {

        try {
            Gson gs = new Gson();
            // Type listType = new TypeToken<List<T>>(){}.getType();
            return gs.fromJson(reader, tp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json字符串返回对象
     *
     * @param strjson json字符串
     * @param cls     对象类型
     * @param <T>     泛型对象
     * @return 对象
     */
    public static <T> T fromJson(String strjson, Class<T> cls) {

        try {
            Gson gs = new Gson();
            // Type listType = new TypeToken<List<T>>(){}.getType();
            return gs.fromJson(strjson, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串返回对象
     *
     * @param reader 数据流
     * @param cls    对象类型
     * @param <T>    泛型对象
     * @return 对象
     */
    public static <T> T fromJson(Reader reader, Class<T> cls) {

        try {
            Gson gs = new Gson();
            // Type listType = new TypeToken<List<T>>(){}.getType();
            return gs.fromJson(reader, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 将对象转换成json字符串
     *
     * @param object 需要转换的对象
     * @return json字符串
     */
    public static String toJson(Object object) {

        return new Gson().toJson(object);
    }
}
