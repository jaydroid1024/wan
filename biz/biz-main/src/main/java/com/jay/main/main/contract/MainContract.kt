package com.jay.main.main.contract

import com.jay.base_component.base.mvp.IView
import com.jay.base_component.network.bean.wan.user.User

interface MainContract {

    interface View : IView {
        fun onUserInfo(user: User?)
    }

    interface Presenter {
        fun getUserInfo()
    }
}