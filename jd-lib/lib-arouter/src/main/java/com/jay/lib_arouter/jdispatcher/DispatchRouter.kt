package com.jay.lib_arouter.jdispatcher

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.jay.android.dispatcher.annotation.Dimension
import com.jay.android.dispatcher.annotation.Dispatch
import com.jay.android.dispatcher.annotation.Priority
import com.jay.android.dispatcher.common.DispatchItem
import com.jay.android.dispatcher.dispatch.DispatchTemplate
import com.jay.lib_arouter.BuildConfig

/**
 * @author jaydroid
 * @version 1.0
 * @date 5/31/21
 */
@Dispatch(
    priority = Priority.HIGH.HIGH_III,
    dimension = Dimension.DIMENSION_DEFAULT,
)
class DispatchRouter : DispatchTemplate() {
    override fun onCreate(app: Application, dispatchItem: DispatchItem) {
        super.onCreate(app, dispatchItem)
        initRouter()
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
        ARouter.init(app)
    }

    companion object {

        fun getApp(): Application? {
            return app
        }
    }
}

