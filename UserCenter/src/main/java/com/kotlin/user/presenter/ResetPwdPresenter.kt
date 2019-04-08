package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.ForgetPwdView
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.presenter.view.ResetPwdView
import com.kotlin.user.service.UserService
import javax.inject.Inject


class ResetPwdPresenter @Inject constructor():BasePresenter<ResetPwdView>() {
    @Inject
    lateinit var userService:UserService

    fun resetPwd(mobile:String, pwd:String){
        /*
            业务逻辑
        */
         //初始化
        if (!checkNetWork()){
            return
        }
        mView.showLoading() //显示加载
        userService.resetPwd(mobile, pwd)
                .execute(object :BaseSubscriber<Boolean>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: Boolean) {     // 重写 noNext 方法
                        super.onNext(t)
                        mView.onResetPwdResult("重置密码成功")

                    }
                },lifecycleProvider)
    }

}