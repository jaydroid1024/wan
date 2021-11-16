package com.jay.wan.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.jay.android.dispatcher.launcher.JDispatcher
import com.jay.base_lib.BuildConfig
import com.jay.base_lib.app.appdelegate.ApplicationDelegate


/**
 * Description: Application
 *
 * @author xuejiewang
 * @version 1.0
 * @date 2019-09-10
 */
class WanApp : Application() {

    /**
     * App生命周期分发代理
     */
    private var applicationDelegate: ApplicationDelegate? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Log.d(TAG, "attachBaseContext")
        Log.d("JDispatcher", "attachBaseContext")
        //appInit的替代方案
        applicationDelegate = ApplicationDelegate(base)
        applicationDelegate?.attachBaseContext(base)
    }


    /**
     * 创建应用程序时回调，回调时机早于任何 Activity。
     */
    override fun onCreate() {
        Log.d(TAG, "onCreate")
        Log.d("JDispatcher", "onCreate")
        super.onCreate()
        instance = this
        initRouter()
        applicationDelegate?.onCreate(this)
        JDispatcher.instance
//            .withDispatchExtraParam(dispatchExtraParam ?: hashMapOf()) //为所有组件分发环境配置信息
            .withDebugAble(true)//调试模式：打印更多日志，实时刷新等
            .onCreate(this)//分发 onCreate
    }

    /**
     * 初始化Router
     */
    private fun initRouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(instance)
    }



    /**
     * 在模拟环境中程序终止时会被调用，终止应用程序时调用，不能保证一定会被调用。
     */
    override fun onTerminate() {
        super.onTerminate()
        applicationDelegate?.onTerminate(this)
    }

    /**
     * 配置改变时触发这个方法，屏幕旋转等
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        applicationDelegate?.onConfigurationChanged(newConfig)
    }

    /**
     * 低内存的时候执行，照片资源（GlideApp 的使用）缓冲的清除
     */
    override fun onLowMemory() {
        super.onLowMemory()
        applicationDelegate?.onLowMemory()
    }

    /**
     * 程序在进行内存清理时执行，可根据不同的 level 来决定是否清除缓存
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        applicationDelegate?.onTrimMemory(level)
    }

    companion object {

        private val TAG = WanApp::class.java.simpleName

        /**
         * 获取应用类实例
         *
         * @return BApp
         */
        var instance: WanApp? = null
    }
}
