package com.jay.base_lib.net.sample

import com.jay.lib_net.AbstractNetwork
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */

class GithubNet : AbstractNetwork<GithubApiService>() {

    override val baseUrl: String
        get() = "https://api.github.com"

    override val apiServiceClass: Class<GithubApiService>
        get() = GithubApiService::class.java

    /*重写相关方法实现个性化定制*/

    override fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient? {
        return super.okHttpClientHandler(builder)
    }

    override fun retrofitBuilderHandler(builder: Retrofit.Builder): Retrofit.Builder {
        return super.retrofitBuilderHandler(builder)
    }

    override fun okHttpClientBuilderHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return super.okHttpClientBuilderHandler(builder)
    }

}
