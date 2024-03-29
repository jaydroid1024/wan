package com.jay.search

import android.animation.AnimatorSet
import android.animation.FloatEvaluator
import android.animation.IntEvaluator
import android.animation.ObjectAnimator
import android.text.TextUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.jay.base_component.arouter.ARHelper
import com.jay.base_component.base.BaseActivity
import com.jay.base_component.utils.hideKeyboard
import com.jay.base_component.widget.ClearEditText
import com.jay.base_component.widget.ViewMarginWrapper


@Route(path = ARHelper.PathSearch.SEARCH_ACTIVITY_PATH)
class SearchActivity : BaseActivity(), SearchHistoryFragment.OnSearchTextListener {

    private lateinit var searchLayout: RelativeLayout
    private lateinit var editText: ClearEditText
    private lateinit var backImgView: ImageView
    private lateinit var searchTxtView: TextView

    override fun getLayoutResId(): Int {
        return R.layout.biz_search_activity_search
    }

    override fun initView() {
        searchLayout = findViewById(R.id.rl_search_parent)
        backImgView = findViewById(R.id.iv_search_back)
        backImgView.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
        editText = findViewById(R.id.et_search_input)
        startAnimation()

        searchTxtView = findViewById(R.id.tv_search)
        searchTxtView.setOnClickListener {
            hideKeyboard(editText)
            val keyword = editText.text.toString().trim()
            if (TextUtils.isEmpty(keyword)) {
                Toast.makeText(SearchActivity@ this, "请输入搜索内容", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            changeFragment(SearchResultFragment.newInstance(keyword))
        }
        changeFragment(SearchHistoryFragment.newInstance())
    }

    private fun startAnimation() {
        editText.post {
            val viewWrapper = ViewMarginWrapper(editText)
            val leftMarginAnimation =
                initMarginAnimation(viewWrapper, "leftMargin", 0, backImgView.width, 300)
            val rightMarginAnimation =
                initMarginAnimation(viewWrapper, "rightMargin", 0, searchTxtView.width, 300)
            val searchTextAnimation =
                initTranslationAnimation(searchTxtView, "translationX", 300L, 200f, 0f)
            val backImgViewAnimation =
                initTranslationAnimation(backImgView, "translationX", 300L, -100f, 0f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                leftMarginAnimation,
                rightMarginAnimation,
                searchTextAnimation,
                backImgViewAnimation
            )
            animatorSet.duration = 300
            animatorSet.start()
        }
    }


    private fun initMarginAnimation(
        obj: Any,
        propertyName: String,
        startMargin: Int,
        endMargin: Int,
        duration: Long
    ): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofObject(
            obj,
            propertyName,
            IntEvaluator(),
            startMargin,
            endMargin
        )
        objectAnimator.duration = duration
        objectAnimator.interpolator = LinearInterpolator()
        return objectAnimator
    }

    private fun initTranslationAnimation(
        target: Any,
        propertyName: String,
        duration: Long,
        start: Float,
        end: Float
    ): ObjectAnimator {
        val objectAnimator =
            ObjectAnimator.ofObject(target, propertyName, FloatEvaluator(), start, end)
        objectAnimator.duration = duration
        objectAnimator.interpolator = DecelerateInterpolator()
        return objectAnimator
    }


    override fun onSearchText(text: String) {
        editText.setText(text)
        val fragment = SearchResultFragment.newInstance(text)
        changeFragment(fragment)
    }

    private fun changeFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_search_container, fragment)
        transaction.commitAllowingStateLoss()
    }
}
