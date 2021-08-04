package com.jaydroid.base_lib.app.appdelegate

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log

/**
 * Application 分发代理类
 *
 * @author wangxuejie
 * @version 1.0
 * @date 2019-10-15 10:57
 */
class ApplicationDelegate(base: Context) : IAppLife {
    private var list: List<IAppLife>? = null
    private var mapList: HashMap<String, ArrayList<IAppLife>>? = null

    init {
        //初始化Manifest文件解析器，用于解析组件在自己的Manifest文件配置的Application
        list = ManifestParser(base).parse()
        //按照优先级分组
        if (list != null && list!!.isNotEmpty()) {
            mapList = HashMap()
            for (life in list!!) {
                Log.e(
                    ManifestParser.TAG, "AppLife name: " + life.javaClass.simpleName
                            + ",AppLife priority: " + life.onPriority()
                )
                if (mapList!!.containsKey(life.onPriority())) {
                    //map中存在此分类，将数据存放当前key的map中
                    mapList!![life.onPriority()]?.add(life)
                } else {
                    //map中不存在，新建key，用来存放数据
                    val priorityList = ArrayList<IAppLife>()
                    priorityList.add(life)
                    mapList!![life.onPriority()] = priorityList
                }
            }
        }
    }

    override fun attachBaseContext(context: Context) {
        if (!mapList.isNullOrEmpty()) {
            //回调高优先级
            attachBaseContextForLife(mapList!![PriorityLevel.HIGH], context)
            //回调中优先级
            attachBaseContextForLife(mapList!![PriorityLevel.MEDIUM], context)
            //回调低优先级
            attachBaseContextForLife(mapList!![PriorityLevel.LOW], context)
        }
    }

    /**
     * 调用各个优先级下的 attachBaseContext
     */
    private fun attachBaseContextForLife(priorityList: ArrayList<IAppLife>?, context: Context) {
        if (!priorityList.isNullOrEmpty()) {
            for (life in priorityList) {
                life.attachBaseContext(context)
            }
        }
    }


    override fun onCreate(application: Application) {
        if (!mapList.isNullOrEmpty()) {
            //回调高优先级
            onCreateForLife(mapList!![PriorityLevel.HIGH], application)
            //回调中优先级
            onCreateForLife(mapList!![PriorityLevel.MEDIUM], application)
            //回调低优先级
            onCreateForLife(mapList!![PriorityLevel.LOW], application)
        }
    }

    /**
     * 调用各个优先级下的 onCreate
     */
    private fun onCreateForLife(priorityList: ArrayList<IAppLife>?, application: Application) {
        if (!priorityList.isNullOrEmpty()) {
            for (life in priorityList) {
                life.onCreate(application)
                Log.e(ManifestParser.TAG, life.javaClass.simpleName + "初始化完成")
            }
        }
    }

    override fun onTerminate(application: Application) {
        if (!mapList.isNullOrEmpty()) {
            //回调高优先级
            onTerminateForLife(mapList!![PriorityLevel.HIGH], application)
            //回调中优先级
            onTerminateForLife(mapList!![PriorityLevel.MEDIUM], application)
            //回调低优先级
            onTerminateForLife(mapList!![PriorityLevel.LOW], application)
        }
    }


    /**
     * 调用各个优先级下的 onTerminate
     */
    private fun onTerminateForLife(priorityList: ArrayList<IAppLife>?, application: Application) {
        if (!priorityList.isNullOrEmpty()) {
            for (life in priorityList) {
                life.onTerminate(application)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (!mapList.isNullOrEmpty()) {
            //回调高优先级
            onConfigurationChangedForLife(mapList!![PriorityLevel.HIGH], newConfig)
            //回调中优先级
            onConfigurationChangedForLife(mapList!![PriorityLevel.MEDIUM], newConfig)
            //回调低优先级
            onConfigurationChangedForLife(mapList!![PriorityLevel.LOW], newConfig)
        }
    }

    /**
     * 调用各个优先级下的 onConfigurationChanged
     */
    private fun onConfigurationChangedForLife(
        priorityList: ArrayList<IAppLife>?,
        newConfig: Configuration
    ) {
        if (!priorityList.isNullOrEmpty()) {
            for (life in priorityList) {
                life.onConfigurationChanged(newConfig)
            }
        }
    }

    override fun onLowMemory() {
        if (!mapList.isNullOrEmpty()) {
            //回调高优先级
            onLowMemoryForLife(mapList!![PriorityLevel.HIGH])
            //回调中优先级
            onLowMemoryForLife(mapList!![PriorityLevel.MEDIUM])
            //回调低优先级
            onLowMemoryForLife(mapList!![PriorityLevel.LOW])
        }
    }

    /**
     * 调用各个优先级下的 onLowMemory
     */
    private fun onLowMemoryForLife(priorityList: ArrayList<IAppLife>?) {
        if (!priorityList.isNullOrEmpty()) {
            for (life in priorityList) {
                life.onLowMemory()
            }
        }
    }

    override fun onTrimMemory(level: Int) {
        if (!mapList.isNullOrEmpty()) {
            //回调高优先级
            onTrimMemoryForLife(mapList!![PriorityLevel.HIGH], level)
            //回调中优先级
            onTrimMemoryForLife(mapList!![PriorityLevel.MEDIUM], level)
            //回调低优先级
            onTrimMemoryForLife(mapList!![PriorityLevel.LOW], level)
        }
    }

    /**
     * 调用各个优先级下的 onTerminate
     */
    private fun onTrimMemoryForLife(priorityList: ArrayList<IAppLife>?, level: Int) {
        if (!priorityList.isNullOrEmpty()) {
            for (life in priorityList) {
                life.onTrimMemory(level)
            }
        }
    }

    override fun onPriority(): String {
        return ""
    }
}
