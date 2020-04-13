package com.jaydroid.main.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.mvp.BaseMVPFragment
import com.jaydroid.base_component.constant.Constants
import com.jaydroid.base_component.network.bean.wan.article.Article
import com.jaydroid.base_component.widget.LinearItemDecoration
import com.jaydroid.main.R
import com.jaydroid.main.home.adapter.HomeRecyclerAdapter
import com.jaydroid.main.home.contract.HomeContract
import com.jaydroid.main.home.presenter.HomePresenter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

@Route(path = ARHelper.PathMain.HOME_FRAGMENT_PATH)
class HomeFragment : BaseMVPFragment<HomeContract.View, HomePresenter>(), HomeContract.View {

    private lateinit var adapter: HomeRecyclerAdapter
    private lateinit var banner: Banner
    private var recyclerView: RecyclerView? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private lateinit var headerView: View
    private var mCurPage: Int = 0
    private var dataList: List<Article> = ArrayList()
    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        refreshLayout = rootView?.findViewById(R.id.srl_home)
        refreshLayout?.setEnableRefresh(true)
        recyclerView = rootView?.findViewById(R.id.rv_home)
        headerView = layoutInflater.inflate(R.layout.layout_home_header, null, false)
        banner = headerView.findViewById(R.id.banner)
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun initData() {
        super.initData()
        val itemDecoration = LinearItemDecoration(mContext).color(
            ContextCompat.getColor(
                mContext,
                R.color.cornsilk
            )
        )
            .height(1f)
            .margin(15f, 15f)
            .jumpPositions(arrayOf(0))
        recyclerView?.addItemDecoration(itemDecoration)
        recyclerView?.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        adapter = HomeRecyclerAdapter(R.layout.item_home_recycler)

        adapter.addHeaderView(headerView)
        // recyclerview 点击监听
        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                ARHelper.routerTo(
                    getDetailParamMap(dataList[position]),
                    ARHelper.PathDetail.DETAIL_ACTIVITY_PATH
                )
            }
        recyclerView?.adapter = adapter

        // 获取 banner
        presenter.getBanner()
        // 获取文章
        presenter.getArticles(mCurPage)

        setListener()
    }

    private fun getDetailParamMap(searchResult: Article): HashMap<String, Any> {
        val map = HashMap<String, Any>(3)
        map[Constants.MapKey.ID] = searchResult.id
        map[Constants.MapKey.TITLE] = searchResult.title
        map[Constants.MapKey.AUTHOR] = searchResult.author
        map[Constants.MapKey.LINK] = searchResult.link
        return map
    }

    private fun setListener() {
        refreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                presenter.getArticles(mCurPage)

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                presenter.getArticles(0)
            }
        })
    }

    override fun onBanner(list: List<com.jaydroid.base_component.network.bean.wan.Banner>?) {
        val urlList = mutableListOf<String>()
        if (list != null) {
            for (banner in list) {
                urlList.add(banner.imagePath)
            }
        }
        banner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                val roundedCorners = RoundedCorners(20)
                val bitmapTransform = RequestOptions.bitmapTransform(roundedCorners)
                Glide.with(context!!).load(path).apply(bitmapTransform).into(imageView!!)
            }
        })
        banner.setImages(urlList)
            .isAutoPlay(true)
            .start()

        banner.setOnBannerListener(object : OnBannerListener {
            override fun OnBannerClick(position: Int) {
                if (list != null) {
                    ARHelper.routerTo(
                        getDetailParamMap(list[position]),
                        ARHelper.PathDetail.DETAIL_ACTIVITY_PATH
                    )
                }
            }
        })

    }

    private fun getDetailParamMap(searchResult: com.jaydroid.base_component.network.bean.wan.Banner): HashMap<String, Any> {
        val map = HashMap<String, Any>(3)
        map[Constants.MapKey.ID] = searchResult.id
        map[Constants.MapKey.TITLE] = searchResult.title
        map[Constants.MapKey.AUTHOR] = searchResult.desc
        map[Constants.MapKey.LINK] = searchResult.url
        return map
    }

    override fun onArticles(page: Int, list: List<Article>?) {
        refreshLayout?.finishRefresh()
        refreshLayout?.finishLoadMore()
        mCurPage = page + 1
        if (list != null) {
            dataList = list
        }
        adapter.setNewData(dataList)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }


    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
