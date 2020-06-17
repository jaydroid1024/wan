package com.jaydroid.wanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaydroid.base_component.arouter.ARHelper
import kotlinx.android.synthetic.main.activity_wan.*

class WanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wan)
        tv_test1.setOnClickListener {
            ARHelper.routerTo(ARHelper.PathWan.WAN_ACTIVITY_PATH)
        }
        tv_test2.setOnClickListener {
            ARHelper.routerTo(ARHelper.PathDetail.DETAIL_ACTIVITY_PATH)
        }
    }
}
