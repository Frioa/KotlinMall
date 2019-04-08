package com.kotlin.user.presenter

import android.util.Log
import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.presenter.view.LoginView
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.UserService
import javax.inject.Inject


class LoginPresenter @Inject constructor():BasePresenter<LoginView>() {
    @Inject
    lateinit var userService:UserService

    fun login(mobile:String, pwd:String, pushId:String){
        /*
            业务逻辑
        */
         //初始化
        if (!checkNetWork()){
            return
        }

        mView.showLoading() //显示加载
        Log.d("pushId = ", "push $pushId")
        userService.login(mobile, pwd, pushId)
                .execute(object :BaseSubscriber<UserInfo>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: UserInfo) {     // 重写 noNext 方法
                        super.onNext(t)
                        mView.onLoginResult(t)

                    }
                },lifecycleProvider)
    }

}