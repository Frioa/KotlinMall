package com.kotlin.mall.goods.service.imp


import com.kotlin.base.ext.convert
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.mall.goods.data.repository.GoodsRepository

import com.kotlin.mall.goods.service.GoodsService
import rx.Observable
import javax.inject.Inject

class GoodsServiceImpl @Inject constructor(): GoodsService {


    @Inject
    lateinit var repository: GoodsRepository

    override fun getGoodsList(parentId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsList(parentId,pageNo).convert()
    }
    override fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsListByKeyword(keyword,pageNo).convert()
    }

    override fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return repository.getGoodsDetail(goodsId).convert()

    }
}