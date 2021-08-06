package com.jay.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.base_component.arouter.ARHelper
import com.jay.base_component.base.mvp.BaseMVPFragment
import com.jay.base_component.constant.Constants
import com.jay.base_component.network.bean.wan.search.SearchResult
import com.jay.base_component.network.bean.wan.search.SearchResultResponse
import com.jay.base_component.widget.LinearItemDecoration
import com.jay.search.adapter.SearchResultAdapter
import com.jay.search.contract.SearchResultContract
import com.jay.search.presenter.SearchResultPresenter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

private const val KEY_WORD = "key_word"

/**
 * 搜索结果
 */
class SearchResultFragment : BaseMVPFragment<SearchResultContract.View, SearchResultPresenter>(),
    SearchResultContract.View {

    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private var mCurPage: Int = 0
    private lateinit var searchResultAdapter: SearchResultAdapter
    private var dataList: ArrayList<SearchResult> = ArrayList()

    override fun getLayoutResId(): Int {
        return R.layout.biz_search_fragment_search_result
    }

    override fun createPresenter(): SearchResultPresenter {
        return SearchResultPresenter()
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        refreshLayout = rootView?.findViewById(R.id.srl_search_result)
        refreshLayout?.setEnableRefresh(false)
        recyclerView = rootView?.findViewById(R.id.rv_search_result)
        val itemDecoration =
            LinearItemDecoration(mContext).color(mContext.resources.getColor(R.color.whitesmoke))
                .height(1f)
                .margin(15f, 15f)
        recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun initData() {
        super.initData()
        recyclerView?.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        searchResultAdapter = SearchResultAdapter(R.layout.biz_search_item_search_result)
        searchResultAdapter.setOnItemClickListener { adapter, view, position ->

            ARHelper.routerTo(
                getDetailParamMap(dataList[position]),
                ARHelper.PathDetail.DETAIL_ACTIVITY_PATH
            )
        }
        recyclerView?.adapter = searchResultAdapter
        presenter.getSearchResult(0, keyword ?: "")

        setListener()
    }

    private fun getDetailParamMap(searchResult: SearchResult): HashMap<String, Any> {
        val map = HashMap<String, Any>(3)
        map[Constants.MapKey.ID] = searchResult.id
        map[Constants.MapKey.TITLE] = searchResult.title
        map[Constants.MapKey.AUTHOR] = searchResult.author
        map[Constants.MapKey.LINK] = searchResult.link
        return map
    }

    private fun setListener() {
        refreshLayout?.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.getSearchResult(mCurPage, keyword)
            }
        })
    }


    override fun onSearchResult(page: Int, response: SearchResultResponse?) {
        val datas = response?.datas
        if (page == 0) {
            if (datas == null || datas.isEmpty()) {
                val emptyView: View =
                    LayoutInflater.from(context)
                        .inflate(R.layout.base_component_layout_empty, null, false)
                searchResultAdapter.emptyView = emptyView
            }
        }
        refreshLayout?.finishLoadMore()
        mCurPage = page + 1
        if (datas != null) {
            dataList.addAll(datas)
        }
        searchResultAdapter.setNewData(dataList)
    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private var keyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            keyword = it.getString(KEY_WORD)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(keyword: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_WORD, keyword)
                }
            }
    }
}
