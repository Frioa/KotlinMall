package com.kotlin.mall.goods.injection.module

import com.kotlin.mall.goods.service.GoodsService
import com.kotlin.mall.goods.service.imp.GoodsServiceImpl

import dagger.Module
import dagger.Provides

@Module
class GoodsModule {
    @Provides
    fun providesGoodsService(service: GoodsServiceImpl):GoodsService{
        return service
    }
}