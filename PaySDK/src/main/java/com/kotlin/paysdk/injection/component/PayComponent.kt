package com.kotlin.paysdk.injection.component

import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.base.injection.component.ActivityComponent

import com.kotlin.paysdk.injection.module.PayModule
import com.kotlin.paysdk.ui.activity.CashRegisterActivity
import dagger.Component

@PerComponentActivityScope
@Component(dependencies = [ActivityComponent::class], modules = [PayModule::class])
interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}