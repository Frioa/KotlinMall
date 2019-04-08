package com.kotlin.order.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.data.protocol.ShipAddress

interface ShipAddressView:BaseView {
    fun onGetShipAddressResult(result:MutableList<ShipAddress>?)
    fun onSetDefaultResult(result:Boolean)
    fun onDeleteResult(result:Boolean)

}