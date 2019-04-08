package com.kotlin.base.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.injection.component.AppComponent
import com.kotlin.base.injection.component.DaggerAppComponent
import com.kotlin.base.injection.module.AppModule
import com.kotlin.base.utils.AppPrefsUtils

open class BaseApplication:Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        initAppInjection()
        context = this

        // ARouter 初始化
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)

        // AndFix 初始化
        AndFixManager.instance.initPatch(this)
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }


    companion object {
        lateinit var  context: Context
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}