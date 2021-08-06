package  search.presenter

import android.util.Log
import com.jay.base_component.base.mvp.BaseObserver
import com.jay.base_component.base.mvp.BasePresenter
import com.jay.base_component.network.bean.wan.search.SearchHot
import com.jay.search.contract.SearchHistoryContract

class SearchHistoryPresenter : BasePresenter<SearchHistoryContract.View>(),
    SearchHistoryContract.Presenter {

    /**
     * 搜索热门
     */
    override fun getSearchHot() {
        addSubscribe(getDefaultNet().getSearchHot(),
            object : BaseObserver<ArrayList<SearchHot>>(getView()) {
                override fun onSuccess(data: ArrayList<SearchHot>?) {
                    if (this@SearchHistoryPresenter.isViewAttached()) {
                        Log.e("debug", "getSearchHot() = " + data?.size)
                        this@SearchHistoryPresenter.getView()?.onSearchHot(data)
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if (this@SearchHistoryPresenter.isViewAttached()) {
                        this@SearchHistoryPresenter.getView()?.onSearchHot(null)
                    }

                }

            })
    }


    override fun addSearchHistory(keyword: String) {

    }


    /**
     * 搜索历史
     */
    override fun getSearchHistory() {


    }

}