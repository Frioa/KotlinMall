package com.kotlin.paysdk.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.paysdk.presenter.view.PayView
import com.kotlin.paysdk.service.PayService

import javax.inject.Inject

class PayPresenter @Inject constructor():BasePresenter<PayView>() {

    @Inject
    lateinit var service: PayService

    fun getPaySign(orderId: Int, totolPrice: Long){
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        service.getPaySign(orderId, totolPrice)
                .execute(object :BaseSubscriber<String>(mView){
            override fun onNext(t: String) {
                super.onNext(t)
                mView.onGetSignResult(t)
            }
        },lifecycleProvider)
    }

    fun payOrder(orderId: Int){
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        service.payOrder(orderId)
                .execute(object :BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        super.onNext(t)
                        mView.onPayOrderResult(t)
                    }
                },lifecycleProvider)
    }

}