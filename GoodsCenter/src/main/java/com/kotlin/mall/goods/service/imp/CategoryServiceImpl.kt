package com.kotlin.mall.goods.service.imp


import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.ext.convert
import com.kotlin.goods.data.protocol.Category
import com.kotlin.mall.goods.data.repository.CatetgoryRepository
import com.kotlin.mall.goods.service.CategoryService
import rx.Observable
import javax.inject.Inject

class CategoryServiceImpl @Inject constructor(): CategoryService{

    @Inject
    lateinit var repository: CatetgoryRepository

    override fun getCategory(parentId:Int):Observable<MutableList<Category>?>{
        return repository.getCategory(parentId).convert()
    }

}