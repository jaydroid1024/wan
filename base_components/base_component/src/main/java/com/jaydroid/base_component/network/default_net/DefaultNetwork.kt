package com.jaydroid.base_component.network.default_net

import android.content.Context
import com.jaydroid.base_component.BuildConfig
import com.jaydroid.base_component.app.BaseComponentApp
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.arouter.service.user.UserService
import com.jaydroid.base_component.network.auth.AuthAbstractNetwork
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
import io.reactivex.Observable

/**
 * BaseNetwork
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */

class DefaultNetwork(context: Context) : AuthAbstractNetwork<DefaultApiService>(context) {
    var userService: UserService? = null

    init {
        userService =
            ARHelper.getService<UserService>(ARHelper.PathUser.USER_SERVICE_PATH)
    }

    override val baseUrl: String
        get() = BuildConfig.BASE_URL

    override val restClass: Class<DefaultApiService>
        get() = DefaultApiService::class.java


    fun login(name: String, pwd: String): Observable<BaseResponse<User>> {
        return getNetworkService()
            .login(name, pwd)
            .compose(RxUtil.applyObservableTransformer())
            .doOnNext {
                userService?.setUserInfo(it.data)
            }
    }

    fun logout(): Observable<BaseResponse<LogoutResult>> {
        return getNetworkService()
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
        return getNetworkService()
            .register(name, pwd, rPwd)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun getBanner(): Observable<BaseResponse<List<Banner>>> {
        return getNetworkService()
            .getBanner()
            .compose(RxUtil.applyObservableTransformer())
    }

    fun getTopArticle(): Observable<BaseResponse<List<Article>>>? {
        return getNetworkService()
            .getTopArticle()
            .compose(RxUtil.applyObservableTransformer())

    }

    fun getArticles(page: Int): Observable<BaseResponse<ArticleResponse>>? {
        return getNetworkService()
            .getArticles(page)
            .compose(RxUtil.applyObservableTransformer())
    }


    fun getArticleFavorites(page: Int): Observable<BaseResponse<ArticleResponse>> {
        return getNetworkService()
            .getArticleFavorites(page)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun getSearchHot(): Observable<BaseResponse<ArrayList<SearchHot>>> {
        return getNetworkService()
            .getSearchHot()
            .compose(RxUtil.applyObservableTransformer())

    }

    fun getSearchResult(
        page: Int,
        keyword: String
    ): Observable<BaseResponse<SearchResultResponse>> {
        return getNetworkService()
            .getSearchResult(page, keyword)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun addFavorite(
        title: String,
        author: String,
        link: String
    ): Observable<BaseResponse<AddFavoriteResponse>> {
        return getNetworkService()
            .addFavorite(title, author, link)
            .compose(RxUtil.applyObservableTransformer())
    }

    fun cancelFavorite(id: Int): Observable<BaseResponse<AddFavoriteResponse>> {
        return getNetworkService()
            .cancelFavorite(id)
            .compose(RxUtil.applyObservableTransformer())
    }


}
