package com.jaydroid.main.main

import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.BaseActivity
import com.jaydroid.main.R
import kotlinx.android.synthetic.main.biz_main_activity_splash.*

class SplashActivity : BaseActivity() {


    override fun getLayoutResId(): Int {
        return R.layout.biz_main_activity_splash
    }

    override fun initView() {
        lav_logo.postDelayed({ gotoMainActivity() }, 2000)
    }

    private fun gotoMainActivity() {
        ARHelper.routerTo(ARHelper.PathMain.MAIN_ACTIVITY_PATH)
        finish()
    }
}
