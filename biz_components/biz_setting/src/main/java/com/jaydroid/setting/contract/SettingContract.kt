package com.jaydroid.setting.contract

import com.jaydroid.base_component.base.mvp.IView

interface SettingContract {

    interface View : IView {
        fun onLogoutResult()
    }

    interface Presenter {
        fun logout()
    }
}