package com.kotlin.mall.goods.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.goods.ui.fragment.GoodsDetailTabOneFragment
import com.kotlin.mall.goods.injection.module.CartModule
import com.kotlin.mall.goods.ui.activity.GoodsActivity
import com.kotlin.mall.goods.injection.module.GoodsModule
import dagger.Component

@PerComponentActivityScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(GoodsModule::class,CartModule::class))
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
    fun inject(fragment: GoodsDetailTabOneFragment)

}