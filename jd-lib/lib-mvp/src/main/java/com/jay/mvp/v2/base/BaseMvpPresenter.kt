package com.jay.mvp.v2.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.UiThread
import com.jay.mvp.v2.contract.IMvpView
import com.jay.mvp.v2.contract.IPresenter

/**
 * @author jaydroid
 * @version 1.0
 * @date 3/2/21
 */
abstract class BaseMvpPresenter<out V : IMvpView<BaseMvpPresenter<V>>> : IPresenter<V> {

    // @UnsafeVariance 忽略协变的检查
    override var view: @UnsafeVariance V? = null

    protected val isViewAttached: Boolean
        @UiThread
        get() = view != null

    @UiThread
    override fun attachView(v: @UnsafeVariance V) {
        view = v
    }

    @UiThread
    override fun detachView() {
        if (view != null) {
            view = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) = Unit
    override fun onSaveInstanceState(outState: Bundle) = Unit
    override fun onViewStateRestored(savedInstanceState: Bundle?) = Unit
    override fun onConfigurationChanged(newConfig: Configuration) = Unit
    override fun onStart() = Unit
    override fun onStop() = Unit
    override fun onResume() = Unit
    override fun onPause() = Unit
    override fun onDestroy() {
        detachView()
    }
}