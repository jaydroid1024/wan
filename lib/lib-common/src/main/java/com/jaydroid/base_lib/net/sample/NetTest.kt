package com.jaydroid.base_lib.net.sample

import android.util.Log
import com.jaydroid.base_lib.app.BaseLibApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author wangxuejie
 * @version 1.0
 * @date 2020/4/14
 */
class NetTest {

    private val gitNetService = GithubNet(BaseLibApp.getApp()).getApiService()

    open fun testNet() {
        Thread(Runnable {
            gitNetService.listRepos("Jay-Droid")
                .enqueue(object : Callback<MutableList<GithubApiService.Repo?>?> {
                    override fun onFailure(
                        call: Call<MutableList<GithubApiService.Repo?>?>,
                        throwable: Throwable
                    ) {
                        Log.d("NetTest", "onFailure" + throwable.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<MutableList<GithubApiService.Repo?>?>,
                        response: Response<MutableList<GithubApiService.Repo?>?>
                    ) {
                        Log.d("NetTest", "onResponse" + response.body()!![0].toString())
                    }

                })
        }).start()

    }
}


