package com.jaydroid.base_component.arouter.service.user

import com.jaydroid.base_component.arouter.service.ARouterService
import com.jaydroid.base_component.network.bean.github.GitUser

/**
 * 用户服务管理
 * @author wangxuejie
 * @date 2019-12-17 15:58
 * @version 1.0
 */
interface GitHubUserService : ARouterService {

    fun getUserInfo(): GitUser?

    fun setUserInfo(user: GitUser?)

}