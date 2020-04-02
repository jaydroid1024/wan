package com.jaydroid.main.main.contract

import com.jaydroid.base_component.base.mvp.IView
import com.jaydroid.base_component.network.bean.wan.User

interface MainContract {

    interface View : IView {
        fun onUserInfo(user: User?)
    }

    interface Presenter {
        fun getUserInfo()
    }
}