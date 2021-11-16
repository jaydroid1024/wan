package com.jay.lib_arouter.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.jay.lib_arouter.constant.RouterConstants


/**
 * ARouter 帮助类
 * 跳转公共方法
 * @author wangxuejie
 * @date 2019-12-17 14:04
 * @version 1.0
 */
object ARHelper {

    private val router: ARouter = ARouter.getInstance()


    /* ========================================================= */
    /* ========================= 服务获取 ======================= */
    /* ========================================================= */

    /*
    todo:
        获取服务参数顺序，没有传 null
        path: String,
        jsonParam: String？,
        mapParams: HashMap<String, Any>?,
        context: Activity?,
        callback: NavigationCallback?
     */

    /**
     * 获取服务的通用方法
     */
    @JvmStatic
    fun <T> getService(service: Class<out T>): T? {
        return router.navigation(service)
    }

    /**
     * 获取通用服务
     */
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> getService(path: String): T? {
        return getService(path, null) as? T?
    }

    /**
     * 获取通用服务
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getService(path: String, mapParams: HashMap<String, Any>?): T? {
        return getService(path, null, mapParams, null, null) as? T?
    }

    /**
     * 获取通用服务
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getService(path: String, jsonParam: String): T? {
        return getService(path, jsonParam, null, null, null) as? T?
    }

    /**
     * 获取通用服务
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getService(path: String, activity: Activity, callBack: NavigationCallback): T? {
        return getService(path, null, null, activity, callBack) as? T?
    }

    /**
     * 获取通用服务
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getService(
        path: String,
        jsonParam: String?,
        mapParams: HashMap<String, Any>?,
        context: Activity?,
        callback: NavigationCallback?
    ): T? {

        val paramsInfo =
            getParamsInfo(path, jsonParam, mapParams, context, null, null, null, callback)
        Log.d(TAG, "getService: $paramsInfo")

       val postcard = router.build(path)

        mapParams?.let {
            postcard.withSerializable(RouterConstants.IntentKey.MAP_PARAMS, mapParams)
        }

        jsonParam?.let {
            postcard.withString(RouterConstants.IntentKey.JSON_PARAMS, jsonParam)
        }

        return if (context != null && callback != null) {
            postcard.navigation(context, callback) as? T?
        } else {
            postcard.navigation() as? T?
        }
    }

    /**
     * 打印参数
     *
     * @param path
     * @param jsonParam
     * @param mapParams
     * @param context
     * @param callback
     * @return
     */
    private fun getParamsInfo(
        path: String,
        jsonParam: String?,
        mapParams: HashMap<String, Any>?,
        context: Context?,
        requestCode: Int?,
        flags: Int?,
        transition: IntArray?,
        callback: NavigationCallback?
    ): String {
        val map = LinkedHashMap<String, Any>()
        map["path"] = path
        map["jsonParam"] = jsonParam ?: "-"
        map["mapParams"] = mapParams ?: "-"
        map["context"] = context?.toString() ?: "-"
        map["requestCode"] = requestCode ?: "-"
        map["flags"] = flags ?: "-"
        map["transition"] = transition ?: "-"
        map["callback"] = callback?.javaClass?.name ?: "-"
        return map.toString()
    }


    /* ========================================================= */
    /* ========================= 路由跳转 ======================= */
    /* ========================================================= */

    /*
    todo:
        获取服务参数顺序，没有传 null
        path: String,
        jsonParam: String?,
        mapParams: HashMap<String, Any>?,
        context: Activity?,
        requestCode: Int?,
        flags: Int?,
        transition: IntArray?,
        callback: NavigationCallback?
     */

    /**
     * ARouter通用跳转方法
     */
    @JvmStatic
    fun routerTo(
        path: String,
        jsonParam: String
    ) {
        routerTo(path, jsonParam, null, null, null, null, null, null)
    }


