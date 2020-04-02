package com.jaydroid.base_lib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 *
 * @author zhanghao
 * @version 1.0
 */
public class ScreenUtils {
    private ScreenUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 设置当前界面为全屏模式
     */
    public static void setFullScreen(Activity activity) {

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 该属性会导致当软键盘弹出时输入框被覆盖
        // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 如果当前为全屏，那么取消全屏模式，回到正常的模式
     */
    public static void cancelFullScreen(Activity activity) {

        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 判断当前手机是否是全屏
     *
     * @return 如果是true，那么当前就是全屏
     */
    public static boolean isFullScreen(Activity activity) {

        int flag = activity.getWindow().getAttributes().flags;
        return (flag & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 判断当前屏幕是否是横屏
     *
     * @param activity 当前的activity
     * @return 如果true就是竖屏
     */
    public static boolean isVerticalScreen(Activity activity) {

        int flag = activity.getResources().getConfiguration().orientation;
        return flag != 0;
    }

    /**
     * 推荐的获取屏幕长宽的方式,但需要API13
     *
     * @return 装载了屏幕长宽的数组，int[0] = width,int[1] = height
     */
    @SuppressLint("NewApi")
    public static int[] getWindow_WH(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new int[]{size.x, size.y};
    }

    /**
     * 获取屏幕长宽的方式(仅在低版本中使用)
     *
     * @return 装载了屏幕长宽的数组，int[0] = width,int[1] = height
     */
    @Deprecated
    public static int[] getWindow_wh(Activity activity) {

        int w = activity.getWindowManager().getDefaultDisplay().getWidth(); // 获得手机屏幕的宽度
        int h = activity.getWindowManager().getDefaultDisplay().getHeight(); // 获得手机屏幕的高度
        return new int[]{w, h};
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     *
     * @param context 上下文
     * @return 屏幕密度
     */
    public static float getScreenDensity(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.density;
    }

    /**
     * 获得屏幕密度（每寸像素：120/160/240/320）
     *
     * @param context 上下文
     * @return 屏幕密度
     */
    public static int getScreenDpi(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.densityDpi;
    }

    /**
     * 获取顶部状态栏高度
     *
     * @return 顶部状态栏高度
     */
    public static int getStatusBarHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取底部导航栏高度
     *
     * @return 底部导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {

        int navigationHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("navigation_bar_height").get(object).toString());
            navigationHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return navigationHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity 上下文
     * @return Bitmap
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity 上下文
     * @return Bitmap
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 全屏切到非全屏时的闪屏问题
     */
    public static void solveSplashScreen(Activity activity) {

        Window window = activity.getWindow();
        // 窗口沾满整个屏幕
        //        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        // 允许窗口扩展到屏幕之外
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // 获取窗口的跟视图
        View view = window.getDecorView();
        // 设置根视图距顶的高度为状态栏的高度
        //
        // L.d(Integer.toString(ScreenUtils.getNavigationBarHeight(activity.getApplicationContext())));
        view.setPadding(
                0,
                ScreenUtils.getStatusBarHeight(activity.getApplicationContext()),
                0,
                ScreenUtils.getNavigationBarHeight(activity.getApplicationContext()));
    }

    /**
     * 判断是否是全面屏
     *
     * @param context
     * @return boolean
     */
    public static boolean isAllScreenDevice(Context context) {

        // 低于 API 21的，都不会是全面屏。。。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            Log.d("isAllScreenDevice", "比率：" + (height / width));
            return height / width >= 1.97f;
        }
        return false;
    }
}
