package com.jaydroid.setting

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.mvp.BaseMVPActivity
import com.jaydroid.base_component.network.auth.isCookieNotEmpty
import com.jaydroid.base_component.network.bean.wan.LoggedInEvent
import com.jaydroid.base_lib.utils.ToastUtils
import com.jaydroid.setting.contract.SettingContract
import com.jaydroid.setting.presenter.SettingPresenter
import org.greenrobot.eventbus.EventBus

@Route(path = ARHelper.PathSetting.SETTING_ACTIVITY_PATH)
class SettingActivity : BaseMVPActivity<SettingContract.View, SettingPresenter>(),
    SettingContract.View {

    private lateinit var logoutBtn: Button

    private lateinit var toolbar: Toolbar

    @BindView(R2.id.iv_test)
    lateinit var ivTest: ImageView

    @BindView(R2.id.tv_test)
    lateinit var tvTest: TextView

    @OnClick(R2.id.iv_test, R2.id.tv_test)
    fun onClick(view: View) {

        when (view.id) {
            R.id.iv_test -> {
                ToastUtils.showLong("测试iv_test")
            }
            R.id.tv_test -> {
                ToastUtils.showLong("测试tv_test")
            }
        }
    }

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

        ivTest.setImageResource(R.mipmap.base_component_ic_launcher)
        tvTest.setText("测试文本")
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
