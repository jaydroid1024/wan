package com.jay.mvp.v2.simple

import android.os.Bundle
import android.widget.TextView
import com.jay.mvp.R
import com.jay.mvp.v2.base.BaseMvpActivity
import com.jay.mvp.v2.base.rx.BaseRxMvpPresenter

class SimpleMvpPresenter : BaseRxMvpPresenter<SimpleMvpActivity>() {

    fun getData(): CharSequence {
        return "Mvp Simple"
    }
}

class SimpleMvpActivity : BaseMvpActivity<SimpleMvpPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_mvp)
        findViewById<TextView>(R.id.tv_mvp).text = presenter.getData()
    }
}