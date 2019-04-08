package com.kotlin.mall.message.presenter.view

import android.app.Notification
import com.kotlin.base.persenter.view.BaseView
import com.kotlin.mall.message.data.protocol.Message


/*
    消息列表 视图回调
 */
interface MessageView : BaseView {

    //获取消息列表回调
    fun onGetMessageResult(result:MutableList<Message>?)
}
