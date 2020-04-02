package com.xing.wanandroid.home.presenter

import com.jaydroid.main.home.bean.Article
import com.jaydroid.main.home.bean.ArticleResponse
import com.jaydroid.base_component.network.bean.wan.Banner
import com.jaydroid.main.home.contract.HomeContract
import com.jaydroid.base_component.base.mvp.BaseObserver
import com.jaydroid.base_component.base.mvp.BasePresenter
import com.jaydroid.base_component.network.bean.wan.BaseResponse
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var dataList = ArrayList<Article>()

    override fun getBanner() {
        addSubscribe(getDefaultNet().getBanner(), object : BaseObserver<List<Banner>>() {
            override fun onSuccess(data: List<Banner>?) {
                getView()?.onBanner(data)
            }
        })
    }

    override fun getArticles(page: Int) {
        val zipObservable =
            Observable.zip(getDefaultNet().getTopArticle(), getDefaultNet().getArticles(page),
                object :
                    BiFunction<BaseResponse<List<Article>>, BaseResponse<ArticleResponse>, BaseResponse<List<Article>>> {
                    override fun apply(
                        resp1: BaseResponse<List<Article>>,
                        resp2: BaseResponse<ArticleResponse>
                    ): BaseResponse<List<Article>> {
                        if (page == 0) {
                            dataList.clear()
                            val topArticles = resp1.data
                            if (topArticles != null) {
                                dataList.addAll(topArticles)
                            }
                        }
                        val data = resp2.data
                        if (data != null) {
                            val articles = data.datas
                            if (articles != null) {
                                dataList.addAll(articles)
                            }
                        }
                        // 因为 BaseObserver 范型指定了为 BaseResponse， 所以这里重新构造 BaseResponse 对象作为返回值
                        return BaseResponse(
                            dataList,
                            dataList,
                            resp1.errorMsg,
                            resp1.errorCode,
                            false
                        )
                    }
                })

        addSubscribe(zipObservable, object : BaseObserver<List<Article>>() {
            override fun onSuccess(data: List<Article>?) {
                getView()?.onArticles(page, data)
            }
        })
    }
}