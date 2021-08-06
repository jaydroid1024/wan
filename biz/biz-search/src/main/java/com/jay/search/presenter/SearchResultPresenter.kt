package com.jay.search.presenter

import com.jay.base_component.BuildConfig
import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.search.SearchResultResponse
import com.jay.search.contract.SearchResultContract
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class SearchResultPresenter : BasePresenter<SearchResultContract.View>(),
    SearchResultContract.Presenter {

    override fun getSearchResult(page: Int, keyword: String?) {
        if (keyword == null) {
            return
        }
        RetrofitUrlManager.getInstance().putDomain("search", BuildConfig.BASE_URL)
        addSubscribe(
            getDefaultNet().getSearchResult(page, keyword),
            object : BaseObserver<SearchResultResponse>(getView()) {
                override fun onSuccess(data: SearchResultResponse?) {
                    if (this@SearchResultPresenter.isViewAttached()) {
                        this@SearchResultPresenter.getView()?.onSearchResult(page, data)
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (this@SearchResultPresenter.isViewAttached()) {
                        this@SearchResultPresenter.getView()?.onSearchResult(page, null)
                    }

                }

            })
    }


}