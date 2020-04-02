package com.jaydroid.base_component.arouter.service.user

import com.jaydroid.base_component.arouter.service.ARouterService
import com.jaydroid.base_component.network.bean.wan.User

/**
 * 用户服务管理
 * @author wangxuejie
 * @date 2019-12-17 15:58
 * @version 1.0
 */
interface UserService : ARouterService {

    fun getUserInfo(): User?

    fun setUserInfo(user: User?)

}