package com.jaydroid.main.main.presenter

import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.arouter.service.user.UserService
import com.jaydroid.base_component.base.mvp.BasePresenter
import com.jaydroid.main.main.contract.MainContract

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    var userService: UserService? = null

    init {
        userService =
            ARHelper.getService<UserService>(ARHelper.PathUser.USER_SERVICE_PATH)
    }

    override fun getUserInfo() {
        val user = userService?.getUserInfo()
        getView()?.onUserInfo(user)
    }

}