package com.jaydroid.base_lib.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * BaseNetwork
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
abstract class BaseNetwork<T> {

    private var networkService: T? = null

    protected abstract val baseUrl: String

    protected abstract val restClass: Class<T>

    private fun initNetworkInterface() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply {
            this.level = (HttpLoggingInterceptor.Level.NONE)
        }
        val client =
            this.okHttpClientHandler(OkHttpClient.Builder()).addInterceptor(loggingInterceptor)
                .build()
        val gson = this.gsonHandler(GsonBuilder().setPrettyPrinting())
            .setDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").create()
        val retrofitBuilder = Retrofit.Builder().baseUrl(this.baseUrl)
        val retrofit = this.retrofitHandler(retrofitBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(client).build()
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
