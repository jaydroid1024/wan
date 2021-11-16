package com.jay.lib_net.jdispatcher

import android.app.Application
import com.facebook.stetho.Stetho
import com.jay.android.dispatcher.annotation.Dimension
import com.jay.android.dispatcher.annotation.Dispatch
import com.jay.android.dispatcher.annotation.Priority
import com.jay.android.dispatcher.common.DispatchItem
import com.jay.android.dispatcher.dispatch.DispatchTemplate

/**
 * @author jaydroid
 * @version 1.0
 * @date 5/31/21
 */
@Dispatch(
    priority = Priority.HIGH_DEFAULT,
    dimension = Dimension.PROCESS_MAIN or Dimension.THREAD_UI or Dimension.BUILD_ALL or Dimension.AUTOMATIC,
)
class DispatchNet : DispatchTemplate() {

    override fun onCreate(app: Application, dispatchItem: DispatchItem) {
        super.onCreate(app, dispatchItem)
        initStetho()
    }

    /**
     * 初始化Stetho
     */
    private fun initStetho() {
        //网络库测试类,需要依赖lib_net
        Stetho.initializeWithDefaults(app)
    }


    companion object {
        fun getApp(): Application? {
            return app
        }
    }
}

