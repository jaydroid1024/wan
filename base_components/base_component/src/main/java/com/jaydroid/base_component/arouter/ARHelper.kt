package com.jaydroid.base_component.arouter

import android.app.Activity
import android.content.Intent
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.jaydroid.base_component.constant.Constants
import java.util.*


/**
 * ARouter 帮助类
 * 路由路径
 * 跳转公共方法
 * @author wangxuejie
 * @date 2019-12-17 14:04
 * @version 1.0
 */
object ARHelper {

    /**
     * main组件
     */
    object PathMain {

        /**
         * group name
         */
        private const val MAIN = "/main"

        /**
         * MainActivity 页面
         */
        const val MAIN_ACTIVITY_PATH = "$MAIN/main/activity"

        /**
         * HomeFragment 页面
         */
        const val HOME_FRAGMENT_PATH = "$MAIN/home/fragment"


    }

    /**
     * user组件
     */
    object PathUser {

        /**
         * group name
         */
        private const val USER = "/login"

        /**
         * 登录页面
         */
        const val LOGIN_ACTIVITY_PATH = "$USER/login/activity"

        /**
         * 注册页面
         */
        const val REGISTER_ACTIVITY_PATH = "$USER/register/activity"

        /**
         * 用户服务
         */
        const val USER_SERVICE_PATH = "$USER/login/service"


    }

    /**
     * setting组件
     */
    object PathSetting {

        /**
         * group name
         */
        private const val SETTING = "/setting"

        /**
         * 设置页面
         */
        const val SETTING_ACTIVITY_PATH = "$SETTING/setting/activity"


    }

    /**
     * favorite组件
     */
    object PathFavorite {

        /**
         * group name
         */
        private const val FAVORITE = "/favorite"

        /**
         * 收藏页面
         */
        const val FAVORITE_ACTIVITY_PATH = "$FAVORITE/favorite/activity"


    }

    /**
     * 获取通用服务
     */
    fun <T> getService(service: Class<out T>): T? {
        return ARouter.getInstance().navigation(service)
    }

    /**
     * 获取通用服务
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getService(path: String): T? {
        return ARouter.getInstance().build(path).navigation() as? T?
    }

    /**
     * ARouter通用跳转方法
     */
    fun routerToWithJson(mapParams: HashMap<String, Any>, path: String) {
        ARouter.getInstance()
            .build(path)
            .withString(Constants.IntentKey.MAP_PARAMS, mapParams.toJson())
            .navigation()
    }

    /**
     * Map转为Json字符串
     */
    private fun Map<String, Any>.toJson(): String {
        return Gson().toJson(this)
    }

    /**
     * Json转为Map
     */
    private fun String?.fromJson(): Map<String, Any> {
        return Gson().fromJson<Map<String, Any>>(this, Map::class.java)

    }

    fun getParamsMap(intent: Intent?): Map<String, Any>? {
        if (intent != null && intent.hasExtra(Constants.IntentKey.MAP_PARAMS)) {
            val paramsJson = intent.getStringExtra(Constants.IntentKey.MAP_PARAMS) as String
            return paramsJson.fromJson()
        }
        return null
    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(path: String) {
        ARouter.getInstance().build(path).navigation()
    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(path: String, activity: Activity, callBack: NavigationCallback) {
        ARouter.getInstance().build(path).navigation(activity, callBack)
    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(mapParams: HashMap<String, Any>, path: String) {
        ARouter.getInstance()
            .build(path)
            .withSerializable(Constants.IntentKey.MAP_PARAMS, mapParams)
            .navigation()
    }

    /**
     * 获取Intent参数封装的 HashMap
     */
    fun getParamsMap(capacity: Int): HashMap<String, Any> {
        return HashMap(capacity)
    }

    /**
     * 从HashMap中获取String类型的参数
     */
    fun getParamsMapString(mapParams: HashMap<*, *>?, key: String): String {
        return mapParams?.let {
            it[key] as? String
        } ?: ""
    }

}