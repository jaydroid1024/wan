package com.jaydroid.base_component.network.auth

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jaydroid.base_lib.net.AbstractNetwork
import okhttp3.Headers
import okhttp3.OkHttpClient


/**
 * Created by taufiqotulfaidah on 12/21/17.
 */

abstract class AuthAbstractNetwork<T>(context: Context) : AbstractNetwork<T>(context),
    AuthorizationInterceptor.HeaderListener {

    private val authInterceptor: AuthorizationInterceptor
        get() = AuthorizationInterceptor(this)

    override fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(authInterceptor)
        builder.cookieJar(
            PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(context)
            )
        )
        return super.okHttpClientHandler(builder)
    }

    override fun onHeaderUpdated(headers: Headers) {

    }

}
