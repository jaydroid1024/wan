package com.jay.base_lib.app.appdelegate

import android.app.Application
import android.content.Context
import android.content.res.Configuration

/**
 * @author wangxuejie
 * @version 1.0
 * @date 2020/3/31
 */
interface IAppLife {

    fun attachBaseContext(context: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)

    fun onConfigurationChanged(newConfig: Configuration)

    fun onLowMemory()

    fun onTrimMemory(level: Int)

    /**
     * 设置该appLife的优先级，必须设置，否则不会回调
     */
    fun onPriority(): String

    companion object {

        private const val TAG = "IAppLife"
    }

}
