package com.jaydroid.detail.web.presenter

import com.jaydroid.base_component.base.mvp.BaseObserver
import com.jaydroid.base_component.base.mvp.BasePresenter
import com.jaydroid.base_component.network.bean.wan.BaseResponse
import com.jaydroid.base_component.network.bean.wan.detail.AddFavoriteResponse
import com.jaydroid.detail.web.contract.WebContract
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