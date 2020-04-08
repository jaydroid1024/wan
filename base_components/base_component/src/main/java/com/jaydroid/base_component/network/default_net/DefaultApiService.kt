package com.jaydroid.base_component.network.default_net

import com.jaydroid.base_component.network.bean.github.Repo
import com.jaydroid.base_component.network.bean.wan.Article
import com.jaydroid.base_component.network.bean.wan.ArticleResponse
import com.jaydroid.base_component.network.bean.wan.Banner
import com.jaydroid.base_component.network.bean.wan.BaseResponse
import com.jaydroid.base_component.network.bean.wan.detail.AddFavoriteResponse
import com.jaydroid.base_component.network.bean.wan.search.SearchHot
import com.jaydroid.base_component.network.bean.wan.search.SearchResultResponse
import com.jaydroid.base_component.network.bean.wan.user.LogoutResult
import com.jaydroid.base_component.network.bean.wan.user.RegisterResponse
import com.jaydroid.base_component.network.bean.wan.user.User
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Jay on 2018/9/27.
 */

interface DefaultApiService {

    /** =======================================================
    Github 开放API：https://developer.github.com/v3/
    baseUrl 请使用：
    https://api.github.com
    ========================================================== */

    //https://api.github.com/users/Jay-Droid/repos
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Flowable<List<Repo>>

    //https://api.github.com/users/Jay-Droid/repos
    @GET("users/{user}/repos")
    fun listRepos2(@Path("user") user: String): Observable<List<Repo>>

    //https://api.github.com/users/Jay-Droid/repos
    @GET("users/{user}/repos")
    fun listRepos3(@Path("user") user: String): Call<List<Repo>>


    /** =======================================================
    WanAndroid 开放API：https://www.wanandroid.com/blog/show/2
    baseUrl 请使用：
    https://www.wanandroid.com
    ========================================================== */

    @POST("user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResponse<User>>


    @GET("user/logout/json")
    fun logout(): Observable<BaseResponse<LogoutResult>>


    @POST("user/register")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Observable<BaseResponse<RegisterResponse>>


    @GET("banner/json")
    fun getBanner(): Observable<BaseResponse<List<Banner>>>

    @GET("article/top/json")
    fun getTopArticle(): Observable<BaseResponse<List<Article>>>

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>


    @GET("lg/collect/list/{page}/json")
    fun getArticleFavorites(@Path("page") page: Int): Observable<BaseResponse<ArticleResponse>>


    @GET("hotkey/json")
    fun getSearchHot(): Observable<BaseResponse<ArrayList<SearchHot>>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchResult(@Path("page") page: Int, @Field("k") keyword: String): Observable<BaseResponse<SearchResultResponse>>


    @POST("lg/collect/{id}/json")
    fun cancelFavorite(@Path("id") id: Int): Observable<BaseResponse<AddFavoriteResponse>>


    @POST("lg/collect/add/json")
    @FormUrlEncoded
    fun addFavorite(@Field("title") title: String, @Field("author") author: String, @Field("link") link: String): Observable<BaseResponse<AddFavoriteResponse>>


}
