package com.jaydroid.main.main

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.jaeger.library.StatusBarUtil
import com.jaydroid.base_component.arouter.ARHelper
import com.jaydroid.base_component.base.mvp.BaseMVPActivity
import com.jaydroid.base_component.network.auth.isCookieNotEmpty
import com.jaydroid.base_component.network.bean.wan.FragmentItem
import com.jaydroid.base_component.network.bean.wan.LoggedInEvent
import com.jaydroid.base_component.network.bean.wan.user.User
import com.jaydroid.base_component.utils.blur
import com.jaydroid.main.R
import com.jaydroid.main.main.adapter.MainViewPageAdapter
import com.jaydroid.main.main.contract.MainContract
import com.jaydroid.main.main.presenter.MainPresenter
import com.jaydroid.main.main.widgets.MainViewPager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = ARHelper.PathMain.MAIN_ACTIVITY_PATH)
class MainActivity : BaseMVPActivity<MainContract.View, MainPresenter>(), MainContract.View,
    View.OnClickListener {

    private lateinit var mainMenu: ImageView
    private lateinit var mainSearch: ImageView
    private lateinit var mainTabLayout: TabLayout
    private lateinit var mainViewPager: MainViewPager
    private lateinit var mAdapter: MainViewPageAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var avatarBackground: ImageView
    private lateinit var usernameTextView: TextView
    private var loggedIn = false

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mDrawerLayout = findViewById(R.id.dl_drawer_layout)
        StatusBarUtil.setColorForDrawerLayout(
            this,
            mDrawerLayout,
            Color.WHITE,
            0
        )
        navigationView = findViewById(R.id.nv_left_navigation)
        val headerView: View = navigationView.getHeaderView(0)
        usernameTextView = headerView.findViewById(R.id.tv_nav_username)
        avatarBackground = headerView.findViewById(R.id.iv_avatar_background)
        mainMenu = findViewById(R.id.iv_main_menu)
        mainSearch = findViewById(R.id.iv_main_search)
        mainTabLayout = findViewById(R.id.tl_main_tab)
        mainViewPager = findViewById(R.id.vp_main_pager)

        mainMenu.setOnClickListener {
            openDrawer()
        }
        usernameTextView.setOnClickListener(this)

        navigationView.setNavigationItemSelectedListener { item ->
            closeDrawer()
            when (item.itemId) {
                R.id.item_nav_favorite -> {
                    ARHelper.routerTo(ARHelper.PathFavorite.FAVORITE_ACTIVITY_PATH)
                }
                R.id.item_nav_setting -> {
                    ARHelper.routerTo(ARHelper.PathSetting.SETTING_ACTIVITY_PATH)
                }
            }
            true
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.tian)
        avatarBackground.setImageBitmap(blur(mContext, bitmap, 22))
    }

    override fun initData() {
        super.initData()
        val list = mutableListOf<FragmentItem>()
        //通过ARouter 获取其他组件提供的fragment
        val homeFragment = ARHelper.getService<Fragment>(ARHelper.PathMain.HOME_FRAGMENT_PATH)
        val homeFragment2 = ARHelper.getService<Fragment>(ARHelper.PathMain.HOME_FRAGMENT_PATH)
        homeFragment?.let {
            list.add(
                FragmentItem(
                    "首页",
                    it
                )
            )
        }

        homeFragment2?.let {
            list.add(
                FragmentItem(
                    "首页",
                    it
                )
            )
        }

        mAdapter = MainViewPageAdapter(this, supportFragmentManager, list)
        mainViewPager.adapter = mAdapter
        mainTabLayout.setupWithViewPager(mainViewPager)

        for (i in 0..mainTabLayout.tabCount) {
            val tabView: TabLayout.Tab? = mainTabLayout.getTabAt(i)
            tabView?.customView = mAdapter.getTabView(i)
        }

        // 默认选中第 0 个
        mainViewPager.currentItem = 0
        changeTabView(mainTabLayout.getTabAt(0), 18f, true)

        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                changeTabView(tab, 14f, false)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTabView(tab, 18f, true)

            }
        })
        setUsernameFromCache()
        presenter.getUserInfo()
        mainSearch.setOnClickListener(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        setUsernameFromCache()
    }

    private fun setUsernameFromCache() {
        loggedIn = isCookieNotEmpty(mContext)
        if (!loggedIn) {
            usernameTextView.text = "点击登陆"
            //todo fix -android-extensions 无法实例化view
//            iv_avatar_background.setImageDrawable(getDrawable(R.drawable.shape_recommend_bg))
//            iv_nav_avatar.setImageDrawable(getDrawable(R.drawable.shape_recommend_bg))
        } else {
            //todo fix -android-extensions 无法实例化view
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.tian)
//            iv_avatar_background.setImageBitmap(blur(mContext, bitmap, 22))
//            iv_nav_avatar.setImageDrawable(getDrawable(R.drawable.tian))
            val user = presenter.userService?.getUserInfo()
            val username: String
            username = if (user != null) {
                user.username
            } else {
                ""
            }
            usernameTextView.text = username
        }
    }

    /**
     * 设置用户名称，头像等信息
     */
    private fun setUsername(user: User?) {
        if (user != null) {
            usernameTextView.text = user.username
        } else {
            usernameTextView.text = "点击登陆"
        }
    }


    override fun onUserInfo(user: User?) {
        Log.e("MainActivity", user.toString())
        loggedIn = isCookieNotEmpty(mContext)
        if (loggedIn) {
            usernameTextView.text = user?.username
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginStatusChanged(event: LoggedInEvent) {
        val user = event.user
        setUsername(user)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }


    /**
     * 打开抽屉
     */
    private fun openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT)
    }

    /**
     * 关闭抽屉
     */
    private fun closeDrawer() {
        mDrawerLayout.closeDrawer(Gravity.LEFT)
    }

    private fun changeTabView(tab: TabLayout.Tab?, textSize: Float, isSelected: Boolean) {
        val view: View? = tab?.customView
        val textView: TextView? = view?.findViewById(R.id.tv_tab_title)
        textView?.textSize = textSize
        if (isSelected) {
            textView?.setTextColor(resources.getColor(android.R.color.black))
            val width = textView?.measuredWidth
            Log.e("", "width = $width")
        } else {
            textView?.setTextColor(Color.parseColor("#ff959698"))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_main_search -> {
                gotoSearchActivity()
                overridePendingTransition(0, 0)
            }
            R.id.tv_nav_username -> {
                loggedIn = isCookieNotEmpty(mContext)
                if (!loggedIn) {
                    gotoLoginActivity()
                    closeDrawer()
                }
            }
        }
    }

    private fun gotoSearchActivity() {
    }

    private fun gotoLoginActivity() {
        ARHelper.routerTo(ARHelper.PathUser.LOGIN_ACTIVITY_PATH)

    }

    private var lastTime: Long = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val now = System.currentTimeMillis()
            if (now - lastTime > 1000) {
                Toast.makeText(mContext, "再按一次,推出应用", Toast.LENGTH_SHORT).show()
                lastTime = now
                return false
            }
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
