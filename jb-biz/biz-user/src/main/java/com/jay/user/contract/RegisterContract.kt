package com.jay.user.contract

import com.jay.base_component.base.mvp.IView
import com.jay.base_component.network.bean.wan.user.RegisterResponse

interface RegisterContract {

    interface View : IView {
        fun onRegisterResult(result: RegisterResponse?)
    }

    interface Presenter {
        fun register(username: String, password: String, repassword: String)
    }
}