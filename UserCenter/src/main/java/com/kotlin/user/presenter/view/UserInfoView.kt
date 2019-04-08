package com.kotlin.user.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.user.data.protocol.UserInfo

interface UserInfoView:BaseView {
    fun onGetUploadTokenResult(result:String)
    fun onEditUserResult(result: UserInfo)
}