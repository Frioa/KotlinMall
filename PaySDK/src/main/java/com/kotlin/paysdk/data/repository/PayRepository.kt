package com.kotlin.paysdk.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.paysdk.data.api.PayApi
import com.kotlin.paysdk.data.protocol.GetPaySignReq
import com.kotlin.paysdk.data.protocol.PayOrderReq
import rx.Observable
import javax.inject.Inject

class PayRepository @Inject constructor() {
    /*
        获取支付宝支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long):Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(PayApi::class.java).getPaySign(GetPaySignReq(orderId, totalPrice))
    }
    /*
        刷新订单状态已支付
     */
    fun payOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(PayApi::class.java).payOrder(PayOrderReq(orderId))
    }
}