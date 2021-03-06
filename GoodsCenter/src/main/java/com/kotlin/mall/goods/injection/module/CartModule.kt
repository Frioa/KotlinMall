package com.kotlin.mall.goods.injection.module

import com.kotlin.goods.service.CartService
import com.kotlin.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/*
    购物车Module
 */
@Module
class CartModule {

    @Provides
    fun provideCartservice(cartService: CartServiceImpl): CartService {
        return cartService
    }

}
