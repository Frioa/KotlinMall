package com.kotlin.mall.goods.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.data.protocol.Category


interface CartListView:BaseView {
    fun onGetCartListResult(result:MutableList<CartGoods>?)
    fun onDeleteCartListResult(result:Boolean?)
    fun onSubmitCartListResult(result: Int)
}