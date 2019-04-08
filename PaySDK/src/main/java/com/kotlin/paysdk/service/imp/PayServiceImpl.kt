package com.kotlin.paysdk.service.imp

import com.kotlin.base.ext.convert
import com.kotlin.base.ext.convertBoolean

import com.kotlin.paysdk.data.repository.PayRepository
import com.kotlin.paysdk.service.PayService
import rx.Observable
import javax.inject.Inject


class PayServiceImpl @Inject constructor(): PayService {

    @Inject
    lateinit var repository: PayRepository

    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return repository.getPaySign(orderId , totalPrice).convert()
    }

    override fun payOrder(orderId: Int): Observable<Boolean> {
        return repository.payOrder(orderId).convertBoolean()
    }





}