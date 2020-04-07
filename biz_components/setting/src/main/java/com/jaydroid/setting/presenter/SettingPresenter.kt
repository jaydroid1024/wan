package com.jaydroid.setting.presenter

import com.jaydroid.setting.contract.SettingContract
import com.jaydroid.base_component.base.mvp.BaseObserver
import com.jaydroid.base_component.base.mvp.BasePresenter
import com.jaydroid.base_component.network.bean.wan.user.LogoutResult

class SettingPresenter : BasePresenter<SettingContract.View>(), SettingContract.Presenter {


    override fun logout() {
        addSubscribe(getDefaultNet().logout(), object : BaseObserver<LogoutResult>(getView()) {
            override fun onSuccess(user: LogoutResult?) {
                getView()?.onLogoutResult()
            }
        })
    }
}