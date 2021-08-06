package com.jay.base_lib.utils;

import com.jay.base_lib.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author zhanghao
 * @version 1.0
 */

public class L {

    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(Utils.getApp().getClass().getSimpleName())   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void i(String msg) {

        if (BuildConfig.DEBUG) {
            Logger.i(msg);
        }
    }

    public static void i(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Logger.t(tag).i(msg);
        }
    }

    public static void d(String msg) {

        if (BuildConfig.DEBUG) {
            Logger.d(msg);
        }
    }

    public static void d(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Logger.t(tag).d(msg);
        }
    }

    public static void e(String msg) {

        if (BuildConfig.DEBUG) {
            Logger.e(msg);
        }
    }

    public static void e(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Logger.t(tag).e(msg);
        }
    }

    public static void v(String msg) {

        if (BuildConfig.DEBUG) {
            Logger.v(msg);
        }
    }

    public static void v(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Logger.t(tag).v(msg);
        }
    }

    public static void json(String json) {

        if (BuildConfig.DEBUG) {
            Logger.json(json);
        }
    }

    public static void json(String tag, String json) {

        if (BuildConfig.DEBUG) {
            Logger.t(tag).json(json);
        }
    }
}
