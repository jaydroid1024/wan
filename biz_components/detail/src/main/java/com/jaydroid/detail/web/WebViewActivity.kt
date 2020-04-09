package com.jaydroid.detail.web


import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.mvp.BaseMVPActivity
import com.jaydroid.base_component.constant.Constants
import com.jaydroid.base_component.network.bean.wan.detail.AddFavoriteResponse
import com.jaydroid.base_component.network.bean.wan.detail.WebOptBean
import com.jaydroid.base_component.utils.dp2px
import com.jaydroid.base_component.widget.XWebView
import com.jaydroid.base_lib.utils.ToastUtils
import com.jaydroid.detail.R
import com.jaydroid.detail.web.contract.WebContract
import com.jaydroid.detail.web.presenter.WebPresenter
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.util.*
import kotlin.collections.ArrayList

@Route(path = ARHelper.PathDetail.DETAIL_ACTIVITY_PATH)
class WebViewActivity : BaseMVPActivity<WebContract.View, WebPresenter>(), WebContract.View {

    private lateinit var toolbar: Toolbar
    private var webView: XWebView? = null
    private var moreMenuItem: MenuItem? = null
    private var title: String? = null
    private var link: String? = null
    private var id: Int? = -1
    private var author: String? = null
    private var dialogFragment: WebDialogFragment? = null
    private var loadUrl: String? = null
    private var appId: String = "wx2c753629bd2e94bd"
    /**
     * Intent传递的参数
     */
    private var mMapParams: HashMap<String, Any>? = null


    override fun initView() {
        toolbar = findViewById(R.id.tb_web)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.base_component_ic_back)
        supportActionBar?.elevation = dp2px(mContext, 5f)
        toolbar.setNavigationOnClickListener {
            goBack()
        }
        webView = findViewById(R.id.pwv_webview)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_web_view
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun createPresenter(): WebPresenter {
        return WebPresenter()
    }

    private fun initIntent() {
        mMapParams =
            intent.getSerializableExtra(Constants.IntentKey.MAP_PARAMS) as? HashMap<String, Any>?
    }

    override fun initData() {
        super.initData()
        initIntent()
        initWxShare()
        loadUrl = mMapParams?.get(Constants.MapKey.LINK) as? String
        id = mMapParams?.get(Constants.MapKey.ID) as? Int
        link = mMapParams?.get(Constants.MapKey.LINK) as? String
        title = mMapParams?.get(Constants.MapKey.TITLE) as? String
        author = mMapParams?.get(Constants.MapKey.AUTHOR) as? String
        if (loadUrl.isNullOrEmpty()) {
            loadUrl = "https://juejin.im/post/5e85fe4e6fb9a03c6f66eef9#heading-3"
        }
        webView?.loadUrl(loadUrl)
        webView?.setWebViewCallback(object : XWebView.OnWebViewCallback {
            override fun onPageFinished(view: WebView?, url: String?) {
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.e("debug", "progres = $newProgress")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }

            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }

            override fun onPageLoadComplete() {
                moreMenuItem?.isVisible = true
            }
        })
    }

    private fun initWxShare() {
        val wxapi = WXAPIFactory.createWXAPI(mContext, "", true)
        // 将应用的appId注册到微信
        wxapi.registerApp(appId)
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // 将该app注册到微信
                wxapi.registerApp(appId)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 获取菜单项
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        moreMenuItem = menu?.findItem(R.id.item_more)
        // 默认是不显示的,页面加载完成才显示
        moreMenuItem?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 菜单项点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.item_more) {
            showMoreDialog()
        }
        return true
    }

    private fun showMoreDialog() {
        val dataList = ArrayList<WebOptBean>()
        dataList.add(WebOptBean(R.drawable.ic_favorite_white, "收藏"))
        dataList.add(WebOptBean(R.drawable.ic_share, "朋友圈"))
        dataList.add(WebOptBean(R.drawable.ic_wx_friend, "微信好友"))
        dataList.add(WebOptBean(R.drawable.ic_link, "复制链接"))
        dataList.add(WebOptBean(R.drawable.ic_refresh, "刷新"))
        dataList.add(WebOptBean(R.drawable.ic_browser, "浏览器打开"))
        if (dialogFragment == null) {
            dialogFragment = WebDialogFragment.newInstance(dataList)
        }
        dialogFragment?.setOnItemClickListener(object : WebDialogFragment.OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> addArticleFavorite()
                    1 -> shareToWeChat(SendMessageToWX.Req.WXSceneTimeline)
                    2 -> shareToWeChat(SendMessageToWX.Req.WXSceneSession)
                    3 -> copyLink()
                    4 -> refreshPage()
                    5 -> openByBrowser()
                }
            }
        })
        val dialog = dialogFragment?.dialog
        val isShowing = dialog?.isShowing ?: false
        if (isShowing) {
            return
        }
        dialogFragment?.show(supportFragmentManager, WebDialogFragment().javaClass.name)
    }

    /**
     * 返回
     */
    private fun goBack() {
        val canGoBack = webView?.canGoBack() ?: false
        if (canGoBack) {
            webView?.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.removeAllViews()
        webView?.clearHistory()
        webView = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goBack()
    }


    /**
     * 刷新
     */
    private fun refreshPage() {
        webView?.reload()
    }

    /**
     * 拷贝链接
     */
    private fun copyLink() {
        // 获取剪贴板管理器：
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
//        val clipData = ClipData.newPlainText("Label", "baidu")
        // 创建链接型 clipData
        val clipData = ClipData.newRawUri("Label", Uri.parse(loadUrl))
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(clipData)
        Toast.makeText(mContext, "已复制至剪切板", Toast.LENGTH_LONG).show()
    }

    /**
     * 分享至微信
     */
    private fun shareToWeChat(scene: Int) {
        WeChatShareUtils.shareWeb(mContext, "", loadUrl ?: "", "", null, null, scene)
    }

    /**
     * 文章收藏
     */
    private fun addArticleFavorite() {
        presenter.addFavorite(id ?: -1, title ?: "", author ?: "", link ?: "")
    }

    /**
     * 浏览器打开
     */
    private fun openByBrowser() {
        val uri = Uri.parse(loadUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


    /**
     * 收藏成功回调
     */
    override fun onAddFavorited(addFavoriteResponse: AddFavoriteResponse?) {
        ToastUtils.showShort("收藏成功")
    }

}
