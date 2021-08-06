package com.jay.user.presenter

import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.LoggedInEvent
import com.jay.base_component.network.bean.wan.user.User
import com.jay.user.contract.LoginContract
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