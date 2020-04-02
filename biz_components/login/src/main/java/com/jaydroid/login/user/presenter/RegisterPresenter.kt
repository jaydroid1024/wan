package com.jaydroid.login.user.presenter

import com.jaydroid.login.user.contract.RegisterContract
import com.jaydroid.base_component.base.mvp.BaseObserver
import com.jaydroid.base_component.base.mvp.BasePresenter
import com.jaydroid.base_component.network.bean.wan.RegisterResponse

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