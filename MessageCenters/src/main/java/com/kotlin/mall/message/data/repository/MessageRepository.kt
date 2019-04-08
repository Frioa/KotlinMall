package com.kotlin.mall.message.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.mall.message.data.api.MessageApi
import com.kotlin.mall.message.data.protocol.Message
import rx.Observable
import javax.inject.Inject

class MessageRepository  @Inject constructor(){
    /*
        获取消息列表
     */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>{
        return RetrofitFactory.instance.create(MessageApi::class.java).getMessageList()
    }
}