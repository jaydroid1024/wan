package com.jay.favorite.presenter

import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.article.ArticleResponse
import com.jay.favorite.contract.FavoriteContract

class FavoritePresenter : BasePresenter<FavoriteContract.View>(), FavoriteContract.Presenter {

    override fun getArticleFavorites(page: Int) {
        addSubscribe(getDefaultNet().getArticleFavorites(page),
            object : BaseObserver<ArticleResponse>(getView()) {
                override fun onSuccess(data: ArticleResponse?) {
                    getView()?.onArticleFavorite(page, data)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    getView()?.onArticleFavorite(page - 1, null)

                }

            })
    }
}