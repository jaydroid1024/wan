package com.jay.main.main

import android.os.Handler
import com.jay.base_component.arouter.ARHelper
import com.jay.base_component.base.BaseActivity
import com.jay.main.R

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
