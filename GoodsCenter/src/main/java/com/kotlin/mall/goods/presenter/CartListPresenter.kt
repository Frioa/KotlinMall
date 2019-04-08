package com.kotlin.mall.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.service.CartService
import com.kotlin.mall.goods.presenter.view.CartListView

import com.kotlin.mall.goods.service.CategoryService
import javax.inject.Inject


class CartListPresenter @Inject constructor():BasePresenter<CartListView>() {
    @Inject
    lateinit var cartService:CartService

    fun getCategory(){
        if (!checkNetWork()){
            return
        }

        mView.showLoading() //显示加载
        cartService.getCartList()
                .execute(object :BaseSubscriber<MutableList<CartGoods>?>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: MutableList<CartGoods>?) {     // 重写 noNext 方法
                        super.onNext(t)
                        mView.onGetCartListResult(t)

                    }
                },lifecycleProvider)
    }


    fun deleteCartList(list:List<Int>){
        if (!checkNetWork()){
            return
        }

        mView.showLoading() //显示加载
        cartService.deleteCartList(list)
                .execute(object :BaseSubscriber<Boolean>(mView){//execute 扩展方法，可以服用方法
                override fun onNext(t: Boolean) {     // 重写 noNext 方法
                    super.onNext(t)
                    mView.onDeleteCartListResult(t)

                }
                },lifecycleProvider)
    }

    //结算
    fun submitCartList(list:MutableList<CartGoods>, totalPrice: Long){
        if (!checkNetWork()){
            return
        }
        mView.showLoading() //显示加载
        cartService.submitCart(list,totalPrice)
                .execute(object :BaseSubscriber<Int>(mView){//execute 扩展方法，可以服用方法
                override fun onNext(t: Int) {     // 重写 noNext 方法
                    super.onNext(t)
                    mView.onSubmitCartListResult(t)
                }
                },lifecycleProvider)
    }

}