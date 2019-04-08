package com.kotlin.paysdk.data.api


import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.paysdk.data.protocol.GetPaySignReq
import com.kotlin.paysdk.data.protocol.PayOrderReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface PayApi {
    /*
        获取支付宝签名
     */
    @POST("pay/getPaySign")
    fun getPaySign(@Body req: GetPaySignReq): Observable<BaseResp<String>>

    /*
        刷新订单状态，已支付
     */
    @POST("order/pay")
    fun payOrder(@Body req: PayOrderReq): Observable<BaseResp<String>>
}