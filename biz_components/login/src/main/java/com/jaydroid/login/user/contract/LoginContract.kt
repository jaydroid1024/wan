package com.jaydroid.login.user.contract

import com.jaydroid.base_component.base.mvp.IView
import com.jaydroid.base_component.network.bean.wan.user.User

interface LoginContract {

    interface View : IView {
        fun onLoginResult(username: String, user: User?)
    }

    interface Presenter {
        fun login(username: String, password: String)
    }
}
