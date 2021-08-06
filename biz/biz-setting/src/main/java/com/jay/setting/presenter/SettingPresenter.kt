package com.jay.setting.presenter

import com.jay.setting.contract.SettingContract
import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.user.LogoutResult

class SettingPresenter : BasePresenter<SettingContract.View>(), SettingContract.Presenter {


    override fun logout() {
        addSubscribe(getDefaultNet().logout(), object : BaseObserver<LogoutResult>(getView()) {
            override fun onSuccess(user: LogoutResult?) {
                getView()?.onLogoutResult()
            }
        })
    }
}