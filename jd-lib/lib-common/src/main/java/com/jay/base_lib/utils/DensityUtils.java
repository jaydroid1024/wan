package com.jay.base_lib.utils;


import android.util.TypedValue;

/**
 * 常用单位转换工具类
 * dp sp px之间的转换
 *
 * @author gaoxiaoduo
 * @version 2.0
 */
public class DensityUtils {

    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static float px2dip(float pxValue) {

        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(float dipValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Utils.getApp().getResources()
                .getDisplayMetrics());
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static float px2sp(float pxValue) {

        return (pxValue / Utils.getApp().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Utils.getApp().getResources()
                .getDisplayMetrics());
    }
}
