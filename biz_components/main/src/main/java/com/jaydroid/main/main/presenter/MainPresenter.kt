package com.jaydroid.main.main.presenter

import com.alibaba.android.arouter.launcher.ARouter
import com.jaydroid.main.main.contract.MainContract
import com.jaydroid.base_component.arouter.ARouterHelper
import com.jaydroid.base_component.arouter.service.user.UserService
import com.jaydroid.base_component.base.mvp.BasePresenter

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    var userService: UserService? = null

    init {
        userService =
            ARouter.getInstance().build(ARouterHelper.Path.LOGIN_SERVICE_PATH).navigation() as UserService?
    }

    override fun getUserInfo() {
        val user = userService?.getUserInfo()
        getView()?.onUserInfo(user)
    }

}