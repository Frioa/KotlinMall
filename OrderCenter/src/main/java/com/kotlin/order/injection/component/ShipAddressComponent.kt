package com.kotlin.order.injection.component

import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.order.data.protocol.EditShipAddressReq
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.injection.module.ShipAddressModule
import com.kotlin.order.ui.activity.OrderConfirmActivity
import com.kotlin.order.ui.activity.ShipAddressActivity
import com.kotlin.order.ui.activity.ShipAddressEditActivity
import dagger.Component

@PerComponentActivityScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(ShipAddressModule::class))
interface ShipAddressComponent {
    fun inject(activity:ShipAddressEditActivity)
    fun inject(activity: ShipAddressActivity)
}