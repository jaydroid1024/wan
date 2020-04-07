package com.jaydroid.favorite.contract

import com.jaydroid.base_component.base.mvp.IView
import com.jaydroid.base_component.network.bean.wan.ArticleResponse

interface FavoriteContract {

    interface View : IView {
        fun onArticleFavorite(page: Int, response: ArticleResponse?)
    }

    interface Presenter {
        fun getArticleFavorites(page: Int)
    }
}