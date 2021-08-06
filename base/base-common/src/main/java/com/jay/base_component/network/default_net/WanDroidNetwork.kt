package com.jay.base_component.network.default_net

import android.content.Context
import com.jay.base_component.BuildConfig
import com.jay.base_component.app.BaseComponentApp
import com.jay.base_component.arouter.ARHelper
import com.jay.base_component.arouter.service.user.UserService
import com.jay.base_component.network.auth.AuthAbstractNetwork
import com.jay.base_component.network.bean.wan.Banner
import com.jay.base_component.network.bean.wan.BaseResponse
import com.jay.base_component.network.bean.wan.article.Article
import com.jay.base_component.network.bean.wan.article.ArticleResponse
import com.jay.base_component.network.bean.wan.detail.AddFavoriteResponse
import com.jay.base_component.network.bean.wan.search.SearchHot
import com.jay.base_component.network.bean.wan.search.SearchResultResponse
import com.jay.base_component.network.bean.wan.user.LogoutResult
import com.jay.base_component.network.bean.wan.user.RegisterResponse
import com.jay.base_component.network.bean.wan.user.User
import io.reactivex.Observable

/**
 * DefaultNetwork：玩Android API 网络请求默认类
 * 真正做业务网络处理的子类，一个baseUrl对应一类网络请求，项目中存在多baseUrl需要新建该类实现
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */

class WanDroidNetwork(context: Context) : AuthAbstractNetwork<WanDroidApiService>(context) {
    var userService: UserService? = null

    init {
        userService =
            ARHelper.getService<UserService>(ARHelper.PathUser.USER_SERVICE_PATH)
    }

    override val baseUrl: String
        get() = BuildConfig.BASE_URL

    override val apiServiceClass: Class<WanDroidApiService>
        get() = WanDroidApiService::class.java


    fun login(name: String, pwd: String): Observable<BaseResponse<User>> {
        return getApiService()
            .login(name, pwd)
            .compose(RxUtil.applyObservableTransformer())
            .doOnNext {
                userService?.setUserInfo(it.data)
            }
    }

    fun logout(): Observable<BaseResponse<LogoutResult>> {
        return getApiService()
            .logout()
            .compose(RxUtil.applyObservableTransformer())
            .doOnNext {
                if (it.isSuccess()) {
                    BaseComponentApp.getInstance().getPersistentCookieJar().clear()
                    userService?.setUserInfo(null)
                }
            }
    }

    fun register(
        name: String,
        pwd: String,
        rPwd: String
    ): Observable<BaseResponse<RegisterResponse>> {
        return getApiService()
            .register(name, pwd, rPwd)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun getBanner(): Observable<BaseResponse<List<Banner>>> {
        return getApiService()
            .getBanner()
            .compose(RxUtil.applyObservableTransformer())
    }

    fun getTopArticle(): Observable<BaseResponse<List<Article>>>? {
        return getApiService()
            .getTopArticle()
            .compose(RxUtil.applyObservableTransformer())

    }

    fun getArticles(page: Int): Observable<BaseResponse<ArticleResponse>>? {
        return getApiService()
            .getArticles(page)
            .compose(RxUtil.applyObservableTransformer())
    }


    fun getArticleFavorites(page: Int): Observable<BaseResponse<ArticleResponse>> {
        return getApiService()
            .getArticleFavorites(page)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun getSearchHot(): Observable<BaseResponse<ArrayList<SearchHot>>> {
        return getApiService()
            .getSearchHot()
            .compose(RxUtil.applyObservableTransformer())

    }

    fun getSearchResult(
        page: Int,
        keyword: String
    ): Observable<BaseResponse<SearchResultResponse>> {
        return getApiService()
            .getSearchResult(page, keyword)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun addFavorite(
        title: String,
        author: String,
        link: String
    ): Observable<BaseResponse<AddFavoriteResponse>> {
        return getApiService()
            .addFavorite(title, author, link)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun cancelFavorite(id: Int): Observable<BaseResponse<AddFavoriteResponse>> {
        return getApiService()
            .cancelFavorite(id)
            .compose(RxUtil.applyObservableTransformer())
    }


}
