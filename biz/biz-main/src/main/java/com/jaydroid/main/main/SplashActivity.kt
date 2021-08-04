package com.jaydroid.main.main

import android.os.Handler
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.BaseActivity
import com.jaydroid.main.R

class SplashActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.biz_main_activity_splash
    }

    override fun initView() {
        Handler().postDelayed({ gotoMainActivity() }, 2000)
    }

    private fun gotoMainActivity() {
        ARHelper.routerTo(ARHelper.PathMain.MAIN_ACTIVITY_PATH)
        finish()
    }
}
