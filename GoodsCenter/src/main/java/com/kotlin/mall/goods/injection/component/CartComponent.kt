package com.kotlin.mall.goods.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.goods.ui.fragment.CartFragment
import com.kotlin.mall.goods.injection.module.CartModule
import dagger.Component

@PerComponentActivityScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CartModule::class))
interface CartComponent {
    fun inject(fragment: CartFragment)
}