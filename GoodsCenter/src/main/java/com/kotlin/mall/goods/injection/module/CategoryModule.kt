package com.kotlin.mall.goods.injection.module

import com.kotlin.mall.goods.service.CategoryService
import com.kotlin.mall.goods.service.imp.CategoryServiceImpl

import dagger.Module
import dagger.Provides

@Module
class CategoryModule {
    @Provides
    fun providesCategoryService(service: CategoryServiceImpl):CategoryService{
        return service
    }
}