package com.jay.net

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jay.lib_net.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * AbstractNetwork
 * 对BaseNetwork对第一层扩展，只做一些全局的网络配置，如：超时，日志，安全等
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
abstract class AbstractNetwork<T>(protected val context: Context) : BaseNetwork<T>() {


    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        return loggingInterceptor
    }

    override fun okHttpClientBuilderHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            //Chrome调试
            builder.addNetworkInterceptor(StethoInterceptor())
            //OkHttp请求日志
            builder.addInterceptor(httpLoggingInterceptor())
            //会将http信息发送到push页面
            builder.addInterceptor(ChuckInterceptor(context))
        }
        builder.addInterceptor(NetworkAvailabilityInterceptor(context))
            .retryOnConnectionFailure(true)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return builder
    }

    /**
     * 返回一个 RetrofitUrlManager 处理后的OkHttpClient
     * 这种方式虽然使用方便，但是侵入型很大，已知问题是会影响证书，需要权衡使用
     */
//    override fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient? {
//        return RetrofitUrlManager.getInstance().with(builder).build()
//    }

    override fun gsonBuilderHandler(builder: GsonBuilder): GsonBuilder {
        builder.setPrettyPrinting()
        return super.gsonBuilderHandler(builder)
    }


}
