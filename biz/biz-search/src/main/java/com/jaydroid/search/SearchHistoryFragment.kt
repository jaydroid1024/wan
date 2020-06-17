package com.jaydroid.search


import android.app.Activity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaydroid.base_component.base.mvp.BaseMVPFragment
import com.jaydroid.base_component.network.bean.wan.search.SearchHistory
import com.jaydroid.base_component.network.bean.wan.search.SearchHot
import com.jaydroid.base_component.widget.flowlayout.FlowAdapter
import com.jaydroid.base_component.widget.flowlayout.FlowLayout
import com.jaydroid.search.adapter.SearchHistoryAdapter
import com.jaydroid.search.contract.SearchHistoryContract
import  search.presenter.SearchHistoryPresenter

/**
 *  搜索历史
 */
class SearchHistoryFragment : BaseMVPFragment<SearchHistoryContract.View, SearchHistoryPresenter>(),
    SearchHistoryContract.View {

    private var recyclerView: RecyclerView? = null
    private var searchHotsList = arrayListOf<SearchHot>()
    private lateinit var flowLayout: FlowLayout<SearchHot>
    private lateinit var onSearchTextListener: OnSearchTextListener

    override fun getLayoutResId(): Int {
        return R.layout.biz_search_fragment_search_history
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        val headerView = layoutInflater.inflate(R.layout.biz_search_layout_search_history_header, null, false)
        flowLayout = headerView.findViewById(R.id.fl_flow)
        recyclerView = rootView?.findViewById(R.id.rv_search_history)
        recyclerView?.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        val adapter = SearchHistoryAdapter(R.layout.biz_search_item_search_history)
        adapter.addHeaderView(headerView)
        recyclerView?.adapter = adapter

    }

    override fun createPresenter(): SearchHistoryPresenter {
        return SearchHistoryPresenter()
    }

    override fun initData() {
        super.initData()
        // 获取搜索热门
        presenter.getSearchHot()
        // 获取搜索历史
        presenter.getSearchHistory()
    }

    override fun onSearchHot(searchHots: ArrayList<SearchHot>?) {
        if (searchHots != null) {
            searchHotsList = searchHots
        }
        flowLayout.setAdapter(object : FlowAdapter<SearchHot>(searchHotsList) {
            override fun getView(position: Int, t: SearchHot, parent: ViewGroup): View {
                val textView = TextView(mContext)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
                textView.setBackgroundResource(R.drawable.biz_search_shape_search_history_bg)
                textView.text = t.name
                textView.setTextColor(resources.getColor(R.color.black))
                return textView
            }
        })
        flowLayout.setOnItemClickListener(object : FlowLayout.OnItemClickListener<SearchHot> {
            override fun onItemClick(
                position: Int,
                adapter: FlowAdapter<SearchHot>,
                parent: FlowLayout<SearchHot>
            ) {
                onSearchTextListener.onSearchText(adapter.getItem(position).name)

            }
        })
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onSearchHistory(searchHistory: ArrayList<SearchHistory>?) {
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onSearchTextListener = activity as OnSearchTextListener
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    interface OnSearchTextListener {
        fun onSearchText(text: String)
    }
}
