package com.kotlin.mall.goods.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.goods.ui.fragment.CategoryFragment
import com.kotlin.mall.goods.injection.module.CategoryModule
import dagger.Component

@PerComponentActivityScope
@Component(dependencies = [ActivityComponent::class],modules = [CategoryModule::class])
interface CatetgoryComponent {
    fun inject(fragment: CategoryFragment)

}