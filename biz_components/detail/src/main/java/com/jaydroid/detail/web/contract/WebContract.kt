package com.jaydroid.detail.web.contract

import com.jaydroid.base_component.base.mvp.IView
import com.jaydroid.base_component.network.bean.wan.detail.AddFavoriteResponse

interface WebContract {

    interface View : IView {
        fun onAddFavorited(addFavoriteResponse: AddFavoriteResponse?)
    }

    interface Presenter {
        fun addFavorite(id: Int, title: String, author: String, link: String)
    }
}