package com.jaydroid.base_lib.net

import retrofit2.Retrofit

/**
 * RetrofitHelper
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:06
 */
class RetrofitHelper {
    companion object {

        private var retrofit: Retrofit? = null

        fun init(retrofit: Retrofit) {
            RetrofitHelper.retrofit = retrofit
        }

        fun retrofit(): Retrofit? {
            return retrofit
        }
    }
}
