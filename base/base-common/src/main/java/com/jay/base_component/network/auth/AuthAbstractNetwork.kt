package com.jay.base_component.network.auth

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jay.net.AbstractNetwork
import okhttp3.Headers
import okhttp3.OkHttpClient


/**
 * AuthAbstractNetwork
 * 处理身份验证authentication
 * 对BaseNetwork对第二层扩展，只做一些和公共业务相关等配置，如：缓存cookie,token处理等
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */

abstract class AuthAbstractNetwork<T>(context: Context) : AbstractNetwork<T>(context),
    AuthorizationInterceptor.HeaderListener {

    private val authInterceptor: AuthorizationInterceptor
        get() = AuthorizationInterceptor(this)

    override fun okHttpClientBuilderHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(authInterceptor)
        //缓存cookie
        builder.cookieJar(
            PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(context)
            )
        )
        return super.okHttpClientBuilderHandler(builder)
    }

    override fun onHeaderUpdated(headers: Headers) {

    }

}
