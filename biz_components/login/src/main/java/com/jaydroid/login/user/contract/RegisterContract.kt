package com.jaydroid.login.user.contract

import com.jaydroid.base_component.base.mvp.IView
import com.jaydroid.base_component.network.bean.wan.user.RegisterResponse

interface RegisterContract {

    interface View : IView {
        fun onRegisterResult(result: RegisterResponse?)
    }

    interface Presenter {
        fun register(username: String, password: String, repassword: String)
    }
}