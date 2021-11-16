package com.jay.mvp.v2.base.rx

import com.jay.mvp.v2.base.BaseMvpPresenter
import com.jay.mvp.v2.contract.IMvpView
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


open class BaseRxMvpPresenter<out V : IMvpView<BaseRxMvpPresenter<V>>> : BaseMvpPresenter<V>() {

    private val mCompositeDisposable by lazy { CompositeDisposable() }

    fun <M> onSubscribe(observable: Observable<M>, subscriber: DisposableObserver<M>) {
        mCompositeDisposable.add(
            observable
                .compose(threadObservableTransformer<M>())
                .subscribeWith(subscriber)
        )
    }


    private fun <M> threadObservableTransformer(): (upstream: Observable<M>) -> ObservableSource<M> =
        {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }


    private fun <M> threadFlowAbleTransformer(): (Flowable<M>) -> Flowable<M> =
        {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }


    override fun detachView() {
        onUnsubscribe()
        super.detachView()
    }

    private fun onUnsubscribe() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

}

