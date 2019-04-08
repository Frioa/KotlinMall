package com.kotlin.base.rx

import com.kotlin.base.persenter.view.BaseView
import rx.Subscriber

open class BaseSubscriber<T>(val baseView:BaseView): Subscriber<T>() {
    override fun onNext(t: T) {
        baseView.hideLoading()
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
        baseView.hideLoading()
        if (e is BaseException){
            baseView.onError(e.msg)
        }
    }

}