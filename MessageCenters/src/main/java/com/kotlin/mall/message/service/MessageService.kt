package com.kotlin.mall.message.service

import com.kotlin.mall.message.data.protocol.Message
import rx.Observable


interface MessageService {
    // 获取消息列表
    fun getMessageList(): Observable<MutableList<Message>?>
}