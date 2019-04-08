package com.kotlin.mall.goods.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.goods.data.protocol.Goods



interface GoodsListView:BaseView {
    fun onGetGoodsListResult(result:MutableList<Goods>?)

}