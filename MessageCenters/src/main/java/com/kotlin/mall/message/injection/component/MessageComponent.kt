package com.kotlin.mall.message.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import dagger.Component
import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.mall.message.injection.module.MessageModule
import com.kotlin.mall.message.ui.fragment.MessageFragment

@PerComponentActivityScope
@Component(dependencies = [ActivityComponent::class], modules = [MessageModule::class])

interface MessageComponent {
    fun inject(fragment: MessageFragment)
}