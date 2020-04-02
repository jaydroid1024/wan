package com.jaydroid.base_component.network.default_net

import android.annotation.SuppressLint
import android.app.Application

/**
 *
 * @author wangxuejie
 * @date 2019-12-25 17:51
 * @version 1.0
 */
@SuppressLint("StaticFieldLeak")
object DefaultNetFactory {

    var defaultNetwork: DefaultNetwork? = null

    /**
     * 获取应用类实例
     *
     * @return Application
     */
    lateinit var application: Application

    fun initialize(app: Application) {
        application = app
    }

    @Synchronized
    fun getDefaultNet(): DefaultNetwork {
        if (defaultNetwork == null) {
            defaultNetwork = DefaultNetwork(application.applicationContext!!)
        }
        return defaultNetwork!!
    }

}