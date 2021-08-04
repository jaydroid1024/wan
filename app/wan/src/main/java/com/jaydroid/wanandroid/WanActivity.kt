package com.jaydroid.wanandroid

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jaydroid.base_component.arouter.ARHelper

class WanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wan_test)
        val tvLogin = findViewById<TextView>(R.id.tv_login)
        tvLogin.setOnClickListener {
            ARHelper.routerTo(ARHelper.PathWan.WAN_ACTIVITY_PATH)
        }
    }
}
