package com.kotlin.user.injection.component

import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.PerComponentActivityScope
import com.kotlin.user.injection.module.UploadModule
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.ui.activity.*
import dagger.Component

@PerComponentActivityScope
@Component(dependencies = [ActivityComponent::class],modules = [UserModule::class, UploadModule::class])
interface UserComponent {
    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ForgetActivity)
    fun inject(activity: ResetPwdActivity)
    fun inject(activity: UserInfoActivity)
}