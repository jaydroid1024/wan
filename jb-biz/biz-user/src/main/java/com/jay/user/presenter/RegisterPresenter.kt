package com.jay.user.presenter

import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.user.RegisterResponse
import com.jay.user.contract.RegisterContract

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    override fun register(username: String, password: String, repassword: String) {
        addSubscribe(getDefaultNet().register(username, password, repassword),
            object : BaseObserver<RegisterResponse>() {
                override fun onSuccess(data: RegisterResponse?) {
                    getView()?.onRegisterResult(data)
                }
            })
    }
}