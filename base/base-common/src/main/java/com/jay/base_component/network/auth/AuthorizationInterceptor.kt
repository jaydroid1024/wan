package com.jay.base_component.network.auth

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * AuthorizationInterceptor
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
class AuthorizationInterceptor(private val listener: HeaderListener?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oriRequest = chain.request()
        val response = chain.proceed(addHeaderAuth(oriRequest))
        listener?.onHeaderUpdated(response.headers)
        return response
    }

    private fun addHeaderAuth(oriRequest: Request): Request {

        return oriRequest.newBuilder().build()
    }

    interface HeaderListener {

        fun onHeaderUpdated(headers: Headers)
    }
}
