package com.jay.search.contract

import com.jay.base_component.base.mvp.IView
import com.jay.base_component.network.bean.wan.search.SearchHistory
import com.jay.base_component.network.bean.wan.search.SearchHot

interface SearchHistoryContract {
    interface View : IView {

        fun onSearchHot(searchHots: ArrayList<SearchHot>?)

        fun onSearchHistory(searchHistory: ArrayList<SearchHistory>?)

    }

    interface Presenter {

        /**
         * 获取搜索热门
         */
        fun getSearchHot()

        /**
         * 获取搜索历史
         */
        fun getSearchHistory()

        /**
         * 添加搜索历史
         */
        fun addSearchHistory(keyword: String)

    }
}