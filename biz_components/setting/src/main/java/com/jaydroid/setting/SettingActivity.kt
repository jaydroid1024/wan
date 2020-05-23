package com.jaydroid.setting

import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.mvp.BaseMVPActivity
import com.jaydroid.base_component.network.auth.isCookieNotEmpty
import com.jaydroid.base_component.network.bean.wan.LoggedInEvent
import com.jaydroid.setting.contract.SettingContract
import com.jaydroid.setting.presenter.SettingPresenter
import org.greenrobot.eventbus.EventBus

@Route(path = ARHelper.PathSetting.SETTING_ACTIVITY_PATH)
class SettingActivity : BaseMVPActivity<SettingContract.View, SettingPresenter>(),
    SettingContract.View {

    private lateinit var logoutBtn: Button

    private lateinit var toolbar: Toolbar


    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun createPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "设置"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        logoutBtn = findViewById(R.id.btn_logout)
        logoutBtn.setOnClickListener { presenter.logout() }

    }

    override fun initData() {
        super.initData()
        val loggedIn = isCookieNotEmpty(mContext)
        if (loggedIn) {
            logoutBtn.visibility = View.VISIBLE
        } else {
            logoutBtn.visibility = View.GONE
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onLogoutResult() {
        EventBus.getDefault().post(LoggedInEvent(null))
        finish()
    }


}
