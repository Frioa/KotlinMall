package com.kotlin.mall.message.service.imp

import com.kotlin.base.ext.convert
import com.kotlin.mall.message.data.protocol.Message
import com.kotlin.mall.message.data.repository.MessageRepository
import com.kotlin.mall.message.service.MessageService
import rx.Observable
import javax.inject.Inject
/*
    消息业务层
 */
class MessageSericeImpl @Inject constructor(): MessageService {
    @Inject
    lateinit var repository: MessageRepository
    /*
        获取消息列表
     */
    override fun getMessageList(): Observable<MutableList<Message>?> {
        return repository.getMessageList().convert()
    }
}