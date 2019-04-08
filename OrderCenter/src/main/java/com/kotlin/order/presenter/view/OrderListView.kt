package com.kotlin.order.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.order.data.protocol.Order

interface OrderListView:BaseView {
    fun onGetOrderListResult(result: MutableList<Order>?)
    fun onConfirmOrderResult(result: Boolean)
    fun onCanceOrderResult(result: Boolean)
}