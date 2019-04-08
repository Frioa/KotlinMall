package com.kotlin.mall.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.mall.goods.presenter.view.GoodsListView
import com.kotlin.mall.goods.service.GoodsService
import javax.inject.Inject


class GoodsListPresenter @Inject constructor():BasePresenter<GoodsListView>() {
    @Inject
    lateinit var goodsService:GoodsService

    fun getGoodsList(categoryuId:Int, pageNo: Int){
        /*
            业务逻辑
        */
         //初始化
        if (!checkNetWork()){
            return
        }

        mView.showLoading() //显示加载
        goodsService.getGoodsList(categoryuId,pageNo)
                .execute(object :BaseSubscriber<MutableList<Goods>?>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: MutableList<Goods>?) {     // 重写 noNext 方法
                        super.onNext(t)
                        mView.onGetGoodsListResult(t)

                    }
                },lifecycleProvider)
    }

    fun getGoodsListByKeyword(keyword:String, pageNo: Int){
        /*
            业务逻辑
        */
        //初始化
        if (!checkNetWork()){
            return
        }

        mView.showLoading() //显示加载
        goodsService.getGoodsListByKeyword(keyword,pageNo)
                .execute(object :BaseSubscriber<MutableList<Goods>?>(mView){//execute 扩展方法，可以服用方法
                override fun onNext(t: MutableList<Goods>?) {     // 重写 noNext 方法
                    super.onNext(t)
                    mView.onGetGoodsListResult(t)

                }
                },lifecycleProvider)
    }

}