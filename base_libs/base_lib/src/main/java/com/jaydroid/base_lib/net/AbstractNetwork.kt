package com.jaydroid.base_lib.net

import android.content.Context
import com.jaydroid.base_lib.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * AbstractNetwork
 * 对BaseNetwork对第一层扩展，只做一些全局的网络配置，如：超时，日志，安全等
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
abstract class AbstractNetwork<T>(val context: Context) : BaseNetwork<T>() {

    override fun retrofitHandler(builder: Retrofit.Builder): Retrofit.Builder {
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return super.retrofitHandler(builder)
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        return loggingInterceptor
    }

    override fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor())
            builder.addInterceptor(ChuckInterceptor(context))
        }
        builder.addInterceptor(NetworkAvailabilityInterceptor(context))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return builder
    }


}
