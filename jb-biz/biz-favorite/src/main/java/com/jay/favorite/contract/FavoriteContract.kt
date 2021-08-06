package com.jay.favorite.contract

import com.jay.base_component.base.mvp.IView
import com.jay.base_component.network.bean.wan.article.ArticleResponse

interface FavoriteContract {

    interface View : IView {
        fun onArticleFavorite(page: Int, response: ArticleResponse?)
    }

    interface Presenter {
        fun getArticleFavorites(page: Int)
    }
}