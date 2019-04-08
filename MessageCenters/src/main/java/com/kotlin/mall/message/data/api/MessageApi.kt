package com.kotlin.mall.message.data.api

import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.mall.message.data.protocol.Message
import retrofit2.http.POST
import rx.Observable


interface MessageApi {
    /*
        消息列表的获取
     */
    @POST("msg/getList")
    fun getMessageList():
            Observable<BaseResp<MutableList<Message>?>>
}