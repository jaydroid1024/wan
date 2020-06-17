package com.jaydroid.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 网络链接信息工具类
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */

object Connectivity {

    fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    fun hasNetwork(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnectedOrConnecting
    }

    fun isWifiConnection(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info!!.type == ConnectivityManager.TYPE_WIFI
    }

}

