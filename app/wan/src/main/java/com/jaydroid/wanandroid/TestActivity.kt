package com.jaydroid.wanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jaydroid.base_component.arouter.ARHelper

@Route(path = ARHelper.PathWan.WAN_ACTIVITY_PATH)
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wan_test)
    }
}
