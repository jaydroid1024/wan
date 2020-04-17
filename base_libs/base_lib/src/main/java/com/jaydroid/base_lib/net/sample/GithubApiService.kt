package com.jaydroid.base_lib.net.sample

import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
interface GithubApiService {

    /** =======================================================
    Github 开放API：https://developer.github.com/v3/
    baseUrl 请使用：
    https://api.github.com
    ========================================================== */

    //https://api.github.com/users/Jay-Droid/repos
    @GET("users/{user}/repos")
    fun listReposByRx(@Path("user") user: String): Flowable<List<Repo>>

    //https://api.github.com/users/Jay-Droid/repos
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<MutableList<Repo?>?>


    data class Repo(val id: String?, val name: String?, val description: String?, val url: String?)

}
