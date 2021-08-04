package com.jaydroid.search.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jaydroid.base_component.network.bean.wan.search.SearchHistory

class SearchHistoryAdapter(@LayoutRes val layoutResId: Int) :
    BaseQuickAdapter<SearchHistory, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: SearchHistory?) {
//        helper.setText()
    }
}