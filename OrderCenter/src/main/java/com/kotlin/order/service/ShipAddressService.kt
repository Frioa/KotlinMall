package com.kotlin.order.service

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.order.data.api.ShipAddressApi
import com.kotlin.order.data.protocol.DeleteShipAddressReq
import com.kotlin.order.data.protocol.ShipAddress
import rx.Observable

interface ShipAddressService {
    /*
        添加收货地址
     */
    fun addShipAddress(shipUserName : String,shipUserMobile:String, shipAddress:String):Observable<Boolean>
    /*
        获取收货地址列表
     */
    fun getShipAddressList():Observable<MutableList<ShipAddress>?>
    /*
         修改收货地址
     */
    fun editShipAddress(address:ShipAddress): Observable<Boolean>
    /*
        删除收货地址
     */
    fun deleteShipAddress(id: Int): Observable<Boolean>
}