package com.kotlin.mall.goods.service

import com.kotlin.goods.data.protocol.Goods
import rx.Observable

interface GoodsService{
    fun getGoodsList(parentId:Int, pageNo: Int):Observable<MutableList<Goods>?>
    fun getGoodsListByKeyword(keyword:String, pageNo: Int):Observable<MutableList<Goods>?>
    fun getGoodsDetail(goodsId: Int): Observable<Goods>


}