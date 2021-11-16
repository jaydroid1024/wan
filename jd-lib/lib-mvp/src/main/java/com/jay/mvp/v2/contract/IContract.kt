package com.jay.mvp.v2.contract

import androidx.annotation.UiThread

/**
 * @author jaydroid
 * @version 1.0
 * @date 3/2/21
 */
interface IPresenter<out View : IMvpView<IPresenter<View>>> : IViewLife {

    val view: View?

    @UiThread
    fun attachView(v: @UnsafeVariance View)

    @UiThread
    fun detachView()
}

interface IMvpView<out Presenter : IPresenter<IMvpView<Presenter>>> : IViewLife {
    val presenter: Presenter
}