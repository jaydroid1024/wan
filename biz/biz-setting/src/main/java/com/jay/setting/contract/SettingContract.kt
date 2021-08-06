package com.jay.setting.contract

import com.jay.base_component.base.mvp.IView

interface SettingContract {

    interface View : IView {
        fun onLogoutResult()
    }

    interface Presenter {
        fun logout()
    }
}