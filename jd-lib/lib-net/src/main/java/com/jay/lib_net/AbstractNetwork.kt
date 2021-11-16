package com.jay.lib_net

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.jay.lib_net.jdispatcher.DispatchNet
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
abstract class AbstractNetwork<T> : BaseNetwork<T>() {

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply { loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        return loggingInterceptor
    }

    override fun okHttpClientBuilderHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            //charles调试
//            setCharlesUtil(builder)
            //Chrome调试
            builder.addNetworkInterceptor(StethoInterceptor())
            //OkHttp请求日志
            builder.addInterceptor(httpLoggingInterceptor())
            //会将http信息发送到push页面
            builder.addInterceptor(ChuckInterceptor(DispatchNet.getApp()))
            //OkHttp Profiler
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }
        builder.retryOnConnectionFailure(true)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
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

    /**
     * 设置charles抓包工具
     * @param builder Builder
     */
    private fun setCharlesUtil(builder: OkHttpClient.Builder) {
        //正式去掉
        val httpsUtils = HttpsSecure()
        //单向验证
        DispatchNet.getApp()?.let {
            httpsUtils.init(
                it,
                null,
                HttpsSecure.CRT_NAME,
                null,
                null,
                HttpsSecure.ONE_VERIFY
            )
        }
        val sslParams = httpsUtils.sslParams
        //Charles工具抓包
        builder.sslSocketFactory(sslParams!!.sSLSocketFactory!!, sslParams.trustManager!!)
        builder.hostnameVerifier(httpsUtils.UnSafeHostnameVerifier())
    }
}
