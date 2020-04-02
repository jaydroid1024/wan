package com.jaydroid.base_lib.app.appdelegate

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import java.util.*

/**
 * 获取metaData信息并解析 IAppLife
 *
 * <meta-data
 * android:name="com.jaydroid.conponent_base.app.BaseApp"
 * android:value="IModuleConfig" />
 */
class ManifestParser(private val context: Context) {

    fun parse(): List<IAppLife> {
        val modules = ArrayList<IAppLife>()
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA
            )
            if (appInfo.metaData != null) {
                //会对其中value为IModuleConfig的meta-data进行解析，并通过反射生成实例
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData.get(key)) {
                        val resource = appInfo.metaData.get(key)
                        Log.e(TAG, "AppLife路径：$key")
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("解析Application失败", e)
        }
        Log.e(TAG, "AppLife数量:" + modules.size)
        return modules
    }

    //通过类名生成实例
    private fun parseModule(className: String): IAppLife {
        val clazz: Class<*>
        try {
            clazz = Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException(e)
        }

        val module: Any
        try {
            module = clazz.newInstance() as IAppLife
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
        return module
    }

    companion object {

        const val TAG = "AppLife"

        private const val MODULE_VALUE = "IModuleConfig"


    }
}