package com.kotlin.mall.message.presenter

import android.app.Notification
import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.mall.message.data.protocol.Message
import com.kotlin.mall.message.service.MessageService
import com.kotlin.mall.message.presenter.view.MessageView
import javax.inject.Inject

/*
    消息列表 Presenter
 */
class MessagePresenter @Inject constructor() : BasePresenter<MessageView>() {

    @Inject
    lateinit var messageService: MessageService

    /*
        获取消息列表
     */
    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        messageService.getMessageList().execute(object : BaseSubscriber<MutableList<Message>?>(mView) {
            override fun onNext(t: MutableList<Message>?) {
                mView.onGetMessageResult(t)
            }
        }, lifecycleProvider)

    }


}
