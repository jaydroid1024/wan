package com.jay.base_lib.utils;

import android.content.Context;

/**
 * 数据清除工具类
 * 1)清除SharedPreferences数据
 *
 * @author gaoxiaoduo
 * @version 1.0
 * @date 16/6/2下午5:40
 */
public class DataClearUtils {
    /**
     * SharedPreferences数据清除
     *
     * @param context 上下文
     */
    public static void clearSp(Context context) {

        SPUtils.clear(context);
    }
}