    /**
     * ARouter通用跳转方法
     */
    @JvmStatic
    fun routerTo(path: String) {
        routerTo(path, null, null)
    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
    ) {
        routerTo(path, mapParams, null)

    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        context: Context,
        flags: Int
    ) {
        routerTo(path, null, context, flags)
    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        activity: Activity,
        flags: Int
    ) {

        routerTo(path, null, activity, flags)
    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
        context: Context?
    ) {
        routerTo(path, mapParams, context, null)

    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
        flags: Int
    ) {
        routerTo(path, mapParams, null, flags)

    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
        context: Context?,
        flags: Int
    ) {

        routerTo(path, null, mapParams, context, null, flags, null, null)

    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        context: Context,
        callBack: NavigationCallback
    ) {

        routerTo(path, null, null, context, null, null, null, callBack)
    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
        context: Context?,
        transition: IntArray?
    ) {
        routerTo(path, mapParams, context, null, transition)
    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
        context: Context?,
        requestCode: Int?,
        transition: IntArray?
    ) {
        routerTo(path, null, mapParams, context, requestCode, null, transition, null)
    }

    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        mapParams: HashMap<String, Any>?,
        requestCode: Int?,
        context: Context?
    ) {
        routerTo(path, null, mapParams, context, requestCode, null, null, null)
    }


    /**
     * ARouter通用跳转方法
     */
    fun routerTo(
        path: String,
        jsonParam: String?,
        mapParams: HashMap<String, Any>?,
        context: Context?,
        requestCode: Int?,
        flags: Int?,
        transition: IntArray?,
        callback: NavigationCallback?
    ) {

        val paramsInfo = getParamsInfo(
            path,
            jsonParam,
            mapParams,
            context,
            requestCode,
            flags,
            transition,
            callback
        )
        Log.d(TAG, "router: $paramsInfo")

        val postcard = router.build(path)

        mapParams?.let {
            postcard.withSerializable(RouterConstants.IntentKey.MAP_PARAMS, mapParams)
        }

        jsonParam?.let {
            postcard.withString(RouterConstants.IntentKey.JSON_PARAMS, jsonParam)
        }

        transition?.let {
            postcard.withTransition(transition[0], transition[1])
        }

        flags?.let {
            postcard.withFlags(flags)
        }

        if (transition?.isNotEmpty() == true && context != null) {
            postcard.navigation(context, callback)
        } else if (requestCode != null && requestCode > 0 && context != null) {
            if (context is Activity) {
                postcard.navigation(context, requestCode, callback)
            } else {
                Log.d(TAG, "context need is Activity")
            }
        } else if (context != null && callback != null) {
            postcard.navigation(context, callback)
        } else {
            postcard.navigation()
        }

    }

    /**
     * 目标页面中获取参数 Map
     */
    fun getMapFromJson(intent: Intent?): Map<String, Any> {
        var paramsMap: Map<String, Any> = HashMap<String, Any>()
        if (intent != null && intent.hasExtra(RouterConstants.IntentKey.JSON_PARAMS)) {
            val paramsJson = intent.getStringExtra(RouterConstants.IntentKey.JSON_PARAMS) as String
            paramsMap = paramsJson.fromJsonToMap()
        }
        Log.d(TAG, "paramsMap: $paramsMap")
        return paramsMap
    }

    /**
     * 目标页面中获取参数 json 字符串
     */
    fun getJson(intent: Intent?): String {
        var paramsJson = ""
        if (intent != null && intent.hasExtra(RouterConstants.IntentKey.JSON_PARAMS)) {
            paramsJson = intent.getStringExtra(RouterConstants.IntentKey.JSON_PARAMS) as String
        }
        Log.d(TAG, "paramsJson: $paramsJson")
        return paramsJson
    }


    /**
     * 获取Intent参数封装的 HashMap
     * @param intent Intent?
     * @return HashMap<*, *>
     */
    fun getMap(intent: Intent?): HashMap<String, Any> {
        var mapParams: HashMap<String, Any> = HashMap()
        if (intent != null && intent.hasExtra(RouterConstants.IntentKey.MAP_PARAMS)) {
            val mapParamTemp =
                intent.getSerializableExtra(RouterConstants.IntentKey.MAP_PARAMS) as? HashMap<String, Any>
            mapParamTemp?.let { mapParams = it }
        }
        Log.d(TAG, "mapParams: $mapParams")

        return mapParams
    }


    /**
     * 获取Intent参数封装的 HashMap
     * @param intent Intent?
     * @return HashMap<*, *>
     */
    fun getNullableParamsMap(intent: Intent?): HashMap<String, Any>? {
        return intent?.getSerializableExtra(RouterConstants.IntentKey.MAP_PARAMS) as HashMap<String, Any>?
    }

    /**
     * 目标页面中从HashMap中获取 String 类型的参数
     */
    fun getStrFromParamsMap(mapParams: Map<String, Any>?, key: String): String {
        return mapParams?.let {
            it[key] as? String
        } ?: ""
    }

    /**
     * 目标页面中从HashMap中获取 Int 类型的参数
     */
    fun getIntFromParamsMap(mapParams: Map<*, *>?, key: String): Int {
        return mapParams?.let {
            it[key] as? Int
        } ?: 0
    }

    /**
     * 目标页面中从HashMap中获取 Double 类型的参数
     */
    fun getDoubleFromParamsMap(mapParams: Map<String, Any>?, key: String): Double {
        return mapParams?.let {
            it[key] as? Double
        } ?: 0.0
    }

    /**
     * 目标页面中从HashMap中获取 Boolean 类型的参数
     */
    fun getBooleanFromParamsMap(mapParams: Map<String, Any>?, key: String): Boolean {
        return mapParams?.let {
            it[key] as? Boolean
        } ?: false
    }


    /**
     * Map转为Json字符串
     */
    private fun Map<String, Any>.toJson(): String {
        val mapJsonStr = Gson().toJson(this)
        return Gson().toJson(this)
    }

    /**
     * Json转为Map
     */
    private fun String?.fromJsonToMap(): Map<String, Any> {
        return Gson().fromJson<Map<String, Any>>(this, Map::class.java)
    }

    private const val TAG = "ARHelper"

    object RequestCode {

        const val REQUEST_CODE_0X10001 = 0X1001


    }

}