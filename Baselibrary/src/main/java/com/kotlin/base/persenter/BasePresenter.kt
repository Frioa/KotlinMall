package com.kotlin.base.persenter

import android.content.Context
import com.kotlin.base.persenter.view.BaseView
import com.kotlin.base.utils.NetWorkUtils
import com.trello.rxlifecycle.LifecycleProvider
import javax.inject.Inject

open class BasePresenter<T:BaseView> {//持有 BaseView 的引用
    lateinit var mView:T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
    @Inject
    lateinit var context: Context

    fun checkNetWork():Boolean{
        if (NetWorkUtils.isNetWorkAvailable(context)){
            return true
        }
        mView.onError("网络不可用")
        return false
    }


    fun checkWifi() = NetWorkUtils.isWifiConnected(context)
}

