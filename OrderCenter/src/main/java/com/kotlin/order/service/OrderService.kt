package com.kotlin.order.service

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.order.data.api.OrderApi
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.data.protocol.SubmitOrderReq
import rx.Observable

interface OrderService {
    /*
        根据 ID 查询订单
     */
    fun getOrderById(orderId : Int):Observable<Order>
    /*
       提交订单
    */
    fun submitOrder(order: Order): Observable<Boolean>
    /*
        根据状态查询订单列表
     */
    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>
    /*
        取消订单
     */
    fun cancelOrder(orderId:Int): Observable<Boolean>
    /*
        确认订单
     */
    fun confirmOrder(orderId: Int):Observable<Boolean>
}