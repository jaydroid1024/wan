package com.jay.search.adapter

import android.text.Html
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jay.base_component.network.bean.wan.search.SearchResult
import com.jay.search.R

class SearchResultAdapter(layoutRes: Int) :
    BaseQuickAdapter<SearchResult, BaseViewHolder>(layoutRes) {
    override fun convert(helper: BaseViewHolder?, item: SearchResult?) {
        helper?.setText(R.id.tv_search_result_title, Html.fromHtml(item?.title))
            ?.setText(R.id.tv_search_result_author, item?.author)
            ?.setText(R.id.tv_search_result_date, item?.niceDate)
            ?.setGone(R.id.iv_search_result_image, !TextUtils.isEmpty(item?.envelopePic))
        Glide.with(mContext).load(item?.envelopePic)
            .into(helper!!.getView(R.id.iv_search_result_image))
    }

}