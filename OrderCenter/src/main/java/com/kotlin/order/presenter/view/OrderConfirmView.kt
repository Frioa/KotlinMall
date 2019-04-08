package com.kotlin.order.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.order.data.protocol.Order

interface OrderConfirmView:BaseView {
    fun onGetOrderById(result:Order)
    fun onSubmitOrderResult(result: Boolean)
}