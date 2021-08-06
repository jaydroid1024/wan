package com.jay.detail.web.presenter

import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.BaseResponse
import com.jay.base_component.network.bean.wan.detail.AddFavoriteResponse
import com.jay.detail.web.contract.WebContract
import io.reactivex.Observable

class WebPresenter : BasePresenter<WebContract.View>(), WebContract.Presenter {

    override fun addFavorite(id: Int, title: String, author: String, link: String) {
        val observable: Observable<BaseResponse<AddFavoriteResponse>> = if (id == -1) {
            getDefaultNet().addFavorite(title, author, link)
        } else {
            getDefaultNet().cancelFavorite(id)
        }
        addSubscribe(observable, object : BaseObserver<AddFavoriteResponse>(getView()) {
            override fun onSuccess(data: AddFavoriteResponse?) {
                getView()?.onAddFavorited(data)
            }

        })
    }

}