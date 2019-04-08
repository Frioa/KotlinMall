package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.presenter.view.ForgetPwdView
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.presenter.view.ResetPwdView
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.service.UploadService
import com.kotlin.user.service.UserService
import javax.inject.Inject


class UserInfoPresenter @Inject constructor():BasePresenter<UserInfoView>() {
    @Inject
    lateinit var userService:UserService

    @Inject
    lateinit var uploadService:UploadService
    fun getUploadToken(){
        if (!checkNetWork()) return

        mView.showLoading()
        uploadService.getUploadToken().execute(object :BaseSubscriber<String>(mView){
            override fun onNext(t: String) {
                super.onNext(t)
                mView.onGetUploadTokenResult(t)
            }
        },lifecycleProvider)
    }

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String){
        if (!checkNetWork()) return

        mView.showLoading()
        userService.ediUser(userIcon,userName,userGender,userSign).execute(object :BaseSubscriber<UserInfo>(mView){
            override fun onNext(t: UserInfo) {
                super.onNext(t)
                mView.onEditUserResult(t)
            }
        },lifecycleProvider)
    }
}