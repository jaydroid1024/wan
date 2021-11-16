package com.jay.base_component.network.default_net

import android.annotation.SuppressLint
import android.app.Application

/**
 * 网络工厂类，提供所有网络请求的总入口
 * @author wangxuejie
 * @date 2019-12-25 17:51
 * @version 1.0
 */
@SuppressLint("StaticFieldLeak")
object DefaultNetFactory {

    var defaultNetwork: WanDroidNetwork? = null

    /**
     * 获取应用类实例
     *
     * @return Application
     */
    lateinit var application: Application

    fun initialize(app: Application) {
        application = app
    }

    /**
     * 获取WanDroid项目需要的网络类
     */
    @Synchronized
    fun getDefaultNet(): WanDroidNetwork {
        if (defaultNetwork == null) {
            defaultNetwork = WanDroidNetwork()
        }
        return defaultNetwork!!
    }


}