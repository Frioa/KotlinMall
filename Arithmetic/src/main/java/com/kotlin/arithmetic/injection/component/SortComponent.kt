package com.kotlin.user.injection.component

import com.kotlin.arithmetic.ui.activity.MainActivity
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.PerComponentActivityScope

import dagger.Component

@PerComponentActivityScope
@Component(dependencies = [ActivityComponent::class])
interface SortComponent {
    fun inject(activity: MainActivity)
}