package com.kotlin.paysdk.service


import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.paysdk.data.protocol.GetPaySignReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface PayService {
    /*
     获取支付宝签名
  */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<String>

    /*
        刷新订单状态已支付
     */
    fun payOrder(orderId: Int): Observable<Boolean>
}