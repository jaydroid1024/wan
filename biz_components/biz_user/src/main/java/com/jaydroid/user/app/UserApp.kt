package com.jaydroid.user.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.jaydroid.base_lib.app.appdelegate.IAppLife
import com.jaydroid.base_lib.app.appdelegate.PriorityLevel

/**
 * UserApp
 *
 * @author wangxuejie
 * @version 1.0
 * @date 2019-10-15 10:57
 */
class UserApp : IAppLife {

    override fun attachBaseContext(context: Context) {
        Log.d(TAG, "attachBaseContext")
    }

    override fun onCreate(application: Application) {
        Log.d(TAG, "onCreate")
    }

    override fun onTerminate(application: Application) {
        Log.d(TAG, "onTerminate")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}
    override fun onLowMemory() {}
    override fun onTrimMemory(level: Int) {}
    override fun onPriority(): String {
        return PriorityLevel.LOW
    }

    companion object {
        private const val TAG = "UserApp"
    }
}