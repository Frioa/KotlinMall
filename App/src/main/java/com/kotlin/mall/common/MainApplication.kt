package com.kotlin.mall.common

import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils

class MainApplication:BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)


    }
    companion object {
//        val JPUSH_ID = JPushInterface.getRegistrationID(context)?:""
    }

}