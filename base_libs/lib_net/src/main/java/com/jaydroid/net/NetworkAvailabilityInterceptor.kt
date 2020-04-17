package com.jaydroid.net

import android.content.Context
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * NetworkAvailabilityInterceptor
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:06
 */
class NetworkAvailabilityInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!Connectivity.hasNetwork(context)) {
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show()
        }
        return chain.proceed(request)
    }

}