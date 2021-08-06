package com.jay.main.home.contract

import com.jay.base_component.base.mvp.IView
import com.jay.base_component.network.bean.wan.Banner
import com.jay.base_component.network.bean.wan.article.Article


interface HomeContract {

    interface View : IView {

        fun onBanner(list: List<Banner>?)

        fun onArticles(page: Int, list: List<Article>?)
    }

    interface Presenter {

        fun getBanner()

        fun getArticles(page: Int)

    }

}