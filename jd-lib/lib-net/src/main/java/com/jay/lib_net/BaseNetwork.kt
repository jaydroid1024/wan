package com.jay.lib_net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * BaseNetwork
 * 网络框架基础类：Retrofit+OkHttp+RxJava+Gson
 * javadoc:
 * https://square.github.io/okhttp/
 * https://square.github.io/retrofit/
 * http://reactivex.io/RxJava/2.x/javadoc/
 * 一般情况下不可直接修改此类，遵循开闭原则只对此类扩展
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
abstract class BaseNetwork<T> : INetwork {

    private var apiService: T? = null

    protected abstract val baseUrl: String

    protected abstract val apiServiceClass: Class<T>

    /**
     * 初始化 Retrofit apiService 实例
     * 默认配置：Retrofit+OkHttp+RxJava+Gson
     * 提供了
     */
    private fun initApiService() {
        val client = okHttpClientHandler(this.okHttpClientBuilderHandler(OkHttpClient.Builder()))
            ?: this.okHttpClientBuilderHandler(OkHttpClient.Builder()).build()
        val gson = this.gsonBuilderHandler(GsonBuilder()).create()
        val retrofitBuilder = Retrofit.Builder().baseUrl(this.baseUrl)
        val retrofit = this.retrofitBuilderHandler(retrofitBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        this.apiService = this.retrofitHandler(retrofit).create(this.apiServiceClass)
    }

    /**
     * 获取默认配置好的 apiService 实例
     */
    fun getApiService(): T {
        if (this.apiService == null) {
            this.initApiService()
        }
        return this.apiService!!
    }

    /**
     * 将 OkHttpClient.Builder 类传递给子类去个性化扩展
     * https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.Builder.html
     * OkHttpClient.Builder 常用方法:
     * - connectTimeout(long timeout,TimeUnit unit): 设置连接的连接超时的时间，默认10s，timeout超时时间，unit时间单位，一般是TimeUnit.SECOND或者TimeUnit.MILLIMETER。
     * - readTimeout(long timeout,TimeUnit unit): 设置连接的读取超时时间，默认10s，timeout超时时间，unit时间单位，读取超时时间指连接的套接字读取超时时间和某个响应数据的IO流的读取时间。
     * - writeTimeout(long timeout,TimeUnit unit): 设置写入超时时间，默认10s，写入超时时间指写入数据时的IO流的操作时间。
     * - cookieJar(CookieJar cookieJar): 设置Cookie管理，用来处理接收到的Cookie，和请求时携带的Cookie。如果不设置，则请求不验证Cookie，也不提供Cookie。
     * - cache(@Nullable Cache cache): 设置响应response的缓存
     * - addInterceptor(Interceptor interceptor): 添加拦截器，添加多个自定义的拦截器，例如打印请求与响应内容，为请求添加统一header等。
     * - addNetworkInterceptor(Interceptor interceptor): 仅处理网络请求与响应，不处理缓存的
     * - retryOnConnectionFailure(boolean retryOnConnectionFailure): 设置连接出现问题时是否重连。默认在以下情况重连1.当主机有多个IP时，当前IP不可用，会自动重连找到可用IP。2.连接池套接字重用可以减少延迟，但这些连接有时候会超时。3.代理服务器不可用。PROXY会按顺序尝试多个代理服务器，最终建立直连连接。
     * - proxy(@Nullable Proxy proxy)： 设置连接使用的HTTP代理。这个优先于proxySelector，尽在代理为空时被授予，默认代理为空。完全禁用代理使用proxy(Proxy.NO_PROXY)。
     * - proxySelector(ProxySelector proxySelector)： 设置要使用的代理选择策略。在没有明确指定代理的情况下使用。代理选择器可以返回多个代理；在这种情况下，它们将按顺序进行尝试，直到建立成功的连接为止。如果未设置，将使用系统范围的默认代理选择器
     * - sslSocketFactory(SSLSocketFactory sslSocketFactory,X509TrustManager trustManager): 设置用于安全HTTPS连接的套接字工厂和信任管理器。如果未设置，将使用系统默认值。大多数应用程序不应调用此方法，而是使用系统默认值。这些类包括特殊的优化，如果实现了这些修饰，这些优化可能会丢失。
     * - dispatcher(Dispatcher dispatcher): 设置用于设置策略和执行异步请求的分派器。不得设置为空。
     */
    protected open fun okHttpClientBuilderHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
    }

    /**
     * 从子类中获取 OkHttpClient
     */
    protected open fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient? {

        return null
    }

    /**
     * 将 Retrofit.Builder 类传递给子类去个性化扩展
     * https://square.github.io/retrofit/2.x/retrofit/
     * -转换器 CONVERTERS
     * 默认情况下，Retrofit 只能将 HTTP body 反序列化为 OkHttp 的 ResponseBody 类型，并且只能接受 @Body 的请求体类型。
     * 可以添加转换器来支持其他类型。有六个流行的序列化库可以提供方便：
     * Gson:        com.squareup.retrofit2:converter-gson
     * Jackson:     com.squareup.retrofit2:converter-jackson
     * Moshi:       com.squareup.retrofit2:converter-moshi
     * Protobuf:    com.squareup.retrofit2:converter-protobuf
     * Wire:        com.squareup.retrofit2:converter-wire
     * Simple XML:  com.squareup.retrofit2:converter-simplexml
     * Scalars:     com.squareup.retrofit2:converter-scalars
     */
    protected open fun retrofitBuilderHandler(builder: Retrofit.Builder): Retrofit.Builder {
        return builder
    }

    /**
     * 将 Retrofit 类传递给子类去个性化扩展
     * RetroFit  用来访问网络的第三方框架   http网络请求工具
     * RetroFit使用步骤:
     * 1.定义一个接口(封装url地址和数据请求)
     * 2.实例化retrofit
     * 3.调用retrofit实例创建接口服务对象
     * 4.调用接口中的方法获取Call对象
     * 5.call对象请求(异步\同步请求)
     * Retrofit 常用方法:
     * https://square.github.io/retrofit/2.x/retrofit/
     * -baseUrl(): API请求的基地址
     */
    protected open fun retrofitHandler(retrofit: Retrofit): Retrofit {
        return retrofit
    }

    /**
     * 将 GsonBuilder 类传递给子类去个性化扩展
     * GsonBuilder 常用方法:
     * https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/GsonBuilder.html
     * - setFieldNamingPolicy 设置序列字段的命名策略(UPPER_CAMEL_CASE,UPPER_CAMEL_CASE_WITH_SPACES,LOWER_CASE_WITH_UNDERSCORES,LOWER_CASE_WITH_DASHES)
     * - addDeserializationExclusionStrategy 设置反序列化时字段采用策略ExclusionStrategy，如反序列化时不要某字段，当然可以采用@Expore代替。
     * - excludeFieldsWithoutExposeAnnotation 设置没有@Expore则不序列化和反序列化
     * - addSerializationExclusionStrategy 设置序列化时字段采用策略，如序列化时不要某字段，当然可以采用@Expore代替。
     * - registerTypeAdapter 为某特定对象设置固定的序列和反序列方式，实现JsonSerializer和JsonDeserializer接口
     * - setFieldNamingStrategy 设置字段序列和反序列时名称显示，也可以通过@Serializer代替
     * - setPrettyPrinting 设置gson转换后的字符串为一个比较好看的字符串
     * - setDateFormat 设置默认Date解析时对应的format格式
     */
    protected open fun gsonBuilderHandler(builder: GsonBuilder): GsonBuilder {
        return builder
    }

}
