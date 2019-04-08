package com.kotlin.base.injection.module

import com.kotlin.base.injection.ActivityScope
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>){

    @ActivityScope
    @Provides
    fun providesContext(): LifecycleProvider<*>{
        return lifecycleProvider
    }
}