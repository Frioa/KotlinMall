package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.UserService
import javax.inject.Inject


class RegisterPresenter @Inject constructor():BasePresenter<RegisterView>() {
    @Inject
    lateinit var userService:UserService

    fun register(mobile:String,verifyCode:String, pwd:String){
        /*
            业务逻辑
        */
         //初始化
        if (!checkNetWork()){
            return
        }
        mView.showLoading() //显示加载
        userService.register(mobile, verifyCode, pwd)
                .execute(object :BaseSubscriber<Boolean>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: Boolean) {     // 重写 noNext 方法
                    super.onNext(t)
                    mView.onRegisterResult("注册成功")
                }
                },lifecycleProvider)
    }

}