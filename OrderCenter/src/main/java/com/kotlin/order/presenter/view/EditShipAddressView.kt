package com.kotlin.order.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.order.data.protocol.Order

interface EditShipAddressView:BaseView {
    fun onAddShipAddressResult(result:Boolean)
    fun onEditShipAddressResult(result:Boolean)

}