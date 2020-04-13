package com.jaydroid.base_lib.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * BaseNetwork
 * 网络框架基础类：Retrofit+OkHttp+Gson
 * 一般情况下不可直接修改此类，遵循开闭原则只对此类扩展
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
abstract class BaseNetwork<T> {

    private var networkService: T? = null

    protected abstract val baseUrl: String

    protected abstract val restClass: Class<T>

    private fun initNetworkInterface() {
        val client = this.okHttpClientHandler(OkHttpClient.Builder()).build()
        val gson = this.gsonHandler(GsonBuilder().setPrettyPrinting()).create()
        val retrofitBuilder = Retrofit.Builder().baseUrl(this.baseUrl)
        val retrofit = this.retrofitHandler(retrofitBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        RetrofitHelper.init(retrofit)
        this.networkService = retrofit.create(this.restClass)
    }

    fun getNetworkService(): T {
        if (this.networkService == null) {
            this.initNetworkInterface()
        }
        return this.networkService!!
    }

    protected open fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
    }

    protected open fun retrofitHandler(builder: Retrofit.Builder): Retrofit.Builder {
        return builder
    }

    private fun gsonHandler(builder: GsonBuilder): GsonBuilder {
        return builder
    }

    companion object {
        val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ssZ"
    }
}
