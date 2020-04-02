package com.jaydroid.base_component.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.jaydroid.base_component.network.default_net.DefaultNetFactory
import com.jaydroid.base_lib.app.appdelegate.IAppLife
import com.jaydroid.base_lib.app.appdelegate.PriorityLevel
import com.jaydroid.base_lib.utils.Utils

/**
 * BaseApp,反射调用
 *
 * @author wangxuejie
 * @version 1.0
 * @date 2019-10-15 10:57
 */
class BaseComponentApp : IAppLife {

    override fun attachBaseContext(base: Context) {
        Log.d(TAG, "attachBaseContext")

    }

    override fun onCreate(application: Application) {
        Log.d(TAG, "onCreate")
        //初始化工具类
        Utils.init(application)
        //初始化网络库
        DefaultNetFactory.initialize(application)

    }

    override fun onTerminate(application: Application) {
        Log.d(TAG, "onTerminate")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigurationChanged")
    }

    override fun onLowMemory() {
        Log.d(TAG, "onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        Log.d(TAG, "onTrimMemory")
    }

    /**
     * 设置该appLife的优先级，必须设置，否则不会回调
     */
    override fun onPriority(): String {
        return PriorityLevel.MEDIUM
    }

    companion object {
        private const val TAG = "BaseComponentApp"
    }
}
