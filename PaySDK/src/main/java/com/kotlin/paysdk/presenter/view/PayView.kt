package com.kotlin.paysdk.presenter.view

import com.kotlin.base.persenter.view.BaseView
/*
    订单详情页 视图回调
 */
interface PayView : BaseView {

    fun onGetSignResult(result: String)

    fun onPayOrderResult(result: Boolean)
}
