package com.jaydroid.user.presenter

import com.jaydroid.base_component.base.mvp.BaseObserver
import com.jaydroid.base_component.base.mvp.BasePresenter
import com.jaydroid.base_component.network.bean.wan.LoggedInEvent
import com.jaydroid.base_component.network.bean.wan.user.User
import com.jaydroid.user.contract.LoginContract
import org.greenrobot.eventbus.EventBus

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String, password: String) {

        addSubscribe(
            getDefaultNet().login(username, password),
            object : BaseObserver<User>(getView()) {
                override fun onSuccess(user: User?) {
                    getView()?.onLoginResult(username, user)
                    EventBus.getDefault().post(
                        LoggedInEvent(
                            user
                        )
                    )
                }
            })
    }
}