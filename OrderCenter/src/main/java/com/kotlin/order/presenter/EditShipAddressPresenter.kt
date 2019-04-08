package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.presenter.view.EditShipAddressView
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.service.ShipAddressService
import javax.inject.Inject

class EditShipAddressPresenter @Inject constructor():BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var shipAddressService:ShipAddressService

    fun addShipAddress(shipUserName: String, shipUserMobile:String, shipAddress:String){
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        shipAddressService.addShipAddress(shipUserName,shipUserMobile,shipAddress).execute(object :BaseSubscriber<Boolean>(mView){
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onAddShipAddressResult(t)
            }
        },lifecycleProvider)
    }


    fun editShipAddress(address:ShipAddress){
        if (!checkNetWork()){
            return
        }
        mView.showLoading()
        shipAddressService.editShipAddress(address).execute(object :BaseSubscriber<Boolean>(mView){
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onEditShipAddressResult(t)
            }
        },lifecycleProvider)
    }

}