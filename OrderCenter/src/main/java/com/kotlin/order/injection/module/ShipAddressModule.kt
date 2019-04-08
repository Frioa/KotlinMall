package com.kotlin.order.injection.module

import com.kotlin.order.service.OrderService
import com.kotlin.order.service.ShipAddressService
import com.kotlin.order.service.impl.OrderServiceImpl
import com.kotlin.order.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ShipAddressModule {

    @Provides
    fun provideShipAddressservice(service: ShipAddressServiceImpl): ShipAddressService {
        return service
    }
}