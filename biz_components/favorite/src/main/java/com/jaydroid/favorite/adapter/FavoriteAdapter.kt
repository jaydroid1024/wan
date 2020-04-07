package com.jaydroid.favorite.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jaydroid.base_component.network.bean.wan.Article
import com.jaydroid.base_component.utils.format
import com.jaydroid.favorite.R

class FavoriteAdapter(layoutResId: Int) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.setText(R.id.tv_home_title, item?.title)
            ?.setText(R.id.tv_home_author, item?.author)
            ?.setText(
                R.id.tv_home_public_time,
                format(item?.publishTime ?: System.currentTimeMillis())
            )
            ?.setText(R.id.tv_home_category, item?.superChapterName)
            ?.setGone(R.id.tv_home_recent, false)   // true 显示，false 隐藏
    }

}